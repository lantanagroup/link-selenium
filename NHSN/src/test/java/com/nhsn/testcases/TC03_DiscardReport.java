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
3. Click on Discard Button
4. Verify Review Table exists
5. Generate Report
6. Submit the Report
7. Click on Discard Button
8. Verify Review Table exists
 */
public class TC03_DiscardReport extends BaseClass {

    LoginPage lPage;
    HomePage hPage;
    JavascriptExecutor jse ;
    ReviewPage rPage;

    @Test
    public void discardReport()  {
        lPage = new LoginPage(driver);
        hPage = new HomePage(driver);
        jse = (JavascriptExecutor)driver;
        rPage = new ReviewPage(driver);
        try
        {
            String reportName = "Test COVID Minimal";
            lPage.loginToNHSNLinkApp(NHSN_USERNAME, NHSN_PASSWORD);
            hPage.generateReport(reportName);
            hPage.discardReportOnReviewScreen();
            rPage.verifyReviewTableExists();
            rPage.clickOnGenerateButton();
            hPage.generateReport(reportName);
            hPage.submitTheReport(false, reportName,"0");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }

    }


}
