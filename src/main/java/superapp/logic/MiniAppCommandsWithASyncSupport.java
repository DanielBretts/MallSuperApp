package superapp.logic;

import superapp.restApi.boundaries.MiniAppCommandBoundary;

public interface MiniAppCommandsWithASyncSupport extends MiniAppCommandsService {
	
	public MiniAppCommandBoundary handleLater(MiniAppCommandBoundary miniAppCommandBoundary,boolean isAsync);

}
