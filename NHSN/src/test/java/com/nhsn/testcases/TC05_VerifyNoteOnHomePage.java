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
3. Enter Note and Click on Save Button
4. Verify Report Saved Text on top Right hand side of the page
5.
 */
public class TC05_VerifyNoteOnHomePage extends BaseClass {

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
            lPage.loginToNHSNLinkApp(NHSN_USERNAME, NHSN_PASSWORD);
            hPage.generateReport("NHSN Medication Administration");
            String expected_NoteText = hPage.enterNote("Automation Notes");
            hPage.clickOnSaveButton();
            hPage.verifyReportSavedText();
            String actual_NoteText = hPage.getNoteText();
            Assert.assertEquals(actual_NoteText,expected_NoteText, "Note Text is not matching after save");
            Assert.assertFalse(hPage.verifySaveButtonIsEnabled(), "Save Button is not disabled");
        }
        catch(Exception e)
        {
           System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }

    }


}