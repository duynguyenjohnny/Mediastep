package test.java.com.mediastep.test.APOLLO;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.java.com.mediastep.models.CreateAccountScreen;
import test.java.com.mediastep.util.TestLink;
import testlink.api.java.client.TestLinkAPIResults;

import test.java.com.mediastep.controller.BasePage;

//@Listeners(TestMethodListener.class)
public class DemoTestlink extends BasePage {

	String className = this.getClass().getSimpleName();
	private CreateAccountScreen createAccountScreen;

	static String SNPropertiesFileSprint = "SN.properties";

	@BeforeMethod
	public void setUp() throws Exception {
		super.setUp(SNPropertiesFileSprint);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}

	@Test(alwaysRun = true)
	public void WEB_SN_TC_1() throws Exception {
		userControl.addLog("WEB_SN_TC_1 Verify UI create account screen");
		String sMethodName = new Object() {
		}.getClass().getEnclosingMethod().getName();

		String URL = "https://beecow.mediastep.ca/signup";
		userControl.opennewurl(URL);
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Email),sMethodName +" was failed");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Password),sMethodName +" was failed");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.FirstName),sMethodName +" was failed");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.LastName),sMethodName +" was failed");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Birthday),sMethodName +" was failed");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Male),sMethodName +" was failed");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Female),sMethodName +" was failed");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Continuebutton),sMethodName +" was failed");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.SHOW),sMethodName +" was failed");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.CreateAccbyPhone),sMethodName +" was failed");

		TestLink.updateResult(PROJECT_NAME, PLAN_NAME, "WEB_SN_TC-1", BUILD_NAME, null, TestLinkAPIResults.TEST_PASSED);
	}

}
