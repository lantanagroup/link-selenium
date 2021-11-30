package com.nhsn.testcases;

import com.nhsn.BaseClass;
import com.nhsn.pages.HomePage;
import com.nhsn.pages.LoginPage;
import com.nhsn.pages.ReviewPage;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
1. Log in to NHSN Application
2. Generate Report
3.Enter the Notes text which is greater than 50 characters length
4.Click on Save button
5.Verify report is saved with updated notes text
6.Click on Review tab
7.Select the filters to get the generated report
8.Verify Note column contains first 50 characters of Note
9.Mouse hover on the sticky note and get the full note value
10.Compare both notes which is on Home page and review page


 */
public class TC07_VerifyTruncatedNoteOnReviewPage extends BaseClass {

    LoginPage lPage;
    HomePage hPage;
    JavascriptExecutor jse ;
    ReviewPage rPage;

    @Test
    public void verifyNote()  {
        lPage = new LoginPage(driver);
        hPage = new HomePage(driver);
        jse = (JavascriptExecutor)driver;
        rPage = new ReviewPage(driver);
        try
        {
            String reportName = "NHSN Medication Administration";
            lPage.loginToNHSNLinkApp(NHSN_USERNAME, NHSN_PASSWORD);
            hPage.generateReport(reportName,"2021-05-05");
            String expected_NoteText = hPage.enterNote("Automation Notes Automation Notes Automation Notes");
            String first50CharOfString = expected_NoteText.substring(0, Math.min(expected_NoteText.length(), 50));
            hPage.clickOnSaveButton();
            hPage.verifyReportSavedText();
            hPage.submitTheReport(false,"NHSN Medication Administration","0");
            String submittedDate = hPage.verifySubmitDateOnHomePage();
            hPage.clickOnReviewTab();
            rPage.enterSubmitDateOnReviewPage(submittedDate);
            rPage.verifyNoteTooltipOnReviewPage(first50CharOfString+"...",expected_NoteText);
            Thread.sleep(10000);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }

    }


}