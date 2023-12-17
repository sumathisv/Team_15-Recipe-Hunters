package com.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utils.Baseutils;

public class RecipePage extends BasePage {

	public WebDriver driver;
	public RecipePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	@FindBy(xpath = "//a[contains(text(),'Diabetic recipes')]")	WebElement recipesDiabeticLink;

	@FindBy(xpath = "//a[contains(text(),'Hypothyroidism Diet')]")	WebElement HypothyroidLink;
	@FindBy(xpath = "//a[contains(text(),'High Blood Pressure')]")	WebElement HypertensionLink;
	@FindBy(xpath = "//a[contains(text(),'PCOS')]")	WebElement PCOSLink;

	public void DiabeticLinkClick()
	{
		recipesDiabeticLink.click();
	}
	public void HypothyroidLinkClick()
	{
		
		HypothyroidLink.click();
	}
	
	
	public void HypertensionLinkClick()
	{Baseutils.explicit_wait(driver, HypertensionLink);
		HypertensionLink.click();
	}
	
	
	public void PCOSLinkClick()
	{
		PCOSLink.click();
	}
	
	
}
