package com.pageObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.utils.Baseutils;
import com.utils.ConfigReader;

public class DiabeticRecipePage extends BasePage {

	public WebDriver driver;
	public DiabeticRecipePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	//@FindBy(xpath = "//a[contains(text(),'Diabetic recipes')]")	WebElement recipesDiabeticLink;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//div[@class='rcc_rcpno']/span")})List<WebElement> RecipeId;
	@FindBy(xpath ="//span[@id='ctl00_cntrightpanel_lblRecipeName']")WebElement RecipeName;
	@FindBy(xpath ="//p/time[@itemprop='prepTime']")WebElement Preparationtime;
	@FindBy(xpath ="//p/time[@itemprop='cookTime']")WebElement Cookingtime;
	@FindBy(xpath ="//*[@id='ctl00_cntrightpanel_pnlRcpMethod']")WebElement Preparationmethod; 
	@FindBy(xpath ="//table[@id='rcpnutrients']/tbody/tr")List<WebElement> Nutrientvalue;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//div[@id='maincontent']/div/div/div[@style='text-align:right;padding-bottom:15px;']/a")})List<WebElement> gotopg;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//span[@class='rcc_recipename']")})List<WebElement> recipecards;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//div[@id='rcpinglist']/div/span[@itemprop='recipeIngredient']/a/span")})List<WebElement> ingredientList;
	@FindBys(value = {@FindBy(how=How.XPATH,using="//div[@id='recipe_tags']/a")})List<WebElement> foodcategory;

	String pgcountDString=ConfigReader.Diebatichref();
	List<String> result=new ArrayList<String>();
	
	 ArrayList<ArrayList<String> > recipeslst = new ArrayList<ArrayList<String>>(); 
	 ArrayList<ArrayList<String> > allergierecipeslst = new ArrayList<ArrayList<String>>(); 
	List<String> eleminatelst=new ArrayList<String>();
	List<String> foodcategorylst=new ArrayList<String>();
	List<String> resultfoodcategory=new ArrayList<String>();
	List<String> recipeingredient= new ArrayList<String>();
	List<String> resultnonveglst=new ArrayList<String>();
	List<String> nonveglst=new ArrayList<String>();
	List<String> resultveganlst=new ArrayList<String>();
	List<String> vegan=new ArrayList<String>();
	List<String> allNutrientvalue= new ArrayList<String>();
	List<String> resultjainlst=new ArrayList<String>();
	List<String> jainlst=new ArrayList<String>();
	
	List<String> resultallergies=new ArrayList<String>();
	List<String> allergieslst=new ArrayList<String>();
	
	List<String> resulttoadd=new ArrayList<String>();
	List<String> toaddlst=new ArrayList<String>();
	String backurl,recipename,recipeid,foodcategorystr,recipecategorystr,ingredientstr,recipeurl,allNutrientvaluestr,toaddstr;
	 public  void getrecipecard() throws InterruptedException, IOException
	 {
//		 //Getting 1st Page.
//		    int pagenumber=Integer.parseInt(gotopg.get(0).getText());
//		   	WebElement pageclick= driver.findElement(By.xpath("//div[@id='maincontent']/div/div[2]//div[@style=\"text-align:right;padding-bottom:15px;\"]/a[@href="+"\""+pgcountDString+pagenumber+"\""+"]"));
//			Baseutils.explicit_wait(driver, pageclick);
//			pageclick.click();
//			int j=0;
//			//pagination-->page Traversal
//			try {
//			while(j<2)
//			{	System.out.println("pagenumber:"+pagenumber);
//				j++;
//							
//			/*Click each recipe and compare with eliminated list*/
//			//Reading Elimination List.	
//			eleminatelst=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(),0);
//			
//			//Travers through each recipes in page.
 	int pagenumber=0;
 	pagenumber=Baseutils.SearchPageCount(gotopg, pgcountDString);
	//for(int p=1;p<=pagenumber;p++)
 	eleminatelst=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(),0,"Sheet1");
 	for(int p=1;p<=2;p++)
	{ 
		WebElement pageclick1= driver.findElement(By.xpath("//div[@id='maincontent']/div/div[2]//div[@style=\"text-align:right;padding-bottom:15px;\"]/a[@href="+"\""+pgcountDString+p+"\""+"]"));
	     pageclick1.click();
			for(int i=0;i<3;i++)
			{  
			 System.out.println("Number of recipe in page"+recipecards.size());
			   recipename=recipecards.get(i).getText();
			   recipeid=RecipeId.get(i).getText();
			   Baseutils.explicit_wait(driver, recipecards.get(i));
			   recipecards.get(i).click();
			   Baseutils.explicit_wait(driver, ingredientList.get(0));
			   recipeurl=driver.getCurrentUrl();
				   for(int t=0;t<ingredientList.size();t++)	
				   {recipeingredient.add(ingredientList.get(t).getText());   }
				   for(int t=0;t<Nutrientvalue.size();t++)	
				   {  allNutrientvalue.add(Nutrientvalue.get(t).getText());   }
			   	//Filter for Elimination List.    
			    result=Baseutils.FilterOperation(ingredientList, eleminatelst);
			    System.out.println(result);
			    //Readind for food category inputlist.
			    foodcategorylst=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(),0, "Sheet4");
			    //Write scraped recipe in final list.
			    if(result.size()==0)
				{  	ArrayList<String>  recipeinfo= new ArrayList<String>();
			    	ArrayList<String>  allergierecipeinfo= new ArrayList<String>();
			    	System.out.println("write on excel");
					/*Filter recipe category*/
					nonveglst=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 0,"Sheet2");
					resultnonveglst=Baseutils.FilterOperation(ingredientList, nonveglst);
					if(resultnonveglst.size()>0)
					{ for(int v=0;v<resultnonveglst.size();v++)
						{	if(resultnonveglst.get(v).equalsIgnoreCase("eggplant"))
							{recipecategorystr="Veg";}						
							else
							{recipecategorystr="Non-Veg";}
						}
					}
					else	
					{	vegan=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 1,"Sheet2");
						resultveganlst=Baseutils.FilterOperation(ingredientList, vegan);
						if(resultveganlst.size()>0)
						{	jainlst=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 2, "Sheet2");
							resultjainlst=Baseutils.FilterOperation(ingredientList, jainlst);
							if(resultjainlst.size()>0)
							recipecategorystr="Veg";
							else
							recipecategorystr="jain";
						}
						else
						recipecategorystr="vegan";									
					}
					resultfoodcategory=Baseutils.FilterOperation(foodcategory, foodcategorylst);
					foodcategorystr=String.join(",", resultfoodcategory);
					ingredientstr=String.join(",",recipeingredient);
					allNutrientvaluestr=String.join(",",allNutrientvalue);
				
					/*To ADD*/
					toaddlst=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 1,"Sheet1");
					resulttoadd=Baseutils.FilterOperation(ingredientList, toaddlst);
					if(resulttoadd.size()>0)
					 toaddstr="YES";
					else
					 toaddstr="NO";
					/* Add each recipe into final recipelist*/
					recipeinfo=Baseutils.additem(recipeid, recipename, foodcategorystr,recipecategorystr,ingredientstr,Preparationtime.getText(),Cookingtime.getText(),Preparationmethod.getText(),allNutrientvaluestr,"Diabetics",recipeurl,toaddstr);
					recipeslst.add(recipeinfo);
			/*---------------------------------------------------------*/		
					/* Add allergie recipe to allergielist*/
					allergieslst=Baseutils.readExcelEliminate(ConfigReader.getInputExcel(), 0, "Sheet4");
	  			    resultallergies=Baseutils.FilterOperation(ingredientList, allergieslst);
				    if(resultallergies.size()>0)
				    {  	allergierecipeinfo=Baseutils.additem(recipeid, recipename, foodcategorystr,recipecategorystr,ingredientstr,Preparationtime.getText(),Cookingtime.getText(),Preparationmethod.getText(),allNutrientvaluestr,"Diabetics",recipeurl,toaddstr);
				    	allergierecipeinfo.addAll(resultallergies);
				    	allergierecipeslst.add(allergierecipeinfo);
				    }
				    else
				    {	allergierecipeinfo=Baseutils.additem(recipeid, recipename, foodcategorystr,recipecategorystr,ingredientstr,Preparationtime.getText(),Cookingtime.getText(),Preparationmethod.getText(),allNutrientvaluestr,"Diabetics",recipeurl,toaddstr);
			    	    allergierecipeinfo.add("No Allergies");
			    	    allergierecipeslst.add(allergierecipeinfo);}
				}
			    recipeingredient.clear();
			    allNutrientvalue.clear();
			    result.clear();
			    backurl=ConfigReader.getApplicationUrl()+pgcountDString+p;
			    
			    driver.navigate().to(backurl);
			}//End:Travers through each recipes in page.
		
	
		
	}	//End of pagination.				
	   
			System.out.println("RecipeInfo in excel"+recipeslst);
			Baseutils.WriteExcel("ScrapedRecipeList", recipeslst);
			Baseutils.WriteExcel("AllergieRecipeList", allergierecipeslst);
	
  }
	 
}