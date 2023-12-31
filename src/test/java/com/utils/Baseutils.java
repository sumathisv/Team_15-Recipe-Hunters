package com.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.pageObject.LandingPage;
import com.pageObject.ScrapperPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Baseutils {

	public static WebDriver driver;
	public static LandingPage landingpage;
	public static ScrapperPage scrapperpg;

	public static List<String> recipeingredient = new ArrayList<String>();
	public static List<String> allNutrientvalue = new ArrayList<String>();
	public static List<String> nonveglst = new ArrayList<String>();
	public static List<String> resultnonveglst = new ArrayList<String>();
	public static List<String> vegan = new ArrayList<String>();
	public static List<String> resultveganlst = new ArrayList<String>();
	public static List<String> resultjainlst = new ArrayList<String>();
	public static List<String> jainlst = new ArrayList<String>();
	static String recipecategorystr;
	static String toaddstr;

	public static WebDriver initializeDrivers() throws Throwable {

		ConfigReader.loadConfig();
		String browser = ConfigReader.getBrowserType();
		if (driver == null) {
			if (browser.equalsIgnoreCase("firefox")) {

				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();

			} else if (browser.equalsIgnoreCase("chrome")) {

				ChromeOptions options = new ChromeOptions();
				// options.addArguments("--remote-allow-origins=*");

				options.addArguments("--headless");

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

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			driver.manage().window().maximize();
		}
		return driver;
	}

	@BeforeTest
	public static LandingPage launchApplication() throws Throwable {

		// Initialize driver from driver factory

		driver = initializeDrivers();
		scrapperpg = new ScrapperPage(driver);
		landingpage = new LandingPage(driver);
		landingpage.landingWebSite();
		return landingpage;
	}

	public static WebDriver getdriver() throws Throwable {
		driver = initializeDrivers();
		return driver;
	}
	
	@AfterTest
	 public static void after() {
	 driver.close();
	 }

	// Input Excel reader.
	public static List<String> readExcelEliminate(String filePath, int cellno, String sheetname) throws IOException {
		List<String> data = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheet(sheetname);
			try {
				for (Row row : sheet) {
					if (row.getCell(cellno).getStringCellValue() != null
							&& row.getCell(cellno).getStringCellValue().length() != 0)
						data.add(row.getCell(cellno).toString());
				}
			} catch (java.lang.NullPointerException e) {
				// TODO: handle exception
			}
		}

		return data;
	}

	// Wait for element
	public static void explicit_wait(WebDriver driver, WebElement e) {
		new WebDriverWait(driver, Duration.ofSeconds(40)).until(ExpectedConditions.visibilityOf(e));
	}

	// Filer Method.
	public static List<String> FilterOperation(List<WebElement> weblst, List<String> comparelst) {
		List<String> result = new ArrayList<String>();

		for (String item : comparelst) {
			if (weblst.stream().filter(s -> s.getText().contains(item)).count() > 0) {
				result.add(item);
			}
		}
		return result;
	}

	public static ArrayList<String> additem(String rid, String rnme, String rfc, String rrc, String rin, String rpt,
			String rct, String rpm, String rnv, String rmc, String rurl, String roa) {
		ArrayList<String> templist = new ArrayList<String>();
		templist.add(rid.substring(0, 12));
		templist.add(rnme);
		templist.add(rfc);
		templist.add(rrc);
		templist.add(rin);
		templist.add(rpt);
		templist.add(rct);
		templist.add(rpm);
		templist.add(rnv);
		templist.add(rmc);
		templist.add(rurl);
		templist.add(roa);
		return templist;
	}

	// Excel into Write
	public static void WriteExcel(String ExcelSheetName, ArrayList<ArrayList<String>> recipefinallst)
			throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(ExcelSheetName);

		XSSFFont font = workbook.createFont();
		CellStyle style = null;
		font.setBold(true);
		int rows = sheet.getPhysicalNumberOfRows();
		if (ExcelSheetName.equals("Diebatics")||ExcelSheetName.equals("PCOS")||ExcelSheetName.equals("Hypertension")||ExcelSheetName.equals("Hypothyriod")) {
			if (rows == 0) {
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
				sheet.getRow(0).createCell(9)
						.setCellValue("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
				sheet.getRow(0).createCell(10).setCellValue("Recipe URL");
				sheet.getRow(0).createCell(11).setCellValue("To Add");
			}
		} else {
			if (rows == 0) {
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
				sheet.getRow(0).createCell(9)
						.setCellValue("Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)");
				sheet.getRow(0).createCell(10).setCellValue("Recipe URL");
				sheet.getRow(0).createCell(11).setCellValue("To Add");
				sheet.getRow(0).createCell(12).setCellValue("Allergies_ingerdient");
			}
		}

		int rowno = rows + 1;

		for (int i = 0; i < recipefinallst.size(); i++) {
			ArrayList<String> arraylist = new ArrayList<String>();
			arraylist = recipefinallst.get(i);
			XSSFRow row = sheet.createRow(rowno++);
			for (int j = 0; j < arraylist.size(); j++) {
				row.createCell(j).setCellValue(arraylist.get(j));
			}
		}

		FileOutputStream FOS = new FileOutputStream(".\\src\\test\\resources\\" + ExcelSheetName + ".xlsx");
		workbook.write(FOS);
		FOS.close();
	}

	public static List<String> getRecipeIngredientList() {
		List<WebElement> ingredientList = scrapperpg.ingredientListWebElement();
		for (int t = 0; t < ingredientList.size(); t++) {
			recipeingredient.add(ingredientList.get(t).getText());
		}
		return recipeingredient;
	}

	public static List<String> getNutrientvalues() {
		List<WebElement> Nutrientvalue = scrapperpg.nutrientvalueWebElement();
		for (int g = 0; g < Nutrientvalue.size(); g++) {
			allNutrientvalue.add(Nutrientvalue.get(g).getText());
		}
		return allNutrientvalue;
	}
	public static List<String> FilterOperationFoodCategory(List<WebElement> weblst,List<String> comparelst)
	   {   List<String> result = new ArrayList<String>();
	   		List<String> webitemlst1 = new ArrayList<String>();
	   		webitemlst1=weblst.stream().filter(s->s.getText()!=null).map(s->s.getText()).collect(Collectors.toList());
				for (String item : comparelst) 
		   {   
			   if(webitemlst1.stream().filter(s->s.contains(item)).count()>0)
				{
				result.addAll(webitemlst1);
				}				
			}	
			
		  return result;
	   }

	public static String recipeCategory() throws IOException {

		//System.out.println("write on excel");
		List<WebElement> ingredientList = scrapperpg.ingredientListWebElement();
		/* Filter recipe category */
		nonveglst = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 0, "Sheet2");
		resultnonveglst = Baseutils.FilterOperationFoodCategory(ingredientList, nonveglst);
		long egglist=resultnonveglst.stream().filter(s->s.equalsIgnoreCase("egg")||s.equalsIgnoreCase("eggs")).count();
		if (resultnonveglst.size() > 0) {
			if(egglist>0)
			{recipecategorystr="Eggetarian";}
			else
			{recipecategorystr="Non-Veg";}
			for(int v=0;v<resultnonveglst.size();v++)
			{	
			if(resultnonveglst.get(v).contains("eggplant"))
				{recipecategorystr="Vegetarian";
				break;}		}
			}
		else {
			vegan = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 1, "Sheet2");
			resultveganlst = Baseutils.FilterOperation(ingredientList, vegan);
			if (resultveganlst.size() > 0) {
				jainlst = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 2, "Sheet2");
				resultjainlst = Baseutils.FilterOperation(ingredientList, jainlst);
				if (resultjainlst.size() > 0)
					recipecategorystr = "Vegetarian";
				else
					recipecategorystr = "jain";
			} else
				recipecategorystr = "vegan";
		}
		return recipecategorystr;
	}

	public static String toAdd(List<String> resulttoadd) {
		if (resulttoadd.size() > 0)
			toaddstr = "YES";
		else
			toaddstr = "NO";
		return toaddstr;
	}
	
	
	public static Boolean recipeNamecheck(String rname,List<String> eleminatelist)
	{
		for (String item : eleminatelist) {
			if(rname.contains(item))
			{return true;}
			}
		return false;
		
	}
	
	
}
