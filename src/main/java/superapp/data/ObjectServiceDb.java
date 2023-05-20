package superapp.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.activemq.artemis.core.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import superapp.logic.ObjectCrud;
import superapp.logic.ObjectQueries;
import superapp.logic.UserCrud;
import superapp.restApi.boundaries.ObjectBoundary;
import superapp.data.exceptions.ForbiddenException;
import superapp.data.exceptions.ObjectNotFoundException;
import superapp.data.exceptions.UserNotFoundException;
import java.util.ArrayList;

@Service
public class ObjectServiceDb implements ObjectQueries {
	private ObjectCrud objectCrud;
	private UserCrud userCrud;
	private String superapp;
	private String delimeter = "_";

	@Autowired
	public void setObjectCrud(ObjectCrud objectCrud) {
		this.objectCrud = objectCrud;
	}

	@Autowired
	public void setUserCrud(UserCrud userCrud) {
		this.userCrud = userCrud;
	}

	@Value("${spring.application.name:2023b.shir.zur}")
	public void setSuperapp(String name) {
		this.superapp = name;
	}

	private ObjectBoundary toBoundary(ObjectEntity entity) {
		ObjectBoundary ob = new ObjectBoundary();
		String a = entity.getId();
		String[] parts = a.split(delimeter);
		String part1 = parts[0]; // superapp name
		String part2 = parts[1]; // internal object id
		ob.setObjectId(new ObjectId().setInternalObjectId(part2).setSuperapp(part1));
		ob.setType(entity.getType());
		ob.setAlias(entity.getAlias());
		ob.setActive(entity.getActive());
		ob.setCreationTimestamp(entity.getCreationTimestamp());
		ob.setLocation(new Location().setLat(entity.getLat()).setLng(entity.getLng()));
		ob.setCreatedBy(entity.getCreatedBy());
//		CreatedBy createdBy_temp = new CreatedBy();
//		createdBy_temp.setUserId(new UserId());
//		createdBy_temp.getUserId().setEmail(entity.getEmail());
//		createdBy_temp.getUserId().setSuperapp(superapp);
//		ob.setCreatedBy(createdBy_temp);
		ob.setObjectDetails(entity.getObjectDetails());
		return ob;
	}

	private ObjectEntity toEntity(ObjectBoundary object) throws ObjectNotFoundException, UserNotFoundException {
		if (object == null) {
			throw new ObjectNotFoundException("Object can not be null");
		}
		ObjectEntity entity = new ObjectEntity();

		entity.setId(superapp + delimeter + object.getObjectId().getInternalObjectId());
		if (object.getType() != null && !object.getType().replaceAll(" ", "").isEmpty()) {
			entity.setType(object.getType());
		} else {
			throw new ObjectNotFoundException("Type can not be null or empty string");
		}
		if (object.getAlias() != null && !object.getAlias().replaceAll(" ", "").isEmpty()) {
			entity.setAlias(object.getAlias());
		} else {
			throw new ObjectNotFoundException("Alias can not be null or empty string");
		}
		if (object.getActive() != null) {
			entity.setActive(object.getActive());
		} else {
			entity.setActive(true);
		}
		if (object.getLocation() != null) {
			entity.setLat(object.getLocation().getLat());
			entity.setLng(object.getLocation().getLng());
		} else {
			entity.setLat((double) 0);
			entity.setLng((double) 0);
		}
		if (object.getCreatedBy() != null) {
			if (object.getCreatedBy().getUserId() != null) {
				if (object.getCreatedBy().getUserId().getEmail() != null) {
					entity.setCreatedBy(object.getCreatedBy());
					entity.getCreatedBy().getUserId().setSuperapp(superapp);
				} else {
					throw new UserNotFoundException("The email user this action was not a valid email");
				}
			} else {
				throw new UserNotFoundException("The user this action was UserId is not a valid user");
			}
		} else {
			throw new UserNotFoundException("The user this action was created by is not a valid user");
		}
		if (object.getObjectDetails() != null) {
			entity.setObjectDetails(object.getObjectDetails());
		} else {
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

	@Deprecated
	@Override
	public ObjectBoundary updateObject(String superApp, String id, ObjectBoundary ob) {
		ObjectEntity existing = this.objectCrud.findById(superApp + delimeter + id).orElseThrow(
				() -> new ObjectNotFoundException("could not find object for update by id: " + (superApp + id)));
		if (ob.getType() != null) {
			existing.setType(ob.getType());
		}
		if (ob.getAlias() != null) {
			existing.setAlias(ob.getAlias());
		}
		if (ob.getActive() != null) {
			existing.setActive(ob.getActive());
		}
		if (ob.getLocation() != null) {
			existing.setLat(ob.getLocation().getLat());
			existing.setLng(ob.getLocation().getLng());
		}
		if (ob.getObjectDetails() != null) {
			existing.setObjectDetails(ob.getObjectDetails());
		}
		existing = this.objectCrud.save(existing);
		return (ObjectBoundary) this.toBoundary(existing);
	}

	@Deprecated
	@Override
	public Optional<ObjectBoundary> getSpecificObject(String superApp, String id) {
		return this.objectCrud.findById(superApp + delimeter + id).map(this::toBoundary);
	}

	@Deprecated
	@Override
	public List<ObjectBoundary> getAllObjects() {
		return this.objectCrud.findAll().stream() // Stream<ObjectEntity>
				.map(this::toBoundary) // Stream<ObjectBound>
				.toList(); // List<Message>
	}

	@Deprecated
	@Override
	public void deleteAllObjects() {
		this.objectCrud.deleteAll();
	}

	@Deprecated
	@Override
	public void bind(String InternalObjectIdOrigin, String InternalObjectIdChildren) {
		ObjectEntity origin = this.objectCrud.findById(superapp + delimeter + InternalObjectIdOrigin).orElseThrow(
				() -> new ObjectNotFoundException("could not find origin Object by id: " + InternalObjectIdOrigin));

		ObjectEntity children = this.objectCrud.findById(superapp + delimeter + InternalObjectIdChildren).orElseThrow(
				() -> new ObjectNotFoundException("could not find child Object by id: " + InternalObjectIdChildren));
		if (origin.getId().equals(children.getId()))
			throw new ObjectNotFoundException("The origin ID and children ID can not be same");

		origin.addChildren(children);

		this.objectCrud.save(origin);

	}

	@Deprecated
	@Override
	public List<ObjectBoundary> getAllChildren(String InternalObjectIdOrigin) {
		ObjectEntity origin = this.objectCrud.findById(superapp + delimeter + InternalObjectIdOrigin).orElseThrow(
				() -> new ObjectNotFoundException("could not find origin Object by id: " + InternalObjectIdOrigin));

		List<ObjectEntity> ChildrenObjects = origin.getChildrenObjects();

		List<ObjectBoundary> rv = new ArrayList<>();
		for (ObjectEntity entity : ChildrenObjects) {
			rv.add(this.toBoundary(entity));
		}
		return rv;
	}

	@Override
	public Optional<ArrayList<ObjectBoundary>> getOrigin(String InternalObjectIdChildren) {
		ObjectEntity children = this.objectCrud.findById(superapp + delimeter + InternalObjectIdChildren).orElseThrow(
				() -> new ObjectNotFoundException("could not find child Object by id: " + InternalObjectIdChildren));

		Optional<ArrayList<ObjectEntity>> originOptional = this.objectCrud.findAllByChildrenObjectsContains(children);
		return originOptional.map(list -> {
			ArrayList<ObjectBoundary> resultList = new ArrayList<>();
			for (ObjectEntity entity : list) {
				resultList.add(toBoundary(entity));
			}
			return resultList;
		});
	}

	@Override
	public void updateObjectCheckingRole(String superApp, String internalObjectId, ObjectBoundary ob,
			String userSuperapp, String email) {
		ObjectEntity existing = this.objectCrud.findById(superApp + delimeter + internalObjectId)
				.orElseThrow(() -> new ObjectNotFoundException(
						"could not find object for update by id: " + (superApp + internalObjectId)));
		UserEntity userEntity = this.userCrud.findById(superapp + delimeter + email)
				.orElseThrow(() -> new UserNotFoundException(
						"could not find User with superapp = " + superapp + " and email = " + email));
		if (userEntity.getRole() == UserRole.SUPERAPP_USER) {
			if (ob.getType() != null && !ob.getType().isEmpty()) {
				existing.setType(ob.getType());
			}
			if (ob.getAlias() != null && !ob.getAlias().isEmpty()) {
				existing.setAlias(ob.getAlias());
			}
			if (ob.getLocation() != null) {
				existing.setLat(ob.getLocation().getLat());
				existing.setLng(ob.getLocation().getLng());
			}
			if (ob.getObjectDetails() != null) {
				existing.setObjectDetails(ob.getObjectDetails());
			}
			existing = this.objectCrud.save(existing);
		} else
			throw new ForbiddenException("This user does not have permission to do this");
	}

	@Override
	public Optional<ObjectBoundary> getObjectCheckingRole(String superApp, String internalObjectId, String userSuperapp,
			String email) {
		UserEntity userEntity = this.userCrud.findById(superapp + delimeter + email)
				.orElseThrow(() -> new UserNotFoundException(
						"could not find User with superapp = " + superapp + " and email = " + email));

		ObjectEntity objectEntity = this.objectCrud.findById(superApp + delimeter + internalObjectId)
				.orElseThrow(() -> new ObjectNotFoundException(
						"could not find object for update by id: " + (superApp + internalObjectId)));

		if (objectEntity.getActive() == false) {
			if (userEntity.getRole() == UserRole.SUPERAPP_USER) {
				return Optional.of(toBoundary(objectEntity));
			} else if (userEntity.getRole() == UserRole.MINIAPP_USER) {
				throw new ObjectNotFoundException("Object is not found");
			} else {
				throw new ForbiddenException("This user does not have permission to do this");
			}
		}
		return Optional.of(toBoundary(objectEntity));
	}

	@Override
	public List<ObjectBoundary> getAllObjectsCheckingRole(String userSuperapp, String email, int size, int page) {
		UserEntity userEntity = this.userCrud.findById(superapp + delimeter + email)
				.orElseThrow(() -> new UserNotFoundException(
						"could not find User with superapp = " + superapp + " and email = " + email));

		if (userEntity.getRole() == UserRole.SUPERAPP_USER) {
			return this.objectCrud.findAll(PageRequest.of(page, size, Direction.DESC, "creationTimestamp", "id"))
					.stream().map(this::toBoundary).toList();
		} else if (userEntity.getRole() == UserRole.MINIAPP_USER) {
			return this.objectCrud
					.findAllByActiveIsTrue(PageRequest.of(page, size, Direction.DESC, "creationTimestamp", "id"))
					.stream().map(this::toBoundary).toList();
		}
		throw new ForbiddenException("This user does not have permission to do this");
	}

	@Override
	public void deleteAllObjectsAdminOnly(String superapp, String email) {
		UserEntity userEntity = this.userCrud.findById(superapp + delimeter + email)
				.orElseThrow(() -> new UserNotFoundException(
						"could not find User with superapp = " + superapp + " and email = " + email));
		if (userEntity.getRole() == UserRole.ADMIN)
			this.objectCrud.deleteAll();
		else
			throw new ForbiddenException("This user does not have permission to do this");

	}

	@Override
	public void bindByPermission(String originId, SuperAppObjectIdBoundary superAppObjectIdBoundary,
			String userSuperapp, String email) {
		String id = superAppObjectIdBoundary.getInternalObjectId();
		ObjectEntity origin = this.objectCrud.findById(superapp + delimeter + originId)
				.orElseThrow(() -> new ObjectNotFoundException("could not find origin Object by id: " + originId));

		ObjectEntity children = this.objectCrud.findById(superapp + delimeter + id)
				.orElseThrow(() -> new ObjectNotFoundException("could not find child Object by id: " + id));
		if (origin.getId().equals(children.getId()))
			throw new ObjectNotFoundException("The origin ID and children ID can not be same");

		origin.addChildren(children);

		this.objectCrud.save(origin);

	}

	@Override
	public List<ObjectBoundary> getAllChildrenByPermission(String superApp, String originId, String userSuperapp,
			String email, int size, int page) {
		UserEntity userEntity = this.userCrud.findById(userSuperapp + delimeter + email)
				.orElseThrow(() -> new UserNotFoundException(
						"could not find User with superapp = " + userSuperapp + " and email = " + email));

		ObjectEntity origin = this.objectCrud.findById(superApp + delimeter + originId)
				.orElseThrow(() -> new ObjectNotFoundException("could not find origin Object by id: " + originId));

		List<ObjectEntity> children = origin.getChildrenObjects();
		
		if (userEntity.getRole() == UserRole.SUPERAPP_USER) {
			return children.stream().map(this::toBoundary).toList();
		} else if (userEntity.getRole() == UserRole.MINIAPP_USER) {
			if(origin.getActive() == false)
				throw new ObjectNotFoundException("Object not found");
			else {
				return this.objectCrud
						.findAllByActiveIsTrue(PageRequest.of(page, size, Direction.DESC, "id"))
						.stream().map(this::toBoundary).toList();
			}
		}

		List<ObjectBoundary> rv = new ArrayList<>();
		//TODO fix return value

		return null;
	}

}
