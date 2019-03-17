package by.dropbox.pkt.application;

import by.dropbox.pkt.model.LModule;
import by.dropbox.pkt.web.WindowsManager;
import by.dropbox.pkt.model.SoundTrack;
import by.dropbox.pkt.web.LevelPage;
import by.dropbox.pkt.web.ModulePage;
import net.lightbody.bmp.BrowserMobProxy;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

    private LevelPage levelPage;
    private ModulePage modulePage;
    private WindowsManager windowsManager;

    private int implicitlyWait = 20;
    public WebDriver webDriver;
    public WebDriverWait wait;
    public Proxy seleniumProxy;

    public BrowserMobProxy getProxy() {
        return proxy;
    }

    public BrowserMobProxy proxy;

    public void init() {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
        wait = new WebDriverWait(webDriver, 30);
        levelPage = new LevelPage(webDriver, wait, implicitlyWait);
        modulePage = new ModulePage(webDriver, wait, implicitlyWait);
        windowsManager = new WindowsManager(webDriver, wait, implicitlyWait);
        webDriver.manage().window().maximize();
    }

    public void deinit() {
        webDriver.quit();
        webDriver = null;
    }

    public LevelPage levelPage() {
        return levelPage;
    }

    public ModulePage modulePage() {
        return modulePage;
    }

    public WindowsManager windows() {
        return windowsManager;
    }

    public void tracksToFiles(String levelPath) throws IOException {

        List<LModule> modules = levelPage().all();
        for (LModule module : modules) {
            String modulePath = levelPath + module.getName();
            modulePath = new File(modulePath).getPath();
            levelPage().goTo(module);
            List<SoundTrack> list = modulePage().all();
            for (SoundTrack sTrack : list) {
                String filePath = (modulePath + '/').replaceAll(" ", "") + (sTrack.getName() + '/').replaceAll(" ", "");
                new File(filePath).mkdirs();
                String mainPlayListFName = filePath + "pl_main_" + sTrack.getName() + ".m3u8";
                downloadFile(mainPlayListFName, sTrack.getUrl().toString());
                List<String> url256k = getUrFromFile(mainPlayListFName);
                String mainPlayListFName256 = filePath + "256_main_" + sTrack.getName() + ".m3u8";
                String mainPlayListUrl = url256k.get(url256k.size() - 1);
                downloadFile(mainPlayListFName256, mainPlayListUrl);
                List<String> audioUrls = getUrFromFile(mainPlayListFName256);
                int segment = 1;
                String toFile = (modulePath + '/').replaceAll(" ", "") + sTrack.getName() + ".ts";
                List<String> fromFiles = new ArrayList<>();
                for (String url : audioUrls) {
                    String audioFile = filePath + "s" + segment + "_" + sTrack.getName() + ".ts";
                    downloadFile(audioFile, url);
                    segment++;
                }
                new File(mainPlayListFName).delete();
                new File(mainPlayListFName256).delete();

            }
        }
    }

    private void writeAudio(String toFile, List<String> fromFiles) throws IOException {

        for (String fromFile : fromFiles) {
            addAudio(toFile, fromFile);
        }
    }

    private List<String> getUrFromFile(String mainPlayListFName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(mainPlayListFName)))) {
            String nextLine = reader.readLine();
            List<String> URLs = new ArrayList<>();
            while (nextLine != null) {
                if (nextLine.startsWith("http"))
                    URLs.add(nextLine);
                nextLine = reader.readLine();
            }
            return URLs;
        }
    }

    private void downloadFile(String fn, String urlToDownload) throws IOException {
        URL website = new URL(urlToDownload);
        if (!Files.exists(Paths.get(fn)))
            try (InputStream in = website.openStream()) {
                Files.copy(in, Paths.get(fn), new StandardCopyOption[]{StandardCopyOption.REPLACE_EXISTING});
            }

    }

    public void addAudio(String toFile, String fromFile) {
        try {

            AudioInputStream toStream = AudioSystem.getAudioInputStream(new File(toFile));
            AudioInputStream fromStream = AudioSystem.getAudioInputStream(new File(fromFile));
            AudioInputStream newStream =
                    new AudioInputStream(
                            new SequenceInputStream(toStream, fromStream),
                            toStream.getFormat(),
                            toStream.getFrameLength() + fromStream.getFrameLength());

            AudioSystem.write(newStream,
                    AudioFileFormat.Type.WAVE,
                    new File(toFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

