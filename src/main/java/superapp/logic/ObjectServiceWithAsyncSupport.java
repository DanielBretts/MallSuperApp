package superapp.logic;

import superapp.restApi.boundaries.ObjectBoundary;

public interface ObjectServiceWithAsyncSupport extends ObjectServiceWithBindingCapabilities{
	
	public ObjectBoundary handleLater(ObjectBoundary boundary);

}
