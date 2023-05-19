package superapp.logic;

import java.util.List;

import superapp.restApi.boundaries.MiniAppCommandBoundary;

public interface MiniAppCommandsQueries extends MiniAppCommandsWithASyncSupport {

	public List<MiniAppCommandBoundary> getCommandsByEmail(String superapp, String email, int size, int page);

	public List<MiniAppCommandBoundary> getMiniAppCommandsByEmail(String miniAppName, String superapp, String email,
			int size, int page);
	
	public void deleteCommandsByEmail(String superapp, String email);
}