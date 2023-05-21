package superapp.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;

import superapp.data.ObjectEntity;

public interface ObjectCrud
		extends ListCrudRepository<ObjectEntity, String>, PagingAndSortingRepository<ObjectEntity, String> {

	public Optional<ArrayList<ObjectEntity>> findAllByChildrenObjectsContains(ObjectEntity theChildrenObjects);

//	public Optional<ObjectEntity> findById(@Param("superapp") String superApp,
//			@Param("email") String email);

	public List<ObjectEntity> findAllByCreatedByUserIdEmail(@Param("superapp") String userSuperapp,
			@Param("email") String email, Pageable pageable);

	public void deleteByCreatedByUserIdEmail(@Param("superapp") String superapp, @Param("email") String email);

	public List<ObjectEntity> findAllByActiveIsTrue(Pageable pageable);

	public List<ObjectEntity> findAllByTypeAndActiveIsTrue(String type,Pageable pageable);

	public List<ObjectEntity> findAllByType(String type, Pageable pageable);

	public List<ObjectEntity> findAllByAliasAndActiveIsTrue(String alias, Pageable pageable);

	public List<ObjectEntity> findAllByAlias(String alias, Pageable pageable);

	public List<ObjectEntity> findAllByParentObjectsIsContainingAndActiveIsTrue(ObjectEntity parent, Pageable pageable);

}
