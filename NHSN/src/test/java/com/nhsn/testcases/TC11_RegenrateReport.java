package com.nhsn.testcases;

import com.nhsn.BaseClass;
import com.nhsn.helpers.CommonFunctions;
import com.nhsn.pages.HomePage;
import com.nhsn.pages.LoginPage;
import com.nhsn.pages.ReviewPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLOutput;

/*
1. Log in to NHSN Application
2. Submit the Report
3. Verify Submit Date on Home Page
4. Mouse Hover on Submit Date and Get the Tool tip value
5. Enter Submit Date on Review Page
6. Mouse Hover on Submit Date and Get the Tool tip value
7. Compare Tooltip values of Submit Date for both Home page and Review Page
 */

public class TC11_RegenrateReport extends BaseClass {

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
            String reportName = "Test COVID Minimal";
            lPage.loginToNHSNLinkApp(NHSN_USERNAME, NHSN_PASSWORD);
            hPage.generateReport(reportName,"2021-05-05");
            String note =  hPage.enterNote("Test Automation");
            hPage.submitTheReport(true,"NHSN Medication Administration","0");
            String previousVersion = hPage.getMinorVersion();
            hPage.clickOnRegenrateButton();
            hPage.verifyReportRegeneratedText();
            Assert.assertTrue(hPage.verifySubmitButtonIsEnabled(), "Submit Button is not enabled after regenerated the report");
            String currentVersion = hPage.getMinorVersion();
            Assert.assertEquals(Integer.parseInt(previousVersion)+1, Integer.parseInt(currentVersion), "Minor version is not incrementing");
            System.out.println("Minor version is incrementing as expected");
           }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }
    }
}