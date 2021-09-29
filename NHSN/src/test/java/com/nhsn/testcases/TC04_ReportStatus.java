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
3. Verify the Report Status on Home Page
4. Submit the report without entering the Note - Reviewing
5. Verify the Report Status on Home page again - Submitted
6. Click on Review Tab and navigate to Review Screen
7. Verify the status of the report
 */
public class TC04_ReportStatus extends BaseClass {

    LoginPage lPage;
    HomePage hPage;
    JavascriptExecutor jse ;
    ReviewPage rPage;

    @Test
    public void reportStatus()  {
        lPage = new LoginPage(driver);
        hPage = new HomePage(driver);
        jse = (JavascriptExecutor)driver;
        rPage = new ReviewPage(driver);
        try
        {
            String reportName = "Test COVID Minimal";
            lPage.loginToNHSNLinkApp(NHSN_USERNAME, NHSN_PASSWORD);
            hPage.generateReport(reportName);
            hPage.verifyReportStatusOnHomePage("Reviewing");
            System.out.println("Report Status is showing as Reviewing as expected on Home Page");
            hPage.submitTheReport(false, reportName,"0");
            hPage.verifyReportStatusOnHomePage("Submitted");
            System.out.println("Report Status is showing as Submitted  as expected on Home Page");
            String submitDate = hPage.verifySubmitDateOnHomePage();
            hPage.clickOnReviewTab();
            rPage.enterSubmitDateOnReviewPage(submitDate);
            rPage.verifyReportStatusOnreviewPage();
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
