package com.nhsn.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.swing.*;
import java.util.Set;

public class HelpPage {
    private WebDriver driver;
    private WebDriverWait wait;
    String secondWindow = null;

    public HelpPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    public String getSecondWindowId() {
        String currentWindow = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        for (String windowId : handles) {
            if (!(windowId.equalsIgnoreCase(currentWindow))) {
                secondWindow = windowId;
                break;
            }
        }
        return secondWindow;

    }

    public void enterEmail() {
        driver.findElement(By.xpath("//input[@id='user-email']")).sendKeys("praveen.manuel@lantanagroup.com");
        System.out.println("Entered the email");
    }

    public void clickOnNextButton() {
        driver.findElement(By.xpath("//span[text()='Next']")).click();
        System.out.println("Clicked On Next Button");
    }

    public void clickOnLoginWithSingleSignOnButton() {
        driver.findElement(By.xpath("//span[text()='Log in with single sign-on']")).click();
        System.out.println("Clicked On Login with Single Sign-On Button");
    }

    public void clickOnContinueButton()
    {
        driver.findElement(By.xpath("//span[text()='Continue']")).click();
        System.out.println("Clicked On Continue Button");
    }

    public void enterPassword()
    {
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Welcome@123");
        System.out.println("Entered the password as Welcome@123");
    }

    public void clickOnLoginButton()
    {
        driver.findElement(By.xpath("//span[text()='Log in']")).click();
        System.out.println("Clicked On Login Button");
    }

    public void requestsLabelIsDisplayed()
    {
        Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Requests']")).isDisplayed(), true, "Requests Text is not displayed on the Help Page");
    }

    public void helpCenterLabelIsDisplayed()
    {
        Assert.assertEquals(driver.findElement(By.xpath("//span[text()='Help Center']")).isDisplayed(), true, "Help Center Text is not displayed on the Help Page");
    }

    public void navigateToHelpPage() {
        driver.switchTo().window(getSecondWindowId());
        enterEmail();
        clickOnNextButton();
        clickOnLoginWithSingleSignOnButton();
        clickOnContinueButton();
        enterPassword();
        clickOnLoginButton();
    }

    public void verifyTextOnHelpPage()
    {
        requestsLabelIsDisplayed();
        helpCenterLabelIsDisplayed();
    }
}
