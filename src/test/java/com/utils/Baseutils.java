package com.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
    //Write into Excel.
    
    
    
    //Input Excel reader.
    public static List<String> readExcelEliminate(String filePath,int cellno) throws IOException {
	    List<String> data = new ArrayList<>();

	    try (FileInputStream fis = new FileInputStream(filePath);
	         Workbook workbook = new XSSFWorkbook(fis)) {
	        	org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0); // Assuming you are working with the first sheet
	        	try {
	        	for (Row row : sheet) {
	        		data.add(row.getCell(cellno).toString());
	            }	
	        	}
	        	catch (java.lang.NullPointerException e) {
					// TODO: handle exception
				}
	    }
	    return data;
	}
	
    //Wait for element
   public static void explicit_wait(WebDriver driver,WebElement e) {
		new WebDriverWait(driver,Duration.ofSeconds(40)).until(ExpectedConditions.visibilityOf(e));}
    
   //Filer Method.
   public static List<String> FilterOperation(List<WebElement> weblst,List<String> comparelst)
   {   List<String> result = new ArrayList<String>();
	   for (String item : comparelst) 
		{ if(weblst.stream().filter(s->s.getText().contains(item)).count()>0)
			{result.add(item);}				
		}	
	  return result;
   }
   
   
   //Excel into Write
   
   public static void WriteExcel(String ExcelSheetName,List<String> recipefinallst) throws IOException
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(ExcelSheetName);
		
		XSSFFont font= workbook.createFont();
		CellStyle style=null;
		font.setBold(true);
		int rows = sheet.getPhysicalNumberOfRows();

		if(rows==0)
		{
		sheet.createRow(0);
		sheet.getColumnStyle(0).setFont(font);
		sheet.getRow(0).createCell(0).setCellValue("RecipeId");
		sheet.getRow(0).createCell(1).setCellValue("Recipe Name");
		sheet.getRow(0).createCell(2).setCellValue("Food Category(Veg/non-veg/vegan/Jain)");
		sheet.getRow(0).createCell(3).setCellValue("Recipe Category(Breakfast/lunch/snack/dinner)");
		sheet.getRow(0).createCell(4).setCellValue("Ingredients");
		sheet.getRow(0).createCell(5).setCellValue("Preparation Time");
		sheet.getRow(0).createCell(6).setCellValue("Cooking Time");
		sheet.getRow(0).createCell(7).setCellValue("Preparation method");
		sheet.getRow(0).createCell(8).setCellValue("Nutrient values");
		sheet.getRow(0).createCell(9).setCellValue("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
		sheet.getRow(0).createCell(10).setCellValue("Recipe URL");	
		}
		int rowno=rows+1;

		for(int i = 0;i<recipefinallst.size(); i++)
		{
			XSSFRow row=sheet.createRow(rowno++);
			row.createCell(0).setCellValue(recipefinallst.get(0));
			row.createCell(1).setCellValue(recipefinallst.get(1));
			row.createCell(2).setCellValue(recipefinallst.get(2));
			row.createCell(3).setCellValue(recipefinallst.get(3));
			row.createCell(4).setCellValue(recipefinallst.get(4));
			row.createCell(5).setCellValue(recipefinallst.get(5));
			row.createCell(6).setCellValue(recipefinallst.get(6));
			row.createCell(7).setCellValue(recipefinallst.get(7));
			row.createCell(8).setCellValue(recipefinallst.get(8));
			row.createCell(9).setCellValue(recipefinallst.get(9));
			row.createCell(10).setCellValue(recipefinallst.get(10));
			
			
			
		}
		
		FileOutputStream FOS = new FileOutputStream(".\\src\\test\\resources\\"+ExcelSheetName+".xlsx");
		workbook.write(FOS);
		FOS.close();	
	}
}
   
   
   
   
   

