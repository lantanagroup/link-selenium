package com.nhsn.testcases;

import com.nhsn.BaseClass;
import com.nhsn.pages.HelpPage;
import com.nhsn.pages.HomePage;
import com.nhsn.pages.LoginPage;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

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
            lPage.loginToNHSNLinkApp("praveen_manuel", "Welcome@123");
            hPage.clickOnGetHelpOption();
            hpPage.navigateToHelpPage();
            hpPage.verifyTextOnHelpPage();
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
