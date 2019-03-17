package by.dropbox.pkt.web;

import by.dropbox.pkt.model.LModule;
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

public class LevelPage extends HelperBase{
    @FindBy(css = "h1[data-usually-unique-id]")
    public List<WebElement> allModules;

    public LevelPage(WebDriver webDriver, WebDriverWait wait, int implicitlyWait) {
        super(webDriver, wait, implicitlyWait);
    }

    public void goTo(LModule module){
        webDriver.navigate().to(module.getUrl());
    }

    public List<LModule> all() throws MalformedURLException {
        List<LModule> modules = new ArrayList<>();
        for(WebElement e:allModules){
            modules.add(fromWeb(e));
        }
        return modules;
    }

    public LModule fromWeb(WebElement e) throws MalformedURLException {
        return new LModule().
                withId(e.getAttribute("data-usually-unique-id")).
                withName(e.findElement(By.cssSelector("a[href]")).getText()).
                withUrl(new URL(e.findElement(By.cssSelector("a[href]")).getAttribute("href")));
    }
}
