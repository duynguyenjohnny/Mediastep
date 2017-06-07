package test.java.com.mediastep.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

public class UserController extends BaseController {
	public UserController(WebDriver driver) {
		super(driver);
	}

	public boolean isCheckBoxEnable(WebElement checkBox) {
		try {
			return !checkBox.getAttribute("innerHTML").contains("disabled");
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : checkBoxEnable");
		}
		return false;
	}

	public String getAtribute(WebElement element, String atr) {
		try {
			// waitForAjax();
			String text = element.getAttribute(atr);
			addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath : " + element);
			return "";
		}
	}

	// TODO: not yet checked
	public HashMap<String, String> getItemUserMenu(String xpath) {
		HashMap<String, String> data = new HashMap<String, String>();
		try {
			WebElement footer = driver.findElement(By.xpath(xpath));
			List<WebElement> columns = footer.findElements(By.tagName("a"));
			for (int i = 1; i <= columns.size(); i++) {
				data.put("option " + i, columns.get(i - 1).getAttribute("text"));
			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException on getItemUserMenu " + xpath);
		}
		return data;
	}

	// TODO
	public ArrayList<String> getAllLinkA(String xpath) {
		ArrayList<String> links = new ArrayList<String>();
		try {
			WebElement action = driver.findElement(By.xpath(xpath));
			List<WebElement> actions = action.findElements(By.tagName("a"));
			for (WebElement webElement : actions) {
				String act = webElement.getText();
				links.add(act);
			}
		} catch (NoSuchElementException e) {
			System.err.println("No such element exception : getAllLinkA");
		}
		return links;
	}

	// TODO
	public String[] getConfirm() {
		String confirm[] = new String[2];
		int i = 0;
		try {
			List<WebElement> links = driver.findElements(By.tagName("a"));
			for (WebElement webElement : links) {
				String href = webElement.getAttribute("href");
				if (href != null && href.contains("javascript:;")) {
					confirm[i] = webElement.getText();
					System.out.println("Confirm : " + confirm[i]);
					i++;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("No Such element exception : getConfirm");
		}
		return confirm;
	}

	// TODO modify, make generic and move to BaseController (maybe duplicated)
	public boolean selectOptionInDropdown(String xpath, String name) {
		try {
			if (name == null | name == "") {
				return false;
			}
			// Get dropdown box
			WebElement groupBox = driver.findElement(By.xpath(xpath));
			// Click on dropdown
			groupBox.findElement(By.tagName("button")).click();
			// waitForAjax();
			// Select checkbox
			List<WebElement> options = groupBox.findElement(By.tagName("ul"))
					.findElements(By.tagName("li"));
			for (WebElement option : options) {
				if (option.getText().contains(name)) {
					addLog("Select option: " + name);
					option.findElement(By.tagName("a")).click();
					// waitForAjax();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				}
			}
			addLog("Option: " + name + " not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
			return false;
		}
	}

	public static String replaceCharAt(String s, int pos, String c) {
		return s.substring(0, pos) + c + s.substring(pos + 1);
	}


	@Override
	public String getTextByXpath(String xpath) {
		String text = "";
		try {
			text = driver.findElement(By.xpath(xpath)).getText();
			addLog("Text : " + text);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath :  " + xpath);
		}
		return text;
	}

	public String getTextByID(String id) {
		String text = "";
		try {
			text = driver.findElement(By.id(id)).getText();
			addLog("Text : " + text);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath :  " + id);
		}
		return text;
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

	public String randomString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 6) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public String randomnumber(int numberchars) {
		String SALTCHARS = "1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < numberchars) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public String randomchars(int numberchars) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < numberchars) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

//	public String randombirthday() {
//		Random random = new Random();
//
//		GregorianCalendar gc = new GregorianCalendar();
//
//		int year = randBetween(1900, 2017);
//		int month = randBetween(01, 12);
//		int day = randBetween(01, 31);
//
//		gc.set(gc.YEAR, year);
//
//		int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
//
//		gc.set(gc.DAY_OF_YEAR, dayOfYear);
//
//		String DOB = (gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 2) + "-" + gc.get(gc.DAY_OF_MONTH)+ 2);
//        return DOB;
//	}
//
	public static int randBetween(int start, int end) {
		return start + (int)Math.round(Math.random() * (end - start));
	}

	public String randomDataOfBirth(int yearStartInclusive, int yearEndExclusive) {
		LocalDate start = LocalDate.ofYearDay(yearStartInclusive, 1);
		LocalDate end = LocalDate.ofYearDay(yearEndExclusive, 1);

		long longDays = ChronoUnit.DAYS.between(start, end);
		int days = (int) longDays;
		if (days != longDays) {
			throw new IllegalStateException("int overflow; too many years");
		}
		int day = randBetween(0, days);
		LocalDate dateOfBirth = start.plusDays(day);

		return dateOfBirth.toString();
	}

	public void clicklgin() {
		WebElement lgin = (WebElement) ((JavascriptExecutor) driver)
				.executeScript("return document.getElementsByClassName('big-blue-button float-right mr68')[0]");
		lgin.click();

	}

	public void clickDOM(String exScript) {
		WebElement clickDOM = (WebElement) ((JavascriptExecutor) driver)
				.executeScript(exScript);
		clickDOM.click();

	}

	public void clicklgout() {
		WebElement lgout = (WebElement) ((JavascriptExecutor) driver)
				.executeScript("return document.getElementsByClassName('button blue float-left ml20 mt6')[0]");
		lgout.click();

	}

	public void addLog(String text){
		Reporter.log(text+ "</br>", true);
	}

	public void clickElement(String exScript) {
		try {
			WebElement click = (WebElement) ((JavascriptExecutor) driver)
					.executeScript(exScript);
			click.click();
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at editData :  " + exScript);
		}
	}

	@Override
	public void type(String xpath, String data) {
		try {
			// waitForAjax();
			driver.findElement(By.xpath(xpath)).clear();
			addLog("change data : " + data);
			driver.findElement(By.xpath(xpath)).sendKeys(data);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at editData :  " + xpath);
		}

	}

	@Override
	public String getElementText(WebElement element) {
		try {
			// waitForAjax();
			String text = element.getText();
			addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath : " + element);
		}
		return "";
	}

	@Override
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

	@Override
	public void typeid(String id, String data) {
		try {
			// waitForAjax();
			driver.findElement(By.id(id)).clear();
			addLog("change data : " + data);
			driver.findElement(By.id(id)).sendKeys(data);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at editData :  " + id);
		}

	}

	@Override
	public void opennewurl(String url) {
		try {
			// waitForAjax();
			addLog("Open new url : " + url);
			// Thread.sleep(2000);
			driver.navigate().to(url);
			// href example: "a[href*='long']"
			// Thread.sleep(1000);
			// waitForAjax();
		} catch (NoSuchElementException e) {
			addLog("No element exception : " + url);
			Assert.assertTrue(false);
		}
	}

	@Override
	public void assertbackgroundcolor(String name, String verify) {
		try {
			// waitForAjax();
			addLog("get background color : " + name);
			// Thread.sleep(2000);
			// ing color =
			// driver.findElement(By.name("btnK")).getCssValue("background-color");
			String color = driver.findElement(By.name(name)).getCssValue(
					"background-color");
			System.out.println("color is " + color);
			Assert.assertEquals(verify, color);
			// driver.navigate().to(url);
			// href example: "a[href*='long']"
			// Thread.sleep(1000);
			// waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + name);
		}
		// catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	@Override
	public void inputtexttofield(String text, String name) {
		try {
			// waitForAjax();
			addLog("input text to : " + name);
			// Thread.sleep(2000);
			// ing color =
			// driver.findElement(By.name("btnK")).getCssValue("background-color");
			driver.findElement(By.name(name)).sendKeys(text);
			;
			// driver.navigate().to(url);
			// href example: "a[href*='long']"
			// Thread.sleep(1000);
			// waitForAjax();
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + name);
		}
		// catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	@Override
	public String jsID(String Id) {
		String text = "";
		try {
			WebElement btn = (WebElement) ((JavascriptExecutor) driver)
					.executeScript("return document.getElementById(Id)");
			text = btn.getText();
			addLog("Text : " + text);
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByjsID :  " + Id);
		}
		return text;
	}

	@Override
	public void clickclassname(String classname) {
		// waitForAjax();
		try {
			addLog("Click : " + classname);
			driver.findElement(By.className(classname)).click();
			// waitForAjax();
			//
		} catch (NoSuchElementException e) {
			Assert.assertTrue(false);
			addLog("No element exception : " + classname);

		}
	}

	@Override
	public void click(String xpath) {
		// waitForAjax();
		try {
			addLog("Click : " + xpath);
			driver.findElement(By.xpath(xpath)).click();
			// waitForAjax();

		} catch (NoSuchElementException e) {
			addLog("No element exception : " + xpath);
			Assert.assertTrue(false);
		}
	}

	@Override
	public void clickid(String id) {
		// waitForAjax();
		try {
			addLog("Click : " + id);
			driver.findElement(By.id(id)).click();
			// waitForAjax();

		} catch (NoSuchElementException e) {
			addLog("No element exception : " + id);
			Assert.assertTrue(false);
		}
	}

}
