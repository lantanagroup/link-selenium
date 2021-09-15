package com.nhsn.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.swing.*;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

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
        String currentURL = driver.getCurrentUrl();
        String reportId = currentURL.split("https://dev.nhsnlink.org/review/")[1];
        System.out.println(currentURL.split("https://dev.nhsnlink.org/review/")[1]);
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

        driver.findElement(By.xpath("//button[contains(text(),'Logout')]")).click();
        System.out.println("Clicked On Logout button");
    }

    public void generateReport(String reportName) throws InterruptedException {
        clickOnSelectDropdown();
        selectReport(reportName);
        selectYesterdayDate();
        clickOnGenerateReportButton();
    }

    public void discardReportOnReviewScreen()
    {
        driver.findElement(By.xpath("//button[text()='Discard']")).click();
        System.out.println("Clicked on Discard Button");
        acceptTheAlert();
    }

    public void submitTheReport()
    {
        clickOnSubmitButton();
        acceptTheAlert();
        verifyReportSavedText();
        verifyReportSubmittedText();
       // tooltipValueOnHomeScreen();
    }

    public void clickOnSubmitButton()
    {
        driver.findElement(By.xpath("//button[text()='Submit']")).click();
        System.out.println("Clicked On Submmit button");
    }

    public boolean acceptTheAlert()
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

    public void verifyReportSavedText()
    {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Report saved!']")));
        System.out.println("Report Saved Message displayed on Top Right hand side");
    }

    public void verifyReportSentText()
    {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Report sent!']")));
        System.out.println("Report Sent Message displayed on Top Right hand side");
    }


    public void verifyReportSubmittedText()
    {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Report sent!']")));
        System.out.println("Report Sent Message displayed on Top Right hand side");
    }

    public void verifyStatus(String status)
    {

    }

    public void NavigateToReviewScree()
    {
        driver.findElement(By.xpath("//a[text()='Review']")).click();
        System.out.println("Clicked On Review button");
    }

    public void tooltipValueOnHomeScreen()
    {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='input-group']/ngb-tooltip-window[@role='tooltip']")));
        System.out.println(driver.findElement(By.xpath("//div[@class='input-group']/ngb-tooltip-window[@role='tooltip']")).getText());
    }

    public void tooltipValueOnReviewScreen()
    {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='input-group']/ngb-tooltip-window[@role='tooltip']")));
        System.out.println(driver.findElement(By.xpath("//div[@class='input-group']/ngb-tooltip-window[@role='tooltip']")).getText());
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

    public void clickOnGetHelpOption()
    {
        driver.findElement(By.xpath("//button[@id='userDropDown']")).click();
        System.out.println("Clicked On User dropdown to logout");

        driver.findElement(By.xpath("//a[contains(text(),'Get Help')]")).click();
        System.out.println("Clicked On Get Help Option");
    }

    public void verifyReportStatusOnHomePage()
    {

    }

    public String enterNote(String note)
    {
        Date date = new Date();
        driver.findElement(By.xpath("//textarea[@id='report-notes']")).sendKeys(note+"-"+ date);
        System.out.println("Entered the Note as - "+ note+"-"+ date);
        return note+"-"+ date;
    }

    public void compareNoteText(String expected)
    {
        // Note text web element properties needs to be changed
    }

    public void clickOnSaveButton()
    {
        driver.findElement(By.xpath("//button[text()='Save']")).click();
        System.out.println("Clicked On Save Button");
    }

    public String verifySubmitDateOnHomePage() throws InterruptedException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        //System.out.println(localDateFormat.parse(simpleDateFormat.format(new Date())));
        String result[] = localDateFormat.parse(simpleDateFormat.format(new Date())).toString().split(" ");
        String expectedSubmitDate = result[1]+" "+ result[2]+", "+ result[5];
        System.out.println("Expected Submit Date:   " + expectedSubmitDate );
        Thread.sleep(3000);;
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='report-submit-date']")));
        String actualSubMitDate = driver.findElement(By.xpath("//div[@id='report-submit-date']")).getText();
        System.out.println("Actual Submit Date:   " + actualSubMitDate);
        Assert.assertEquals(actualSubMitDate, expectedSubmitDate, "Submit Date on Home Page is not showing correct");

        return (Instant.now().toString().split("T")[0]);
    }

    public String toolTipOfSubmittedDateOnHomePage()
    {
        WebElement submittedDateElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='report-submit-date']")));
        Actions action = new Actions(driver);
        action.moveToElement(submittedDateElement).build().perform();
        WebElement tooltipElement = driver.findElement(By.xpath("//ngb-tooltip-window[@role='tooltip']"));
        String toolTipTextHomePage = tooltipElement.getText();
        System.out.println("Tooltip Text on HomePage is -" + tooltipElement.getText());
        return toolTipTextHomePage;
    }
    public void verifySubmitButtonIsDisabled()
    {

    }

    public void verifyDiscardButtonIsDisabled()
    {

    }

}
