/**
 * 
 */
package ws.server;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
/**
 * @author YunTing
 *
 */
public class ContainerDecoder implements Decoder.Text<Container>{

	@Override
	  public Container decode(String jsonMessage) throws DecodeException {

	    JsonObject jsonObject = Json
	        .createReader(new StringReader(jsonMessage)).readObject();
	    Container message = new Container();
	    message.setContainerName(jsonObject.getString("cName"));
	    message.setContainerType(jsonObject.getString("cType"));
	    message.setDestination(jsonObject.getString("destination"));
	    message.setUserName(jsonObject.getString("user"));
	    message.setContainerWeight(Float.valueOf(jsonObject.getString("cWeight")));
	    
	    return message;

	  }

	  @Override
	  public boolean willDecode(String jsonMessage) {
	    try {
	      // Check if incoming message is valid JSON
	      Json.createReader(new StringReader(jsonMessage)).readObject();
	      return true;
	      
	    } catch (Exception e) {
	      return false;
	    }
	  }

	  @Override
	  public void init(EndpointConfig ec) {
	    //System.out.println("MessageDecoder -init method called");
	  }

	  @Override
	  public void destroy() {
	    //System.out.println("MessageDecoder - destroy method called");
	  }
}
