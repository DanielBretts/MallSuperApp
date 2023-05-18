package superapp.data;

import superapp.logic.MiniAppCommandsService;
import superapp.restApi.boundaries.MiniAppCommandBoundary;

public interface MiniAppWithASyncSupport extends MiniAppCommandsService {
	
	public MiniAppCommandBoundary handleLater(MiniAppCommandBoundary massage);

}
