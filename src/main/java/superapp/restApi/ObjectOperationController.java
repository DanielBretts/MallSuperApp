package superapp.restApi;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import superapp.data.SuperAppObjectIdBoundary;
import superapp.data.exceptions.ObjectNotFoundException;
import superapp.logic.ObjectServiceWithBindingCapabilities;
import superapp.restApi.boundaries.ObjectBoundary;

@RestController
public class ObjectOperationController {
	private ObjectServiceWithBindingCapabilities objects;
	
	
	@Autowired	
	public ObjectOperationController(ObjectServiceWithBindingCapabilities objects) {
		super();
		this.objects = objects;
	}
	
	@RequestMapping(method = {RequestMethod.PUT},
			path = "/superapp/objects/{superapp}/{InternalObjectId}/children",
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void BindAnExistingObjectToChildren (
			@PathVariable("InternalObjectId") String originId, 
			@RequestBody SuperAppObjectIdBoundary superAppObjectIdBoundary) {
		this.objects
			.bind(originId, superAppObjectIdBoundary.getInternalObjectId());
		
	}
	
	@RequestMapping(method = {RequestMethod.GET},
			path = "/superapp/objects/{superapp}/{InternalObjectId}/children",
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ObjectBoundary[] getAllChildren (
			@PathVariable("InternalObjectId") String originId) {
		List<ObjectBoundary> rv = this.objects
			.getAllChildren(originId);
		
		return rv.toArray(new ObjectBoundary[0]);
	}
	
	@RequestMapping(method = {RequestMethod.GET},
			path = "/superapp/objects/{superapp}/{InternalObjectId}/parents",
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ObjectBoundary GetAnArrayWithObjectParent(
			@PathVariable("InternalObjectId") String responseId) {
		Optional<ObjectBoundary> rv = this.objects
			.getOrigin(responseId);
		
		return rv.orElseThrow(()->new ObjectNotFoundException("could not find origin for Object with id: " + responseId));
	}

}
