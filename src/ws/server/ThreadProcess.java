/**
 * 
 */
package ws.server;

import java.io.EOFException;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.websocket.Session;

/**
 * @author YunTing
 *
 */
public class ThreadProcess extends Thread implements Runnable{

	static ConcurrentLinkedQueue<Thread> queue;
	public Container container;
	
	public Session session;
    public long burstTime;
    public long arrivalTime;
    public final int executionTime = 0;
    public int timeSlice = 10000;

    public int countHigh = 0;
    
    static int counter = 0;
    static Timer timer;
    public long startTime;
    public float weightLeft = 0;
    public boolean validate;
	
    static Connection conn;
	PreparedStatement preparedStatement = null;
	
	public long getExecutionTime(){
		return executionTime;
	}
	
	public boolean getValidation(){
		return validate;
	}
	
	public ThreadProcess(ConcurrentLinkedQueue<Thread> q, Container c, long startTime){
		this.queue = q;
	    this.container = c;
	    this.startTime = startTime;
	}
	
	public void insertValues(String containerName, String containerType, String destination, float weight, String userName) throws EOFException {

		synchronized (this) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/shipWareDB", "root", "iwillnotforget");
				PreparedStatement ps = conn.prepareStatement(
						"INSERT INTO containers(containerName, containerType, destination, weight, userName) VALUES " + "('"
								+ containerName + "','" + containerType +"','" + destination + "','" + weight + "','" + userName + "')");

				if (ps.executeUpdate() > 0)
					System.out.println("You have successfully add a container for Client " + queue.peek().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public float getWeight(String type, String destination, float weight) throws EOFException {

		float totalWeight = 0;
		float maxWeight = 9000;
		synchronized (this) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/shipWareDB", "root", "iwillnotforget");
				PreparedStatement statement = conn.prepareStatement("SELECT SUM(weight) FROM containers WHERE containerType = ? AND destination = ?");    
				statement.setString(1, type);
				statement.setString(2, destination);
				ResultSet rs = statement.executeQuery();
	            rs.next();
	            if(rs.getString(1) != null)
	            	totalWeight = Float.valueOf(rs.getString(1));
	            
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return maxWeight - totalWeight;
	}
	
	
	@Override
	public void run(){
		
		Thread current = Thread.currentThread();
		System.out.println("ThreadProcess: " + current.getId());
		//get remainingWeight from ShipVessel
		try {
			weightLeft = getWeight(container.getContainerType(), container.getDestination(), container.containerWeight);
		} catch (EOFException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			System.out.println("Thread " + current.getId() + " Sleeping....");
			current.sleep(5000);
			insertData();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
//		
//		System.out.println("Back on running Thread: " + queue.peek().getId());
		
		
		/* Round Robin*/
//		System.out.println("Queue Size: " + queue.size());
//		burstTime = startTime + 2000;
//		Thread nextThread;
//		
//		this.setPriority(6);
//		System.out.println("Thread " + Thread.currentThread().getId() + " is running...");
//		
//		if(System.nanoTime()/1000000 < burstTime) { // wait for max 1 seconds.
//	    	System.out.println((System.nanoTime() - startTime)/1000000 + "milliseconds");
//	    	if(Thread.currentThread().isAlive()) {
//	    		insertData();
//			}
//	    }else{
//	    	try {
//	    		nextThread = queue.peek();
//		    	if(nextThread != null && nextThread.isAlive()){
//		    		nextThread.setPriority(4);
//		    		Thread.currentThread().sleep(2000);
//		    		System.out.println("Context switch");
//		    		nextThread.setPriority(2);
//		    	}
//	    	} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    }
		
		/* Priority */
		/* After 3 highest Priority, run a normal priority to prevent starvation */
//		System.out.println("Doing Priority");
//		if(current.getPriority() == MAX_PRIORITY){
//			counting();
//		}
//		 
//		if(countHigh == 3 && current.getPriority() == MAX_PRIORITY){
//			current.yield();
//		}else{
//			countHigh = 0;
//		}
//	
//		/* Add to DB */
//		if(weightLeft > container.getContainerWeight()){
//			insertData();
//		}else{
//			validate = false;
//		}
		
	}
	
	public void insertData(){
		try {
			insertValues(container.getContainerName(), container.getContainerType(), container.getDestination(), container.getContainerWeight(), container.getUserName());
			System.out.println("Arrival Time (" + queue.peek().getId() + "): " + startTime/1000000 + " milliseconds");
			System.out.println("Finish Time (" + queue.peek().getId() + "): " + System.nanoTime()/1000000 + " milliseconds");
			System.out.println("Total Execution Time (" + queue.peek().getId() + "): " + ((System.nanoTime() - startTime) / 1000000) + " milliseconds");
			validate = true;
			return;
		} catch (EOFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  
	public synchronized int counting(){
		countHigh++;
		return countHigh;
	}
	
	public void roundRobin(){
	    
//		if(queue.size() > 1){
//		    queue.poll();
//		    queue.offer(Thread.currentThread());
//		    System.out.println("Change Thread");
//		    Thread.currentThread().yield();
//		    roundRobin();
//		}else{
//			insertData();
//		}
	}
}
