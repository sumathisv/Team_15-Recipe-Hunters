package com.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RecipePage extends BasePage {

	public WebDriver driver;
	public RecipePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	@FindBy(xpath = "//a[contains(text(),'Diabetic recipes')]")	WebElement recipesDiabeticLink;

	
	public void DiabeticLinkClick()
	{
		recipesDiabeticLink.click();
	}
	
	
	
	
}
