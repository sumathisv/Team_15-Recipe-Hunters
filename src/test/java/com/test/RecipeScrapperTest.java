package com.test;

import org.testng.annotations.Test;

import com.pageObject.DiabeticRecipePage;
import com.pageObject.ScrapperPage;
import com.pageObject.RecipePage;
import com.utils.Baseutils;
import com.utils.ConfigReader;

public class RecipeScrapperTest extends Baseutils {

	
	@Test
	public void DiabeticRecipeTest() throws Throwable
	{
	RecipePage recipepg=new RecipePage(driver);
	
	
		landingpage.clickRecipeButton();
		recipepg.DiabeticLinkClick();
		scrapperpg.scrapperReceipes(ConfigReader.Diebatichref(),"Diebatics", 0, 1);
		landingpage.clickRecipeButton();
		recipepg.HypothyroidLinkClick();
		scrapperpg.scrapperReceipes(ConfigReader.hypothyrohref(),"Hypothyroidism", 2, 3);
		
		
	}

}