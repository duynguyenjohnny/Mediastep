package test.java.com.mediastep.util;

import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestLink {
	/**
	 *
	 * @param testProject
	 * @param testPlan
	 * @param testCaseName
	 * @param build
	 * @param ExcutionNote
	 * @param result
	 * @throws TestLinkAPIException
	 */
	public static final String DEVKEY ="bed1de6a318ab11678ee0b96272eefba";
	public static final String URL ="http://192.168.1.124/testlink/lib/api/xmlrpc/v1/xmlrpc.php";

	public static void updateResult(String testProject, String testPlan, String testCaseName,String build, String ExcutionNote, String result) throws TestLinkAPIException {
		System.out.println("Start update result to Testlink");
		TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(DEVKEY,URL);
		testlinkAPIClient.reportTestCaseResult(testProject, testPlan, testCaseName, build, ExcutionNote, result);
		System.out.println("End update result to Testlink");
	}

}
