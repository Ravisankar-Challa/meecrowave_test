package meecrowave.controller;

import java.util.logging.Logger;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import meecrowave.model.ErrorMessage;

public class PersonControllerIt {
	
	public static final Logger log = Logger.getLogger(PersonControllerIt.class.getSimpleName()); 
	
	private static final Client client = ClientBuilder.newClient();
	private static final String BASE_URL = "http://localhost:8080";
	private static final Jsonb jsonb = JsonbBuilder.create();
	
	@Test
	void testSayHello() {
		String response = client.target(BASE_URL)
								.path("/api/hello/test")
								.request()
								.get()
								.readEntity(String.class);
		log.info(response);
	}
	
	@Test
	void shouldReturnBadRequest1() {
		Response response = client.target(BASE_URL)
								.path("/api/hello")
								.request()
								.get();
		ErrorMessage errorMessage = jsonb.fromJson(response.readEntity(String.class), ErrorMessage.class);
		assertAll(
			() -> assertThat(response.getStatus(), equalTo(400)),
			() -> assertThat(errorMessage.getErrorCode(), equalTo("004")),
			() -> assertThat(errorMessage.getErrorDescription(), equalTo("Invalid value  : PersonController :"
					+ " sayHello.arg0 : may not be null"))
		);
	}
	
	@Test
	void shouldReturnBadRequest2() {
		Response response = client.target(BASE_URL)
								.path("/api/hello")
								.queryParam("test", 123)
								.request()
								.get();
		assertThat(response.getStatus(), equalTo(400));
		ErrorMessage errorMessage = jsonb.fromJson(response.readEntity(String.class), ErrorMessage.class);
		assertThat(errorMessage.getErrorCode(), equalTo("004"));
		assertThat(errorMessage.getErrorDescription(), equalTo("Invalid value 123 : PersonController :"
				+ " sayHello.arg0 : must match the following regular expression: [A-Z]"));
		
	}
	
	@Test
	void shouldReturnBadRequest3() {
		Response response = client.target(BASE_URL)
								.path("/api/hello")
								.queryParam("test", "R")
								.request()
								.get();
		assertThat(response.getStatus(), equalTo(501));
		ErrorMessage errorMessage = jsonb.fromJson(response.readEntity(String.class), ErrorMessage.class);
		assertThat(errorMessage.getErrorCode(), equalTo("000"));
		assertThat(errorMessage.getErrorDescription(), equalTo("errorMessage"));
		
	}
	
	@Test
	void shouldReturnBadRequest4() {
		Response response = client.target(BASE_URL)
								.path("/api/hello")
								.queryParam("test", "R")
								.request()
								.post(Entity.entity("{\"name\":\"ravi\"}", APPLICATION_JSON));
		assertThat(response.getStatus(), equalTo(400));
		ErrorMessage errorMessage = jsonb.fromJson(response.readEntity(String.class), ErrorMessage.class);
		assertThat(errorMessage.getErrorCode(), equalTo("004"));
		assertThat(errorMessage.getErrorDescription(), equalTo("Invalid value ravi : PersonController :"
				+ " sayHello.arg1.name : Please check the name"));
		
	}
}
