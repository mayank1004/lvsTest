package GP_Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectGP {
	
	ResultSet rs;
	public Connection db; 
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public final static String devGP = "jdbc:postgresql://dcgpm.drivecaminc.loc:5432/lytxbi_dev"; 
	public final static String stageGP = "jdbc:postgresql://hqs1v-grnmpp01.drivecaminc.loc:5432/lytxbi_stg"; 
	public final static String prodGP = "jdbc:postgresql://acedwm.drivecam.net:5432/lytxbi";
	

	public  void connect(String connectionString, String userName, String pwd) {
		
		
		// this.connectionURL = connectionURL;

		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Connecting to...."+connectionString);
			db = DriverManager.getConnection(connectionString, userName, pwd);
			System.out.println("Connected");
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public  void closeConnection() {

		try {
			db.close();
			System.out.println("Connection closed....");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public  ResultSet getQueryResult(String query){
		
		//Connection conn1 = connect(connection, userName, password);
		if(db!=null){
		
		System.out.println("Query Executed :"+query);
		
		Statement st = null;
			 
			
			try {
				System.out.println("Creating statement....");
				st = db.createStatement();
				System.out.println("Completed creating statement");
				
				System.out.println("Creating results....");
				rs = st.executeQuery(query);		
				System.out.println("completed creating result");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				closeConnection();
				e.printStackTrace();
			}
			finally{
				closeConnection();
			}
			
			return rs;
		} else{
			System.out.println("unable to establish connection....");
			return null;
		}
			
	}

}
