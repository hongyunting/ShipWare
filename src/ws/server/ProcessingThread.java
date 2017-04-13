/**
 * 
 */
package ws.server;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

import javax.websocket.Session;

/**
 * @author ahtinq
 *
 */
public class ProcessingThread extends Thread implements Runnable {

	Session session;
	static ConcurrentLinkedQueue<Session> queue;
	Container container;
	long startTime;
	public static int countHigh = 0;
	boolean check = false;
			
	public ProcessingThread(Session session) {
		this.session = session;
	}

	public ProcessingThread(ConcurrentLinkedQueue<Session> queue, Container c, long startTime, Session session){
	      this.queue = queue;
	      this.container = c;
	      this.startTime = startTime;
	      this.session = session;
	}
	
	@Override
	public void run(){
		System.out.println(Thread.currentThread());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		synchronized(container){
			System.out.println("HERE");
			for(int i=0; i< 10; i++){
				System.out.println(i);
			}
		Thread current = Thread.currentThread();
		queue.add(session);
		try{
			
			//Thread.sleep(2000);
			
			int containerID = Container.addContainer(container.getContainerName(), container.getContainerType(), container.getDestination(), container.getContainerWeight(), container.getUserName());
			System.out.println("Container ID: " + containerID);
			int vesselID = ShipVessel.getShipVesselID(container.getContainerType(), container.getDestination());
			System.out.println("Vessel ID: " + vesselID);
			
			//check weight
			float weightLeft = ShipVessel.getWeightLeft(vesselID);
			if(weightLeft == 0){
				//create new vessel and insert
				vesselID = ShipVessel.getShipVesselID(container.getContainerType(), container.getDestination());
			}
			//insert the mapping
			ShipVessel.loading(vesselID, containerID);
			
				
			showTiming(container);
			check = true;
			sendAll("Done: " + session.getId());
				
		}catch (Exception e) {
	        e.printStackTrace();
	    }
		}
	}
	
	public void showTiming(Thread current) throws EOFException{
		System.out.println("Arrival Time (" + current.getId() + "): " + startTime/1000000 + " milliseconds");
		System.out.println("Finish Time (" + current.getId() + "): " + System.nanoTime()/1000000 + " milliseconds");
		System.out.println("Total Execution Time (" + current.getId() + "): " + ((System.nanoTime() - startTime) / 1000000) + " milliseconds");
	}
	
	public void showTiming(Container c) throws EOFException{
		System.out.println("Arrival Time (" + c.getContainerName() + "): " + startTime/1000000 + " milliseconds");
		System.out.println("Finish Time (" + c.getContainerName() + "): " + System.nanoTime()/1000000 + " milliseconds");
		System.out.println("Total Execution Time (" + c.getContainerName() + "): " + ((System.nanoTime() - startTime) / 1000000) + " milliseconds");
	}
	
	private void sendClient(String str) {
        try {
        	System.out.println("In send Client Method");
        	session.getBasicRemote().sendText(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private static void sendAll(String msg) throws IOException {
		try {
			/* Send the new Weight of all possible combination to all open WebSocket sessions */
			ArrayList<Session> closedSessions = new ArrayList<>();
			for (Session session : queue) {
				if (!session.isOpen()) {
					System.err.println("Closed session: " + session.getId());
					closedSessions.add(session);
				} else {
					session.getBasicRemote().sendText(msg);
				}
			}
			queue.removeAll(closedSessions);
			System.out.println("Sending " + msg + " to " + queue.size() + " clients");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public boolean complete(){
		return check;
	}
	
}
