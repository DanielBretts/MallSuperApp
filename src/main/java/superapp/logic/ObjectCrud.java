package superapp.logic;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import superapp.data.ObjectEntity;
import superapp.restApi.boundaries.ObjectBoundary;

public interface ObjectCrud extends ListCrudRepository<ObjectEntity, String>{
	
	public Optional<ObjectEntity> findAllByChildrenContains(ObjectEntity theChildren);


}