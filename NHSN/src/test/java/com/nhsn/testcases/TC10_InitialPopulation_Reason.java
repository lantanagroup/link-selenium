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
1.Log in to NHSN Application
2.Generate the report
3.Verify Default values of Initial Population textbox
4.Increase the population value more than minimum value
5.Enter reason for change
6.Click on Save Button
7.Verify Report Saved text on top right side of the page
8.Decrease the Population value back to Minimum value
9.Verify reason for change textbox is not displayed
10.Submit the report after the changes

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
            hPage.generateReport(reportName,"2021-05-05");
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
           System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }
    }

}
