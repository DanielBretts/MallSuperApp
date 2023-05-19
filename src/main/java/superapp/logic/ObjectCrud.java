package superapp.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import superapp.data.ObjectEntity;

public interface ObjectCrud
		extends ListCrudRepository<ObjectEntity, String>, PagingAndSortingRepository<ObjectEntity, String> {

	public Optional<ArrayList<ObjectEntity>> findAllByChildrenObjectsContains(ObjectEntity theChildrenObjects);

	public Optional<ObjectEntity> findByEmail(@Param("superapp") String email,
			@Param("email") String superApp);

	public List<ObjectEntity> findAllByEmail(@Param("superapp") String userSuperapp,
			@Param("email") String email, Pageable pageable);

	public void deleteByEmail(@Param("superapp") String superapp, @Param("email") String email);

//	public void updateObjectByEmail(@Param("createdBy_superApp") String userSuperapp,
//			@Param("created_by_email") String email);
}
