package com.test;

import org.testng.annotations.Test;

import com.pageObject.DiabeticRecipePage;
import com.pageObject.RecipePage;
import com.utils.Baseutils;

public class RecipeScrapperTest extends Baseutils {

	
	@Test
	public void DiabeticRecipeTest() throws Throwable
	{
	RecipePage recipepg=new RecipePage(driver);
	DiabeticRecipePage diabeticpg=new DiabeticRecipePage(driver);
		landingpage.clickRecipeButton();
		recipepg.DiabeticLinkClick();
		diabeticpg.getrecipecard();
	}
	

}
