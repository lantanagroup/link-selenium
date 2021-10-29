package com.nhsn.helpers;

import com.nhsn.pages.HomePage;
import com.nhsn.pages.LoginPage;
import com.nhsn.pages.ReviewPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class CommonFunctions {

    private WebDriver driver;
    public CommonFunctions(WebDriver driver)
    {
        this.driver = driver;
    }

    LoginPage lPage;
    HomePage hPage;
    JavascriptExecutor jse ;
    ReviewPage rPage;



    public String submitTheReport(String userName, String password, String reportName) throws InterruptedException
    {
        lPage = new LoginPage(driver);
        hPage = new HomePage(driver);
        jse = (JavascriptExecutor)driver;
        rPage = new ReviewPage(driver);
        String note = "";

        try {
            lPage.loginToNHSNLinkApp(userName, password);
            hPage.generateReport(reportName,"2021-05-05");
            note =  hPage.enterNote("Test Automation");
            hPage.clickOnSubmitButton();
            hPage.acceptTheAlert();
            hPage.verifyReportSavedText();
            hPage.verifyReportSentText();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }
        return note;
    }


}
