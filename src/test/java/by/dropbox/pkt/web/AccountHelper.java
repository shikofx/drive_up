package by.dropbox.pkt.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountHelper extends HelperBase {
    @FindBy(css = "div.account-action-buttons button")
    public WebElement loginPageButton;

    @FindBy(css = "[name=login_email]~label")
    public WebElement loginField;

    @FindBy(css = "[name=login_password]~label")
    public WebElement passwordField;

    @FindBy(css = "button.login-button")
    public WebElement loginButton;

    AccountHelper(WebDriver webDriver, WebDriverWait wait, int implicitlyWait) {
        super(webDriver, wait, implicitlyWait);
    }


    public void loginAs(String username, String password) {
        loginPageButton.click();
        if (isElementPresent(loginField, 10)) {
            inputText(loginField, username);
        }
        if (isElementPresent(passwordField, 10)) {
            inputText(passwordField, password);
        }
        loginButton.click();
    }
}
