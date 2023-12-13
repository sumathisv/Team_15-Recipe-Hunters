package com.utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.pageObject.BasePage;
import com.pageObject.LandingPage;
import com.utils.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Baseutils {

	public  static WebDriver driver;
	public static LandingPage landingpage;
	

	public static WebDriver initializeDrivers() throws Throwable {

		ConfigReader.loadConfig();
		String browser = ConfigReader.getBrowserType();
		if (driver == null) {
		if (browser.equalsIgnoreCase("firefox")) {

			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions options = new ChromeOptions();
			//options.addArguments("--remote-allow-origins=*");

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);

		} else if (browser.equalsIgnoreCase("safari")) {

			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();

		} else if (browser.equalsIgnoreCase("edge")) {

			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();

		}
		// Set Page load timeout
		//driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		}
		return driver;
	}
	
    @BeforeMethod
	public  static LandingPage launchApplication() throws Throwable {
		// Get browser Type from config file
		
		
		
		// Initialize driver from driver factory
		
		 driver=initializeDrivers();
	
		 landingpage = new LandingPage(driver);
		landingpage.landingWebSite();
		return landingpage;
	}
	
	//@AfterMethod
	//public  static void after() {
		//driver.close();
	//}
	
}
