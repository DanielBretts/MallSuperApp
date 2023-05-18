package superapp.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import superapp.logic.ObjectCrud;
import superapp.logic.ObjectQueries;
import superapp.restApi.boundaries.ObjectBoundary;
import superapp.data.exceptions.ObjectNotFoundException;
import superapp.data.exceptions.UserNotFoundException;
import java.util.ArrayList;

@Service
public class ObjectServiceDb implements ObjectQueries{
	private ObjectCrud objectCrud;
	private String superapp;
	private ObjectMapper jackson;
	private JmsTemplate jmsTemplate;
	private String delimeter = "_"; 


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
		String[] parts = a.split(delimeter);
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
		if(object == null) {
			throw new ObjectNotFoundException("Object can not be null");
		}
		ObjectEntity entity = new ObjectEntity();
		
		entity.setId(superapp +delimeter+ object.getObjectId().getInternalObjectId());
		if (object.getType() != null && !object.getType().replaceAll(" ","").isEmpty()) {
			entity.setType(object.getType());
		}else {
			throw new ObjectNotFoundException("Type can not be null or empty string");
		}
		if (object.getAlias() != null && !object.getAlias().replaceAll(" ","").isEmpty()) {
			entity.setAlias(object.getAlias());
		}else {
			throw new ObjectNotFoundException("Alias can not be null or empty string");
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
			String email;
			if(object.getCreatedBy().getUserId() != null) {
				email = object.getCreatedBy().getUserId().getEmail();
			}else {
				throw new UserNotFoundException("UserId can not be null");
			}
			if(email != null && !email.replaceAll(" ", "").isEmpty()) {
				entity.setCreatedBy_email(object.getCreatedBy().getUserId().getEmail());
				entity.setCreatedBy_superApp(superapp);
			}else {
				throw new UserNotFoundException("Email can not be null or empty string");
			}
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
	public ObjectBoundary createObject(ObjectBoundary object) {
		object.setObjectId(new ObjectId().setInternalObjectId(UUID.randomUUID().toString()));
		object.getObjectId().setSuperapp(superapp);
		ObjectEntity entity = (ObjectEntity) toEntity(object);
		entity.setCreationTimestamp(new Date());
		entity = this.objectCrud.save(entity);
		return (ObjectBoundary) toBoundary(entity);
	}

	@Override
	public ObjectBoundary updateObject(String superApp, String id, ObjectBoundary ob) {
		ObjectEntity existing = this.objectCrud
				.findById(superApp+delimeter+id)
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
	public Optional<ObjectBoundary> getSpecificObject(String superApp, String id) {
	    return this.objectCrud
	    		.findById(superApp+delimeter+id)
				.map(this::toBoundary);
	}
	
	@Override
	public List<ObjectBoundary> getAllObjects() {
		return this.objectCrud.findAll()
				.stream() // Stream<ObjectEntity>
				.map(this::toBoundary) // Stream<ObjectBound>
				.toList(); //List<Message>
	}

	@Override
	public void deleteAllObjects() {
		this.objectCrud.deleteAll();
	}
	
	@Override
	public void bind(String InternalObjectIdOrigin, String InternalObjectIdChildren) {
		ObjectEntity origin = 
				  this.objectCrud
					.findById(superapp+delimeter+InternalObjectIdOrigin)
					.orElseThrow(()->new ObjectNotFoundException("could not find origin Object by id: " + InternalObjectIdOrigin));
		
		ObjectEntity children= 
				this.objectCrud
				.findById(superapp+delimeter+InternalObjectIdChildren)
				.orElseThrow(()->new ObjectNotFoundException("could not find child Object by id: " + InternalObjectIdChildren));
		if(origin.getId().equals(children.getId()))
			throw new ObjectNotFoundException("The origin ID and children ID can not be same");
		origin.addChildren(children);
				
		this.objectCrud.save(origin);
		
	}
	@Override
	public List<ObjectBoundary> getAllChildren(String InternalObjectIdOrigin) {
		ObjectEntity origin = 
				  this.objectCrud
					.findById(superapp+delimeter+InternalObjectIdOrigin)
					.orElseThrow(()->new ObjectNotFoundException("could not find origin Object by id: " + InternalObjectIdOrigin));

		List<ObjectEntity> ChildrenObjects = origin
			.getChildrenObjects();
		
		List<ObjectBoundary> rv = new ArrayList<>();
		for (ObjectEntity entity : ChildrenObjects) {
			rv.add(this.toBoundary(entity));
		}
		return rv;
	}
	@Override
	public Optional<ArrayList<ObjectBoundary>> getOrigin(String InternalObjectIdChildren) {
		ObjectEntity children= 
				this.objectCrud
				.findById(superapp+delimeter+InternalObjectIdChildren)
				.orElseThrow(()->new ObjectNotFoundException("could not find child Object by id: " + InternalObjectIdChildren));
		
		Optional<ArrayList<ObjectEntity>> originOptional = this.objectCrud
			.findAllByChildrenObjectsContains(children);
	    return originOptional
	            .map(list -> {
	                ArrayList<ObjectBoundary> resultList = new ArrayList<>();
	                for (ObjectEntity entity : list) {
	                    resultList.add(toBoundary(entity));
	                }
	                return resultList;
	            });
	}
	@Override
	public ObjectBoundary handleLater(ObjectBoundary boundary) {
		// TODO Auto-generated method stub
		return null;
	}


}


