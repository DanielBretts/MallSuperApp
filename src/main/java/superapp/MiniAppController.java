package superapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import superapp.logic.MiniAppCommandsService;
import superapp.logic.UsersService;

@RestController
public class MiniAppController {
	
	private MiniAppCommandsService miniAppCommandsService;
	
	@Autowired
	public void setMiniAppCommandsService(MiniAppCommandsService miniAppCommandsService) {
		this.miniAppCommandsService = miniAppCommandsService;
	}	
	
	/*
	 *  Invoke a MiniApp command
	 */
	@RequestMapping( 
	path = {"/superapp/miniapp/{miniAppName}"},
	method = {RequestMethod.POST},
	produces = {MediaType.APPLICATION_JSON_VALUE},
	consumes = {MediaType.APPLICATION_JSON_VALUE})
	public MiniAppCommandBoundary invokeCommand (@PathVariable("miniAppName") String miniAppName ,@RequestBody MiniAppCommandBoundary miniApp) {
//		miniApp.setCommand(miniAppName);
		return (MiniAppCommandBoundary) miniAppCommandsService.invokeCommand(miniApp);
	}

}
