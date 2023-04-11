package superapp.logic;

import org.springframework.data.repository.CrudRepository;

import superapp.data.ObjectEntity;

public interface ObjectCrud extends CrudRepository<ObjectEntity, String>{

}