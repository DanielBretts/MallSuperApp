package interfaces;

import org.springframework.data.repository.CrudRepository;

import demo.ObjectBoundaryEntity;

public interface ObjectCrud extends CrudRepository<ObjectBoundaryEntity, String>{

}