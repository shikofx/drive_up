package by.dropbox.pkt.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class WindowsManager extends HelperBase {


    public WindowsManager(WebDriver webDriver, WebDriverWait wait, int implicitlyWait) {
        super(webDriver, wait, implicitlyWait);
    }

    public void goTo(URL url){
        webDriver.navigate().to(url);
    }
}
