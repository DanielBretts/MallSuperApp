package superapp.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import superapp.data.ObjectEntity;

public interface ObjectCrud
		extends ListCrudRepository<ObjectEntity, String>, PagingAndSortingRepository<ObjectEntity, String> {

	public Optional<ArrayList<ObjectEntity>> findAllByChildrenObjectsContains(ObjectEntity theChildrenObjects);

	public List<ObjectEntity> findAllByCreatedByUserIdEmail(@Param("superapp") String userSuperapp,
			@Param("email") String email, Pageable pageable);

	public void deleteByCreatedByUserIdEmail(@Param("superapp") String superapp, @Param("email") String email);

	public List<ObjectEntity> findAllByActiveIsTrue(Pageable pageable);

	public List<ObjectEntity> findAllByTypeAndActiveIsTrue(String type,Pageable pageable);

	public List<ObjectEntity> findAllByType(String type, Pageable pageable);

	public List<ObjectEntity> findAllByAliasAndActiveIsTrue(String alias, Pageable pageable);

	public List<ObjectEntity> findAllByAlias(String alias, Pageable pageable);

	//public List<ObjectEntity> findByLocationNearAndActiveIsTrue(Point center, Distance radius, Pageable pageable);

	public List<ObjectEntity> findAllByParentObjectsIsContainingAndActiveIsTrue(ObjectEntity parent, Pageable pageable);

	public List<ObjectEntity> findAllByParentObjectsIsContaining(ObjectEntity origin, Pageable pageable);

	public List<ObjectEntity> findAllByChildrenObjectsIsContaining(ObjectEntity children, Pageable pageable);
	
	public List<ObjectEntity> findAllByChildrenObjectsIsContainingAndActiveIsTrue(ObjectEntity parent, Pageable pageable);

	@Query("{ 'location': { $nearSphere: { $geometry: { type: 'Point', coordinates: [?0, ?1] }, $maxDistance: ?2 } } }")
	public List<ObjectEntity> findByLocationNear(@Param("lat")double lat, @Param("lng")double lng,@Param("maxDistance")double maxDistance, Pageable pageable);

	@Query("{ 'active': true, 'location': { $nearSphere: { $geometry: { type: 'Point', coordinates: [?0, ?1] }, $maxDistance: ?2 } } }")
	public List<ObjectEntity> findByLocationNearAndActiveIsTrue(@Param("lat")double lat, @Param("lng")double lng,@Param("maxDistance")double maxDistance, PageRequest of);
}
