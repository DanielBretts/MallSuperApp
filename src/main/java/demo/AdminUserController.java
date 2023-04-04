package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminUserController {
	@RequestMapping(path = { "/superapp/admin/users" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ArrayList<UserBoundary> getAllUsers() {
		ArrayList<UserBoundary> allUsers = new ArrayList<>();
		allUsers.add(new NewUserBoundary("Shir", "google.com", "shirrzurr@gmail.com", "Team Leader").getUserBoundary());
		allUsers.add(new NewUserBoundary("Daniel", "google.com", "daniel@gmail.com", "The ! - Team Leader")
				.getUserBoundary());
		allUsers.add(new NewUserBoundary("Rabea", "google.com", "rabea@gmail.com", "CEO").getUserBoundary());
		return allUsers;
	}

	@RequestMapping(path = { "/superapp/admin/miniapp" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ArrayList<MiniAppCommandBoundary> getAllCommandsHistory() {

		ArrayList<MiniAppCommandBoundary> allCommands = new ArrayList<>();

		Map<String, List<String>> commandAttributes = new HashMap<String, List<String>>();
		List<String> attributes = new ArrayList<>();
		attributes.add("this makes an action");
		commandAttributes.put("key", attributes);

		Map<String, ObjectId> targetObjects = new HashMap<String, ObjectId>();
		targetObjects.put("objectID", new ObjectId());

		Map<String, UserId> invokedBy = new HashMap<String, UserId>();
		invokedBy.put("userId", new UserId("test@gmail.com"));

		MiniAppCommandBoundary commandBoundary = new MiniAppCommandBoundary(new CommandID("dummyApp"), "doSomething",
				targetObjects, invokedBy, commandAttributes);
		allCommands.add(commandBoundary);
		return allCommands;
	}

	@RequestMapping(path = { "/superapp/admin/users" }, method = { RequestMethod.DELETE })
	public void deleteAllUsers() {
		// need to implement
	}

	@RequestMapping(path = { "/superapp/admin/objects" }, method = { RequestMethod.DELETE })
	public void deleteAllObjects() {
		// need to implement
	}

	@RequestMapping(path = { "/superapp/admin/miniapp" }, method = { RequestMethod.DELETE })
	public void deleteAllCommands() {
		// need to implement
	}

	@RequestMapping(path = { "admin/miniapp/{miniAppName}" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ArrayList<MiniAppCommandBoundary> getMiniAppCommandHistory(@PathVariable String miniAppName) {
		ArrayList<MiniAppCommandBoundary> allCommands = new ArrayList<>();

		Map<String, List<String>> commandAttributes = new HashMap<String, List<String>>();
		List<String> attributes = new ArrayList<>();
		attributes.add("this makes an action");
		commandAttributes.put("key", attributes);

		Map<String, ObjectId> targetObjects = new HashMap<String, ObjectId>();
		targetObjects.put("objectID", new ObjectId());

		Map<String, UserId> invokedBy = new HashMap<String, UserId>();
		invokedBy.put("userId", new UserId("test@gmail.com"));

		MiniAppCommandBoundary commandBoundary = new MiniAppCommandBoundary(new CommandID(miniAppName), "doSomething",
				targetObjects, invokedBy, commandAttributes);
		allCommands.add(commandBoundary);
		return allCommands;
	}

}
