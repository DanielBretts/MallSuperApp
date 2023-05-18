package superapp.logic;

import java.util.List;

import superapp.restApi.boundaries.MiniAppCommandBoundary;

public interface MiniAppCommandsService {
	
	@Deprecated
	public Object invokeCommand(MiniAppCommandBoundary command);
	
	public List<MiniAppCommandBoundary> getAllCommands();
	
	public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName);
	
	public void deleteAllCommands();
}
