package com.pageObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.utils.Baseutils;
import com.utils.ConfigReader;

public class ScrapperPage {

	public WebDriver driver ;
	public List<String> result = new ArrayList<String>();
	public List<String> foodcategorylst = new ArrayList<String>();
	public List<String> resultfoodcategory = new ArrayList<String>();
	public List<String> eleminatelst = new ArrayList<String>();
	public List<String> recipeingredient = new ArrayList<String>();
	public List<String> allNutrientvalue = new ArrayList<String>();
	public List<String> toaddlst = new ArrayList<String>();
	public List<String> resulttoadd = new ArrayList<String>();
	public ArrayList<ArrayList<String>> recipeslst = new ArrayList<ArrayList<String>>();
	public ArrayList<ArrayList<String>> allergierecipeslst = new ArrayList<ArrayList<String>>();
	public List<String> resultallergies = new ArrayList<String>();
	public List<String> tempresultallergies = new ArrayList<String>();
	public List<String> allergieslst = new ArrayList<String>();
	public List<String> nonveglst = new ArrayList<String>();
	public List<String> resultnonveglst = new ArrayList<String>();
	public List<String> vegan = new ArrayList<String>();
	public List<String> resultveganlst = new ArrayList<String>();
	public List<String> resultjainlst = new ArrayList<String>();
	public List<String> jainlst = new ArrayList<String>();
	
	@FindBys(value = {
			@FindBy(how = How.XPATH, using = "//div[@id='maincontent']/div/div/div[@style='text-align:right;padding-bottom:15px;']/a") })
	List<WebElement> gotopg;
	@FindBys(value = { @FindBy(how = How.XPATH, using = "//span[@class='rcc_recipename']") })
	List<WebElement> recipecards;
	@FindBys(value = { @FindBy(how = How.XPATH, using = "//div[@class='rcc_rcpno']/span") })
	public List<WebElement> RecipeId;
	@FindBys(value = {
			@FindBy(how = How.XPATH, using = "//div[@id='rcpinglist']/div/span[@itemprop='recipeIngredient']/a/span") })
	public List<WebElement> ingredientList;
	@FindBy(xpath = "//table[@id='rcpnutrients']/tbody/tr")
	public List<WebElement> Nutrientvalue;
	@FindBys(value = { @FindBy(how = How.XPATH, using = "//div[@id='recipe_tags']/a") })
	public List<WebElement> foodcategory;
	@FindBy(xpath = "//p/time[@itemprop='prepTime']")
	public WebElement Preparationtime;
	@FindBy(xpath = "//p/time[@itemprop='cookTime']")
	public WebElement Cookingtime;
	@FindBy(xpath = "//*[@id='ctl00_cntrightpanel_pnlRcpMethod']")
	public WebElement Preparationmethod;
	@FindBy(xpath = "//div[@id='maincontent']/div/div/div[@style='text-align:right;padding-bottom:15px;']/a[last()]")
	public WebElement pagenumberWeb;
	public String recipeName, recipeID, recipeURL, recipecategorystr, foodcategorystr,
	ingredientstr, allNutrientvaluestr, toaddstr, backurl;
	public int pagenumber = 0;
	public int totalEliminateIng;

	public ScrapperPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void scrapperReceipes(String pgcountDString, String morbidity, int eliminateCellno, int toAddCellno,String excelname,String Allergyexcelname)
			throws InterruptedException, IOException {
         try {
		pagenumber = Integer.parseInt(pagenumberWeb.getText());
		eleminatelst = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), eliminateCellno, "Sheet1");
		System.out.println(pagenumber);
		for (int p = 1; p <=pagenumber ; p++) {
			System.out.println("Page:" + p);
			WebElement pageclick1 = driver.findElement(By.xpath(
					"//div[@id='maincontent']/div/div[2]//div[@style=\"text-align:right;padding-bottom:15px;\"]/a[@href="
							+ "\"" + pgcountDString + p + "\"" + "]"));
			pageclick1.click();
			for (int i = 0; i < recipecards.size(); i++) {
				recipeName = recipecards.get(i).getText();
				System.out.println("recipe:" +recipeName);
				recipeID = RecipeId.get(i).getText();
				Baseutils.explicit_wait(driver, recipecards.get(i));
				recipecards.get(i).click();
				Baseutils.explicit_wait(driver, ingredientList.get(0));
				recipeURL = driver.getCurrentUrl();
				recipeingredient = Baseutils.getRecipeIngredientList();
				allNutrientvalue = Baseutils.getNutrientvalues();
				result = Baseutils.FilterOperation(ingredientList, eleminatelst);
				foodcategorylst = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 0, "sheet4");
				if (result.size() == 0) {
					ArrayList<String> recipeinfo = new ArrayList<String>();
					ArrayList<String> allergierecipeinfo = new ArrayList<String>();
                try {
					recipecategorystr = Baseutils.recipeCategory();
					resultfoodcategory = Baseutils.FilterOperation(foodcategory, foodcategorylst);
					foodcategorystr = String.join(",", resultfoodcategory);
					ingredientstr = String.join(",", recipeingredient);
					allNutrientvaluestr = String.join(",", allNutrientvalue);
					/* To ADD */
					toaddlst = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), toAddCellno, "Sheet1");
					resulttoadd = Baseutils.FilterOperation(ingredientList, toaddlst);
					toaddstr = Baseutils.toAdd(resulttoadd);

					
					
					/* Add each recipe into final recipelist */
					recipeinfo = Baseutils.additem(recipeID, recipeName, foodcategorystr,
							recipecategorystr, ingredientstr, Preparationtime.getText(), Cookingtime.getText(),
							Preparationmethod.getText(), allNutrientvaluestr, morbidity,recipeURL, toaddstr);
					//Baseutils.WriteExcelIntermediate(excelname+"Intermediate", recipeinfo);
					recipeslst.add(recipeinfo);
					
                	/* Add allergie recipe to allergielist */
               		allergieslst = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 0, "Sheet3");
					resultallergies = Baseutils.FilterOperation(ingredientList, allergieslst);
					if (resultallergies.size()> 0) {
						tempresultallergies	=Baseutils.FilterOperationFoodCategory(ingredientList, allergieslst);
						System.out.println("TempAllergies:"+tempresultallergies);
						long tempcount=tempresultallergies.stream().filter(s->s.contains("eggplant")).count();
						allergierecipeinfo = Baseutils.additem(recipeID, recipeName,
									foodcategorystr, recipecategorystr, ingredientstr, Preparationtime.getText(),
									Cookingtime.getText(), Preparationmethod.getText(), allNutrientvaluestr, morbidity,
									recipeURL, toaddstr);
						if(tempcount==1)
						{ int indx=resultallergies.indexOf("egg");
						  resultallergies.remove(indx);
						  System.out.println("index of egg"+indx);
						  System.out.println("result after deleting egg"+resultallergies);}
						if(resultallergies.isEmpty())
							allergierecipeinfo.add("No Allergies");
						else
						{allergierecipeinfo.addAll(resultallergies);}
						allergierecipeslst.add(allergierecipeinfo);
					} else {
						allergierecipeinfo = Baseutils.additem(recipeID, recipeName,
								foodcategorystr, recipecategorystr, ingredientstr, Preparationtime.getText(),
								Cookingtime.getText(), Preparationmethod.getText(), allNutrientvaluestr, morbidity,
								recipeURL, toaddstr);
						allergierecipeinfo.add("No Allergies");
						//Baseutils.WriteExcelIntermediate(Allergyexcelname+"Intermediate", allergierecipeinfo);
						allergierecipeslst.add(allergierecipeinfo);
							}
                }catch(org.openqa.selenium.NoSuchElementException e){
                	        	
                }
				}
				recipeingredient.clear();
				allNutrientvalue.clear();
				
				result.clear();
				backurl = ConfigReader.getApplicationUrl() + pgcountDString + p;
				driver.navigate().to(backurl);
			} // End:Travers through each recipes in page.
		} 		// End of pagination.
		}catch (UnhandledAlertException f) {
        	 try {
        	        Alert alert = driver.switchTo().alert();
        	        String alertText = alert.getText();
        	        System.out.println("Alert data: " + alertText);
        	        alert.accept();
        	    } catch (NoAlertPresentException e) {  }
		}
		Baseutils.WriteExcel(excelname, recipeslst);
		Baseutils.WriteExcel(Allergyexcelname, allergierecipeslst);
		allergierecipeslst.clear();
		recipeslst.clear();
	}

	public List<WebElement> ingredientListWebElement() {
		return ingredientList;
	}
	public List<WebElement> nutrientvalueWebElement() {
		return Nutrientvalue;
	}
}
