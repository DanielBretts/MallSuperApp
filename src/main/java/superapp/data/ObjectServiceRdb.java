package superapp.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import superapp.logic.ObjectCrud;
import superapp.logic.ObjectService;
import superapp.restApi.boundaries.ObjectBoundary;
import superapp.data.exceptions.ObjectNotFoundException;
import superapp.data.exceptions.UserNotFoundException;


@Service
public class ObjectServiceRdb implements ObjectService{
	private ObjectCrud objectCrud;
	private String superapp;


	@Autowired
	public void setObjectCrud(ObjectCrud objectCrud) {
		this.objectCrud = objectCrud;
	}
	@Value("${spring.application.name:2023b.shir.zur}")
	public void setSuperapp(String name) {
		this.superapp = name;
	}

	private ObjectBoundary toBoundary(ObjectEntity entity) {
		ObjectBoundary ob = new ObjectBoundary();
		String a = entity.getId();
		String[] parts = a.split("_");
		String part1 = parts[0]; // supper app name
		String part2 = parts[1]; // internal object id
		ob.setObjectId(new ObjectId().setInternalObjectId(part2).setSuperapp(part1));
		ob.setType(entity.getType());
		ob.setAlias(entity.getAlias());
		ob.setActive(entity.getActive());
		ob.setCreationTimestamp(entity.getCreationTimestamp());
		ob.setLocation(new Location().setLat(entity.getLat()).setLng(entity.getLng()));
		CreatedBy createdBy_temp = new CreatedBy();
		createdBy_temp.setUserId(new UserId());
		createdBy_temp.getUserId().setEmail(entity.getCreatedBy_email());
		createdBy_temp.getUserId().setSuperapp(superapp);
		ob.setCreatedBy(createdBy_temp);				
		ob.setObjectDetails(entity.getObjectDetails());
		return ob;
	}
	private ObjectEntity toEntity(ObjectBoundary object) throws ObjectNotFoundException,UserNotFoundException {
		ObjectEntity entity = new ObjectEntity();
		
		entity.setId(superapp +'_'+ object.getObjectId().getInternalObjectId());
		if (object.getType() != null) {
			entity.setType(object.getType());
		}else {
			throw new ObjectNotFoundException("Type can not be null");
		}
		if (object.getAlias() != null) {
			entity.setAlias(object.getAlias());
		}else {
			throw new ObjectNotFoundException("Alias can not be null");
		}
		if (object.getActive() != null) {
			entity.setActive(object.getActive());
		}else {
			entity.setActive(true);
		}		
		if (object.getLocation() != null) {
			entity.setLat(object.getLocation().getLat());
			entity.setLng(object.getLocation().getLng());
		}else {
			entity.setLat((double) 0);
			entity.setLng((double) 0);
		}
		if(object.getCreatedBy() != null) {
			entity.setCreatedBy_email(object.getCreatedBy().getUserId().getEmail());
			entity.setCreatedBy_superApp(superapp);
		}else {
			throw new UserNotFoundException("The user this action was created by is not a valid user");
		}
		
		if (object.getObjectDetails() != null) {
			entity.setObjectDetails(object.getObjectDetails());
		}else {
			entity.setObjectDetails(new HashMap<>());
		}
		return entity;
	}

	@Override
	@Transactional//(readOnly = false)
	public ObjectBoundary creatObject(ObjectBoundary object) {
		object.setObjectId(new ObjectId().setInternalObjectId(UUID.randomUUID().toString()));
		object.getObjectId().setSuperapp(superapp);
		ObjectEntity entity = (ObjectEntity) toEntity(object);
		entity.setCreationTimestamp(new Date());
		entity = this.objectCrud.save(entity);
		return (ObjectBoundary) toBoundary(entity);
	}

	@Override
	@Transactional
	public ObjectBoundary updatObject(String superApp, String id, ObjectBoundary ob) {
		ObjectEntity existing = this.objectCrud
				.findById(superApp+'_'+id)
				.orElseThrow(()->new ObjectNotFoundException("could not find object for update by id: " + (superApp+id)));
		if(ob.getType() != null) {
			existing.setType(ob.getType());
		}
		if(ob.getAlias() != null) {
			existing.setAlias(ob.getAlias());
		}
		if(ob.getActive() != null) {
			existing.setActive(ob.getActive());
		}
		if(ob.getLocation() != null) {
			existing.setLat(ob.getLocation().getLat());
			existing.setLng(ob.getLocation().getLng());
		}
		if(ob.getObjectDetails() != null) {
			existing.setObjectDetails(ob.getObjectDetails());
		}
		existing = this.objectCrud.save(existing);
		return (ObjectBoundary) this.toBoundary(existing);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<ObjectBoundary> getSpecificObject(String superApp, String id) {
	    return this.objectCrud
	    		.findById(superApp+'_'+id)
				.map(this::toBoundary);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ObjectBoundary> getAllObjects() {
		Iterable<ObjectEntity> iterable = this.objectCrud.findAll();
		Iterator<ObjectEntity> iterator = iterable.iterator();
		List<ObjectBoundary> rv = new ArrayList<>();
		while (iterator.hasNext()) {
			ObjectBoundary objectBoundary = (ObjectBoundary) toBoundary(iterator.next());
			rv.add(objectBoundary);
		}
		return rv;
	}

	@Override
	public void deleteAllObjects() {
		this.objectCrud.deleteAll();
	}


}


