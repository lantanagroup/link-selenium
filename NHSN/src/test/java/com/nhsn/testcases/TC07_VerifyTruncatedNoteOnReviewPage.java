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
5. This code needs to be modified when the Save note issue is fixed.
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
            lPage.loginToNHSNLinkApp(NHSN_USERNAME, NHSN_PASSWORD);
            //hPage.generateReport("NHSN Medication Administration");
            //String expected_NoteText = hPage.enterNote("Automation Notes Automation Notes Automation Notes");
            String expected_NoteText="I am going to display only 50 characters of a note. After that I will display a page symbol.";
            String first50CharOfString = expected_NoteText.substring(0, Math.min(expected_NoteText.length(), 50));
           /* hPage.clickOnSaveButton();
            hPage.verifyReportSavedText();
            hPage.submitTheReport();
            String submittedDate = hPage.verifySubmitDateOnHomePage();*/
            hPage.clickOnReviewTab();
            //rPage.enterSubmitDateOnReviewPage(submittedDate);
            rPage.enterSubmitDateOnReviewPage("2021-08-30");
            rPage.verifyNoteTooltipOnReviewPage(first50CharOfString+"...",expected_NoteText);
            Thread.sleep(10000);
        }
        catch(Exception e)
        {
            if(!(sourceRun.equalsIgnoreCase("local")))
            {
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Error in the Test case\"}}");
            }
            System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }

    }


}