package superapp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import superapp.data.ObjectId;
import superapp.restApi.boundaries.ObjectBoundary;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SuperAppObjectTest {
	
	private int port;
	private String baseUrl;
	private RestTemplate restTemplate;
	
	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
		this.baseUrl = "http://localhost:" + this.port;
		this.restTemplate = new RestTemplate();
	}
	
	@AfterEach
	public void teardown() {
		this.restTemplate
			.delete(this.baseUrl + "/superapp/admin/objects");
	}
	
//	@Test
//	public void testSuccessfulPosingOfObject() {
//		// GIVEN the server is up
//		// AND the database is empty
//				
//		// WHEN I POST /superapp/objects using {
//										//	    "objectId": {
//										//	        "superapp": "2023b.shir.zur",
//										//	    },
//										//	    "type": "abc",
//										//	    "alias": "nissan",
//										//	    "active": false,
//										//	    "location": {
//										//	        "lat": 4520,
//										//	        "lng": 0.0
//										//	    },
//										//	    "createdBy": {
//										//	        "userId": {
//										//	            "superapp": "2023b.shir.zur",
//										//	            "email": "nissanyam1@gmail.com"
//										//	        }
//										//	    },
//										//	    "objectDetails": {
//										//	    }
//										//		}
//		ObjectBoundary newObjectBoundary = new ObjectBoundary();
//		newObjectBoundary.setObjectId(new ObjectId().setInternalObjectId(this.restTemplate
//				.postForObject(this.baseUrl+"/superapp/objects", newObjectBoundary, ObjectBoundary.class)
//				.getObjectId().getInternalObjectId()));
//		newObjectBoundary.getObjectId().setSuperapp(baseUrl)
//		
//	}
	

}
