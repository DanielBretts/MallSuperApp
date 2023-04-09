package demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import interfaces.ObjectService;

public class ObjectServiceRdb implements ObjectService,ConnectionsDb{
	private ObjectCrud objectCrud;

	@Autowired
	public void setObjectCrud(ObjectCrud objectCrud) {
		this.objectCrud = objectCrud;
	}

	@Override
	public Object toBoundary(Object entity) {
		ObjectEntity oEntity = (ObjectEntity)entity;
		ObjectBoundary ob = new ObjectBoundary();
		ob.setObjectId(new ObjectId().setInternalObjectId(oEntity.getId()));
		ob.setType(oEntity.getType()); /// TODO: need to be enum
		ob.setAlias(oEntity.getAlias());
		ob.setActive(oEntity.getActive());
		ob.setCreationTimestamp(oEntity.getCreationTimestamp());
		ob.setLocation(new Location().setLat(oEntity.getLat()).setLng(oEntity.getLng()));
		UserId userId =  new UserId();
		userId.setEmail(oEntity.getCreatedBy());
		ob.setCreatedBy(userId);
		ob.setObjectDetails(oEntity.getObjectDetails());
		return ob;
	}
	@Override
	public Object toEntity(Object boundary) {
		ObjectBoundary object = (ObjectBoundary)boundary;
		ObjectEntity entity = new ObjectEntity();
		
		entity.setId(object.getObjectId().getInternalObjectId());
		
		if (object.getType() != null) {
			entity.setType(object.getType());
		}else {
			entity.setType(null);// TODO : do defult enum
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
			entity.setLat(object.getLocation().getLat());
			entity.setLng(object.getLocation().getLng());
		}else {
			entity.setLat((double) 0);
			entity.setLng((double) 0);
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
	@Transactional//(readOnly = false)
	public ObjectBoundary creatObject(ObjectBoundary object) {
		object.setObjectId(new ObjectId().setInternalObjectId(UUID.randomUUID().toString()));
		ObjectEntity entity = (ObjectEntity) toEntity(object);
		/// TODO: need to be enum to type!!
		entity.setCreationTimestamp(new Date());
		entity = this.objectCrud.save(entity);
		return (ObjectBoundary) this.toBoundary(entity);
	}


	@Override
	@Transactional
	public ObjectBoundary updatObject(String superApp, String id, ObjectBoundary ob) {
		ObjectEntity existing = this.objectCrud
				.findById(id)
				.orElseThrow(()->new ObjectNotFoundException("could not find object for update by id: " + id));
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

//	@Override
//	@Transactional(readOnly = true)
//	public Optional<ObjectBoundary> getSpecificObject(String superApp, String id) {
//		return this.objectCrud
//				.findById(id)
//				.map(this::toBoundary);
//	}
	@Override
	@Transactional(readOnly = true)
	public Optional<ObjectBoundary> getSpecificObject(String superApp, String id) {
	    return this.objectCrud
	            .findById(id)
	            .map(entity -> toBoundary(entity))
	            .map(object -> (ObjectBoundary) object);
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

//private ObjectBoundary toBoundary(ObjectEntity entity) {
//ObjectBoundary ob = new ObjectBoundary();
//ob.setObjectId(new ObjectId().setInternalObjectId(entity.getId()));
//ob.setType(entity.getType()); /// TODO: need to be enum
//ob.setAlias(entity.getAlias());
//ob.setActive(entity.getActive());
//ob.setCreationTimestamp(entity.getCreationTimestamp());
//ob.setLocation(new Location().setLat(entity.getLat()).setLng(entity.getLng()));
//UserId userId =  new UserId();
//userId.setEmail(entity.getCreatedBy());
//ob.setCreatedBy(userId);
//ob.setObjectDetails(entity.getObjectDetails());
//return ob;
//}
//private ObjectEntity toEntity(ObjectBoundary object) {
//ObjectEntity entity = new ObjectEntity();
//
//entity.setId(object.getObjectId().getInternalObjectId());
//
//if (object.getType() != null) {
//entity.setType(object.getType());
//}else {
//entity.setType(null);// TODO : do defult enum
//}
//if (object.getAlias() != null) {
//entity.setAlias(object.getAlias());
//}else {
//object.setAlias(null);
//}
//if (object.getActive() != null) {
//entity.setActive(object.getActive());
//}else {
//object.setActive(true);
//}
//
//entity.setCreationTimestamp(object.getCreationTimestamp());
//
//if (object.getLocation() != null) {
//entity.setLat(object.getLocation().getLat());
//entity.setLng(object.getLocation().getLng());
//}else {
//entity.setLat((double) 0);
//entity.setLng((double) 0);
//}
//
//entity.setCreatedBy(object.getCreatedBy());
//
//if (object.getObjectDetails() != null) {
//entity.setObjectDetails(object.getObjectDetails());
//}else {
//entity.setObjectDetails(new HashMap<>());
//}
//return entity;
//}
