package commonMethods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class ReusableMethods {
	public static Properties Conpro;
	public static WebDriver driver;
	//method for lanuching browser
	public static WebDriver startBrowser() throws Throwable
	{
		Conpro = new Properties();
		//load property
		Conpro.load(new FileInputStream("./PropertyFiles/Environment.Properties"));
		if(Conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().deleteAllCookies();
		}
		else if(Conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			Reporter.log("Browser value is not matching",true);
		}
		return driver;
	}
	public static void openUrl()
	{  
		driver.get(Conpro.getProperty("Url"));
	}
	public static void waitForElement(String Locatortype,String Locatorvalue,String testData)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(testData)));
		if(Locatortype.equalsIgnoreCase("name"))
		{
			//wait untill element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locatorvalue)));
		}
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			//wait untill element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locatorvalue)));
		}
		if(Locatortype.equalsIgnoreCase("id"))
		{
			//wait untill element is visible
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locatorvalue)));
		}
	}
	public static void typeAction(String Locatortype,String Loactorvalue,String testdata)
	{
		if(Locatortype.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(Loactorvalue)).clear();
			driver.findElement(By.xpath(Loactorvalue)).sendKeys(testdata);
		}
		if(Locatortype.equalsIgnoreCase("name")) 
		{
			driver.findElement(By.name(Loactorvalue)).clear();
			driver.findElement(By.name(Loactorvalue)).sendKeys(testdata);
		}
		if(Locatortype.equalsIgnoreCase("id")) 
		{
			driver.findElement(By.id(Loactorvalue)).clear();
			driver.findElement(By.id(Loactorvalue)).sendKeys(testdata);
		}
	}
	public static void clickAction(String Locatortype,String Locatorvalue)
	{
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locatorvalue)).click();
		}
		if(Locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locatorvalue)).click();
		}
		if(Locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locatorvalue)).sendKeys(Keys.ENTER);
		}
	}
	public static void validateTitle(String Expected_title)
	{
		String Actval_Title = driver.getTitle();
		try
		{
			Assert.assertEquals(Actval_Title, Expected_title,"Titile is not maching");
		}catch (AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	
	}
	
	public static void closeBrowser() 
	{
		driver.quit();	
	}
	
	public static String genratdate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("DD_MM_YYYY");
		return df.format(date);
	}
	public static void dropDownAction(String Locatortype, String Locatorvalue, String Testdata)
	{
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
//			int value = Integer.parseInt(Testdata);
			Select element = new Select(driver.findElement(By.xpath(Locatorvalue)));
			element.selectByVisibleText(Testdata);
		}
		if(Locatortype.equalsIgnoreCase("name"))
		{
			//int value = Integer.parseInt(Testdata);
			Select element = new Select(driver.findElement(By.xpath(Locatorvalue)));
			element.selectByVisibleText(Testdata);
		}
		if(Locatortype.equalsIgnoreCase("id"))
		{
			//int value = Integer.parseInt(Testdata);
			Select element = new Select(driver.findElement(By.xpath(Locatorvalue)));
			element.selectByVisibleText(Testdata);
		}
	}
	public static void Capstock(String Locatortype, String Locatorvalue) throws Throwable
	{
		String StockNumber = "";
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			StockNumber = driver.findElement(By.xpath(Locatorvalue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("name"))
		{
			StockNumber = driver.findElement(By.name(Locatorvalue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("id"))
		{
			StockNumber = driver.findElement(By.id(Locatorvalue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/StockNumber.txt");
		BufferedWriter Bw = new BufferedWriter(fw);
		Bw.write(StockNumber);
		Bw.flush();
		Bw.close();
	}
	
	public static void StockTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/StockNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_data = br.readLine();
		if(!driver.findElement(By.xpath(Conpro.getProperty("Search-Textbox"))).isDisplayed())
			driver.findElement(By.xpath(Conpro.getProperty("Search_panel"))).click();
		    driver.findElement(By.xpath(Conpro.getProperty("Search-Textbox"))).clear();
		    driver.findElement(By.xpath(Conpro.getProperty("Search-Textbox"))).sendKeys(Exp_data);
		    Thread.sleep(2000);
		    driver.findElement(By.xpath(Conpro.getProperty("Search-Button"))).click();
		    Thread.sleep(3000);
		String Act_data = driver.findElement(By.xpath("//table[@class ='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Act_data+"    "+Exp_data,true);
		try
		{
			Assert.assertEquals(Exp_data, Act_data,"Stock number not found in table");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
	public static void capsup(String Locatortype, String Locatorvalue) throws Throwable
	{
		String Suppliernumber = " ";
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			Suppliernumber = driver.findElement(By.xpath(Locatorvalue)).getAttribute("value");
					}
		
		if(Locatortype.equalsIgnoreCase("name"))
		{
			Suppliernumber = driver.findElement(By.name(Locatorvalue)).getAttribute("value");
					}
		if(Locatortype.equalsIgnoreCase("id"))
		{
			Suppliernumber = driver.findElement(By.id(Locatorvalue)).getAttribute("value");
					}
		FileWriter fw = new FileWriter("./CaptureData/Suppliernumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(Suppliernumber);
		bw.flush();
		bw.close();
	}
	public static void supplierTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/Suppliernumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_data = br.readLine();
		if(!driver.findElement(By.xpath(Conpro.getProperty("Search-Textbox"))).isDisplayed())
			driver.findElement(By.xpath(Conpro.getProperty("Search_panel"))).click();
		    driver.findElement(By.xpath(Conpro.getProperty("Search-Textbox"))).clear();
		    driver.findElement(By.xpath(Conpro.getProperty("Search-Textbox"))).sendKeys(Exp_data);
		    Thread.sleep(2000);
		    driver.findElement(By.xpath(Conpro.getProperty("Search-Button"))).click();
		    Thread.sleep(3000);
		String Act_data = driver.findElement(By.xpath("//table[@class ='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(Act_data+"    "+Exp_data,true);
		try
		{
			Assert.assertEquals(Exp_data, Act_data,"Stock number not found in table");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
	}
   public static void captuerCustomer(String locatortype,String locatorvalue) throws Throwable
   {
	   String CustomerNumber = " ";
		if(locatortype.equalsIgnoreCase("xpath"))
		{
			CustomerNumber = driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
					}
		
		if(locatortype.equalsIgnoreCase("name"))
		{
			CustomerNumber = driver.findElement(By.name(locatorvalue)).getAttribute("value");
					}
		if(locatortype.equalsIgnoreCase("id"))
		{
			CustomerNumber = driver.findElement(By.id(locatorvalue)).getAttribute("value");
					}
		FileWriter fw = new FileWriter("./CaptureData/CustomerNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(CustomerNumber);
		bw.flush();
		bw.close();
   }

   public static void customerTable() throws Throwable 
   {
	   FileReader fr = new FileReader("./CaptureData/CustomerNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_data = br.readLine();
		if(!driver.findElement(By.xpath(Conpro.getProperty("Search-Textbox"))).isDisplayed())
			driver.findElement(By.xpath(Conpro.getProperty("Search_panel"))).click();
		    driver.findElement(By.xpath(Conpro.getProperty("Search-Textbox"))).clear();
		    driver.findElement(By.xpath(Conpro.getProperty("Search-Textbox"))).sendKeys(Exp_data);
		    Thread.sleep(2000);
		    driver.findElement(By.xpath(Conpro.getProperty("Search-Button"))).click();
		    Thread.sleep(3000);
		String Act_data = driver.findElement(By.xpath("//table[@class = 'table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Act_data+"    "+Exp_data,true);
		try
		{
			Assert.assertEquals(Exp_data, Act_data,"Stock number not found in table");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
   }
	
   public static void capnum(String locatortype,String locatorvalue) throws Throwable 
   {
	   String purchaseNumber = " ";
		if(locatortype.equalsIgnoreCase("xpath"))
		{
			purchaseNumber = driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
					}
		
		if(locatortype.equalsIgnoreCase("name"))
		{
			purchaseNumber = driver.findElement(By.name(locatorvalue)).getAttribute("value");
					}
		if(locatortype.equalsIgnoreCase("id"))
		{
			purchaseNumber = driver.findElement(By.id(locatorvalue)).getAttribute("value");
					}
		FileWriter fw = new FileWriter("./CaptureData/purchaseNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(purchaseNumber);
		bw.flush();
		bw.close();
   }
   public static void capdate(String locatortype,String locatorvalue) throws Throwable
   {
	   String Capturedata = " ";
		if(locatortype.equalsIgnoreCase("xpath"))
		{
			Capturedata = driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
					}
		
		if(locatortype.equalsIgnoreCase("name"))
		{
			Capturedata = driver.findElement(By.name(locatorvalue)).getAttribute("value");
					}
		if(locatortype.equalsIgnoreCase("id"))
		{
			Capturedata = driver.findElement(By.id(locatorvalue)).getAttribute("value");
					}
		FileWriter fw = new FileWriter("./CaptureData/Capturedate.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(Capturedata);
		bw.flush();
		bw.close();
   }
   public static void purchaseTable() throws Throwable 
   {
	   FileReader fr = new FileReader("./CaptureData/purchaseNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_data = br.readLine();
		if(!driver.findElement(By.xpath(Conpro.getProperty("Search-Textbox"))).isDisplayed())
			driver.findElement(By.xpath(Conpro.getProperty("Search_panel"))).click();
		    driver.findElement(By.xpath(Conpro.getProperty("Search-Textbox"))).clear();
		    driver.findElement(By.xpath(Conpro.getProperty("Search-Textbox"))).sendKeys(Exp_data);
		    Thread.sleep(2000);
		    driver.findElement(By.xpath(Conpro.getProperty("Search-Button"))).click();
		    Thread.sleep(3000);
		String Act_data = driver.findElement(By.xpath("//table[@class = 'table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Act_data+"    "+Exp_data,true);
		try
		{
			Assert.assertEquals(Exp_data, Act_data,"Stock number not found in table");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}   
   }
}

