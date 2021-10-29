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
3. Click on View Line Level Button
4. Verify the View Line Level tale column names
 */
public class TC09_ViewLineLevelData extends BaseClass
{
    LoginPage lPage;
    HomePage hPage;
    JavascriptExecutor jse ;
    HelpPage hpPage;
    @Test
    public void verifyViewLineLevelData()
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
            hPage.clickOnViewLineLevelDataButton();
            hPage.verifyViewLineLevelTableColumns();

        }
        catch(Exception e)
        {
           System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }
    }







}
