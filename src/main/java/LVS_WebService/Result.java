package LVS_WebService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import LVSFields.Company;
import LVSFields.Fleet;
import LVSFields.UsageType;
import LVSFields.User;

public class Result {
	
	
	public Object serialNumber;
	public Object durationSec;
	public Object sizeKb;
	public Date dateStarted;
	public Date dateEnded;
	public Date dateLogged;
	public Date dateCreated;
	public Object usageType_name;
	public Object usageType_previewCount;
	public Object company_id;
	public Object company_name;
	public Object fleet_id;
	public Object fleet_name;
	public Object fleet_externalIds;
	public Object user_id;
	public Object user_name;
	public Object user_xternalId;
	
	
	public List<UsageType> usageType = new ArrayList<UsageType>();
	public List<Company> company = new ArrayList<Company>();
	public List<Fleet> fleet = new ArrayList<Fleet>();
	public List<User> user = new ArrayList<User>();
	
	
	public Result(Object serialNumber, Object durationSec, Object sizeKb, Date dateStarted,
			Date dateEnded, Date dateLogged, Date dateCreated, List<UsageType> usageType, List<Company> company,
			List<Fleet> fleet, List<User> user) {
		
		
		this.serialNumber = serialNumber;
		this.durationSec = durationSec;
		this.sizeKb = sizeKb;
		this.dateStarted = dateStarted;
		this.dateEnded = dateEnded;
		this.dateLogged = dateLogged;
		this.dateCreated = dateCreated;
		this.usageType = usageType;
		this.company = company;
		this.fleet = fleet;
		this.user = user;
	}


	public Result(Object serialNumber, Object durationSec, Object sizeKb, Date dateStarted, Date dateEnded,
			Date dateLogged, Date dateCreated, Object usageType_name, Object usageType_previewCount,
			Object company_id, Object company_name, Object fleet_id, Object fleet_name, Object fleet_externalIds,
			Object user_id, Object user_name, Object user_xternalId) {
		super();
		this.serialNumber = serialNumber;
		this.durationSec = durationSec;
		this.sizeKb = sizeKb;
		this.dateStarted = dateStarted;
		this.dateEnded = dateEnded;
		this.dateLogged = dateLogged;
		this.dateCreated = dateCreated;
		this.usageType_name = usageType_name;
		this.usageType_previewCount = usageType_previewCount;
		this.company_id = company_id;
		this.company_name = company_name;
		this.fleet_id = fleet_id;
		this.fleet_name = fleet_name;
		this.fleet_externalIds = fleet_externalIds;
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_xternalId = user_xternalId;
	}


	
	
	

}
