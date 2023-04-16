package superapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import superapp.boundaries.MiniAppCommandBoundary;
import superapp.boundaries.UserBoundary;
import superapp.logic.*;

@RestController
public class AdminUserController {
	
	private UsersService usersService;
	private ObjectService objectService;
	private MiniAppCommandsService miniAppCommandsService;
	
	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@Autowired
	public void setObjectService(ObjectService objectService) {
		this.objectService = objectService;
	}
	
	@Autowired
	public void setMiniAppCommandsService(MiniAppCommandsService miniAppCommandsService) {
		this.miniAppCommandsService = miniAppCommandsService;
	}


	@RequestMapping(path = { "/superapp/admin/users" }, method = { RequestMethod.DELETE })
	public void deleteAllUsers() {
		this.usersService.deleteAllUsers();
	}

	@RequestMapping(path = { "/superapp/admin/objects" }, method = { RequestMethod.DELETE })
	public void deleteAllObjects() {
		this.objectService.deleteAllObjects();
	}

	@RequestMapping(path = { "/superapp/admin/miniapp" }, method = { RequestMethod.DELETE })
	public void deleteAllCommands() {
		this.miniAppCommandsService.deleteAllCommands();
	}
	
	@RequestMapping(path = { "/superapp/admin/users" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<UserBoundary> getAllUsers() {
		return usersService.getAllUsers();
	}
	
	@RequestMapping(path = { "/superapp/admin/miniapp" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<MiniAppCommandBoundary> getAllCommandsHistory() {
		return miniAppCommandsService.getAllCommands();
	}


	@RequestMapping(path = { "/superapp/admin/miniapp/{miniAppName}" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<MiniAppCommandBoundary> getMiniAppCommandHistory(@PathVariable String miniAppName) {	
		return miniAppCommandsService.getAllMiniAppCommands(miniAppName);
	}

}
