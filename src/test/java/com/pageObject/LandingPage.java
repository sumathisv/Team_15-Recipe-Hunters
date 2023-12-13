package com.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utils.ConfigReader;

public class LandingPage extends BasePage{
	public WebDriver driver;
	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	@FindBy(xpath = "//div[text()='RECIPES']")
	WebElement recipesButton;

	
	public void landingWebSite()
	{
		String url =ConfigReader.getApplicationUrl();
		driver.get(url);
	}
	
	public void clickRecipeButton() {

		recipesButton.click();
	}
}
