package com.nhsn.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private static WebDriver driver;

    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
    }

    public void enterUserId(String userId)
    {
        driver.findElement(By.id("username")).sendKeys(userId);
    }

    public void enterPassword(String password)
    {
        driver.findElement(By.id("password")).sendKeys(password);
    }

    public void clickOnSignIn()
    {
        driver.findElement(By.id("kc-login")).click();
    }

    public void loginToNHSNLinkApp(String userId, String password) throws InterruptedException {
        enterUserId(userId);
        Thread.sleep(1000);
        enterPassword(password);
        Thread.sleep(1000);
        clickOnSignIn();
        Thread.sleep(2000);
        driver.navigate().refresh();
        Thread.sleep(2000);
        driver.navigate().refresh();
    }

}
