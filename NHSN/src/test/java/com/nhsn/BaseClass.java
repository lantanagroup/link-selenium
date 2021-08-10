package com.nhsn;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseClass {

    public WebDriver driver;
    public String sourceRun ;
    public String env ;
    public String NHSN_url;
    public String redirectedURL;

    public static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    public static final String AUTOMATE_KEY = System.getenv("BROWSERSTACK_AUTOMATE_KEY");
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    @Parameters({ "source","environment" ,"browser" })
    @BeforeClass
    public void initiateBrowser( @Optional ("local") String source,@Optional ("dev") String environment, @Optional ("firefox") String browser) throws MalformedURLException, InterruptedException {
        sourceRun = source;
        env = environment;

        switch(environment) {
            case "dev" :
                NHSN_url = "https://dev.nhsnlink.org";
                break;
            case "demo" :
                NHSN_url = "https://demo.nhsnlink.org";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + environment);
        }
        if(source.equalsIgnoreCase("local"))
        {
            if (browser.equalsIgnoreCase("chrome")) {
                driver = new ChromeDriver();
            } else  if (browser.equalsIgnoreCase("firefox")) {
                driver = new FirefoxDriver();
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        }
        else {
            System.out.println("Browser value - " + browser);
            DesiredCapabilities caps = new DesiredCapabilities();
            if (browser.equalsIgnoreCase("chrome_91")) {
                caps.setCapability("browser", "chrome");
                caps.setCapability("browser_version", "91.0");
                caps.setCapability("os", "Windows");
                caps.setCapability("os_version", "10");
            } else if (browser.equalsIgnoreCase("Windows8_chrome_91")) {
                caps.setCapability("browser", "chrome");
                caps.setCapability("browser_version", "91.0");
                caps.setCapability("os", "Windows");
                caps.setCapability("os_version", "8.1");
            } else if (browser.equalsIgnoreCase("firefox")) {
                caps.setCapability("browser", "firefox");
                caps.setCapability("browser_version", "90.0");
                caps.setCapability("os", "Windows");
                caps.setCapability("os_version", "10");
            } else if (browser.equalsIgnoreCase("edge")) {
                caps.setCapability("browser", "edge");
                caps.setCapability("browser_version", "91.0");
                caps.setCapability("os", "Windows");
                caps.setCapability("os_version", "10");
            } else if (browser.equalsIgnoreCase("ie")) {
                caps.setCapability("browser", "ie");
                caps.setCapability("browser_version", "11.0");
                caps.setCapability("os", "Windows");
                caps.setCapability("os_version", "10");
            }
            caps.setCapability("build", "browserstack-build-40");
            driver = new RemoteWebDriver(new URL(URL), caps);
        }
        driver.get(NHSN_url);
        Thread.sleep(2000);
        redirectedURL = driver.getCurrentUrl();
        identityProviderValidation(redirectedURL, environment);
    }

    @AfterClass
    public void closeBrowser() throws InterruptedException {
        driver.quit();
    }

    public void identityProviderValidation(String redirectedURL, String env)  {
        Assert.assertTrue(redirectedURL.startsWith("https://oauth.nhsnlink.org/auth/realms/NHSNLink/protocol/openid-connect/auth?response_type=code&client_id=nhsnlink-app"), "Environment URL is not redirected to Identity Provider");
        System.out.println("Environment URL is redirected to Identity Provider as expected");

        if(env.equalsIgnoreCase("dev"))
        {
            Assert.assertTrue(redirectedURL.split("redirect_uri=")[1].startsWith("https%3A%2F%2Fdev.nhsnlink.org"), "Redirected URL does not contains Dev Environment URL");
            System.out.println("Redirected URL contains Dev Environment URL");
        }
        else if(env.equalsIgnoreCase("demo"))
        {
            Assert.assertTrue(redirectedURL.split("redirect_uri=")[1].startsWith("https%3A%2F%2Fdemo.nhsnlink.org"), "Redirected URL does not contains Demo Environment URL");
            System.out.println("Redirected URL contains Demo Environment URL");
        }

    }
}
