package superapp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import superapp.logic.ObjectService;

@RestController
public class ObjectController {
	private ObjectService objectService;
	
	@Autowired
	public ObjectController (ObjectService objectService) {
		this.objectService = objectService;		
	}
	
	@RequestMapping( // Create an object
			path = {"/superapp/objects"},
			method = {RequestMethod.POST},
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
			public ObjectBoundary createObjectBoundary (@RequestBody ObjectBoundary ob) {
				System.out.println(ob.toString());
				return this.objectService.creatObject(ob);
	}
	
	@RequestMapping( // Update an object
			path = {"/superapp/objects/{superApp}/{id}"},
			method = {RequestMethod.PUT},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
			public void update (@PathVariable ("superApp") String superApp , @PathVariable ("id") String id,
					@RequestBody ObjectBoundary ob) {
				objectService.updatObject(superApp, id, ob);
	}
	
	@RequestMapping( // Retrieve object
			path = {"/superapp/objects/{superApp}/{id}"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
			@ResponseBody
			public Optional<ObjectBoundary> getObjectById(@PathVariable ("superApp") String superApp
					,@PathVariable("id") String id){
				return this.objectService.getSpecificObject(superApp , id);
			}
	
	@RequestMapping( // Get All objects
			path = {"/superapp/objects"},
			method = {RequestMethod.GET},
			produces = {MediaType.APPLICATION_JSON_VALUE})
			public ObjectBoundary[] getObjects () {
				return objectService.getAllObjects().toArray(new ObjectBoundary[0]);
			}
	
}