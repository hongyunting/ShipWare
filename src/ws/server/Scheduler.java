package ws.server;

public class Scheduler extends Thread{

	private CircularList queue;
	private int timeSlice;
	private static final int DEFAULT_TIME_SLICE = 1000; // 1 second

	public Scheduler() {
		timeSlice = DEFAULT_TIME_SLICE;
		queue = new CircularList();
	}

	public Scheduler(int quantum) {
		timeSlice = quantum;
		queue = new CircularList();
	}

	public void addThread(Thread t) {
		t.setPriority(2);
		queue.addItem(t);
	}

	private void schedulerSleep() {
		try {
			Thread.sleep(timeSlice);
		} catch (InterruptedException e) {
		}
	}

	public void run() {
		Thread current;

		this.setPriority(6);

		while (true) {
			try {
				current = (Thread) queue.getNext();

				if ((current != null) && (current.isAlive())) {
					current.setPriority(4);
					schedulerSleep();
					System.out.println("* * * Context Switch * * * ");
					current.setPriority(2);
				}

			} catch (NullPointerException e3) {
			}
		}
	}
	
}
