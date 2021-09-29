package com.nhsn.testcases;

import com.nhsn.BaseClass;
import com.nhsn.pages.HelpPage;
import com.nhsn.pages.HomePage;
import com.nhsn.pages.LoginPage;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLOutput;

/*
1. Log in to NHSN Application
2. Generate the report
3. Verify Save button is disabled initially
4. Enter the Notes Text
5. verify Save button is enabled
6. Click on Save button and verify Report saved
7. Verify Save button is disabled
8. Enter Notes
9. Click on Submit Button without saving
10. It should prompt to save changes first
11. It should prompt to submit the report if Saved
12. Report Saved Text is displayed on top right side of the page
13. Report Sent text is displayed on top right side of the page
14. Verify Report status is Submitted
 */
public class TC10_InitialPopulation_Reason extends BaseClass
{
    LoginPage lPage;
    HomePage hPage;
    JavascriptExecutor jse ;
    HelpPage hpPage;
    @Test
    public void verifyInitialPopulationReason()
    {
        lPage = new LoginPage(driver);
        hPage = new HomePage(driver);
        hpPage = new HelpPage(driver);
        jse = (JavascriptExecutor)driver;
        try
        {
            String reportName = "NHSN Medication Administration";
            lPage.loginToNHSNLinkApp(NHSN_USERNAME, NHSN_PASSWORD);
            hPage.generateReport(reportName);
            hPage.verifyDefaultValuesOfInitialPopulationTextbox();
            hPage.enterReasonForChange("1");
            hPage.clickOnSaveButton();
            hPage.verifyReportSavedText();
            hPage.validateReasonAfterSave();
            hPage.enterReasonForChange("0");
            hPage.clickOnSaveButton();
            hPage.verifyReportSavedText();
            hPage.enterReasonForChange("1");
            hPage.clickOnSaveButton();
            hPage.verifyReportSavedText();
            Thread.sleep(3000);
            hPage.submitTheReport(false, "NHSN Medication Administration","1");
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
