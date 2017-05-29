package test.java.com.mediastep.controller;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;


public class ExtentManager {
		private static ExtentReports extent;
	    
	    public synchronized static ExtentReports getReporter(String filePath) {
	        if (extent == null) {
	            extent = new ExtentReports(filePath, true);
	            
	            extent
	            	.addSystemInfo("User Name", "Duy")
	                .addSystemInfo("Host Name", "Nguyen")
	                .addSystemInfo("Environment", "QA");
	            
	            extent.loadConfig(new File("Extent-Config.xml"));
	        }
	        
	        return extent;
	    }
}
