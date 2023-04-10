package interfaces;

import org.springframework.data.repository.CrudRepository;

import demo.UserEntity;

public interface UserCrud extends CrudRepository<UserEntity,String>{
	

}
