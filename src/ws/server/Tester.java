package ws.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.ContainerProvider;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;


public class Tester {
	static int i;
    static int numberOfConnections = 2; //number of simultaneous connections.

    public static void main(String[] args) {
    }
    
    @Test
    public void submittingForm() throws Exception {
    	
    	for(int i = 0; i< 2; i++){
    		try (final WebClient webClient = new WebClient()) {
    			
                // Get the first page
                final HtmlPage page1 = webClient.getPage("http://localhost:8081/ShipWare/Containers.html");
                final HtmlForm form = page1.getFormByName("message-form");
                final HtmlSubmitInput button = form.getInputByName("submit");
                final HtmlPage page2 = button.click();
                webClient.close();
                
            } catch (FailingHttpStatusCodeException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (MalformedURLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
}
