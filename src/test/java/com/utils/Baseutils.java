package com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.BeforeMethod;

import com.pageObject.LandingPage;

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
	
   /* public static int  SearchPageCount(List<WebElement> ele,String s) throws InterruptedException, IOException
	{  
		int pagenumber=Integer.parseInt(ele.get(0).getText());
		WebElement testclick= driver.findElement(By.xpath("//div[@id='maincontent']/div/div[2]//div[@style=\"text-align:right;padding-bottom:15px;\"]/a[@href="+"\""+s+pagenumber+"\""+"]"));
		Thread.sleep(2000);
		testclick.click();
		try {
		while(true)
		{	pagenumber=pagenumber+1;
			WebElement testclick1= driver.findElement(By.xpath("//div[@id='maincontent']/div/div[2]//div[@style=\"text-align:right;padding-bottom:15px;\"]/a[@href="+"\""+s+pagenumber+"\""+"]"));
				if(testclick1.isDisplayed())
				{	Thread.sleep(1000);
				   testclick1.click();									
				}
				else
					break;
		}					
		}
		catch (org.openqa.selenium.NoSuchElementException e) {  	  }
		return pagenumber;
		}*/
    
    
    //Input Excel reader
   public static List<String> readExcelEliminate(String filePath,int cellno) throws IOException {
	    List<String> data = new ArrayList<>();

	    try (FileInputStream fis = new FileInputStream(filePath);
	         Workbook workbook = new XSSFWorkbook(fis)) {
	        	org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0); // Assuming you are working with the first sheet
	        	for (Row row : sheet) {data.add(row.getCell(cellno).toString());
	            }	          	   
	    }
	    return data;
	}
	
    
    
    
}
