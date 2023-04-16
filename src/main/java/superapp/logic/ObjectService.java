package superapp.logic;

import java.util.List;
import java.util.Optional;

import superapp.boundaries.ObjectBoundary;

public interface ObjectService {
	
	public ObjectBoundary creatObject(ObjectBoundary object);

	public ObjectBoundary updatObject(String superApp, String id, ObjectBoundary ob);

	public Optional<ObjectBoundary> getSpecificObject(String superApp, String id);

	public List<ObjectBoundary> getAllObjects();
	
	public void deleteAllObjects();
	
}
