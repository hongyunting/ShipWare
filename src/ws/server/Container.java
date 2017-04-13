package ws.server;
/**
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

/**
 * @author YunTing
 *
 */
public class Container {

	public String containerName;
	public String containerType;
	public String destination;
	public float containerWeight;
	public String userName;
		
	static Connection conn;
	PreparedStatement preparedStatement = null;
	
	public Container(){
		
	}
	
	public Container(String containerName, String containerType, String destination, float containerWeight, String userName){
		this.containerName = containerName;
		this.containerType = containerType;
		this.containerWeight = containerWeight;
		this.destination = destination;
		this.userName = userName;
	}
	
	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getContainerName() {
		return containerName;
	}
	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}
	public float getContainerWeight() {
		return containerWeight;
	}
	public void setContainerWeight(float containerWeight) {
		this.containerWeight = containerWeight;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public synchronized static int addContainer(String containerName, String containerType, String destination, float weight, String userName){
		int containerID = 0;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/shipWareDB", "root", "iwillnotforget");

			
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO containers(containerName, containerType, destination, weight, userName) VALUES " + "('"
							+ containerName + "','" + containerType +"','" + destination + "','" + weight + "','" + userName + "')", Statement.RETURN_GENERATED_KEYS);

			ps.executeUpdate(); 
			ResultSet rs = ps.getGeneratedKeys(); 
			rs.next(); 
			containerID = rs.getInt(1); 
			ps.close(); 
			System.out.println("You have successfully add a container");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return containerID;
	}
	
//	public static int getContainerID(){
//		int containerID = 0;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/shipWareDB", "root", "iwillnotforget");
//			PreparedStatement statement = conn.prepareStatement("SELECT LAST(containerID) AS containerID FROM containers");    
//			ResultSet rs = statement.executeQuery();
//            rs.next();
//            //if not null, return vesselID
//            if(rs.getString(1) != null){
//            	containerID = Integer.parseInt(rs.getString(1));
//            }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return containerID;
//	}
}
