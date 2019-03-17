package by.dropbox.pkt.web;

import by.dropbox.pkt.model.SoundTrack;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FilenameUtils.normalize;

public class ModulePage extends HelperBase {
    @FindBy(css = "div.collection-file-preview-embed")
    public List<WebElement> allTracks;

    @FindBy(css = "iframe.pad-lightbox-iframe")
    public WebElement audioPlayer;

    @FindBy(css = "button.lightbox-close span span")
    public WebElement playerCloseButton;

    public ModulePage(WebDriver webDriver, WebDriverWait wait, int implicitlyWait) {
        super(webDriver, wait, implicitlyWait);
    }

    public List<SoundTrack> all() throws MalformedURLException {
        List<SoundTrack> allSoundTracks = new ArrayList<>();
        for(WebElement e:allTracks){
            allSoundTracks.add(fromWeb(e));
        }
        return allSoundTracks;
    }

    public SoundTrack fromWeb(WebElement e) throws MalformedURLException {
        return new SoundTrack().
                withId(e.findElement(By.cssSelector("div.collection-item-description div[data-zone-id]")).getAttribute("data-zone-id")).
                withName(normalize(e.findElement(By.cssSelector("div.collection-item-description .shared-link-title")).getText())).
                withSourceUrl(getUrl(e));
    }

    private URL getUrl(WebElement e) throws MalformedURLException {
        e.findElement(By.cssSelector("span.image-downloads-disabled")).click();
        webDriver.switchTo().frame(audioPlayer);
        String urlString = null;
        if(isElementPresent(webDriver.findElement(By.cssSelector("source")),20))
         urlString = webDriver.findElement(By.cssSelector("source")).getAttribute("src");
        webDriver.switchTo().defaultContent();
        playerCloseButton.click();
        return new URL(urlString);
    }
}
