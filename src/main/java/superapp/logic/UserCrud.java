package superapp.logic;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import superapp.data.UserEntity;

public interface UserCrud extends ListCrudRepository<UserEntity,String>{

	public List<UserEntity> findAllBySuperappAndEmail(@Param("superapp")String superapp, @Param("email")String email, PageRequest of);
	
	public void deleteByEmail(@Param("superapp")String superapp, @Param("email")String email);
}
