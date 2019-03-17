package by.dropbox.pkt;

import by.dropbox.pkt.application.ApplicationManager;
import org.testng.annotations.BeforeSuite;

public class TestBase {
    public ApplicationManager app = new ApplicationManager();

    @BeforeSuite
    public void start(){
        app.init();
    }
}
