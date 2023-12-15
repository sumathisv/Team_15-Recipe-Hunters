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
	//@FindBy(xpath = "//a[contains(text(),'Diabetic recipes')]")	WebElement recipesDiabeticLink;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//div[@class='rcc_rcpno']/span")})List<WebElement> RecipeId;
	@FindBy(xpath ="//span[@id='ctl00_cntrightpanel_lblRecipeName']")WebElement RecipeName;
	@FindBy(xpath ="//p/time[@itemprop='prepTime']")WebElement Preparationtime;
	@FindBy(xpath ="//p/time[@itemprop='cookTime']")WebElement Cookingtime;
	@FindBy(xpath ="//span[@id='procsection1']/../ol[@itemprop='recipeInstructions']")WebElement Preparationmethod; 
	@FindBy(xpath ="//table[@id='rcpnutrients']/tbody/tr")List<WebElement> Nutrientvalue;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//div[@id='maincontent']/div/div/div[@style='text-align:right;padding-bottom:15px;']/a")})List<WebElement> gotopg;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//span[@class='rcc_recipename']")})List<WebElement> recipecards;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//div[@id='rcpinglist']/div/span[@itemprop='recipeIngredient']/a/span")})List<WebElement> ingredientList;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//div[@id='recipe_tags']/a")})List<WebElement> foodcategory;

	String pgcountDString=ConfigReader.Diebatichref();
	List<String> result=new ArrayList<String>();
	
	 ArrayList<ArrayList<String> > recipeslst = new ArrayList<ArrayList<String>>(); 
	List<String> eleminatelst=new ArrayList<String>();
	List<String> foodcategorylst=new ArrayList<String>();
	List<String> resultfoodcategory=new ArrayList<String>();
	List<String> recipeingredient= new ArrayList<String>();
	List<String> resultnonveglst=new ArrayList<String>();
	List<String> nonveglst=new ArrayList<String>();
	List<String> resultveganlst=new ArrayList<String>();
	List<String> vegan=new ArrayList<String>();
	List<String> resultjainlst=new ArrayList<String>();
	List<String> jainlst=new ArrayList<String>();
	List<String> allNutrientvalue= new ArrayList<String>();
	List<String> resulttoadd=new ArrayList<String>();
	List<String> toaddlst=new ArrayList<String>();
	String backurl,recipename,recipeid,foodcategorystr,ingredientstr,recipeurl, allNutrientvaluestr;
	 public void getrecipecard() throws InterruptedException, IOException
	 {
		 //Getting 1st Page.
		    int pagenumber=Integer.parseInt(gotopg.get(0).getText());
		   
			WebElement pageclick= driver.findElement(By.xpath("//div[@id='maincontent']/div/div[2]//div[@style=\"text-align:right;padding-bottom:15px;\"]/a[@href="+"\""+pgcountDString+pagenumber+"\""+"]"));
			Baseutils.explicit_wait(driver, pageclick);
			pageclick.click();
			int j=0;
			//pagination-->page Traversal
			try {
			while(j<2)
			{	
				j++;
				pagenumber=pagenumber+1;			
			/*Click each recipe and compare with eliminated list*/
			//Reading Elimination List.	
			eleminatelst=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(),0);
			//Travers through each recipes in page.
			for(int i=0;i<3;i++)
			{  
			   recipename=recipecards.get(i).getText();
			   recipeid=RecipeId.get(i).getText();
			  
			   recipecards.get(i).click();
			   recipeurl=driver.getCurrentUrl();
			   for(int t=0;t<ingredientList.size();t++)	
			   {
				   recipeingredient.add(ingredientList.get(t).getText());
			   }
			   
			   for(int t=0;t<Nutrientvalue.size();t++)	
			   {
				   allNutrientvalue.add(Nutrientvalue.get(t).getText());
			   }
			   	//Filter for Elimination List.    
			    result=Baseutils.FilterOperation(ingredientList, eleminatelst);
			    System.out.println(result);
			    //Readind for food category inputlist.
			    foodcategorylst=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(),1);
			    //Write scraped recipe in final list.
			    if(result.size()==0)
				{ 
			    	ArrayList<String>  recipeinfo= new ArrayList<String>();
			    	System.out.println("write on excel");
					recipeinfo.add(recipeid);
					recipeinfo.add(recipename);
					/*Filter recipe category*/
					nonveglst=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 2);
					resultnonveglst=Baseutils.FilterOperation(ingredientList, nonveglst);
					if(resultnonveglst.size()>0)
					{recipeinfo.add("Non-Veg");
					}
					else	{
						vegan=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 3);
						resultveganlst=Baseutils.FilterOperation(ingredientList, vegan);
						if(resultveganlst.size()>0)
						{
							jainlst=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 4);
							resultjainlst=Baseutils.FilterOperation(ingredientList, jainlst);
							if(resultjainlst.size()>0)
							{recipeinfo.add("Veg");}
							else
							{recipeinfo.add("jain");}
						}
						else{recipeinfo.add("vegan");
						}						
					}
					
					resultfoodcategory=Baseutils.FilterOperation(foodcategory, foodcategorylst);
					foodcategorystr=String.join(",", resultfoodcategory);
					recipeinfo.add(foodcategorystr);
					
					ingredientstr=String.join(",",recipeingredient);
					recipeinfo.add(ingredientstr);
					recipeinfo.add(Preparationtime.getText());
					recipeinfo.add(Cookingtime.getText());
					recipeinfo.add(Preparationmethod.getText());
					allNutrientvaluestr=String.join(",",allNutrientvalue);
					recipeinfo.add(allNutrientvaluestr);
					recipeinfo.add("Diabetic");
					recipeinfo.add(recipeurl);
					/*To ADD*/
					 
					toaddlst=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 5);
					resulttoadd=Baseutils.FilterOperation(ingredientList, toaddlst);
					if(resulttoadd.size()>0)
					 recipeinfo.add("YES");
					else
					 recipeinfo.add("NO");	
					/* Add each recipe into final recipelist*/
					recipeslst.add(recipeinfo);
					
				}
			    recipeingredient.clear();
			    allNutrientvalue.clear();
			    result.clear();
			    backurl=ConfigReader.getApplicationUrl()+pgcountDString+pagenumber;
			    driver.navigate().to(backurl);
			    Thread.sleep(1000);
			}//End:Travers through each recipes in page.
			WebElement pageclick1= driver.findElement(By.xpath("//div[@id='maincontent']/div/div[2]//div[@style=\"text-align:right;padding-bottom:15px;\"]/a[@href="+"\""+pgcountDString+pagenumber+"\""+"]"));
					if(pageclick1.isDisplayed())
					{	Baseutils.explicit_wait(driver, pageclick1);
					   pageclick1.click();									
					}
					else
						break;
			}	//End of pagination.				
	        }catch (org.openqa.selenium.NoSuchElementException e) {  }
			System.out.println("RecipeInfo in excel"+recipeslst);
			Baseutils.WriteExcel("ScrapedRecipeList", recipeslst);
			
	  }
	 }
