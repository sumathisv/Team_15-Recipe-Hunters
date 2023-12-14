package com.pageObject;

import java.io.FileOutputStream;
import java.io.IOException;
import com.utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class DiabeticRecipePage extends BasePage {

	public WebDriver driver;
	public DiabeticRecipePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	@FindBy(xpath = "//a[contains(text(),'Diabetic recipes')]")	WebElement recipesDiabeticLink;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//div[@id='maincontent']/div/div/div[@style='text-align:right;padding-bottom:15px;']/a")})List<WebElement> gotopg;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//span[@class='rcc_recipename']")})List<WebElement> recipecards;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//div[@id='rcpinglist']/div/span[@itemprop=\"recipeIngredient\"]/a/span")})List<WebElement> ingredientList;
	
	String pgcountDString="/recipes-for-indian-diabetic-recipes-370?pageindex=";
	List<String> result = new ArrayList<String>();
	 public void getrecipecard() throws InterruptedException, IOException
	 {
		 Workbook workbook = new XSSFWorkbook();
         FileOutputStream fos = new FileOutputStream("ScrapedEliminatedList.xls");
        Sheet sheet = workbook.createSheet();
        String[] heading={"RecipeID","RecipeName","Recipe Category(Breakfast/lunch/snack/dinner)","Food Category(Veg/non-veg/vegan/Jain)","Ingredients","Preparation Time","Cooking Time","Preparation method","Nutrient values","Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)","Recipe URL"};
        int rowNum = 0;
        Row rowh=sheet.createRow(rowNum++);
        int cellNumh = 0;
        for (String cellData :heading ) {
            Cell cell = rowh.createCell(cellNumh++);
            cell.setCellValue(cellData);
        	}
		 int pagenumber=Integer.parseInt(gotopg.get(0).getText());
			WebElement testclick= driver.findElement(By.xpath("//div[@id='maincontent']/div/div[2]//div[@style=\"text-align:right;padding-bottom:15px;\"]/a[@href="+"\""+pgcountDString+pagenumber+"\""+"]"));
			Thread.sleep(2000);
			testclick.click();
			int j=0;
			try {
			while(j<2)
			{	
				j++;
				pagenumber=pagenumber+1;
			
			/*Click each recipe and compare with eliminated list*/
			List<String> eleminatelst=Baseutils.readExcelEliminate("C:\\Users\\Viru\\git\\Team_15-Recipe-Hunters\\src\\test\\resources\\EliminatedInputData\\Eliminatelist.xlsx",0);
			//System.out.println("Recipecards size"+recipecards.size());
			
				//recipecards loop checking for 1 card
			for(int i=0;i<1;i++)
			{  
			    recipecards.get(i).click();
			    for (String item : eleminatelst) 
					{  
					if(ingredientList.stream().filter(s->s.getText().contains(item)).count()>0)
					{	
							result.add(item);
					}
				
					}	
			    System.out.println(result);
			    if(result.size()==0)
				{
					System.out.println("write on excel");
				}
			    result.clear();
			    driver.navigate().back();
			    driver.navigate().back();
			    Thread.sleep(2000);
			}
			
			WebElement testclick1= driver.findElement(By.xpath("//div[@id='maincontent']/div/div[2]//div[@style=\"text-align:right;padding-bottom:15px;\"]/a[@href="+"\""+pgcountDString+pagenumber+"\""+"]"));
					if(testclick1.isDisplayed())
					{	Thread.sleep(1000);
					   testclick1.click();									
					}
					else
						break;
			}					
	 }catch (org.openqa.selenium.NoSuchElementException e) {  	  }
			workbook.write(fos);
			workbook.close();
	 }
	 }
