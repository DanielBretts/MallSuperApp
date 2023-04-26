package superapp.logic;

import org.springframework.data.repository.ListCrudRepository;

import superapp.data.ObjectEntity;

public interface ObjectCrud extends ListCrudRepository<ObjectEntity, String>{

}