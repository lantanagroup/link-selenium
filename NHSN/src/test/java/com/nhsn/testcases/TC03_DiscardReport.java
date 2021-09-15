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
            lPage.loginToNHSNLinkApp(NHSN_USERNAME, NHSN_PASSWORD);
            hPage.generateReport("Test COVID Minimal");
            hPage.discardReportOnReviewScreen();
            rPage.verifyReviewTableExists();
            rPage.clickOnGenerateButton();
            hPage.generateReport("Test COVID Minimal");
            hPage.submitTheReport();
            hPage.discardReportOnReviewScreen();
            rPage.verifyReviewTableExists();
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
