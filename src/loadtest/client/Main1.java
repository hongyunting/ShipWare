package loadtest.client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class Main1 {

	public static void main(String[] args) {
		
			for (int i = 0; i < 2; i++) {
				Desktop desktop = java.awt.Desktop.getDesktop();
				  URI oURL;
				try {
					oURL = new URI("http://localhost:8081/ShipWare/Containers.html");
					desktop.browse(oURL);
				} catch (URISyntaxException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  

			}

	}

}
