package com.nhsn.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class ViewLineLevelDataPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ViewLineLevelDataPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
    }
    public void clickOnExcludeSelectedButton()
    {
       WebElement excludeButton = driver.findElement(By.xpath("//button[contains(text(),'Exclude Selected')]"));
       excludeButton.click();
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=' Close ']")));
    }

    public void clickOnExcludeButtonOnFirstRow()
    {
        WebElement firstRow = driver.findElement(By.xpath("//h6[text()='Patients']/following-sibling::table[1]//tbody/tr[1]"));
        firstRow.findElement(By.xpath("//button[text()='Exclude']")).click();
        System.out.println("clicked on Exclude Button for First Row");
    }

    public int noOfPatientsOnLiveLevelData()
    {
        return driver.findElements(By.xpath("//h6[text()='Patients']/following-sibling::table[1]//tbody/tr")).size();
    }

    public void verifyExcludeCheckboxOnFirstRow() throws InterruptedException {
        WebElement firstRowCheckbox = driver.findElement(By.xpath("//h6[text()='Patients']/following-sibling::table[1]//tbody/tr["+noOfPatientsOnLiveLevelData()+"]//input[@type='checkbox']"));
        Assert.assertTrue(firstRowCheckbox.isSelected(),"The Check box is not checked as Expected");
        System.out.println("Checkbox is Checked");

    }
    public void verifyExcludeButtonNotDisplayed() throws InterruptedException {
        try {
            WebElement firstRowExcludeButton = driver.findElement(By.xpath("//h6[text()='Patients']/following-sibling::table[1]//tbody/tr[" + noOfPatientsOnLiveLevelData() + "]//button[text()='Exclude']"));
            Assert.fail("Exclude Button is Displayed");
        }
        catch(Exception e)
        {
            System.out.println("Exclude Button is Not Displayed as Expected");


        }

    }

}