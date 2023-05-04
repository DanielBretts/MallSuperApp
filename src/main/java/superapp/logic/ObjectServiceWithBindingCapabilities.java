package superapp.logic;

import java.util.List;
import java.util.Optional;

import superapp.restApi.boundaries.ObjectBoundary;

public interface ObjectServiceWithBindingCapabilities extends ObjectService{
	public void bind (String InternalObjectIdOrigin, String InternalObjectIdChildren);
	
	public List<ObjectBoundary> getAllChildren(String InternalObjectIdOrigin);
	
	public Optional<ObjectBoundary> getOrigin(String InternalObjectIdChildren);

}
