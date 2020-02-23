package test.Resources.Generic.TrafficJam;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;



/* ######################################################################################################
 * Class Name: WebLibrary
 * Description: Contains the methods which are generic to all web page applications
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
public class WebLibrary extends FrameworkLibrary
{
	public final static Logger log = Logger.getLogger(WebLibrary.class);
	
/* ######################################################################################################
 * Method Name: SetText
 * Description: To clear contents and enter text in a Web Edit box
 * Input Parameters: Element Xpath , Data Value
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
	public static Boolean SetText(String ObjectXpath,String Value)
    {
        Boolean stepStatus = true;
        try
        {
        	Highlight(ObjectXpath);
        	driver.findElement(By.xpath(ObjectXpath)).click();
        	driver.findElement(By.xpath(ObjectXpath)).clear();
            driver.findElement(By.xpath(ObjectXpath)).sendKeys(Value);
            String Enteredvalue="";
            Enteredvalue=driver.findElement(By.xpath(ObjectXpath)).getAttribute("value");
            if (!Enteredvalue.equalsIgnoreCase(Value))
            {
            	stepStatus = false;
            }
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
    }
/* ######################################################################################################
 * Method Name: ClickElement
 * Description: To perform Click operation on a WebElement
 * Input Parameters: Element Xpath
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
    public static Boolean ClickElement(String ObjectXpath)
    {
        Boolean stepStatus = true;
        try
        {
        	Highlight(ObjectXpath);
        	driver.findElement(By.xpath(ObjectXpath)).click();
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
    }
/* ######################################################################################################
 * Method Name: SetTextAndEscape
 * Description: To clear contents , enter text in WebElement and then click on Escape
 * Input Parameters: Element Xpath
 * Output: True/False
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
    public static Boolean SetTextAndEscape(String ObjectXpath,String Value)
    {
        Boolean stepStatus = true;
        try
        {
        	Highlight(ObjectXpath);
        	driver.findElement(By.xpath(ObjectXpath)).click();
        	driver.findElement(By.xpath(ObjectXpath)).clear();
            driver.findElement(By.xpath(ObjectXpath)).sendKeys(Value);
            wait(0.5);
            driver.findElement(By.xpath(ObjectXpath)).sendKeys(Keys.ESCAPE);
            String Enteredvalue="";
            Enteredvalue=driver.findElement(By.xpath(ObjectXpath)).getAttribute("value");
            if (!Enteredvalue.equalsIgnoreCase(Value))
            {
            	stepStatus = false;
            }
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
    }
/* ######################################################################################################
 * Method Name: Exist
 * Description: To verify the existence of WebElement
 * Input Parameters: Element Xpath
 * Output: True/False
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
	public static Boolean Exist(String ObjectXpath)
    {
        Boolean stepStatus = true;
        try
        {
        	driver.findElement(By.xpath(ObjectXpath));
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
    }
	/* ######################################################################################################
	 * Method Name: SelectMenuOption
	 * Description: To perform Click operation on a menu item
	 * Input Parameters: Element Xpath
	 * Output: True/False
	 * Author: Omprakash.darsi - KL00056
     * Organization: KaayLabs
     * Date Created: 02-02-2020
	 * ######################################################################################################
	 */
	    public static Boolean SelectMenuOption(String ObjectXpath1, String ObjectXpath2)
	    {
	        Boolean stepStatus = true;
	        try
	        {
	        	Highlight(ObjectXpath1);
	        	Actions obj = new Actions(driver);
	        	obj.moveToElement(driver.findElement(By.xpath(ObjectXpath1))).build().perform();
	        	driver.findElement(By.xpath(ObjectXpath2)).click();
	        }
	        catch (Exception e)
	        {
	            stepStatus = false;
	        }
	        return stepStatus;
	    }
	
/* ######################################################################################################
 * Method Name: SelectOPtionByText
 * Description: To select option from dropdown based on visible text
 * Input Parameters: Element Xpath , Text of an option
 * Output: True/False
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
	public static Boolean SelectOPtionByText(String ObjectXpath,String Option)
	{
		Boolean stepStatus = true;
        try
        {
        	Highlight(ObjectXpath);
        	ObjectXpath = ObjectXpath + "/option[text()='" + Option + "']";
        	driver.findElement(By.xpath(ObjectXpath)).click();
        	String selectedvalue = driver.findElement(By.xpath(ObjectXpath)).getAttribute("text");
        	if(!selectedvalue.trim().equalsIgnoreCase(Option.trim()))
        	{
        		stepStatus = false;
        	}
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
	}
/* ######################################################################################################
 * Method Name: SelectOPtionByValue
 * Description: To select option from dropdown based on value
 * Input Parameters: Element Xpath , value of an option
 * Output: True/False
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
	public static Boolean SelectOPtionByValue(String ObjectXpath,String Option)
	{
		Boolean stepStatus = true;
        try
        {
    		Highlight(ObjectXpath);
    		ObjectXpath = ObjectXpath + "/option[@value='" + Option + "']";
        	driver.findElement(By.xpath(ObjectXpath)).click();
        	String selectedvalue = driver.findElement(By.xpath(ObjectXpath)).getAttribute("text");
        	if(!selectedvalue.trim().equalsIgnoreCase(Option.trim()))
        	{
        		stepStatus = false;
        	}
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
	}
/* ######################################################################################################
 * Method Name: launchBrowser
 * Description: To Launch a selected browser
 * Input Parameters: Browser Name
 * Output: WebDriver Instance
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
    public static WebDriver launchBrowser(String BrowserName)
	{
		WebDriver Tempdriver = null;
		switch (BrowserName.toLowerCase())
		{
			case "firefox":
			{
				Tempdriver = new FirefoxDriver();
				break;
			}
			case "internetexplorer":
			{
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				System.setProperty("webdriver.ie.driver", GlobalVariables.CurrentDirectory + "\\" +"JarFiles\\BrowserServers\\IEDriverServer.exe");
				Tempdriver = new InternetExplorerDriver(capabilities);
				break;
			}
			case "chrome":
			{
				System.setProperty("webdriver.chrome.driver",GlobalVariables.CurrentDirectory + "\\" +"JarFiles\\BrowserServers\\chromedriver.exe");
				Tempdriver = new ChromeDriver();
				break;
			}
			default:
			{
				System.out.println("please Select the correct browser");
			}
		}
		return Tempdriver;
	}
/* ######################################################################################################
 * Method Name: OpenUrl
 * Description: To Open a specified URL
 * Input Parameters: URL
 * Output: True/False
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
    public static Boolean OpenUrl(String URL)
    {
    	Boolean stepStatus = true;
    	try
    	{
    		driver.get(URL);
    		driver.manage().window().maximize();
    	}
    	catch(Exception e)
    	{
    		stepStatus = false;
    	}
    	String CurrentUrl = driver.getCurrentUrl();
    	if (!CurrentUrl.contains(URL))
    	{
    		stepStatus = false;
    	}
    	return stepStatus;
    }
/* ######################################################################################################
 * Method Name: SetImplicitWait
 * Description: To set implicit wait for a driver instance
 * Input Parameters: TimeInSec
 * Output: True/False
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
	public static Boolean SetImplicitWait(int TimeInSec)
    {
        Boolean stepStatus = true;
        try
        {
        	driver.manage().timeouts().implicitlyWait(TimeInSec, TimeUnit.SECONDS);
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
    }
/* ######################################################################################################
 * Method Name: SwitchToWindowByTitle
 * Description: To switch a driver instance of a driver to a new window based on the title
 * Input Parameters: Partial Title value
 * Output: True/False
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
	public static boolean SwitchToWindowByTitle(String Text)
	{
		Boolean stepStatus = false;
		try
        {
			Set<String> allhandles = driver.getWindowHandles();
			for(String handle1: allhandles)
			{
				driver.switchTo().window(handle1);
				String pageURL = driver.getTitle();
				if (pageURL.contains(Text))
				{
					stepStatus = true;
					break;
				}
			}
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
		return stepStatus;
	}
/* ######################################################################################################
 * Method Name: SwitchToWindowByURL
 * Description: To switch a driver instance of a driver to a new window based on the URL
 * Input Parameters: Partial URL value
 * Output: True/False
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
	public static boolean SwitchToWindowByURL(String Text)
	{
		Boolean stepStatus = false;
		try
        {
			Set<String> allhandles = driver.getWindowHandles();
			for(String handle1: allhandles)
			{
				driver.switchTo().window(handle1);
				String pageURL = driver.getCurrentUrl();
				if (pageURL.contains(Text))
				{
					stepStatus = true;
					break;
				}
			}
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
		return stepStatus;
	}
/* ######################################################################################################
 * Method Name: SwitchToWindowByHandle
 * Description: To switch a driver instance of a driver to a new window based on the Handle
 * Input Parameters: Window Handle
 * Output: True/False
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
	public static boolean SwitchToWindowByHandle(String HandleText)
	{
		Boolean stepStatus = true;
		try
        {
			driver.switchTo().window(HandleText);
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
		return stepStatus;
	}
	
/* ######################################################################################################
 * Method Name: wait
 * Description: To Make the execution halt for the specified time
 * Input Parameters: TimeInSeconds
 * Output: Void
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
    public static void wait(double d)
    {
    	try
    	{
    		Thread.sleep((long) (d*1000));
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
/* ######################################################################################################
 * Method Name: wait
 * Description: To Make the execution halt for the specified time
 * Input Parameters: TimeInSeconds
 * Output: Void
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
        public static void wait(int d)
        {
        	try
        	{
        		Thread.sleep((long) (d*1000));
        	}
        	catch(Exception e)
        	{
        		
        	}
        }
/* ######################################################################################################
 * Method Name: Highlight
 * Description: To Highlight a WebElement Based on the Java Script Executor
 * Input Parameters: Xpath of a WebElement
 * Output: Void
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
    public static void Highlight(String ObjectXpath)
	{
		try
		{
		   WebElement element = driver.findElement(By.xpath(ObjectXpath));
		   JavascriptExecutor js = (JavascriptExecutor)driver;
		   for (int iCnt = 0; iCnt < 2; iCnt++) 
		   {
		         js.executeScript("arguments[0].style.border='solid 4px black'", element);
		         Thread.sleep(200);
		         js.executeScript("arguments[0].style.border=''", element);
		         Thread.sleep(200);
		   }
		}
		catch(Exception e)
		{
			
		}
	}
/* ######################################################################################################
 * Method Name: JsSetText
 * Description: To Enter Text in a WebElement Based on the Java Script Executor
 * Input Parameters: Xpath of a WebElement, Value
 * Output: True/False
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
	public static Boolean JsSetText(String ObjectXpath,String Value)
	{
		boolean stepstatus = true;
		try
		{
			Highlight(ObjectXpath);
			WebElement element = driver.findElement(By.xpath(ObjectXpath));
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].value='" + Value + "'", element);
            String Enteredvalue="";
            Enteredvalue=driver.findElement(By.xpath(ObjectXpath)).getAttribute("value");
            if (!Enteredvalue.equalsIgnoreCase(Value))
            {
            	stepstatus = false;
            }
		}
		catch(Exception e)
		{
			stepstatus = false;
		}
		return stepstatus;
	}
/* ######################################################################################################
 * Method Name: getTimeStamp
 * Description: To generate a string based on date and time stamp
 * Input Parameters: None
 * Output: Current Date and Time Stamp as String
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
	public static String getTimeStamp() 
	{
		DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
	    String DateTimeStamp = dateTimeInstance.format(Calendar.getInstance().getTime());
	    DateTimeStamp = DateTimeStamp.replace(",", "");
	    DateTimeStamp = DateTimeStamp.replace(" ", "_");
	    DateTimeStamp = DateTimeStamp.replace(":", "-");
		return  DateTimeStamp;
	}
/* ######################################################################################################
 * Method Name: JsClickElement
 * Description: To Click on a WebElement Based on the Java Script Executor
 * Input Parameters: Xpath of WebElement
 * Output: True/False
 * Author: Omprakash.darsi - KL00056
 * Organization: KaayLabs
 * Date Created: 02-02-2020
 * ######################################################################################################
 */
	public static Boolean JsClickElement(String ObjectXpath)
	{
		boolean stepstatus = true;
		try
		{
			Highlight(ObjectXpath);
			WebElement element = driver.findElement(By.xpath(ObjectXpath));
		  //Creating JavaScriptExecuter Interface
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", element);
		}
		catch(Exception e)
		{
			stepstatus = false;
		}
		return stepstatus;
	}
	
	public static void waitForPageToLoad() {
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(DriverFacade.getDriver(), Long.parseLong(getProps1("ExplicitWaitDuration")));
			wait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					return ((JavascriptExecutor) DriverFacade.getDriver()).executeScript("return document.readyState")
							.equals("complete");
				}
			});
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	protected static WebElement $(String locator) {
		long start = System.currentTimeMillis();
		try {
			WebDriverWait wait = new WebDriverWait(DriverFacade.getDriver(),
					Long.parseLong(getProps1("ExplicitWaitDuration")));
			wait.until(ExpectedConditions.presenceOfElementLocated(getLocatorType(locator)));
		} catch (Exception e) {
			System.out.println(e);
			long end = System.currentTimeMillis();
			System.out.println("Waited for " + ((end - start) / 1000) + " seconds for the Locator to Present : "
					+ locator + ":Locator NOT Found");
			System.out.println("Element Not found :: " + locator);
		}
		return DriverFacade.getDriver().findElement(getLocatorType(locator));
	}

	public void assertElementDisplay(String locator) {
		try {
			Assert.assertTrue(waitForVisibilityOfElement(locator).isDisplayed(), "Element is not displayed in the DOM");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void assertText(String locator, String text) {
		try {
			waitForTextToBePresent(locator, text);
			Assert.assertEquals(text, getText(locator));
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("Runtime exception happened");
		}
	}

	public static WebElement waitForElementToBeClickable(String locator) throws Exception {
		WebDriverWait wait = new WebDriverWait(DriverFacade.getDriver(),
				Long.parseLong(getProps1("ExplicitWaitDuration")));
		return wait.until(ExpectedConditions.elementToBeClickable(getLocatorType(locator)));
	}

	public WebElement waitForVisibilityOfElement(String locator) throws Exception {
		WebDriverWait wait = new WebDriverWait(DriverFacade.getDriver(),
				Long.parseLong(getProps1("ExplicitWaitDuration")));
		return wait
				.until(ExpectedConditions.visibilityOf(DriverFacade.getDriver().findElement(getLocatorType(locator))));
	}

	public boolean waitForElementToDisappear(String locator) throws Exception {
		long start = System.currentTimeMillis();
		try {
			WebDriverWait wait = new WebDriverWait(DriverFacade.getDriver(),
					Long.parseLong(getProps1("ExplicitWaitDuration")));
			wait.until(
					ExpectedConditions.invisibilityOf(DriverFacade.getDriver().findElement(getLocatorType(locator))));
			return true;
		} catch (Exception e) {
			System.out.println(e);
			long end = System.currentTimeMillis();
			System.out.println("Waited for " + ((end - start) / 1000) + " seconds for the Locator to disappear : "
					+ locator + ":Locator Not Found");
			return false;
		}
	}

	public boolean waitForElementToAppear(String locator) throws Exception {
		long start = System.currentTimeMillis();
		try {
			WebDriverWait wait = new WebDriverWait(DriverFacade.getDriver(),
					Long.parseLong(getProps1("ExplicitWaitDuration")));
			wait.until(ExpectedConditions.visibilityOf(DriverFacade.getDriver().findElement(getLocatorType(locator))));
			return true;
		} catch (Exception e) {
			System.out.println(e);
			long end = System.currentTimeMillis();
			System.out.println("Waited for " + ((end - start) / 1000) + " seconds for the Locator to disappear : "
					+ locator + ":Locator Not Found");
			return false;
		}
	}

	public boolean waitForTextToBePresent(String locator, String text) {
		long start = System.currentTimeMillis();
		try {
			WebDriverWait wait = new WebDriverWait(DriverFacade.getDriver(),
					Long.parseLong(getProps1("ExplicitWaitDuration")));
			wait.until(ExpectedConditions
					.textToBePresentInElement(DriverFacade.getDriver().findElement(getLocatorType(locator)), text));
			return true;
		} catch (Exception e) {
			System.out.println(e);
			long end = System.currentTimeMillis();
			System.out.println(
					"Waited for " + ((end - start) / 1000) + " seconds for the Text:" + text + ":Text NOT Found");
			return false;
		}
	}

	public List<WebElement> getAllElements(String locator) throws Exception {
		WebDriverWait wait = new WebDriverWait(DriverFacade.getDriver(),
				Long.parseLong(getProps1("ExplicitWaitDuration")));
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getLocatorType(locator)));
		return DriverFacade.getDriver().findElements(getLocatorType(locator));
	}

	/**
	 * <p>
	 * Method to identify locator type. As part of this minor framework, we have
	 * used only ID, Xpath, Class, link
	 *  & Css selectors
	 * </p>
	 * 
	 * @param locator
	 * @return
	 * @throws Exception
	 */
	public static By getLocatorType(String locator)  {
		if (locator.startsWith("xpath="))
			return By.xpath(locator.split("xpath=")[1]);
		else if (locator.startsWith("css="))
			return By.cssSelector(locator.split("css=")[1]);
		else if (locator.startsWith("id="))
			return By.id(locator.split("id=")[1]);
		else if (locator.startsWith("link="))
			return By.linkText(locator.split("link=")[1]);
		else if (locator.startsWith("class="))
			return By.className(locator.split("class=")[1]);
		else
			return null;
	}

	public void clickElement(String locator) throws Exception {
		Boolean element = isElementEmpty(locator);
		if(!element) {
		setImplicitWait();
		waitForElementToBeClickable(locator).click();
		}
		else {
			System.out.println("<p>"+locator +" - Locator not clicked</p>");
		}
	}

	public void clickElement1(String locator) throws Exception {
		driver.findElement(getLocatorType(locator)).click();
	}

	public void navigateBack() {
		driver.navigate().back();
		driver.navigate().refresh();
		setImplicitWait();
	}

	public void refresh() throws Exception {
		driver.navigate().refresh();
		setImplicitWait();
	}

	public Boolean isElementEmpty(String locator) {
		Boolean element = null;
		try {
			element = driver.findElements(getLocatorType(locator)).isEmpty();
		} catch (Exception e) {
			System.out.println(e);
		}
		return element;
	}

	public static List<WebElement> getElements(String locator) throws Exception {
		return driver.findElements(getLocatorType(locator));
	}

	public int getSize(String locator) throws Exception {
		int element = driver.findElements(getLocatorType(locator)).size();
		return element;

	}

	public static void clickElementJS(String locator) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) DriverFacade.getDriver();
		js.executeScript("arguments[0].click()", $(locator));
	}

	public static void typeOnElement(String locator, String text)  {
		$(locator).clear();
		$(locator).sendKeys(text);
	}

	public void getElement(String locator) throws Exception {
		$(locator);
	}

	public String getText(String locator) {
		String text = null;
		try {
			text = $(locator).getText().trim();
		} catch (Exception e) {
			System.out.println(e);
		}
		return text;
	}

/*	public WebElement moveToWebElement(String locator) throws Exception {
		Actions action = new Actions(DriverFacade.getDriver());
		action.moveToElement($(locator));
		action.perform();
		return $(locator);
	}*/

/*	public void selectDropdownByText(String locator, String text) throws Exception {
		try {
		Select select = new Select($(locator));
		String text1 = text.trim();
		if (!text1.isEmpty() & text1 != null) {
			clickElement(locator);
			select.selectByVisibleText(text1);
				String selectedText = getSelectedItem(locator);
				Assert.assertEquals(selectedText, text);
		} else {
			select.selectByIndex(0);
		}
		}
		catch(Exception e){
			System.out.println("dropdown select exception");
		}
	}*/

	public void dynamicSelect(String locator) {
		try {
			Boolean element = isElementEmpty(locator);
			if (!element) {
				clickElement(locator);
				clickElement(locator);
				List<WebElement> options = driver.findElements(By.className("Select-option"));
				if (options.size() > 1) {
					options.get(1).click();
				} else if (options.size() > 0) {
					options.get(0).click();
				}
				else if(options.size()<=0) {
					String arrow = locator+"//span[@class='Select-arrow-zone']";
					clickElement(arrow);
				}
			}
			else {
				System.out.println(locator + "---->Element not found");
			}
		} catch (Exception e) {
			System.out.println(e);
			logs("<p>Error on selection : " + e.getMessage() + "</p>");
		}
	}
	
	public void dynamicSelect1(String locator, String selectOptions) {
		try {
			Boolean element = isElementEmpty(locator);
			if (!element) {
				clickElement(locator);
				List<WebElement> options = getElements(selectOptions);
				if (options.size() > 1) {
					options.get(1).click();
				} else if (options.size() > 0) {
					options.get(0).click();
				}
			}
			else {
				System.out.println(locator + "---->Element not found");
			}
		} catch (Exception e) {
			System.out.println(e);
			logs("<p>Error on selection : " + e.getMessage() + "</p>");
		}
	}

	public void scrollThePage(int count) {
		JavascriptExecutor jse = (JavascriptExecutor) DriverFacade.getDriver();
		for (int scrollCount = 0; scrollCount < count; scrollCount++)
			jse.executeScript("window.scrollBy(0,250)", "");
	}

	public void scrollToBottomOfPage() {
		JavascriptExecutor jse = (JavascriptExecutor) DriverFacade.getDriver();
		jse.executeScript("window.scrollTo(0,document.body.scrollHeight)", "");
	}

	public void scrollToTopOfPage() {
		JavascriptExecutor jse = (JavascriptExecutor) DriverFacade.getDriver();
		jse.executeScript("window.scrollTo(document.body.scrollHeight,0)", "");
	}

	/*public void selectDropdownByIndex(String locator, int index) throws Exception {
		Select select = new Select($(locator));
		select.selectByIndex(index);
	}*/

	/*public String getSelectedItem(String locator) throws Exception {
		Select select = new Select($(locator));
		return select.getFirstSelectedOption().getText().trim();
//		return null;
	}*/

	public static String firstLetterUpperCase(String subGrid) {
		String upper_case_line = "";
		@SuppressWarnings("resource")
		Scanner lineScan = new Scanner(subGrid);
		while (lineScan.hasNext()) {
			String word = lineScan.next();
			upper_case_line += Character.toUpperCase(word.charAt(0)) + word.substring(1) + " ";

		}
		String sa = upper_case_line.substring(0, upper_case_line.length() - 1);
		return sa.trim();
	}

	public static List<String> webelementsListText(String locator) throws Exception {
		List<String> entity = new ArrayList<String>();
		List<WebElement> sub = getElements(locator);
		for (WebElement e : sub) {
			String a = e.getText();
			entity.add(a);
		}
		return entity;
	}
	public void moveToWebElement_Keys(String locator) throws Exception {

		Actions action = new Actions(DriverFacade.getDriver());
		action.moveToElement($(locator));
		action.click($(locator));
		action.perform();
		action.sendKeys(Keys.ARROW_DOWN);
		action.sendKeys(Keys.ENTER);
		action.perform();
	}public void moveToWebElement_Keys_doublearow_Down(String locator) throws Exception {

		Actions action = new Actions(DriverFacade.getDriver());
		action.moveToElement($(locator));
		action.click($(locator));
		action.perform();
		action.sendKeys(Keys.ARROW_DOWN);
		action.sendKeys(Keys.ARROW_DOWN);
		action.sendKeys(Keys.ENTER);
		action.perform();
	}

	public void moveToWebElement(String locator) throws Exception {

		Actions action = new Actions(DriverFacade.getDriver());
		action.moveToElement($(locator));
		action.click($(locator));
		action.perform();
	}


}
 

