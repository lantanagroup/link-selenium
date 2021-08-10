package com.nhsn.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver)
    {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    public void clickOnSelectDropdown() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.xpath("//button[@id='measureEvalDropDown']")).click();
        System.out.println("Clicked on Select Dropdown");
    }

    public void selectReport(String reportName)
    {
        driver.findElement(By.xpath("//button[text()='"+reportName+" ']")).click();
        System.out.println("Clicked on the report - "+ reportName);

    }

    public void selectYesterdayDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        WebElement dateElement = driver.findElement(By.xpath("//input[@name='dp']"));
        dateElement.clear();
        dateElement.sendKeys(dateFormat.format(cal.getTime()));
    }

    public void clickOnGenerateReportButton()
    {
        driver.findElement(By.xpath("//button[text()='Generate Report']")).click();
        System.out.println("Clicked On Generate Report Button");
        isAlertPresent();
    }

    public String getReportId(String reportName)
    {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Report Id:')]")));
        WebElement reportIdElement = driver.findElement(By.xpath("//p[contains(text(),'Report Id:')]"));
        String reportId = reportIdElement.getText().split(":")[1].trim();
        System.out.println("Report id for Report Name - "+reportName+"- "+ reportId);
        return reportId;
    }

    public boolean isAlertPresent()
    {
        try
        {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
            System.out.println("Accepted the Alert");
            return true;
        }
        catch (Exception Ex)
        {
            return false;
        }
    }
    public void logout()
    {
        driver.findElement(By.xpath("//button[@id='userDropDown']")).click();
        System.out.println("Clicked On User dropdown to logout");

        driver.findElement(By.xpath("//button[text()='Logout']")).click();
        System.out.println("Clicked On Logout button");
    }

    public void generateReport(String reportName) throws InterruptedException {
        clickOnSelectDropdown();
        selectReport(reportName);
        selectYesterdayDate();
        clickOnGenerateReportButton();
        getReportId(reportName);
        logout();
    }

    public void countNoOfReports(int count) throws InterruptedException {
        clickOnSelectDropdown();
        Thread.sleep(1000);
        List<WebElement>  countOfReports = driver.findElements(By.xpath("//div[@aria-labelledby='measureEvalDropDown']//div"));
        System.out.println(countOfReports.size());
        Assert.assertEquals(countOfReports.size() , count,  "No Of Reports are not matching");
    }

    public void validateTheNamesOfReports(List<WebElement> reportNames) {

        List<WebElement> Reports = driver.findElements(By.xpath("//div[@aria-labelledby='measureEvalDropDown']//div"));
        List ActualReportNames = new ArrayList();
        for(int i=0;i<Reports.size(); i++)
        {
            ActualReportNames.add(Reports.get(i).getText());
        }

//        for(int i = 0; i < ActualReportNames.size(); i++) {
//            System.out.println(ActualReportNames.get(i).toString());
//        }

        Assert.assertTrue(ActualReportNames.equals(reportNames), "Report Names are not matching");
        System.out.println("Report Names are Matching as expected");
    }

    public void clickOnReviewTab()
    {
        driver.findElement(By.xpath("//a[text()='Review']")).click();
        System.out.println("Clicked on the Review Tab");
    }
}
