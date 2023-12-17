package com.test;

import org.testng.annotations.Test;

import com.pageObject.DiabeticRecipePage;
import com.pageObject.HypothyroidismRecipePage;
import com.pageObject.RecipePage;
import com.utils.Baseutils;
import com.utils.ConfigReader;

public class RecipeScrapperTest extends Baseutils {

	
	@Test
	public void DiabeticRecipeTest() throws Throwable
	{
	RecipePage recipepg=new RecipePage(driver);
	DiabeticRecipePage diabeticpg=new DiabeticRecipePage(driver);
	HypothyroidismRecipePage hypothroidpg = new HypothyroidismRecipePage(driver);
		landingpage.clickRecipeButton();
		//recipepg.DiabeticLinkClick();
		//diabeticpg.getrecipecard();
		recipepg.HypothyroidLinkClick();
		hypothroidpg.scrapperReceipes(ConfigReader.hypothyrohref(),"Hypothyroidism", 2, 3);
		
	}

}