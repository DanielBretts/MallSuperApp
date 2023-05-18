package superapp.restApi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import superapp.logic.*;
import superapp.restApi.boundaries.MiniAppCommandBoundary;
import superapp.restApi.boundaries.ObjectBoundary;
import superapp.restApi.boundaries.UserBoundary;

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
	public void deleteAllUsers( 
			@RequestParam(name = "userSuperapp", required = false, defaultValue = "2023b.shir.zur")String superapp,
			@RequestParam(name = "userEmail", required = true) String email){
		this.usersService.deleteAllUsers();
	}

	@RequestMapping(path = { "/superapp/admin/objects" }, method = { RequestMethod.DELETE })
	public void deleteAllObjects( 
		@RequestParam(name = "userSuperapp", required = false, defaultValue = "2023b.shir.zur")String superapp,
		@RequestParam(name = "userEmail", required = true) String email){
		this.objectService.deleteAllObjects();
	}

	@RequestMapping(path = { "/superapp/admin/miniapp" }, method = { RequestMethod.DELETE })
	public void deleteAllCommands( 
			@RequestParam(name = "userSuperapp", required = false, defaultValue = "2023b.shir.zur")String superapp,
			@RequestParam(name = "userEmail", required = true) String email){
		this.miniAppCommandsService.deleteAllCommands();
	}
	
	@RequestMapping(path = { "/superapp/admin/users" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<UserBoundary> getAllUsers() {
		return usersService.getAllUsers();
	}
	
	@RequestMapping(path = { "/superapp/admin/objects" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<ObjectBoundary> getAllObjects( 
			@RequestParam(name = "userSuperapp", required = false, defaultValue = "2023b.shir.zur")String superapp,
			@RequestParam(name = "userEmail", required = true) String email,
			@RequestParam(name = "size", required = true) int size,
			@RequestParam(name = "page", required = false, defaultValue = "0")int page){
		return objectService.getAllObjects();
	}
	
	@RequestMapping(path = { "/superapp/admin/miniapp" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<MiniAppCommandBoundary> getAllCommandsHistory( 
			@RequestParam(name = "userSuperapp", required = false, defaultValue = "2023b.shir.zur")String superapp,
			@RequestParam(name = "userEmail", required = true) String email,
			@RequestParam(name = "size", required = true) int size,
			@RequestParam(name = "page", required = false, defaultValue = "0")int page){
		return miniAppCommandsService.getAllCommands();
	}


	@RequestMapping(path = { "/superapp/admin/miniapp/{miniAppName}" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public List<MiniAppCommandBoundary> getMiniAppCommandHistory(@PathVariable String miniAppName,
			@RequestParam(name = "userSuperapp", required = false, defaultValue = "2023b.shir.zur")String superapp,
			@RequestParam(name = "userEmail", required = true) String email,
			@RequestParam(name = "size", required = true) int size,
			@RequestParam(name = "page", required = false, defaultValue = "0")int page){	
		return miniAppCommandsService.getAllMiniAppCommands(miniAppName);
	}

}
