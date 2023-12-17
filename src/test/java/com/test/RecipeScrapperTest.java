package com.test;

import org.testng.annotations.Test;

import com.pageObject.DiabeticRecipePage;
import com.pageObject.HypertensionRecipePage;
import com.pageObject.RecipePage;
import com.utils.Baseutils;

public class RecipeScrapperTest extends Baseutils {

	HypertensionRecipePage hypertensionpg;
	DiabeticRecipePage diabeticpg;
	@Test
	public void DiabeticRecipeTest() throws Throwable
	{
	RecipePage recipepg=new RecipePage(driver);
	 diabeticpg=new DiabeticRecipePage(driver);
		landingpage.clickRecipeButton();
		recipepg.DiabeticLinkClick();
		diabeticpg.getrecipecard();
	}
	
	@Test
	public void HyperTensionRecipeTest() throws Throwable
	{
	RecipePage recipepg=new RecipePage(driver);
	 hypertensionpg=new HypertensionRecipePage(driver);
	landingpage.clickRecipeButton();
	recipepg.HypertensionLinkClick();
	hypertensionpg.getrecipecard();
	}
}