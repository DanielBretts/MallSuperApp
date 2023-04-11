package superapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import superapp.logic.UsersService;

@RestController
public class UserController {

	private UsersService usersService;
	
	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	/*
	 * Create a new user
	 */
	@RequestMapping(
			path = { "/superapp/users" },
			method = { RequestMethod.POST },
			produces = {MediaType.APPLICATION_JSON_VALUE },
			consumes = { MediaType.APPLICATION_JSON_VALUE })
	public UserBoundary createUserBoundary(@RequestBody NewUserBoundary nub) {
		UserBoundary ub = new UserBoundary(nub.getEmail(),nub.getRole(),nub.getUsername(),nub.getAvatar(),new UserId(nub.getEmail()));
		return usersService.createUser(ub);
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
		return this.usersService.login(superapp, email);
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
