package superapp.logic;

import java.util.List;
import java.util.Optional;

import superapp.data.SuperAppObjectIdBoundary;
import superapp.restApi.boundaries.ObjectBoundary;

public interface ObjectQueries extends ObjectServiceWithBindingCapabilities {

	public void updateObjectCheckingRole(String superApp, String id, ObjectBoundary ob, String userSuperapp,
			String email);

	public Optional<ObjectBoundary> getObjectCheckingRole(String superApp, String id, String userSuperapp,
			String email);

	public List<ObjectBoundary> getAllObjectsCheckingRole(String userSuperapp, String email, int size, int page);

	public void deleteAllObjectsAdminOnly(String superapp, String email);

	public void bindByPermission(String originId, SuperAppObjectIdBoundary superAppObjectIdBoundary,
			String userSuperapp, String email);

	public List<ObjectBoundary> getAllChildrenByPermission(String superApp, String originId, String userSuperapp,
			String email, int size, int page);

	public List<ObjectBoundary> getObjectsByType(String superapp, String email, String type, int size, int page);

	public List<ObjectBoundary> getObjectsByAlias(String superapp, String email, String alias, int size, int page);
}
