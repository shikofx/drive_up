package by.dropbox.pkt;


import by.dropbox.pkt.application.ApplicationProperties;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTest extends TestBase{

    @BeforeClass
    public void setUp() throws MalformedURLException {
        app.windows().goTo(new URL(ApplicationProperties.downloadURL));
    }

    @Test
    public void downloadMP3() throws IOException {
        app.tracksToFiles("files/test/");
    }
}
