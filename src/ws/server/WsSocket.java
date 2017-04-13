package ws.server;
import java.io.EOFException;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.net.URI;
import java.sql.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.websocket.ContainerProvider;
/**
 * 
 */
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.ServerEndpoint;


/**
 * @author YunTing
 * WebSocket to establish connection
 */

@ServerEndpoint
(value = "/websocketendpoint",  
encoders = { ContainerEncoder.class }, 
decoders = { ContainerDecoder.class })
public class WsSocket extends Thread {
	
	private static ConcurrentLinkedQueue<Session> queue = new ConcurrentLinkedQueue<Session>();
	long startTime;
	Session session;
	boolean isAdmin = false;
	
	static {
		//System.out.println("CPU Time: "+threadMXBean.getCurrentThreadCpuTime());
		
//		// rate publisher thread, generates a new value for USD rate every 2
//		// seconds.
//		rateThread = new Thread() {
//			public void run() {
//				//DecimalFormat df = new DecimalFormat("#.####");
//				while (true) {
//					double d = 2 + Math.random();
//					if (queue != null)
//						//sendAll("USD Rate: " + df.format(d));
//					try {
//						sleep(2000);
//					} catch (InterruptedException e) {
//					}
//				}
//			};
//		};
//		rateThread.start();
	}


	// Decorates the function associated with handling a connection open event
	@OnOpen
	public synchronized void onOpen(Session session) throws IOException{
		queue.add(session);
		this.session = session;
		System.out.println("Open Connection for Client: " + session.getId());
	}
	
	// Decorates the function associated with handling an event on connection close
	@OnClose
	public synchronized void onClose(Session session){
		queue.remove(session);
	    System.out.println("Close Connection for Client: " + session.getId());	    
	}

	// Decorates the function associated with handling an event on message received
	@OnMessage
	public void onMessage(Session session, Container c) throws InterruptedException, IOException{
		startTime = System.nanoTime();
		
    	Container container = new Container();
	    container.setContainerName(c.getContainerName());
	    container.setContainerType(c.getContainerType());
	    container.setDestination(c.getDestination());
	    container.setContainerWeight(c.getContainerWeight());
	    container.setUserName(c.getUserName());
	    
	    /*Round-Robin Scheduler*/
	    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	    Scheduler CPUScheduler = new Scheduler();
	    CPUScheduler.start();

	    /*Threading*/
	    ProcessingThread pt = new ProcessingThread(queue, c, startTime, session);
	    Thread pThread = new Thread(pt);
	    
	    /*Priority Scheduler*/
	    PriorityScheduler pScheduler = new PriorityScheduler();
	    if(c.getUserName().equalsIgnoreCase("Admin")){
			isAdmin = true;
			pThread.setPriority(Thread.MAX_PRIORITY);
		}
	    
	    pThread.start();
	    
	    /*Round Robin*/
	    CPUScheduler.addThread(pThread);
	    
	    /*Priority Scheduler*/
	    pScheduler.addThread(pThread, isAdmin);
	    pScheduler.start();
	    

	}
	
	
	// Decorates the function associated with handling a connection error event
	@OnError
	public void onError(Session s, Throwable e){
	    e.printStackTrace();
	}
	
	public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }
}

