package superapp.logic;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import superapp.data.MiniAppCommandEntity;

public interface MiniAppCommandsCrud extends ListCrudRepository<MiniAppCommandEntity, String> {

	public List<MiniAppCommandEntity> findAllByEmail(@Param("invokedBy.userId.superapp") String superapp, @Param("invokedBy.userId.email") String email,
			Pageable pageable);

}
