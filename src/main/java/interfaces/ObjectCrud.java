package interfaces;

import org.springframework.data.repository.CrudRepository;

import demo.ObjectEntity;

public interface ObjectCrud extends CrudRepository<ObjectEntity, String>{

}