package superapp.logic;

import java.util.List;

import superapp.restApi.boundaries.MiniAppCommandBoundary;

public interface MiniAppCommandsService {
	
	@Deprecated
	public Object invokeCommand(MiniAppCommandBoundary command);
	
	@Deprecated
	public List<MiniAppCommandBoundary> getAllCommands();
	
	@Deprecated
	public List<MiniAppCommandBoundary> getAllMiniAppCommands(String miniAppName);
	
	@Deprecated
	public void deleteAllCommands();
}
