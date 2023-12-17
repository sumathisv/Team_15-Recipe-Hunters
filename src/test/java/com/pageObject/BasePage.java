package com.pageObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.utils.Baseutils;
import com.utils.ConfigReader;

public class BasePage {

	public WebDriver driver;
	public List<String> recipeingredient = new ArrayList<String>();
	public List<String> allNutrientvalue = new ArrayList<String>();
	public List<String> nonveglst = new ArrayList<String>();
	List<String> resultnonveglst = new ArrayList<String>();
	List<String> vegan = new ArrayList<String>();
	List<String> resultveganlst = new ArrayList<String>();
	List<String> resultjainlst = new ArrayList<String>();
	List<String> jainlst = new ArrayList<String>();
	String recipecategorystr,toaddstr;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBys(value = {
			@FindBy(how = How.XPATH, using = "//div[@id='maincontent']/div/div/div[@style='text-align:right;padding-bottom:15px;']/a") })
	List<WebElement> gotopg;
	@FindBys(value = { @FindBy(how = How.XPATH, using = "//span[@class='rcc_recipename']") })
	List<WebElement> recipecards;
	@FindBys(value = { @FindBy(how = How.XPATH, using = "//div[@class='rcc_rcpno']/span") })
	List<WebElement> RecipeId;
	@FindBys(value = {
			@FindBy(how = How.XPATH, using = "//div[@id='rcpinglist']/div/span[@itemprop='recipeIngredient']/a/span") })
	List<WebElement> ingredientList;
	@FindBy(xpath = "//table[@id='rcpnutrients']/tbody/tr")
	List<WebElement> Nutrientvalue;
	@FindBys(value = { @FindBy(how = How.XPATH, using = "//div[@id='recipe_tags']/a") })
	List<WebElement> foodcategory;
	@FindBy(xpath ="//p/time[@itemprop='prepTime']")WebElement Preparationtime;
	@FindBy(xpath ="//p/time[@itemprop='cookTime']")WebElement Cookingtime;
	@FindBy(xpath ="//*[@id='ctl00_cntrightpanel_pnlRcpMethod']")WebElement Preparationmethod; 

	public List<String> getRecipeIngredientList() {
		for (int t = 0; t < ingredientList.size(); t++) {
			recipeingredient.add(ingredientList.get(t).getText());
		}
		return recipeingredient;
	}

	public List<String> getNutrientvalues() {
		for (int g = 0; g < Nutrientvalue.size(); g++) {
			allNutrientvalue.add(Nutrientvalue.get(g).getText());
		}
		return allNutrientvalue;
	}

	public String recipeCategory() throws IOException {
		
			System.out.println("write on excel");
			/* Filter recipe category */
			nonveglst = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 0,"Sheet2");
			resultnonveglst = Baseutils.FilterOperation(ingredientList, nonveglst);
			if (resultnonveglst.size() > 0) {
				
				if(resultnonveglst.size()==1)
				{
				
				if(resultnonveglst.get(0).equalsIgnoreCase("egg")||resultnonveglst.get(0).equalsIgnoreCase("eggs"))
				{
					recipecategorystr = "Eggitarian";
				}
				else
				recipecategorystr = "Non-Veg";
				}
				recipecategorystr = "Non-Veg";
			} else {
				vegan = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 1,"Sheet2");
				resultveganlst = Baseutils.FilterOperation(ingredientList, vegan);
				if (resultveganlst.size() > 0) {
					jainlst = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(),2,"Sheet2");
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

	public String toAdd(List<String> resulttoadd)
	{
		if(resulttoadd.size()>0)
			 toaddstr="YES";
			else
			 toaddstr="NO";
		
		return toaddstr;
	}
	
	
}
