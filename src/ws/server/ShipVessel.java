/**
 * 
 */
package ws.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author ahtinq
 *
 */
public class ShipVessel {
	
	private static ShipVessel shipVessel;
	
	private int vesselID;
	private String vesselType;
	private String vesselDest;
	private int [] containerArray;
	
	static Connection conn;
	PreparedStatement preparedStatement = null;
	
	public ShipVessel(int vesselID, String vesselType, String vesslDest, int[] containerArray){
		this.vesselID = vesselID;
		this.vesselType = vesselType;
		this.vesselDest = vesselDest;
		this.containerArray = containerArray;
	}
	
	public static ShipVessel getShipVessel() {
		return shipVessel;
	}

	public static void setShipVessel(ShipVessel shipVessel) {
		ShipVessel.shipVessel = shipVessel;
	}
	
	public static int getShipVesselID(String type, String destination) {
		int vesselID = 0;
		
		//find out the number of suitable vessel
		vesselID = findExistingVessel(type, destination);
		if(vesselID == 0){
			vesselID = newVessel(type, destination);
		}
		
		return vesselID;
			
	}
	
	public synchronized static int newVessel(String vesselType, String vesselDest){
		int vesselID = 0;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/shipWareDB", "root", "iwillnotforget");
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO vessel(vesselType, vesselDest) VALUES " + "('"
							+ vesselType + "','" + vesselDest +"')", Statement.RETURN_GENERATED_KEYS);

			ps.executeUpdate(); 
			ResultSet rs = ps.getGeneratedKeys(); 
			rs.next(); 
			vesselID = rs.getInt(1); 
			ps.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vesselID;
	}
	
	public synchronized static int findExistingVessel(String vesselType, String vesselDest){
		
		int vesselID = 0;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/shipWareDB", "root", "iwillnotforget");
			PreparedStatement statement = conn.prepareStatement("SELECT vesselID FROM vessel WHERE vesselType = ? AND vesselDest = ? ORDER BY vesselID DESC LIMIT 1");    
			statement.setString(1, vesselType);
			statement.setString(2, vesselDest);
			ResultSet rs = statement.executeQuery();
            if(rs.next()){
	            //if not null, return vesselID
	            vesselID = Integer.parseInt(rs.getString(1));
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vesselID;
	}

	public synchronized static float getWeightLeft(int vesselID){
		float totalWeight = 0;
		float maxWeight = 9000;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/shipWareDB", "root", "iwillnotforget");
				PreparedStatement statement = conn.prepareStatement("SELECT SUM(weight) FROM containers INNER JOIN loadedVessel ON loadedVessel.containerID = containers.containerID WHERE vesselID = ?");    
				statement.setInt(1, vesselID);
				ResultSet rs = statement.executeQuery();
	            rs.next();
	            if(rs.getString(1) != null)
	            	totalWeight = Float.valueOf(rs.getString(1));
	            
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return maxWeight - totalWeight;
	}
	
	public synchronized static void loading(int vesselID, int containerID){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/shipWareDB", "root", "iwillnotforget");
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO loadedVessel(vesselID, containerID) VALUES " + "('"
							+ vesselID + "','" + containerID +"')");

			if (ps.executeUpdate() > 0)
				System.out.println("You have successfully loaded the container onto vessel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public String getAllWeights(){
//		
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/shipWareDB", "root", "iwillnotforget");
//			String query = "SELECT ";
//			PreparedStatement statement = conn.prepareStatement("SELECT SUM(weight) FROM containers INNER JOIN loadedVessel ON loadedVessel.containerID = containers.containerID WHERE vesselID = ?");    
//			statement.setInt(1, vesselID);
//			ResultSet rs = statement.executeQuery();
//            rs.next();
//            if(rs.getString(1) != null)
//            	totalWeight = Float.valueOf(rs.getString(1));
//            
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return "";
//	}
	
}
