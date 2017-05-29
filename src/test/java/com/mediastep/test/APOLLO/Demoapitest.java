package test.java.com.mediastep.test.APOLLO;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;
import org.testng.annotations.Test;

import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIResults;

import test.java.com.mediastep.controller.BasePage;
import test.java.com.mediastep.controller.DemoapirResponse;

@SuppressWarnings("deprecation")
public class Demoapitest extends BasePage {

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}

	@Test(alwaysRun = true)
	public void TC_4() throws ClientProtocolException, IOException {
		String result = "";
		// String exception = null;
		//String APIUrl = "http://192.168.1.107:9080/api/account";
		//String APIUrl = "http://admin:admin@192.168.1.107:9080/api/account";
		// try {
//		userControl.clickid("account-menu");
//		userControl.click(".//*[@id='login']/span[2]");
//		userControl.typeid("username", "admin");
//		userControl.typeid("password", "admin");
//		userControl
//				.click("html/body/div[1]/div/div/div[2]/div/div[3]/form/div[4]/button");
		//userControl.opennewurl(APIUrl);
		// driver.navigate().to("http://192.168.1.83:9080/api/account");
		// WebElement webElement = driver.findElement(By.tagName("pre"));
		DemoapirResponse demoApiResponse = new DemoapirResponse();
		String ExpectedString = demoApiResponse.GetResponse();
		System.out.println(ExpectedString);
		//String ActualString = userControl.gettextelementbytagname("pre");
		String ActualString = "{\"login\":\"admin\",\"firstName\":\"Administrator\",\"lastName\":\"Administrator\",\"email\":\"admin@localhost\",\"activated\":true,\"langKey\":\"en\",\"authorities\":[\"ROLE_USER\",\"ROLE_ADMIN\"]}";
		// Assert.assertTrue(webElement.getText().equals(ExpectedString));
		Assert.assertTrue(ActualString.equals(ExpectedString));
		result = TestLinkAPIResults.TEST_PASSED;
		// try {
		try {
			updateTestLinkResult("TC-4", null, result);
		} catch (TestLinkAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

// } catch (TestLinkAPIException e) {
// TODO Auto-generated catch block
// e.printStackTrace();
// }
// } catch (Exception ex) {
// result = TestLinkAPIResults.TEST_FAILED;
// exception = ex.getMessage();
// ex.printStackTrace();
// } finally {
// try {
// updateTestLinkResult("TC-4", exception, result);
// } catch (TestLinkAPIException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// }
// }

// public void updateTestLinkResult(String testCase, String exception,
// String result) throws TestLinkAPIException {
// TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(DEVKEY, URLAPI);
// testlinkAPIClient.reportTestCaseResult(PROJECT_NAME, PLAN_NAME,
// testCase, BUILD_NAME, exception, result);
// }
// }
