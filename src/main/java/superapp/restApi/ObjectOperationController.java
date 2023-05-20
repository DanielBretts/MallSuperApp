package superapp.restApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import superapp.data.SuperAppObjectIdBoundary;
import superapp.data.exceptions.ObjectNotFoundException;
import superapp.logic.ObjectQueries;
import superapp.logic.ObjectServiceWithBindingCapabilities;
import superapp.restApi.boundaries.ObjectBoundary;

@RestController
public class ObjectOperationController {
	private ObjectQueries objects;

	@Autowired
	public ObjectOperationController(ObjectQueries objects) {
		super();
		this.objects = objects;
	}

	@RequestMapping(method = {
			RequestMethod.PUT }, path = "/superapp/objects/{superapp}/{InternalObjectId}/children", consumes = {
					MediaType.APPLICATION_JSON_VALUE })
	public void BindAnExistingObjectToChildren(@PathVariable("superApp") String superApp,
			@PathVariable("InternalObjectId") String originId,
			@RequestBody SuperAppObjectIdBoundary superAppObjectIdBoundary,
			@RequestParam(name = "userSuperapp", required = true) String userSuperapp,
			@RequestParam(name = "userEmail", required = true) String email) {
		this.objects.bindByPermission(originId, superAppObjectIdBoundary, userSuperapp, email);
	}

	@RequestMapping(method = {
			RequestMethod.GET }, path = "/superapp/objects/{superapp}/{InternalObjectId}/children", produces = {
					MediaType.APPLICATION_JSON_VALUE })
	public ObjectBoundary[] getAllChildren(@PathVariable("superApp") String superApp,
			@PathVariable("InternalObjectId") String originId,
			@RequestParam(name = "userSuperapp", required = true) String userSuperapp,
			@RequestParam(name = "userEmail", required = true) String email,
			@RequestParam(name = "size", required = true) int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		List<ObjectBoundary> rv = this.objects.getAllChildrenByPermission(superApp, originId, userSuperapp, email, size,
				page);
		return rv.toArray(new ObjectBoundary[0]);
	}

	@RequestMapping(method = {
			RequestMethod.GET }, path = "/superapp/objects/{superapp}/{InternalObjectId}/parents", produces = {
					MediaType.APPLICATION_JSON_VALUE })
	public ObjectBoundary[] GetAnArrayWithObjectParent(@PathVariable("InternalObjectId") String childrenId) {
		Optional<ArrayList<ObjectBoundary>> rv = this.objects.getOrigin(childrenId);
		return rv.map(list -> list.toArray(new ObjectBoundary[0])).orElseThrow(
				() -> new ObjectNotFoundException("could not find origin for Object with id: " + childrenId));
	}
}
