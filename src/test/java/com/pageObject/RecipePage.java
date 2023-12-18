package com.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RecipePage  {

	public WebDriver driver;
	public RecipePage(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	@FindBy(xpath = "//a[contains(text(),'Diabetic recipes')]")	WebElement recipesDiabeticLink;
	@FindBy(xpath = "//a[@id='ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht211']")	WebElement HypothyroidismLink;
	@FindBy(xpath = "//a[contains(text(),'PCOS')]")	WebElement recipesPCOSLink;
	@FindBy(xpath = "//a[@id='ctl00_cntleftpanel_ttlhealthtree_tvTtlHealtht152']")	WebElement recipesHypertensionLink;
	public void DiabeticLinkClick()
	{
		recipesDiabeticLink.click();
	}
	public void HypothyroidLinkClick()
	{
		HypothyroidismLink.click();
	}
	public void PCOSLinkClick()
	{
		recipesPCOSLink.click();
	}
	public void HypertensionLinkClick()
	{
		recipesHypertensionLink.click();
	}
	
	
	
}
