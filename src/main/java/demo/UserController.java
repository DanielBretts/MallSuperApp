package demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	/*
	 * Create a new user
	 */
	@RequestMapping(
			path = { "/superapp/users" },
			method = { RequestMethod.POST },
			produces = {MediaType.APPLICATION_JSON_VALUE },
			consumes = { MediaType.APPLICATION_JSON_VALUE })
	public UserBoundary createUserBoundary(@RequestBody NewUserBoundary nub) {
		return nub.getUserBoundary();
	}

	/*
	 * Login valid user and retrieve user details
	 */
	@RequestMapping(path = { "/superapp/users/login/{superapp}/{email}" },
			method = { RequestMethod.GET },
			produces = {MediaType.APPLICATION_JSON_VALUE })
	public UserBoundary getUserBoundaryByEmail(@PathVariable("superapp") String superapp,
			@PathVariable("email") String email) {
		/*
		 * Need to look for an UserId by SuperApp & Email
		 * 
		 * According to this UserId get object UserBoundary
		 */
		UserBoundary ub = new UserBoundary(email,"Team Leader", "Shir","google.com", new UserId(email));
		return ub;
	}

	@RequestMapping( // Update the details
			path = { "/superapp/users/{superapp}/{userEmail}" },
			method = { RequestMethod.PUT }, 
			consumes = {MediaType.APPLICATION_JSON_VALUE })
	public void update(@PathVariable("superapp") String superapp, @PathVariable("userEmail") String email,
			@RequestBody UserBoundary update) {

		/*
		 * Need to find the old UserBoundary and PUT the new details.
		 * userBounady.update(update);
		 */
		System.err.println(update.toString());
	}

}
