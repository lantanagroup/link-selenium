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
2. Click on Get Help Option under Top right hand side
3. Navigate To Help Page
4. Verify the Help Text
 */
public class TC02_GetHelp_Test extends BaseClass
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
            lPage.loginToNHSNLinkApp(NHSN_USERNAME, NHSN_PASSWORD);
            hPage.clickOnGetHelpOption();
            hpPage.navigateToHelpPage();
            hpPage.verifyTextOnHelpPage();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }
    }







}
