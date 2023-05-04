package superapp.logic;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import superapp.data.ObjectEntity;

public interface ObjectCrud extends ListCrudRepository<ObjectEntity, String>{
	
	public Optional<ObjectEntity> findAllByRelatedObjectsContains(ObjectEntity theRelatedObjects);
	
	
}
