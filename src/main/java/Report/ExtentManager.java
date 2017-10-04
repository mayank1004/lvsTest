package Report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import common.Configuration;

public class ExtentManager {
private static ExtentReports extent;
public static String path = new Configuration().getReportPath();

    public static ExtentReports getInstance() {
    	if (extent == null)
    		createInstance("test-output/extent.html", "test","test");
    	
        return extent;
    }
    
    public static ExtentReports createInstance(String fileName, String title, String name) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle(title);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(name);
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        return extent;
    }

}
