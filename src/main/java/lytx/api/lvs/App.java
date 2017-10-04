package lytx.api.lvs;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import GP_Connection.ConnectGP;
import LVS_WebService.APIToList;
import LVS_WebService.Authorization;
import LVS_WebService.Result;
import common.Comparison;
import common.Configuration;

/*
 * Approach:
 * 1. Consume lvs_reestAPI and store it as collection MAP called "APIresult" - item_id as key and rest of the JSONfields as value
 * 2. Consume GP data and store it as collection MAP called "gpResult" - item_id as key and rest of the columns as value
 * 3. Iterate through each element from "APIResult" and compare values with "gpResult"
 * 4. Generate test report
 * 
 * TBD: need to add Logs, currently manual checking for counts
 */


public class App {
	static Map<String, Result> APIresult = new HashMap<String, Result>();
	static List<Integer> gpResult = new ArrayList<Integer>();

	
	public static void main(String[] args) {

		
		
		
		//Restful API data consumption
		System.out.println("Starting thread for LVS rest api consumption....");
		APIresult = getAPIResult();

			

		// GP data consumption
		System.out.println("Starting thread for GP data consumption....");
		gpResult = getDBResult();
				
				
		
		//Comparison
		Comparison compare1 = new Comparison();
		System.out.println("Started comparing data.....");
		compare1.comparison(APIresult, APIresult);

		
		System.out.println("Completed execution...");
		System.out.println("***************************");
		System.out.println("Test report generated at path: C:\\Users\\mayank.bhatt\\workspace\\lvs\\Report.html");
		System.out.println("***************************");
	}
	
	

	
	public static Map<String, Result> getAPIResult() {
		
		// get authentication string
		System.out.println("Getting auth key...");
		String auth = Authorization.getAuth("");

		// Get values from configuration file
		Configuration config1 = new Configuration();
		
		// uri string
		String uri = config1.getURI();
		
		// rest method name
		String restMethodName = config1.getAllMethod();

		// instance of APIToList class that will be used to send api request,
		// get response in JSON and return in map/string
		APIToList lvs = new APIToList();

		// Create Map to store api response (Items JSONArray) in result map -
		// key is Item id
		Map<String, Result> result = new HashMap<String, Result>();

		// initialize next page URL with no url
		String nextPageURL = "";

		// loop runs until api response has nextPage == null
		int page = 1;
		
		while (nextPageURL != null) {
			System.out.println("------------------------------");
			
			// Sends API request with parameters, fetch single page data and return as "APIPageResult" map and keep adding "APIPageResult" pages into result map
			System.out.println("Getting data for page:"+page);
			Map<String, Result> APIPageResult = lvs.getURIResponse(uri, restMethodName, "", auth);
			System.out.println("Page "+page+" returned "+APIPageResult.size()+" number of records..");
			result.putAll(APIPageResult);
			
			// gets the nextPage URL
			nextPageURL = lvs.getNextPage();
			System.out.println("Next page token is:"+nextPageURL);
			page++;
			System.out.println("------------------------------");
		}

		return result;
	}

	public static List<Integer> getDBResult() {

		List<Integer> resultList = new ArrayList<Integer>();
		ResultSet rs = null;

		ConnectGP connect1 = new ConnectGP();
		Configuration config1 = new Configuration();
		String GPEnv = config1.getGPEnv().toLowerCase();

		String connection = null;
		switch (GPEnv) {
		case "dev":
			connection = ConnectGP.devGP;
			break;
		case "prod":
			connection = ConnectGP.prodGP;
			break;
		case "stage":
			connection = ConnectGP.stageGP;
			break;
		default:
			connection = ConnectGP.devGP;
			break;
		}

		String userName = config1.getUserName();
		String password = config1.getPassword();
		String query = config1.getQuery();
		
		
		System.out.println("Connecting to GP db....");
		connect1.connect(connection, userName, password);
		
		System.out.println("Running query:"+query);
		rs = connect1.getQueryResult(query);
		try {
			while (rs.next()) {
				resultList.add(rs.getInt("company_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Returning resultset...");
		return resultList;
	}




	
}
