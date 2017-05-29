package test.java.com.mediastep.controller;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.io.File;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import test.java.com.mediastep.util.DateTime;
import test.java.com.mediastep.util.FolderFile;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;

public abstract class BasePage {
	// DEVKEY and URL api
	public static String DEVKEY = "bed1de6a318ab11678ee0b96272eefba";
	public static String URLAPI = "http://192.168.1.124/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
	// public static String URLAPI = "";
	// Substitute your project name Here
	public final String PROJECT_NAME = "SN_WEB";

	// Substitute your test plan Here
	public final String PLAN_NAME = "SN_TestPlan";

	// Substitute your build name
	public final String BUILD_NAME = "Sprint_06";

//	public String Testlink_ProjectName;
//	public String Testlink_TestPlanName;
//	public String Testlink_BuildName;
	private static String reportFolder = "";
	protected ExtentTest test;
	private static ExtentReports extent;
	final String reportPath = "./Extent.html";
	protected String url;
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected UserController userControl;
	protected BaseController baseAction;

	public void setUp(String propertyFile) throws Exception {
		try{
			System.out.println("Before Method: Setup");
			//initControllers();
		}catch (Exception ex){
			System.out.println("Error Before Method: Setup:" + ex.getMessage());
		}

	}

	public enum OS {
		OS_LINUX, OS_WINDOWS, OS_OTHERS, OS_SOLARIS, OS_MAC_OS_X
	}

	protected void initControllers(WebDriver driver) {
		userControl = new UserController(driver);
		baseAction = new BaseController(driver);
	};

	protected abstract void initData();

	@BeforeSuite
	protected void beforeSuite() {
		reportFolder = DateTime.getCurrentTime("MM-dd-yyyy_HHmmss");
		String reportPath = "test-reports/" + reportFolder + "/images";
		FolderFile.createMutilFolder(reportPath);
		extent = ExtentManager.getReporter("test-reports/" + reportFolder
				+ "/ExtentReport.html");
	}

	@Parameters({ "browser", "url" })
	@BeforeMethod
	public void initializeDriver(@Optional("firefox") String browser,
			@Optional("https://beecow.mediastep.ca/") String url, Method method) {
		System.out.println("*********************** Start :" + method.getName()
				+ " ***********************");
		try {
			this.url = url;
			if (browser.equalsIgnoreCase("firefox")) {
				System.out.println(" Executing on FireFox");
				setDriverSystemPath();
				driver = new FirefoxDriver();
				wait = new WebDriverWait(driver, 600);
				// String selectLinkOpeninNewTab =
				// Keys.chord(Keys.CONTROL,Keys.RETURN);
				// driver.findElement(By.linkText(url)).sendKeys(selectLinkOpeninNewTab);
			} else if (browser.equalsIgnoreCase("chrome")) {
				System.out.println(" Executing on CHROME");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("chrome.switches", "--disable-extensions");
				options.addArguments("disable-popup-blocking");
				options.addArguments("disable-impl-side-painting");
				System.setProperty("webdriver.chrome.driver",
						"driver//chromedriver.exe");
				driver = new ChromeDriver(options);
				wait = new WebDriverWait(driver, 600);
				// driver = new ChromeDriver();

			} else if (browser.equalsIgnoreCase("ie")) {
				System.out.println("Executing on IE");
				System.setProperty("webdriver.ie.driver",
						"driver/IEDriverServer64.exe");
				driver = new InternetExplorerDriver();
				wait = new WebDriverWait(driver, 600);

			} else {
				throw new IllegalArgumentException(
						"The Browser Type is Undefined");
			}

			String URL = "https://beecow.mediastep.ca/";
			try {
				driver.navigate().to(URL);
			} catch (WebDriverException e) {
				System.out.println("URL was not loaded");
			}
			test = extent.startTest(method.getName()).assignCategory(
					getClass().getSimpleName());
			// driver.get(url);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
			driver.manage().window().maximize();
			initControllers(driver);
			initData();

		} catch (TimeoutException e) {
			System.out.println("Page load time out exception");
			driver.navigate().refresh();
			driver.quit();
		} catch (UnreachableBrowserException e) {
			System.out.println("Unreacheable browser exception");
			driver.navigate().refresh();
			driver.quit();

		}

	}

	private void setDriverSystemPath() {
		if (getOs() == OS.OS_WINDOWS) {
			System.setProperty("webdriver.gecko.driver",
					"driver//geckodriver.exe");
		} else if (getOs() == OS.OS_LINUX) {
			System.setProperty("webdriver.gecko.driver", "driver/geckodriver");
		}
		// Add another check when have a new OS
	}

	public OS getOs() {
		String osName = System.getProperty("os.name");
		String osNameMatch = osName.toLowerCase();
		OS osType = null;
		;
		if (osNameMatch.contains("linux")) {
			osType = OS.OS_LINUX;
		} else if (osNameMatch.contains("windows")) {
			osType = OS.OS_WINDOWS;
		} else if (osNameMatch.contains("solaris")
				|| osNameMatch.contains("sunos")) {
			osType = OS.OS_SOLARIS;
		} else if (osNameMatch.contains("mac os")
				|| osNameMatch.contains("macos")
				|| osNameMatch.contains("darwin")) {
			osType = OS.OS_MAC_OS_X;
		} else {
			osType = OS.OS_OTHERS;
		}
		return osType;
	}

	@AfterMethod(alwaysRun = true)
	public void afterTest(ITestResult iTestResult, Method method) {
		String exception = null;
		String result = "";
		if (driver == null)
			return;
		Reporter.setCurrentTestResult(iTestResult);
		MouseMover();
		if (!iTestResult.isSuccess()) {
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			test.log(LogStatus.FAIL, iTestResult.getThrowable());
			//test.log(LogStatus.FAIL, iTestResult.getThrowable() + screenShoot());
			result = TestLinkAPIResults.TEST_FAILED;
			exception = iTestResult.toString();
			// exception =iTestResult.getMessage();
			System.out.println(exception);
			String TCNAME = iTestResult.getName().toString().toUpperCase();
			String testcaseid = TCNAME.replace("_", "-");
			System.out.println(testcaseid);
			try {
				updateTestLinkResult(testcaseid, exception, result);
				//updateTestLinkResult(PROJECT_NAME, PLAN_NAME, "WEB_SN_TC-1", BUILD_NAME, null, TestLinkAPIResults.TEST_PASSED);
			} catch (TestLinkAPIException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			userControl.addErrorLog(exception);
			try {
				File scrnsht = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				String destDir = "target/surefire-reports/screenshots";
				DateFormat dateFormat = new SimpleDateFormat(
						"dd_MMM_yyyy__hh_mm_ssaa");
				String destFile =  dateFormat.format(new Date()) + ".png" + "_"+ method.getName();
				org.apache.commons.io.FileUtils.copyFile(scrnsht, new File(
						destDir + "/" + destFile));
				Reporter.log("View error : <a href=../screenshots/" + destFile
						+ ">Screenshot</a>");

			} catch (Exception e) {
				e.printStackTrace();
			}
			//String imgPath = "";
			String imgName = DateTime.getCurrentTime("MM-dd-yyyy_HHmmss") + ".png"+ "_"+ method.getName();
			try {
				File scrnsht = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				File path = new File("").getAbsoluteFile();
				String pathfile = path.toString() + "\\test-reports\\"
						+ reportFolder + "\\images\\" + imgName;
				FileUtils.copyFile(scrnsht, new File(pathfile));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//imgPath = test.addScreenCapture("./images/" + imgName);
			//return imgPath;
			Reporter.log("FAILED", true);
		} else {
			Reporter.log("PASSED", true);
			test.log(LogStatus.PASS, "Your Test was Passed");
		}
		// close browser after finish test case
		if (driver == null) {
			return;
		}
		System.out.println("Close browser");
		driver.close();
		Reporter.log(
				"===========================================================================",
				true);
		extent.endTest(test);
		extent.flush();
		driver.quit();
	}

	@AfterSuite
	protected void afterSuite() {
		extent.close();
	}

	public void updateTestLinkResult(String testCase, String exception,
			String result) throws TestLinkAPIException {
		TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(DEVKEY,
				URLAPI);
		testlinkAPIClient.reportTestCaseResult(PROJECT_NAME, PLAN_NAME,
				testCase, BUILD_NAME, exception, result);
	}

	public void MouseMover() {
		try {
			Robot robot = new Robot();
			Point point = MouseInfo.getPointerInfo().getLocation();
			robot.mouseMove(point.x + 120, point.y + 120);
			robot.mouseMove(point.x, point.y);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public String screenShoot() {
//		String imgPath = "";
//		String imgName = DateTime.getCurrentTime("MM-dd-yyyy_HHmmss") + ".png";
//		try {
//			File scrnsht = ((TakesScreenshot) driver)
//					.getScreenshotAs(OutputType.FILE);
//			File path = new File("").getAbsoluteFile();
//			String pathfile = path.toString() + "\\test-reports\\"
//					+ reportFolder + "\\images\\" + imgName;
//			FileUtils.copyFile(scrnsht, new File(pathfile));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		imgPath = test.addScreenCapture("./images/" + imgName);
//		return imgPath;
//	}
}
