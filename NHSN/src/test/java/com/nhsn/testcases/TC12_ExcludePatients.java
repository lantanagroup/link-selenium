package com.nhsn.testcases;

import com.nhsn.BaseClass;
import com.nhsn.pages.HelpPage;
import com.nhsn.pages.HomePage;
import com.nhsn.pages.LoginPage;
import com.nhsn.pages.ViewLineLevelDataPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
1. Log in to NHSN Application
2. Generate the report
3. Click on View Line Level Button
4. Verify Exclude patient functionality
 */
public class TC12_ExcludePatients extends BaseClass
{
    LoginPage lPage;
    HomePage hPage;
    ViewLineLevelDataPage vldPage;
    JavascriptExecutor jse ;
    HelpPage hpPage;
    @Test
    public void excludePatientsOnLineLevelData()
    {
        lPage = new LoginPage(driver);
        hPage = new HomePage(driver);
        hpPage = new HelpPage(driver);
        vldPage = new ViewLineLevelDataPage(driver);
        jse = (JavascriptExecutor)driver;
        try
        {
            String reportName = "Test COVID Minimal";
            lPage.loginToNHSNLinkApp(NHSN_USERNAME, NHSN_PASSWORD);
            hPage.generateReport(reportName,"2021-05-05");
            hPage.clickOnViewLineLevelDataButton();
            String noOfPatients = hPage.noOfPatientsOnLiveLevelData();
            System.out.println("No Of patients on Line Level Data- "+ noOfPatients);
            hPage.clickOnExcludeButton();
            Assert.assertTrue(hPage.thisFieldIsRequiredDisplayed(), "This field is required label is not showing at bottom of the Reason dropdown");
            System.out.println("This field is required label is showing at bottom of the Reason dropdown");
            hPage.verifyTheReasons();
            hPage.getTooltipOfExcludedSelected();
            hPage.clickOnReIncludeButton();

            vldPage.clickOnExcludeButtonOnFirstRow();
            Assert.assertFalse(hPage.verifyExcludedSelectedButtonIsEnabled(),"Exclude Selected Button is not disabled");
            hPage.selectTheReason("Other, specify");
            Assert.assertTrue(hPage.verifyExcludedSelectedButtonIsEnabled(),"Exclude Selected Button is not Enabled");

//            if(noOfPatients.equals("1"))
//            {
//                System.out.println("No Of Patients in the report is - 1");
//                Assert.assertTrue(hPage.noPatientsInTheReportTextIsDisplayed(),"No patients in the report text is not displayed after excluding the all patients");
//                System.out.println("No patients in the report text is displayed after excluding the patient");
//            }
//            else
//            {
//                System.out.println("No Of Patients in the report is - "+noOfPatients.trim());
//                int patients = Integer.parseInt(driver.findElement(By.xpath("//h6[contains(text(),'Total Patients included in report:')]")).getText().split(":")[1].trim());
//                Assert.assertEquals(patients, Integer.parseInt(noOfPatients.trim())-1, "No Of Patients are not matching");
//                System.out.println("No Of Patients are matching after excluding the one of patient");
//            }
            vldPage.clickOnExcludeSelectedButton();
            vldPage.verifyExcludeCheckboxOnFirstRow();
            vldPage.verifyExcludeButtonNotDisplayed();
            hPage.clickOnCloseButton();
        }
        catch(Exception e)
        {
           System.out.println(e.getMessage());
            Assert.fail("Error in the Test");
        }
    }







}
