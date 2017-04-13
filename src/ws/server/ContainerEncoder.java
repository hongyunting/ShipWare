/**
 * 
 */
package ws.server;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
/**
 * @author Yun Ting
 *
 */
public class ContainerEncoder implements Encoder.Text<Container> {
	
	@Override
	public String encode(Container container) throws EncodeException {

		JsonObject jsonObject = Json.createObjectBuilder()
				.add("cName", container.getContainerName())
				.add("cType", container.getContainerType())
				.add("destination", container.getDestination())
				.add("cWeight", container.getContainerWeight())
				.add("user", container.getUserName())
				.build();
		
		System.out.println(container.getContainerName());
		return jsonObject.toString();

	}

	@Override
	public void init(EndpointConfig ec) {
//		System.out.println("MessageEncoder - init method called");
	}

	@Override
	public void destroy() {
//		System.out.println("MessageEncoder - destroy method called");
	}
}
