package com.nhsn.testcases;

import com.nhsn.BaseClass;
import com.nhsn.helpers.CommonFunctions;
import com.nhsn.pages.HomePage;
import com.nhsn.pages.LoginPage;
import com.nhsn.pages.ReviewPage;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
1. Log in to NHSN Application
2. Submit the Report
3. Verify Submit Date on Home Page
4. Mouse Hover on Submit Date and Get the Tool tip value
5. Enter Submit Date on Review Page
6. Mouse Hover on Submit Date and Get the Tool tip value
7. Compare Tooltip values of Submit Date for both Home page and Review Page
 */

public class TC06_VerifySubmitDateOnReviewPage  extends BaseClass {

    CommonFunctions cFunctions;
    HomePage hPage;
    ReviewPage rPage;
    LoginPage lPage;

    @Test
    public void verifySubmitDate()  {
        cFunctions = new CommonFunctions(driver);
        hPage = new HomePage(driver);
        rPage = new ReviewPage(driver);
        lPage = new LoginPage(driver);
        try
        {
            lPage.loginToNHSNLinkApp(NHSN_USERNAME, NHSN_PASSWORD);
            hPage.generateReport("NHSN Medication Administration");
            String note =  hPage.enterNote("Test Automation");
            hPage.submitTheReport(true,"NHSN Medication Administration","0");
            String submittedDate = hPage.verifySubmitDateOnHomePage();
            String toolTip_HomePagePageSubmittedDate = hPage.toolTipOfSubmittedDateOnHomePage();
            hPage.clickOnReviewTab();
            rPage.enterSubmitDateOnReviewPage(submittedDate);
            rPage.verifySubmittedReportOnReviewPage(note);
            String toolTip_ReviewPageSubmittedDate = rPage.getTooltipOfSubmittedDate(note);
            Assert.assertEquals(toolTip_ReviewPageSubmittedDate, toolTip_HomePagePageSubmittedDate, "Tooltip for submit date is showing wrong");
            System.out.println("Tooltip Text for submitted date on both Homepage and Review page showing as expected");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }
    }
}