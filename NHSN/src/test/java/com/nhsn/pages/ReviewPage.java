package com.nhsn.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class ReviewPage {
    private WebDriver driver;

    public ReviewPage(WebDriver driver)
    {
        this.driver = driver;
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



}
