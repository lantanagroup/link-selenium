package com.nhsn.testcases;

import com.nhsn.BaseClass;
import com.nhsn.helpers.CommonFunctions;
import com.nhsn.pages.HomePage;
import com.nhsn.pages.LoginPage;
import com.nhsn.pages.ReviewPage;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC06_VerifySubmitDateOnReviewPage  extends BaseClass {

    CommonFunctions cFunctions;
    HomePage hPage;
    ReviewPage rPage;

    @Test
    public void verifySubmitDate()  {
        cFunctions = new CommonFunctions(driver);
        hPage = new HomePage(driver);
        rPage = new ReviewPage(driver);
        try
        {
            String note = cFunctions.submitTheReport("praveen_manuel", "Welcome@123", "NHSN Medication Administration");

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