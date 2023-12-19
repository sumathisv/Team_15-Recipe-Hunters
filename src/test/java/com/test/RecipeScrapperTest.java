package com.test;

import org.testng.annotations.Test;


import com.pageObject.ScrapperPage;
import com.pageObject.RecipePage;
import com.utils.Baseutils;
import com.utils.ConfigReader;

public class RecipeScrapperTest extends Baseutils {

	
	@Test
	public void hyothyriodRecipeTest() throws Throwable
	{
	RecipePage recipepg=new RecipePage(driver);
	landingpage.clickRecipeButton();
	recipepg.HypothyroidLinkClick();
	scrapperpg.scrapperReceipes(ConfigReader.hypothyrohref(),"Hypothyroidism", 2, 3,"Hypothyriod","AllergyHypothyriod");
	
	
	landingpage.clickRecipeButton();
	recipepg.IndiandinnerHypothyroidLinkClick();
	scrapperpg.scrapperReceipes(ConfigReader.hypothyroindiandinnerHref(),"Hypothyroidism", 2, 3,"HypothyriodIndianDinner","AllergyHypothyriodIndianDinner");
	}
	
	@Test	
	public void diabeticRecipeTest() throws Throwable
	{
	RecipePage recipepg=new RecipePage(driver);
	landingpage.clickRecipeButton();		
	recipepg.DiabeticLinkClick();
    scrapperpg.scrapperReceipes(ConfigReader.Diebatichref(),"Diebatics", 0, 1,"Diebatics","AllergyDiebatics");
  
	}
	@Test	
	public void PCOSRecipeTest() throws Throwable
	{
	RecipePage recipepg=new RecipePage(driver);
	landingpage.clickRecipeButton();		
	recipepg.PCOSLinkClick();
    scrapperpg.scrapperReceipes(ConfigReader.pcoshref(),"PCOS", 6, 7,"PCOS","AllergyPCOS");
    
    
    landingpage.clickRecipeButton();	
    recipepg.highfiberPCOSLinkClick();
    scrapperpg.scrapperReceipes(ConfigReader.PCOShighfiberHref(),"PCOS", 6, 7,"PCOSHighfiber","AllergyPCOSHighfiber");
    
    
    landingpage.clickRecipeButton();	
    recipepg.indiansaladPCOSLinkClick();
    scrapperpg.scrapperReceipes(ConfigReader.PCOSindiansaladHref(),"PCOS", 6, 7,"PCOSSalad","AllergyPCOSSalad");
	}
@Test
	public void HypertensionRecipeTest() throws Throwable
	{
	RecipePage recipepg=new RecipePage(driver);
	landingpage.clickRecipeButton();		
	recipepg.SoupHypertensionLinkClick();
    scrapperpg.scrapperReceipes(ConfigReader.hypertensionsoupHref(),"Hypertension", 4, 5,"HypertensionSoup","AllergyHypertensionSoup");
    
	
	landingpage.clickRecipeButton();		
	recipepg.HypertensionLinkClick();
    scrapperpg.scrapperReceipes(ConfigReader.hypertensionhref(),"Hypertension", 4, 5,"Hypertension","AllergyHypertension");
    
    
    
  	}
}