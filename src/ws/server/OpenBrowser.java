/**
 * 
 */
package ws.server;

import java.awt.Desktop;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author ahtinq
 *
 */
public class OpenBrowser {

	private static final int MYTHREADS = 100;
	 
	public static void main(String args[]) throws Exception {
//		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
//		String[] hostList = { "http://localhost:8081/ShipWare/Containers.html","http://localhost:8081/ShipWare/Containers.html",
//				"http://localhost:8081/ShipWare/Containers.html","http://localhost:8081/ShipWare/Containers.html",
//				"http://localhost:8081/ShipWare/Containers.html","http://localhost:8081/ShipWare/Containers.html",
//				"http://localhost:8081/ShipWare/Containers.html","http://localhost:8081/ShipWare/Containers.html",
//				"http://localhost:8081/ShipWare/Containers.html","http://localhost:8081/ShipWare/Containers.html",
//				"http://localhost:8081/ShipWare/Containers.html","http://localhost:8081/ShipWare/Containers.html",
//				"http://localhost:8081/ShipWare/Containers.html","http://localhost:8081/ShipWare/Containers.html",
//				"http://localhost:8081/ShipWare/Containers.html","http://localhost:8081/ShipWare/Containers.html"
//				};
// 
//		for (int i = 0; i < hostList.length; i++) {
// 
//			String url = hostList[i];
//			Runnable worker = new MyRunnable(url);
//			executor.execute(worker);
//		}
//		executor.shutdown();
//		// Wait until all threads are finish
//		while (!executor.isTerminated()) {
// 
//		}
//		System.out.println("\nFinished all threads");
		
		// Have one (or more) threads ready to do the async tasks. Do this during startup of your app.
		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS); 

		// Fire a request.
		Future<Response> response = executor.submit(new Request(new URL("http://localhost:8081/ShipWare/Containers.html")));

		// Get the response (here the current thread will block until response is returned).
		InputStream body = response.get().getBody();
		System.out.println();

		// Shutdown the threads during shutdown of your app.
		executor.shutdown();
		
//		if(Desktop.isDesktopSupported())
//		{
//		  Desktop.getDesktop().browse(new URI("http://localhost:8081/ShipWare/Containers.html"));
//		}
	}
 
	public static class MyRunnable implements Runnable {
		private final String url;
 
		MyRunnable(String url) {
			this.url = url;
		}
 
		@Override
		public void run() {
 
			String result = "";
			int code = 200;
			try {
				URL siteURL = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
 
				code = connection.getResponseCode();
				if (code == 200) {
					result = "Green\t";
				}
			} catch (Exception e) {
				result = "->Red<-\t";
			}
			System.out.println(url + "\t\tStatus:" + result);
		}
	}
}
