package test.java.com.mediastep.test.APOLLO;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.java.com.mediastep.controller.BasePage;
import test.java.com.mediastep.models.CreateAccountScreen;
import test.java.com.mediastep.models.LoginScreen;
import test.java.com.mediastep.util.TestLink;
import testlink.api.java.client.TestLinkAPIResults;

//@Listeners(TestMethodListener.class)
public class Registration extends BasePage {

	String className = this.getClass().getSimpleName();
	private CreateAccountScreen createAccountScreen;
	private LoginScreen loginScreen;

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
		userControl.addLog("click create account link");
		Thread.sleep(1000);
		userControl.click(loginScreen.createaccountlink);

		userControl.addLog("VP: Title appears");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Title),sMethodName +" Title was not found");
		userControl.addLog("VP: Email appears");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Email),sMethodName +" Email was not found");
		userControl.addLog("VP: Password appears");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Password),sMethodName +" Password was not found");
		userControl.addLog("VP: FirstName appears");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.FirstName),sMethodName +" FirstName was not found");
		userControl.addLog("VP: LastName appears");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.LastName),sMethodName +" LastName was not found");
		userControl.addLog("VP: Birthday appears");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Birthday),sMethodName +" Birthday was not found");
		userControl.addLog("VP: Male appears");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Male),sMethodName +" Male was not found");
		userControl.addLog("VP: Female appears");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Female),sMethodName +" Female was not found");
		userControl.addLog("VP: Continuebutton appears");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.Continuebutton),sMethodName +" Continuebutton was not found");
		userControl.addLog("VP: SHOW appears");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.SHOW),sMethodName +" SHOW was not found");
		userControl.addLog("VP: CreateAccbyPhone appears");
		Assert.assertEquals(true, userControl.isElementPresent(createAccountScreen.CreateAccbyPhone),sMethodName +" CreateAccbyPhone was not found");

		userControl.addLog("Type in password");
		userControl.type(createAccountScreen.Password,userControl.randomchars(6));
		userControl.addLog("Click on show");
		userControl.click(createAccountScreen.SHOW);
		userControl.addLog("Click on hide");
		userControl.click(createAccountScreen.SHOW);
		userControl.addLog("Click on Birthday");
		userControl.click(createAccountScreen.Birthday);
		userControl.addLog("Click on Female");
		userControl.click(createAccountScreen.Female);
		userControl.addLog("Click on male");
		userControl.click(createAccountScreen.Male);
		//userControl.addLog("Click on Term of use");
		//userControl.addLog("Click on Privacy Policy");
		//userControl.addLog("Click on Create account by phone number");
		//userControl.addLog("Click on Create account by Email");
		userControl.addLog("Input all madatory fields");
		userControl.type(createAccountScreen.Email,userControl.randomString()+"@yopmail.com");
		userControl.type(createAccountScreen.FirstName,userControl.randomString());
		userControl.type(createAccountScreen.LastName,userControl.randomString());
		userControl.type(createAccountScreen.Birthday,userControl.randomDataOfBirth(1900,2017));
		userControl.addLog("Touch on Continue button");
		userControl.click(createAccountScreen.Continuebutton);

		TestLink.updateResult(PROJECT_NAME, PLAN_NAME, "WEB_SN_TC-1", BUILD_NAME, null, TestLinkAPIResults.TEST_PASSED);
	}

}
