package com.nhsn.testcases;

import com.nhsn.BaseClass;
import com.nhsn.pages.HelpPage;
import com.nhsn.pages.HomePage;
import com.nhsn.pages.LoginPage;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

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
public class TC08_SaveButton_Test extends BaseClass
{
    LoginPage lPage;
    HomePage hPage;
    JavascriptExecutor jse ;
    HelpPage hpPage;
    @Test
    public void verifyGetHelpLink()
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
            Assert.assertFalse(hPage.verifySaveButtonIsEnabled());
            System.out.println("Save Button is Disabled as expected");
            hPage.enterNote("Test Automation");
            Assert.assertTrue(hPage.verifySaveButtonIsEnabled());
            System.out.println("Save Button is Enabled as expected");
            hPage.clickOnSaveButton();
            hPage.verifyReportSavedText();
            Assert.assertFalse(hPage.verifySaveButtonIsEnabled());
            System.out.println("Save Button is Disabled as expected");
            hPage.enterNote("Test Automation");
            hPage.submitTheReport(true, reportName,"0");
            hPage.generateReport(reportName);
            hPage.submitTheReport(false, reportName,"0");
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
