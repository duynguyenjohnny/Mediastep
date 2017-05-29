package test.java.com.mediastep.controller;


import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import test.java.com.mediastep.autotools.IGuiAutoTool;
import test.java.com.mediastep.util.StringUtil;

public class BaseController {
	
	public IGuiAutoTool autoTool;		
	protected String url;
	protected WebDriver driver;
	protected Wait<WebDriver> wait;
	JavascriptExecutor js = (JavascriptExecutor) driver;
		
	public class Constant {
		public static final int TIME_WAIT = 30;
		public static final int TIME_SLEEP = 5000;
	
		}
	
	public enum enumOsType{
		Windows,
		Linux,
		MacOS,
		Unknown;
	}
	public BaseController(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);				
		wait = new WebDriverWait(driver, Constant.TIME_WAIT);
	}
	
	protected enumOsType getOsType() {
		enumOsType osType = enumOsType.Unknown;
		String osname = System.getProperty("os.name").toLowerCase();
		if (osname.contains("unix") || osname.contains("linux")) {
			osType = enumOsType.Linux;
		} else if (osname.contains("windows")) {
			osType = enumOsType.Windows;
		} else if (osname.contains("mac os")) {
			osType = enumOsType.MacOS;
		}
		return osType;
	}
	
	public WebDriver getDriver(){
		return driver;
	}
				
	public void addLog(String logmsg) {
		Reporter.log(logmsg + "</br>", true);
	}
	
	public void addErrorLog(String logmsg) {
		Reporter.log("<font color='red'> " + logmsg + " </font></br>", true);
	}
	
	public void addSuccessLog(String logmsg) {
		Reporter.log("<font color='green'> " + logmsg + " </font></br>", true);
	}
	
	public void waitForAjax() {
		System.err.println("Checking active ajax calls by calling jquery.active ...");
		try {
			if (driver instanceof JavascriptExecutor) {
				JavascriptExecutor jsDriver = (JavascriptExecutor) driver;				
				for (int i = 0; i < Constant.TIME_WAIT; i++) {
					Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
					// return should be a number
					if (numberOfAjaxConnections instanceof Long) {
						Long n = (Long) numberOfAjaxConnections;
						System.err.println("Number of active jquery ajax calls: " + n);
						if (n.longValue() == 0L)
							break;
					}
					Thread.sleep(3000);
				}
			} else {
				System.err.println("Web driver: " + driver
						+ " cannot execute javascript");
			}
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}

	
	public void type(String xpath, String data) {
		try {
			//waitForAjax();
			driver.findElement(By.xpath(xpath)).clear();
			addLog("change data : "+data);
			driver.findElement(By.xpath(xpath)).sendKeys(data);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at editData :  "+ xpath);
		}
		
	}
	
	public void click(String xpath) {
		//waitForAjax();
		try {
			addLog("Click : " + xpath);
			driver.findElement(By.xpath(xpath)).click();
			//waitForAjax();
//			try {
//				if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES))
//						.isDisplayed()) {
//					Thread.sleep(3000);
//					waitForAjax();
//				}
//			} catch (NoSuchElementException e) {
//			} catch (InterruptedException e){				
//			}
		} catch (NoSuchElementException e) {
			addLog("No element exception : " + xpath);
			Assert.assertTrue(false);
		}
	}
	
	public void clickclassname(String classname) {
	//	waitForAjax();
		try {
			addLog("Click : " + classname);
			driver.findElement(By.className(classname)).click();
			//waitForAjax();
//		
		} catch (NoSuchElementException e) {
			addLog("No element exception : " + classname);
			Assert.assertTrue(false);
		}
	}
	
	public String getTextByXpath(String xpath) {
		String text = "";
		try {
			text = driver.findElement(By.xpath(xpath)).getText();
			addLog("Text : " + text);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath :  "+ xpath);
		}
		return text;
	}
	
	public String jsID(String Id) {
		String text = "";
		try {
			WebElement btn = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementById(Id)");
			text = btn.getText();
			addLog("Text : " + text);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByjsID :  "+ Id);
		}
		return text;
	}

	public boolean jsisElementPresent(String xpath) {
		try {
			boolean isDisplayed = driver.findElement(By.xpath(xpath)).isDisplayed();
			if (isDisplayed) {
				addLog("Element displayed : " + xpath);
				return true;
			} else {
				addLog("Element doesn't existed : " + xpath);
				return false;
			}
		} catch (NoSuchElementException e) {
			addLog("Element doesn't existed : " + xpath);
			return false;
		}
	}
	
	public boolean isElementPresent(WebElement element) {
		try {
			if (element == null) {
				return false;
			}
			boolean isDisplayed = element.isDisplayed();
			if (isDisplayed) {
				addLog("Element displayed : " + element.getText());
				return true;
			} else {
				addLog("Element doesn't existed : " + element.getText());
				return false;
			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException on isElementPresent " + element.getText());
			return false;
		}
	}
	
	public boolean isElementPresent(String xpath) {
		try {
			boolean isDisplayed = driver.findElement(By.xpath(xpath)).isDisplayed();
			if (isDisplayed) {
				addLog("Element displayed : " + xpath);
				return true;
			} else {
				addLog("Element doesn't existed : " + xpath);
				return false;
			}
		} catch (NoSuchElementException e) {
			addLog("Element doesn't existed : " + xpath);
			return false;
		}
	}
	
	public String getTextByXpath(WebElement element) {
		try {
			//waitForAjax();
			String text = element.getText();
			addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath : " + element);
		}
		return "";
	}
	
	public String getCurrentURL() {
		addLog(driver.getCurrentUrl());
		return driver.getCurrentUrl();
	}
	//-----------------------methods from homeController
	public boolean isLinkExist(String Xpath) {
		try {
			WebElement control = driver.findElement(By.xpath(Xpath));
			String isHref = control.getAttribute("href");
			if (isHref != null) {
				return true;
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException" + Xpath);
		}
		return false;
	}
	
	public void editData(WebElement element, String data) {
		try {
			//waitForAjax();
			element.clear();
			addLog("change data : " + data);
			element.sendKeys(data);
			//waitForAjax();
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at editData :  " + element);
		}
	}
	
	public String clickOptionByIndex(String xpath, int indexnumber) {
		try {
			//waitForAjax();
			WebElement dropDownListBox = driver.findElement(By.xpath(xpath));
			Select clickThis = new Select(dropDownListBox);
			System.out.print(clickThis);
			addLog("click : item index " + indexnumber);
			clickThis.selectByIndex(indexnumber);
			String textSelected = getItemSelected(xpath);
			addLog("click : " + textSelected);
			// wait data loading
			//waitForAjax();
			return textSelected;
		} catch (NoSuchElementException e) {
			addErrorLog("No such elemetn exception");
		}
		return "";
	}
	
	public String getItemSelected(String xpath) {
		return readField(xpath);
	}
	
	public String readField(String xpath) {
		try {
			//waitForAjax();
			WebElement footer = driver.findElement(By.xpath(xpath));
			List<WebElement> columns = footer
					.findElements(By.tagName("option"));
			for (WebElement column : columns) {
				if (column.isSelected()) {
					String selected = column.getText();
					addLog("Item selected : " + selected);
					return selected;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException at readField : " + xpath);
		}
		return "";
	}

	/**
	 * get size of the input table id
	 * @param tableNameInfo get from class: PageHome (BRAND_TABLE_INFO, LINK_APP_DEVICES, LINK_AUDIO_ROUTES,
	 * COMPANY_LIST_TABLE_INFO, PRODUCT_TABLE_INFO, ADMIN_USER_LIST_TABLE_INFO, PROMOTION_TABLE_INFO)
	 * @return
	 */
	public int getPageSize(String tableNameInfo) {
		try {
			String text = "";
			if (driver.findElements(By.xpath(tableNameInfo)).size() > 0) {
				text = driver.findElement(By.xpath(tableNameInfo)).getText();
			} 
			addLog("page size text: " + text);
			int size = StringUtil.getPageSize(text);
			return size;
		} catch (NoSuchElementException e) {
			addErrorLog("-------NoSuchElementException------- : getPageSize");
		}
		return 0;
	}
	
	public void clickText(String text) {
		try {
			//waitForAjax();
			addLog("Click : " + text);
//			Thread.sleep(2000);
			driver.findElement(By.partialLinkText(text)).click();
//			Thread.sleep(1000);
			//waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + text);
		} 
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	public void clickhref(String href) {
		try {
			//waitForAjax();
			addLog("Click : " + href);
//			Thread.sleep(2000);
			driver.findElement(By.cssSelector(href)).click();
			//href example: "a[href*='long']"
//			Thread.sleep(1000);
			//waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + href);
		} 
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	public void opennewurl(String url) {
		try {
			//waitForAjax();
			addLog("Open new url : " + url);
//			Thread.sleep(2000);
			driver.navigate().to(url);
			//href example: "a[href*='long']"
//			Thread.sleep(1000);
			//waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + url);
		} 
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	public void assertbackgroundcolor(String name,  String verify) {
		try {
			//waitForAjax();
			addLog("get background color : " + name);
//			Thread.sleep(2000);
			//ing color = driver.findElement(By.name("btnK")).getCssValue("background-color");
			String color = driver.findElement(By.name(name)).getCssValue("background-color");
			System.out.println("color is "+color);
			Assert.assertEquals(verify, color);
			//driver.navigate().to(url);
			//href example: "a[href*='long']"
//			Thread.sleep(1000);
			//waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + name);
		} 
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	public void inputtexttofield(String text,  String name) {
		try {
			//waitForAjax();
			addLog("input text to : " + name);
//			Thread.sleep(2000);
			//ing color = driver.findElement(By.name("btnK")).getCssValue("background-color");
			driver.findElement(By.name(name)).sendKeys(text);;
			//driver.navigate().to(url);
			//href example: "a[href*='long']"
//			Thread.sleep(1000);
			//waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + name);
		} 
//		catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	public void editData(String editXpath, String data) {
		if (data != null) {
			try {
				//waitForAjax();
				WebElement element = driver.findElement(By.xpath(editXpath));
				//note: work around because element.clear() wont' work in some cases
				element.sendKeys(Keys.BACK_SPACE);
				element.sendKeys(Keys.BACK_SPACE);
				element.clear();
				addLog("change data : " + data);
//				driver.findElement(By.xpath(editXpath)).sendKeys(data);
				element.sendKeys(data);
				//waitForAjax();
			} catch (NoSuchElementException e) {
				addLog("NoSuchElementException at editData :  "
						+ editXpath);
			}
		}
	}
	
	public boolean uploadFileElement(String fileName) {
		if (fileName == "" | fileName == null) {
			return false;
		}
		try {
			//waitForAjax();
			//autoTool.mouseWheel("down", 4);
			// Move window to the right corner
			// Click on upload link
			WebElement btnadd = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('button black float-right mt6')[0]");
			btnadd.click();
			// Init data
			String uri = System.getProperty("user.dir") + File.separator + "asset" + File.separator;
			// Upload file
			String filePath = uri + fileName;
			addLog("Upload file : " + filePath);
			String uploadWindow = "File Upload";
			autoTool.winWait(uploadWindow, "", 12);
			Thread.sleep(1000);
			autoTool.winActivate(uploadWindow);
			autoTool.ControlSetText(uploadWindow, "", "[Class:Edit1]", filePath.trim().toString());
			Thread.sleep(3000);
			autoTool.controlClick(uploadWindow, "", "[Text:&Open]");
			Thread.sleep(2000);
			autoTool.mouseWheel("down", 1);
			WebElement btnconfirmadd = (WebElement) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName('imgeditor_button')[0]");
			btnconfirmadd.click();
			//btnUseThisImage
			//autoTool.controlClick(focuswindow, "", "DirectUIHWND1");
			Thread.sleep(2000);
			
			if(autoTool.winExists(uploadWindow)){
				addLog("File not found or not invalid");
				String winID = autoTool.winGetHandle(uploadWindow);
				autoTool.controlClick("[Handle:" + winID + "]", "", "[Text:OK]");
				Thread.sleep(1000);
				autoTool.controlClick(uploadWindow, "", "[Text:Cancel]");
				return false;
			}
			//waitForAjax();
			
			return true;
		} catch (NoSuchElementException e) {
			addLog("No File name exception: " + fileName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void selectConfirmationDialogOption(String option) {
		try {
			Thread.sleep(3000);
			addLog("Select option: " + option);
			click(driver.findElement(By.xpath("//*[@href='javascript:;' and text() = '" + option + "']")));
			Thread.sleep(2000);
			//waitForAjax();
		} catch (NoSuchElementException e) {
			addLog("No such element exception");
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void click(WebElement element) {
		try {
			addLog("Click element: //*[@id=" + element.getAttribute("id") + "]");
			element.click();
			//waitForAjax();
		} catch (NoSuchElementException e) {
			addLog("Element: " + element + " is not present");
		}
	}
	
	

	public boolean waitForElementClickable(String xpath) {
		try {
			addLog("Wait for element: " + xpath + " clickable");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			return true;

		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return false;
		}
	}
	
	public boolean waitForElementDisappear(String xpath) {
		try {
			addLog("Wait for element: " + xpath + " disappear");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
			return true;

		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return false;
		}
	}


	public boolean checkMessageDisplay(String message) {
		String text = driver.getPageSource();
		if (text.contains(message)) {
			addLog("Message: " + message + " found");
			return true;
		} else {
			addLog("Message: " + message + " not found");
			return false;
		}
	}
	
	public int getTotalItem(String xpath) {
		try {
			String text = getTextByXpath(xpath);
			return StringUtil.getNumAtIndex(text, 2);
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : getTotalItem");
		}
		return 0;
	}

	public int getPerPage(String xpath) {
		try {
			String text = getTextByXpath(xpath);
			return StringUtil.getNumAtIndex(text, 1);
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : getPerPage");
		}
		return 0;
	}

	
	  // TODO move to wrapper class
	public boolean selectACheckbox(String xpath) {
		try {
			WebElement checkbox = driver.findElement(By.xpath(xpath));
			if (!checkbox.isSelected()) {
				addLog("Select checkbox: " + xpath);
				checkbox.click();
				//waitForAjax();
				return true;
			}
			addLog("Checkbox: " + xpath + " is already selected");
			return true;
		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return false;
		}
	}
	
	// TODO: need to refactor the following method
	public String getElementText(WebElement element) {
		try {
			//waitForAjax();
			String text = element.getText();
			addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath : "
					+ element);
		}
		return "";
	}

	public void clickid(String id) {
		// TODO Auto-generated method stub
	//	waitForAjax();
		try {
			addLog("Click : " + id);
			driver.findElement(By.xpath(id)).click();
		//	waitForAjax();

		} catch (NoSuchElementException e) {
			addLog("No element exception : " + id);
			Assert.assertTrue(false);
		}
	}

	public void typeid(String id, String data) {
		// TODO Auto-generated method stub
		try {
			//waitForAjax();
			driver.findElement(By.id(id)).clear();
			addLog("change data : "+data);
			driver.findElement(By.id(id)).sendKeys(data);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at editData :  "+ id);
		}
	}

	public String gettextelementbytagname(String tagname) {
		try {
			WebElement webElement = driver.findElement(By.tagName(tagname));
			addLog("Element is get successful ");
			String text = webElement.getText();
			addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException: Element was not exist ");
		}
		return "";
	}
	}

