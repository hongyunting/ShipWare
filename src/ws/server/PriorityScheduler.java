package ws.server;

import java.util.concurrent.ConcurrentLinkedQueue;

public class PriorityScheduler extends Thread{
	
	private static ConcurrentLinkedQueue<Thread> queue = new ConcurrentLinkedQueue<Thread>();
	private static final int DEFAULT_TIME_SLICE = 2000; // 1 second
	private static int countHigh = 0;
	private boolean isAdmin;

	public PriorityScheduler() {
	}

	
	public void addThread(Thread t, boolean isAdmin) {
		this.isAdmin = isAdmin;
		queue.offer(t);		
	}
	
	private void schedulerSleep(long timeSlice) {
		try {
			System.out.println("Thread sleep");
			Thread.sleep(timeSlice);
		} catch (InterruptedException e) {
		}
	}

	public void run() {
		Thread current;
		System.out.println("Is is admin:" + isAdmin);
		
		if(isAdmin)
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		
		if(Thread.currentThread().getPriority() == MAX_PRIORITY){
			counting();
		}
		
		while (true) {
			try {
				current = (Thread) queue.peek();
				if(getCount() == 4 && Thread.currentThread().getPriority() == MAX_PRIORITY){
					setCount();
					current.setPriority(4);
					schedulerSleep(1000);
					current.setPriority(10);					
					
				}
			} catch (NullPointerException e3) {
			}
		}
	}

	
	public synchronized static void counting(){
		countHigh++;
		System.out.println("Counting Admin: " + countHigh);
	}
	
	public static int getCount(){
		return countHigh;
	}
	
	public static void setCount(){
		countHigh = 0;
	}
	
}
