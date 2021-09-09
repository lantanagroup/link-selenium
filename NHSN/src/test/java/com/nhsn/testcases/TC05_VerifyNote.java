package com.nhsn.testcases;

import com.nhsn.BaseClass;
import com.nhsn.pages.HomePage;
import com.nhsn.pages.LoginPage;
import com.nhsn.pages.ReviewPage;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC05_VerifyNote extends BaseClass {

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
            lPage.loginToNHSNLinkApp("praveen_manuel", "Welcome@123");
            hPage.generateReport("Test COVID Minimal");
            hPage.enterNote("Automation Notes");
            hPage.clickOnSaveButton();
            hPage.verifyReportSavedText();
            driver.navigate().refresh();

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