package com.pageObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.utils.Baseutils;
import com.utils.ConfigReader;

public class HypothyroidismRecipePage extends BasePage {

	public WebDriver driver;
	public List<String> result = new ArrayList<String>();
	public List<String> foodcategorylst = new ArrayList<String>();
	List<String> resultfoodcategory = new ArrayList<String>();
	public List<String> eleminatelst = new ArrayList<String>();
	public List<String> recipeingredient = new ArrayList<String>();
	public List<String> allNutrientvalue = new ArrayList<String>();
	List<String> toaddlst = new ArrayList<String>();
	List<String> resulttoadd = new ArrayList<String>();
	ArrayList<ArrayList<String>> recipeslst = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> allergierecipeslst = new ArrayList<ArrayList<String>>();
	List<String> resultallergies = new ArrayList<String>();
	List<String> allergieslst = new ArrayList<String>();
	
	String hypothyroidRecipeName, hypothyroidRecipeID, hypothroidRecipeURL, recipecategorystr, foodcategorystr,
			ingredientstr, allNutrientvaluestr, toaddstr,backurl;
	public int pagenumber=0;
	public int totalEliminateIng;

	public HypothyroidismRecipePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	public void scrapperReceipes(String pgcountDString, String morbidity, int eliminateCellno, int toAddCellno ) throws InterruptedException, IOException {
		
		pagenumber = Baseutils.SearchPageCount(gotopg, pgcountDString);
		eleminatelst = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), eliminateCellno,"Sheet1");
		System.out.println(eleminatelst);
		System.out.println(pagenumber);
		for (int p = 1; p <= pagenumber-1; p++) {
			WebElement pageclick1 = driver.findElement(By.xpath(
					"//div[@id='maincontent']/div/div[2]//div[@style=\"text-align:right;padding-bottom:15px;\"]/a[@href="
							+ "\"" + pgcountDString + p + "\"" + "]"));
			pageclick1.click();

			for (int i = 0; i <recipecards.size(); i++) {
				System.out.println("Number of recipe in page" + recipecards.size());
				hypothyroidRecipeName = recipecards.get(i).getText();
				hypothyroidRecipeID = RecipeId.get(i).getText();
				Baseutils.explicit_wait(driver, recipecards.get(i));
				recipecards.get(i).click();
				Baseutils.explicit_wait(driver, ingredientList.get(0));
				hypothroidRecipeURL = driver.getCurrentUrl();
				recipeingredient = getRecipeIngredientList();
				allNutrientvalue = getNutrientvalues();
				
				result=Baseutils.FilterOperation(ingredientList, eleminatelst);
			    System.out.println(result);
				
				foodcategorylst = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 0,"sheet4");
				if (result.size()==0) {
					ArrayList<String> recipeinfo = new ArrayList<String>();
					ArrayList<String> allergierecipeinfo = new ArrayList<String>();

					recipecategorystr = recipeCategory();
					resultfoodcategory = Baseutils.FilterOperation(foodcategory, foodcategorylst);
					System.out.println(resultfoodcategory);
					foodcategorystr = String.join(",", resultfoodcategory);
					ingredientstr = String.join(",", recipeingredient);
					allNutrientvaluestr = String.join(",", allNutrientvalue);
					/* To ADD */
					toaddlst = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), toAddCellno,"Sheet1");
					resulttoadd = Baseutils.FilterOperation(ingredientList, toaddlst);
					
					toaddstr = toAdd(resulttoadd);

					/* Add each recipe into final recipelist */
					recipeinfo = Baseutils.additem(hypothyroidRecipeID, hypothyroidRecipeName, foodcategorystr,
							recipecategorystr, ingredientstr, Preparationtime.getText(), Cookingtime.getText(),
							Preparationmethod.getText(), allNutrientvaluestr, morbidity, hypothroidRecipeURL,
							toaddstr);
					recipeslst.add(recipeinfo);
					/*---------------------------------------------------------*/
					/* Add allergie recipe to allergielist */
					allergieslst = Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 0,"Sheet3");
					resultallergies = Baseutils.FilterOperation(ingredientList, allergieslst);
					
					if (resultallergies.size()>0) {
						allergierecipeinfo = Baseutils.additem(hypothyroidRecipeID, hypothyroidRecipeName,
								foodcategorystr, recipecategorystr, ingredientstr, Preparationtime.getText(),
								Cookingtime.getText(), Preparationmethod.getText(), allNutrientvaluestr, morbidity,
								hypothroidRecipeURL, toaddstr);
						allergierecipeinfo.addAll(resultallergies);
						allergierecipeslst.add(allergierecipeinfo);
					} else {
						allergierecipeinfo = Baseutils.additem(hypothyroidRecipeName, hypothyroidRecipeName,
								foodcategorystr, recipecategorystr, ingredientstr, Preparationtime.getText(),
								Cookingtime.getText(), Preparationmethod.getText(), allNutrientvaluestr, morbidity,
								hypothroidRecipeURL, toaddstr);
						allergierecipeinfo.add("No Allergies");
						allergierecipeslst.add(allergierecipeinfo);
					}
				}
				recipeingredient.clear();
			    allNutrientvalue.clear();
			    result.clear();
			    backurl=ConfigReader.getApplicationUrl()+pgcountDString+p;
			    
			    driver.navigate().to(backurl);

			} // End:Travers through each recipes in page.

		} // End of pagination.
		System.out.println("RecipeInfo in excel"+recipeslst);
		Baseutils.WriteExcel("ScrapedRecipeList", recipeslst);
		Baseutils.WriteExcel("AllergieRecipeList", allergierecipeslst);
	}
}