package com.nhsn.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
        cal.add(Calendar.DATE, -10);
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
        clickOnGenerateButton();
        clickOnSelectDropdown();
        selectReport(reportName);
        selectYesterdayDate();
        clickOnGenerateReportButton();
    }

    public void discardReportOnReviewScreen()
    {
        driver.findElement(By.xpath("//button[text()=' Discard ']")).click();
        System.out.println("Clicked on Discard Button");
        acceptTheAlert();
    }

    public void clickOnGenerateButton()
    {
        driver.findElement(By.xpath("//a[text()='Generate']")).click();
        System.out.println("Clicked on Generate Button on Home Page");
    }

    public void submitTheReport(boolean save, String reportName, String population) throws InterruptedException {
        clickOnSubmitButton();
        acceptTheAlert();
        if(save)
        {
            acceptTheAlert();
            verifyReportSavedText();  //Report Saved text does not show when there is nothing to save it
        }
        verifyReportSentText();
        verifyReportStatusOnHomePage("Submitted");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(verifyNotesTextAreaisEnabled(),"Notes Text Area is not disabled");
        System.out.println("Notes Text Area is disabled as expected");
        if(reportName.equalsIgnoreCase("NHSN Medication Administration"))
        {
            softAssert.assertFalse(verifyPopulationTextboxIsEnabled(),"Population Textbox is not disabled");
            System.out.println("Population Textbox  is disabled as expected");

            if(!(population.equals("0")))
            {
                softAssert.assertFalse(verifyReasonForChangeIsEnabled(),"Reason for Change textbox is not disabled");
                System.out.println("Reason for Change Textbox is disabled as expected");
            }
        }
        softAssert.assertAll();
    }

    public void clickOnSubmitButton()
    {
        driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).click();
        System.out.println("Clicked On Submit button");
    }

    public boolean acceptTheAlert()
    {
        try
        {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.accept();
            System.out.println("Accepted the Alert for - "+ alertText);
            return true;
        }
        catch (Exception Ex)
        {
            return false;
        }
    }

    public void verifyReportSavedText() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Report saved!']")));
        System.out.println("Report Saved Message displayed on Top Right hand side");
    }

    public String getNoteText()
    {
       return (driver.findElement(By.xpath("//textarea")).getAttribute("value"));
    }


    public void verifyReportSentText()
    {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Report sent!')]")));
        System.out.println("Report Sent Message displayed on Top Right hand side");
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

    public void verifyReportStatusOnHomePage(String expectedStatus) throws InterruptedException {
        Thread.sleep(3000);
        WebElement reportStatusElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='report-status-version']")));
        String reportStatusWithVersion = reportStatusElement.getAttribute("value");
        String reportStatus = reportStatusWithVersion.split("-")[0].trim();
        Assert.assertEquals(expectedStatus,reportStatus,"Report status is not matching as expected");
    }

    public String enterNote(String note)
    {
        Date date = new Date();
        WebElement noteElement = driver.findElement(By.xpath("//textarea[@id='report-notes']"));
        noteElement.clear();
        noteElement.sendKeys(note+"-"+ date);
        System.out.println("Entered the Note as - "+ note+"-"+ date);
        return note+"-"+ date;
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
    public boolean verifySubmitButtonIsEnabled() throws InterruptedException {
        Thread.sleep(3000);
        return driver.findElement(By.xpath("//button[contains(text(),'Submit')]")).isEnabled();
    }

    public boolean verifyDiscardButtonIsEnabled() throws InterruptedException {
        Thread.sleep(3000);
        return driver.findElement(By.xpath("//button[contains(text(),'Discard')]")).isEnabled();
    }

    public boolean verifySaveButtonIsEnabled()
    {
        return driver.findElement(By.xpath("//button[contains(text(),'Save')]")).isEnabled();
    }

    public boolean verifyNotesTextAreaisEnabled()
    {
        return driver.findElement(By.xpath("//textarea[@placeholder='Notes...']")).isEnabled();
    }

    public boolean verifyPopulationTextboxIsEnabled()
    {
        return driver.findElement(By.xpath("//app-calculated-field/input")).isEnabled();
    }

    public void clickOnViewLineLevelDataButton()
    {
        WebElement viewLineLevelData = driver.findElement(By.xpath("//button[text()='View Line-Level Data']"));
        viewLineLevelData.click();
        System.out.println("Clicked on View Line Level Data Button");
    }

    public void verifyViewLineLevelTableColumns()
    {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='modal-body']/table")));
        System.out.println("View Line Level Data Table exists");
        List<WebElement> table = driver.findElements(By.xpath("//div[@class='modal-body']/table/thead/tr/th"));
        int noOfColumns = table.size();
        Assert.assertEquals(4,noOfColumns, "No Of columns in the View Line Level Data are not matching");
        ArrayList<String> actualColumns = new ArrayList<String>();
        for (WebElement column: table)
        {
            if(!(column.getText().equals("")))
            {
               actualColumns.add(column.getText());
            }
        }
        List<String> expectedColumns = Arrays.asList("Name", "Sex", "DOB"," ");
        Assert.assertTrue(expectedColumns.equals(actualColumns), "View Line Level Table column names are not matching");
    }

    public void verifyDefaultValuesOfInitialPopulationTextbox()
    {
       String defaultValue = driver.findElement(By.xpath("//label[text()='Initial Population']/parent::div//app-calculated-field/input")).getAttribute("min");
       Assert.assertEquals("0", defaultValue, "Default value is not matching for the Initial Population field");
    }

    public void enterReasonForChange(String population)
    {
        WebElement initialPopulationtextbox = driver.findElement(By.xpath("//label[text()='Initial Population']/parent::div//app-calculated-field/input"));
        initialPopulationtextbox.clear();
        if(!(population.equals("0")))
        {
           initialPopulationtextbox.sendKeys(population);
           Assert.assertTrue(driver.findElement(By.xpath("//textarea[@placeholder='Please indicate a reason for the change.']")).isDisplayed());
           System.out.println("Please indicate a reason for the change text is displayed as expected");

           Assert.assertTrue(driver.findElement(By.xpath("//div[text()='This field is required']")).isDisplayed());
           System.out.println("This field is required text is displayed as expected");

            driver.findElement(By.xpath("//textarea[@placeholder='Please indicate a reason for the change.']")).sendKeys("Test Automation");
        }
        else
        {
            try
            {
                Assert.assertFalse(driver.findElement(By.xpath("//textarea[@placeholder='Please indicate a reason for the change.']")).isDisplayed());
            }
            catch(Exception e)
            {
                System.out.println("Please indicate a reason for the change text is not displayed as expected");
            }
        }
    }

    public void validateReasonAfterSave()
    {
        Assert.assertEquals("Test Automation", driver.findElement(By.xpath("//textarea[@placeholder='Please indicate a reason for the change.']")).getAttribute("value"),"Reason After Save is not matching");
        System.out.println("Reason After Save is showing as expected - Test Automation");
    }

    public boolean verifyReasonForChangeIsEnabled()
    {
        return driver.findElement(By.xpath("//textarea[@placeholder='Please indicate a reason for the change.']")).isEnabled();
    }

}
