package common;

import java.util.Map;
import java.util.Map.Entry;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import LVS_WebService.Result;
import Report.ExtentManager;

public class Comparison {

	private static boolean compare(String str1, String str2, ExtentTest test) {
		boolean result =  (str1 == null ? str2 == null : str1.equals(str2));
		if(result){
			test.pass("API value is:"+str1+" <br />DB Value is:"+str2);
		} else{
			test.fail("API value is:"+str1+" <br />DB Value is:"+str2);
		}
		return result;
	}
	
	private static boolean compare(int int1, int int2, ExtentTest test) {
	    boolean result = int1==int2;
	    if(result){
			test.pass("API value is:"+int1+" <br />DB Value is:"+int2);
		} else{
			test.fail("API value is:"+int1+" <br />DB Value is:"+int2);
		}
		return result;
	}
	
	private static boolean compare(Object obj1, Object obj2, ExtentTest test) {
	    boolean result =  (obj1 == null ? obj2 == null : obj1.equals(obj2));
	    if(result){
			test.pass("API value is:"+obj1+" <br />DB Value is:"+obj2);
		} else{
			test.fail("API value is:"+obj1+" <br />DB Value is:"+obj2);
		}
		return result;
	}
	
	private static boolean compare(Long int1, Long int2, ExtentTest test) {
	    boolean result =  int1==int2;
	    if(result){
			test.pass("API value is:"+int1+" <br />DB Value is:"+int2);
		} else{
			test.fail("API value is:"+int1+" <br />DB Value is:"+int2);
		}
		return result;
	}
	
	public  void comparison(Map<String, Result> APIMap, Map<String, Result> DBMap){
		int testNumber = 1;
		
		ExtentReports report1 = ExtentManager.createInstance(ExtentManager.path, "LVS","LVS_testSuite");
		System.out.println("Creating report, path is:"+Configuration.path+"/Report.html");
		
		ExtentTest test1 = report1.createTest(testNumber+") Comparing total numbers of rows");
		testNumber++;
		compare(APIMap.size(), DBMap.size(), test1);
		
		for(Entry<String, Result> map: APIMap.entrySet()){
			ExtentTest test = report1.createTest(testNumber+") Creating test to validate item id:"+map.getKey());
			if(DBMap.containsKey(map.getKey())){
				
				
				compare(map.getValue().company_id, DBMap.get(map.getKey()).company_id,test);
				compare(map.getValue().company_name, DBMap.get(map.getKey()).company_name,test);
				compare(map.getValue().serialNumber, DBMap.get(map.getKey()).serialNumber,test);
				compare(map.getValue().fleet_externalIds, DBMap.get(map.getKey()).fleet_externalIds,test);
				//type other columns to compare
				
			} else{
				test.fail("Id "+map.getKey()+" is not present in DB");
			}
			testNumber++;
		}
		testNumber++;
		report1.flush();
		System.out.println("Completed generating report...");
	}
}
