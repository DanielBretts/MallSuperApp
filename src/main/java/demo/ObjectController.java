package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ObjectController {
	

	@RequestMapping( // Get All objects
	path = {"/superapp/objects"},
	method = {RequestMethod.GET},
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public ArrayList<ObjectBoundary> getObjects () {
		Map <String, Object> objectDetails = new HashMap<String, Object>();
		objectDetails.put("key1", "can be set to any value you wish");
		objectDetails.put("key2", "you can also name the attributrs any name you like");
		objectDetails.put("key3", 9.99);
		objectDetails.put("key4", true);
		ObjectBoundary ob = new ObjectBoundary(new ObjectId(),"dummyType","demo instance",true
				,new Location(32.1133,34.818),new UserId("email : jill@demo.org"), objectDetails);
		ArrayList<ObjectBoundary> arrOB = new ArrayList<>();
		arrOB.add(ob);
		return arrOB;
	}
	
	@RequestMapping( // Retrieve object
	path = {"/superapp/objects/{superApp}/{id}"},
	method = {RequestMethod.GET},
	produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public ObjectBoundary getObjectById(@PathVariable("id") String id){
		Map <String, Object> objectDetails = new HashMap<String, Object>();
		objectDetails.put("key1", "can be set to any value you wish");
		objectDetails.put("key2", "you can also name the attributrs any name you like");
		objectDetails.put("key3", 9.99);
		objectDetails.put("key4", true);
		return new ObjectBoundary(new ObjectId().setInternalObjectId(id),"dummyType","demo instance",true
				,new Location(32.1133,34.818),new UserId("email : jill@demo.org"), objectDetails);
	}
	
	@RequestMapping( // Create an object
	path = {"/superapp/objects"},
	method = {RequestMethod.POST},
	produces = {MediaType.APPLICATION_JSON_VALUE},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ObjectBoundary createObjectBoundary (@RequestBody ObjectBoundary ob) {
		return ob;
	}
	
	@RequestMapping( // Update an object
	path = {"/superapp/objects/{superApp}/{id}"},
	method = {RequestMethod.PUT},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void update (@RequestBody ObjectBoundary ob) {
		ObjectBoundary ob1 = new ObjectBoundary();
		ob1.setAlias(ob.getAlias());
		System.err.println(ob.toString());
	}
	
	
}