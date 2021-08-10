package com.nhsn.testcases;

import com.nhsn.BaseClass;
import com.nhsn.pages.HomePage;
import com.nhsn.pages.LoginPage;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC04_COVID_19_RiskFactor_Test extends BaseClass {
    LoginPage lPage;
    HomePage hPage;
    JavascriptExecutor jse ;
    @Test
    public void generateCovidReport() throws InterruptedException {
        lPage = new LoginPage(driver);
        hPage = new HomePage(driver);
        jse = (JavascriptExecutor)driver;
        try
        {
            lPage.loginToNHSNLinkApp("praveen_manuel", "Welcome@123");
            hPage.generateReport("COVID 19  Risk Factor");
        }
        catch(Exception e)
        {
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Error in the Test case\"}}");
            System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }
    }
}
