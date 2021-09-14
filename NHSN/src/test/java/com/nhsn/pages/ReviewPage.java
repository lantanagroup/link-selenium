package com.nhsn.pages;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ReviewPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ReviewPage(WebDriver driver)
    {

        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }


    public void verifyReviewTableExists()
    {
        Assert.assertTrue( driver.findElement(By.xpath("//nandina-review/table")).isDisplayed() , "Review Table exists");
        System.out.println("Review Table exists on Review Page");
    }

    public void validateColumnNamesOnReviewTable()
    {
        List<WebElement> noOfColumnsOnReviewTable = driver.findElements(By.xpath("//nandina-review/table//tr[1]/th"));
        Assert.assertEquals( noOfColumnsOnReviewTable.size(), 7,  "No of Columns in the Review Table is not matching");

        List expectedColumnNames = new ArrayList();
        expectedColumnNames.add("Measure");
        expectedColumnNames.add("Reporting Period");
        expectedColumnNames.add("Status");
        expectedColumnNames.add("Submitted Date");
        expectedColumnNames.add("Submitter");
        expectedColumnNames.add("Notes");
        expectedColumnNames.add(" ");

        List ActualColumnNames = new ArrayList();
        for(int i=0;i<noOfColumnsOnReviewTable.size(); i++)
        {
            ActualColumnNames.add(noOfColumnsOnReviewTable.get(i).getText());
        }

        Assert.assertTrue(ActualColumnNames.equals(expectedColumnNames),"Column Names are not matching on Review Table");
        System.out.println("Column Names are matching as expected on Review Table");

    }

    public void clickOnGenerateButton()
    {
        driver.findElement(By.xpath("//a[text()='Generate']")).click();
        System.out.println("Clicked on Generate Button");
    }

    public void enterSubmitDateOnReviewPage(String date) throws InterruptedException {

        WebElement submitDateTextbox = driver.findElement(By.xpath("//input[@name='dp']"));
        char dateArray[] = date.toCharArray();
        for(int i=0;i< dateArray.length;i++)
        {
            submitDateTextbox.sendKeys(Character.toString(dateArray[i]));
            Thread.sleep(1000);
        }
        System.out.println("Entered the date as -"+ date);
    }

    public void verifySubmittedReportOnReviewPage( String note)  {

        WebElement reportNote = driver.findElement(By.xpath("//table//span[text()='"+note+"']"));
        Assert.assertTrue(reportNote.isDisplayed(), "Recently Submitted Report is not displayed on the review Page");
        System.out.println(note + " is displayed as expected i.e. Recently Submitted Report is displayed on the review Page");
    }

    public String getTooltipOfSubmittedDate(String note) throws InterruptedException {

        WebElement submittedDateElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table//span[text()='"+note+"']/ancestor::tr/td[4]/span")));
        Actions action = new Actions(driver);
        action.moveToElement(submittedDateElement).build().perform();
        WebElement tooltipElement = driver.findElement(By.xpath("//ngb-tooltip-window[@role='tooltip']"));
        String toolTipText_ReviewPage = tooltipElement.getText();
        System.out.println("Tooltip Text on Review Page is -" + tooltipElement.getText());
        return toolTipText_ReviewPage;
    }



}
