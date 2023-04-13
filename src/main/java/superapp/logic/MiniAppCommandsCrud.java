package superapp.logic;

import org.springframework.data.repository.CrudRepository;

import superapp.data.MiniAppCommandEntity;

public interface MiniAppCommandsCrud extends CrudRepository <MiniAppCommandEntity, String> {
	
}
