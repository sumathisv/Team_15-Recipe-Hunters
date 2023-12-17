package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import com.pageObject.DiabeticRecipePage;
import com.pageObject.RecipePage;
import com.pageObject.ScrapperPage;
import com.utils.Baseutils;
import com.utils.ConfigReader;

public class RecipeScrapperTest extends Baseutils {

	
	@Test
	public void DiabeticRecipeTest() throws Throwable
	{
	RecipePage recipepg=new RecipePage(driver);
	DiabeticRecipePage diabeticpg=new DiabeticRecipePage(driver);
	ScrapperPage scrapperpg = new ScrapperPage(driver);
		landingpage.clickRecipeButton();
		recipepg.PCOSLinkClick();
//		diabeticpg.getrecipecard();
		scrapperpg.getrecipecard(ConfigReader.pcoshref(), "PCOS");
		landingpage.clickRecipeButton();
		recipepg.DiabeticLinkClick();
		scrapperpg.getrecipecard(ConfigReader.Diebatichref(), "Diabetics");
	}
}