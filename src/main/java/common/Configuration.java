package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;



public class Configuration {

	static String  childPath = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static public String path = new File(childPath).getParentFile().getParentFile().getPath();
	

	
	private  Properties getConfigFile(){
		File credentialFile;
		
		credentialFile = new File(path + "/Config/config.properties");
		
		
		FileReader reader = null;
		try {
			reader = new FileReader(credentialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}

		Properties props = new Properties();

		// load the properties file:
		try {
			props.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	private  Properties getCredentialFile(){
		File credentialFile;
		
		//credentialFile = new File(path + "/Config/credential.properties");
		credentialFile = new File("C:/Users/mayank.bhatt/Documents/Credential/credential.properties");
		
		FileReader reader = null;
		try {
			reader = new FileReader(credentialFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}

		Properties props = new Properties();

		// load the properties file:
		try {
			props.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	public String getURI(){
		Configuration config1 = new Configuration();
		Properties prop1 =  config1.getConfigFile();
		return (String) prop1.get("uri");
	}
	
	public String getAllMethod(){
		Configuration config1 = new Configuration();
		Properties prop1 =  config1.getConfigFile();
		return (String) prop1.get("GETMethodAll");
	}
	
	public String getQuery(){
		Configuration config1 = new Configuration();
		Properties prop1 =  config1.getConfigFile();
		return (String) prop1.get("query");
	}
	
	
	public String getGPEnv(){
		Configuration config1 = new Configuration();
		Properties prop1 =  config1.getConfigFile();
		return (String) prop1.get("GPEnv");
	}
	
	public String getUserName(){
		Configuration config1 = new Configuration();
		Properties prop1 =  config1.getCredentialFile();
		return (String) prop1.get("userName");
	}
	
	public String getPassword(){
		Configuration config1 = new Configuration();
		Properties prop1 =  config1.getCredentialFile();
		return (String) prop1.get("password");
	}
	
	public String getReportPath(){
		Configuration config1 = new Configuration();
		Properties prop1 =  config1.getConfigFile();
		return (String) prop1.get("reportPath");
	}
	
}
