package superapp.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import superapp.data.ObjectEntity;

public interface ObjectCrud extends ListCrudRepository<ObjectEntity, String> {

	public Optional<ArrayList<ObjectEntity>> findAllByChildrenObjectsContains(ObjectEntity theChildrenObjects);

	public Optional<ObjectEntity> findByEmail(@Param("createdBy_superApp") String email,
			@Param("created_by_email") String superApp);

	public List<ObjectEntity> findAllByEmail(@Param("createdBy_superApp") String userSuperapp,
			@Param("created_by_email") String email, Pageable pageable);

	public void deleteByEmail(@Param("createdBy_superApp")String superapp, @Param("created_by_email")String email);

	public void updateObjectByEmail(@Param("createdBy_superApp") String userSuperapp, @Param("created_by_email")String email);
}
