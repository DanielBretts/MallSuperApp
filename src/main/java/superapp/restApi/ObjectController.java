package superapp.restApi;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import superapp.logic.ObjectQueries;
import superapp.logic.ObjectService;
import superapp.restApi.boundaries.ObjectBoundary;

@RestController
public class ObjectController {
	private ObjectQueries objectService;

	@Autowired
	public ObjectController(ObjectQueries objectService) {
		this.objectService = objectService;
	}

	@RequestMapping( // Create an object
			path = { "/superapp/objects" }, method = { RequestMethod.POST }, produces = {
					MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ObjectBoundary createObjectBoundary(@RequestBody ObjectBoundary ob) {
		return this.objectService.createObject(ob);
	}

	@RequestMapping( // Update an object
			path = { "/superapp/objects/{superApp}/{internalObjectId}" }, method = { RequestMethod.PUT }, consumes = {
					MediaType.APPLICATION_JSON_VALUE })
	public void update(@PathVariable("superApp") String superApp, @PathVariable("internalObjectId") String id,
			@RequestBody ObjectBoundary ob, @RequestParam(name = "userSuperapp", required = true) String userSuperapp,
			@RequestParam(name = "userEmail", required = true) String email) {
		objectService.updateObjectCheckingRole(superApp, id, ob, userSuperapp, email);
	}

	@RequestMapping( // Retrieve object
			path = { "/superapp/objects/{superApp}/{internalObjectId}" }, method = { RequestMethod.GET }, produces = {
					MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public Optional<ObjectBoundary> getObjectById(@PathVariable("superApp") String superApp,
			@PathVariable("internalObjectId") String id, @RequestParam(name = "userSuperapp", required = true) String superapp,
			@RequestParam(name = "userEmail", required = true) String email) {
		return this.objectService.getObjectCheckingRole(superApp, id, superapp, email);
	}

	@RequestMapping( // Get All objects
			path = { "/superapp/objects" }, method = { RequestMethod.GET }, produces = {
					MediaType.APPLICATION_JSON_VALUE })
	public ObjectBoundary[] getObjects(@RequestParam(name = "userSuperapp", required = true) String superapp,
			@RequestParam(name = "userEmail", required = true) String email,
			@RequestParam(name = "size", required = true) int size,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		return objectService.getAllObjectsCheckingRole(superapp, email, size, page).toArray(new ObjectBoundary[0]);
	}

}