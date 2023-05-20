package superapp.logic;

import java.util.List;
import java.util.Optional;

import superapp.restApi.boundaries.ObjectBoundary;

public interface ObjectQueries extends ObjectServiceWithBindingCapabilities {

	public void updateObjectByEmail(String superApp, String id, ObjectBoundary ob, String userSuperapp, String email);

	public Optional<ObjectBoundary> getObjectBySpecificEmail(String superApp, String id, String userSuperapp,
			String email);

	public List<ObjectBoundary> getAllObjectsByEmail(String userSuperapp, String email, int size, int page);

	public void deleteAllObjectsAdminOnly(String superapp, String email);
}
