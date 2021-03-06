package com.nhsn.testcases;

import com.nhsn.BaseClass;
import com.nhsn.pages.HomePage;
import com.nhsn.pages.LoginPage;
import com.nhsn.pages.ReviewPage;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/*
1. Log in to NHSN Application
2. URL is redirected to Environment URL
3. Count the No of reports and matched with 4
4. Verify the names of the reports
5. Click on Review Tab
6. Verify Review Table exists on Review Page
7. Verify the columns on Review Table
 */

public class TC01_SmokeTest extends BaseClass {
    LoginPage lPage;
    HomePage hPage;
    ReviewPage rPage;
    JavascriptExecutor jse ;
    @Test
    public void SmokeTest() throws InterruptedException {
        lPage = new LoginPage(driver);
        hPage = new HomePage(driver);
        rPage = new ReviewPage(driver);

        jse = (JavascriptExecutor)driver;
        try
        {
            lPage.loginToNHSNLinkApp(NHSN_USERNAME, NHSN_PASSWORD);

            redirectedToEnvironmentURL(driver.getCurrentUrl(), env);

            hPage.countNoOfReports(4);
            List expectedReportNames = new ArrayList();
            expectedReportNames.add("Admissions from Skilled Nursing Facility to Hospital COVID-19 Reporting");
            expectedReportNames.add("COVID 19 Risk Factor");
            expectedReportNames.add("Test COVID Minimal");
            expectedReportNames.add("NHSN Medication Administration");

            hPage.validateTheNamesOfReports(expectedReportNames);
            hPage.clickOnReviewTab();
            rPage.verifyReviewTableExists();
            rPage.validateColumnNamesOnReviewTable();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }

    }

    public boolean redirectedToEnvironmentURL(String url, String env)
    {
        boolean redirected = false;

        if (env.equalsIgnoreCase(("dev")))
        {
            Assert.assertTrue(url.equals("https://dev.nhsnlink.org/generate"), "URL is not redirected to Environment URL after logging in");
            redirected = true;
        }
        else if (env.equalsIgnoreCase(("demo")))
        {
            Assert.assertTrue(url.equals("https://demo.nhsnlink.org/generate"), "URL is not redirected to Environment URL after logging in");
            redirected = true;
        }

        System.out.println("URL is redirected to Environment URL after logging in");
        return redirected ;
    }



}
