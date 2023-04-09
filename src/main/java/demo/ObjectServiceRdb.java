package demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import interfaces.ObjectService;

public class ObjectServiceRdb implements ObjectService{
	private ObjectCrud objectCrud;

	@Autowired
	public void setObjectCrud(ObjectCrud objectCrud) {
		this.objectCrud = objectCrud;
	}
	
	@Override
	@Transactional//(readOnly = false)
	public ObjectBoundary creatObject(ObjectBoundary object) {
		object.setObjectId(new ObjectId().setInternalObjectId(UUID.randomUUID().toString()));
		ObjectBoundaryEntity entity = toEntity(object);
		/// TODO: need to be enum to type!!
		entity.setCreationTimestamp(new Date());
		entity = this.objectCrud.save(entity);
		return this.toBoundary(entity);
	}

	private ObjectBoundary toBoundary(ObjectBoundaryEntity entity) {
		ObjectBoundary ob = new ObjectBoundary();
		ob.setObjectId(new ObjectId().setInternalObjectId(entity.getId()));
		ob.setType(entity.getType()); /// TODO: need to be enum
		ob.setAlias(entity.getAlias());
		ob.setActive(entity.getActive());
		ob.setCreationTimestamp(entity.getCreationTimestamp());
		ob.setLocation(entity.getLocation());
		UserId userId =  new UserId();
		userId.setEmail(entity.getCreatedBy());
		ob.setCreatedBy(userId);
		ob.setObjectDetails(entity.getObjectDetails());
		return ob;
	}

	private ObjectBoundaryEntity toEntity(ObjectBoundary object) {
		ObjectBoundaryEntity entity = new ObjectBoundaryEntity();
		
		entity.setId(object.getObjectId().getInternalObjectId());
		
		if (object.getType() != null) {
			entity.setType(object.getType());
		}else {
			// TODO : do defult enum
		}
		if (object.getAlias() != null) {
			entity.setAlias(object.getAlias());
		}else {
			object.setAlias(null);
		}
		if (object.getActive() != null) {
			entity.setActive(object.getActive());
		}else {
			object.setActive(true);
		}
		
		entity.setCreationTimestamp(object.getCreationTimestamp());
		
		if (object.getLocation() != null) {
			entity.setLocation(object.getLocation());
		}else {
			entity.setId(null);
		}
		
		entity.setCreatedBy(object.getCreatedBy());
		
		if (object.getObjectDetails() != null) {
			entity.setObjectDetails(object.getObjectDetails());
		}else {
			entity.setObjectDetails(new HashMap<>());
		}
		return entity;
	}

	@Override
	@Transactional
	public void updatObject(String superApp, String id, ObjectBoundary ob) {
		ObjectBoundaryEntity existing = this.objectCrud
				.findById(id)
				.orElseThrow(()->new ObjectNotFoundException("could not find object for update by id: " + id));
			
		
	}
}
