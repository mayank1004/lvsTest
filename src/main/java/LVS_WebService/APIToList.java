package LVS_WebService;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import LVSFields.Company;
import LVSFields.Fleet;
import LVSFields.UsageType;
import LVSFields.User;

public class APIToList {

	private String nextPage;

	public String getNextPage() {
		return nextPage;
	}

	public Map<String, Result> getURIResponse(String uri, String method, String pageURL, String auth) {

		Map<String, Result> resultMap = new HashMap<String, Result>();

		String fullURI = uri + method;
		System.out.println(fullURI);
		String jsonStr = null;

		try {

			Client client = Client.create();
			WebResource webResource = client.resource(fullURI);
			// Get response from RESTful Server get(ClientResponse.class);
			ClientResponse response = null;
			response = webResource.header("Authorization", auth)

					.get(ClientResponse.class);

			jsonStr = response.getEntity(String.class);

			JSONObject jsonObject = getJsonObject(jsonStr);

			nextPage = (String) jsonObject.get("next");

			resultMap = getResult(jsonObject);
			System.out.println("Successfully consume data from Restful API for this page...");

		} catch (Exception e) {
			System.out.println("*********Error:" + e.getMessage());
			System.out.println("Error consuming data from Restful API, please check:" + fullURI);
			e.printStackTrace();

		}

		return resultMap;

	}

	public static Date getDate(String date) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
		TemporalAccessor accessor = timeFormatter.parse(date);

		return Date.from(Instant.from(accessor));
	}

	public Map<String, Result> getResult(JSONObject jsonObj) {

		String id;
		Object serialNumber;
		Object durationSec;
		Object sizeKb;
		Date dateStarted;
		Date dateEnded;
		Date dateLogged;
		Date dateCreated;

		Map<String, Result> resultMap = new HashMap<String, Result>();
		JSONArray items = (JSONArray) jsonObj.get("items");
		
		for (int itemCount = 0; itemCount < items.size(); itemCount++) {
			JSONObject item_i = (JSONObject) items.get(itemCount);
			id = (String) item_i.get("id");
			serialNumber = item_i.get("serialNumber");
			durationSec = item_i.get("durationSec");
			sizeKb = item_i.get("sizeKb");
			dateStarted = getDate((String) item_i.get("dateStarted"));
			dateEnded = getDate((String) item_i.get("dateEnded"));
			dateLogged = getDate((String) item_i.get("dateLogged"));
			dateCreated = getDate((String) item_i.get("dateCreated"));

			List<UsageType> usageType = new ArrayList<UsageType>();
			JSONObject usageType_i = (JSONObject) item_i.get("usageType");
			Object usage_name = usageType_i.get("name");
			Object usage_previewCnt = usageType_i.get("previewCount");
			usageType.add(new UsageType(usage_name, usage_previewCnt));

			List<Company> company = new ArrayList<Company>();
			JSONObject company_i = (JSONObject) item_i.get("company");
			Object company_id = company_i.get("id");
			Object company_name = company_i.get("name");
			company.add(new Company(company_id, company_name));

			List<Fleet> fleet = new ArrayList<Fleet>();

			JSONObject fleet_i = (JSONObject) item_i.get("fleet");
			Object fleet_id = fleet_i.get("id");
			Object fleet_name = fleet_i.get("name");
			Object fleet_externalId = fleet_i.get("externalId");

			fleet.add(new Fleet(fleet_id, fleet_name, fleet_externalId));

			List<User> user = new ArrayList<User>();

			JSONObject user_i = (JSONObject) item_i.get("user");
			Object user_id = user_i.get("id");
			Object user_name = user_i.get("name");
			Object user_externalId = user_i.get("externalId");

			user.add(new User(user_id, user_name, user_externalId));

			Result rs1 = new Result(serialNumber, durationSec, sizeKb, dateStarted, dateEnded, dateLogged, dateCreated,
					usage_name, usage_previewCnt, company_id, company_name, fleet_id, fleet_name, fleet_externalId,
					user_id, user_name, user_externalId);
			resultMap.put(id, rs1);
		}

		return resultMap;

	}

	public static JSONObject getJsonObject(String jsonString) {
		JSONParser parser = new JSONParser();

		JSONObject jsonObject = null;

		try {
			Object obj = parser.parse(jsonString);
			jsonObject = (JSONObject) obj;

		} catch (ParseException e) {

			e.printStackTrace();
		}
		return jsonObject;
	}
}
