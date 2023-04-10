package demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import interfaces.ConnectionsDb;
import interfaces.UserCrud;
import interfaces.UsersService;

@Service
public class UsersRdb implements UsersService,ConnectionsDb{
	
	private UserCrud userCrud;
	private roleEnum defaultRole;
	
	@Autowired
	public void setUserCrud(UserCrud userCrud) {
		this.userCrud = userCrud;
	}
	
	@Value("${mallApp.default.role:MINIAPP_USER}")
	public void setDefaultRole(roleEnum defaultRole) {
		this.defaultRole = defaultRole;
	}

	@Override
	public UserBoundary createUser(UserBoundary user) {
//		message.setId(UUID.randomUUID().toString());
		UserEntity entity = (UserEntity) this.toEntity(user);
		if (entity.getAvatar() == null) {
			entity.setAvatar(user.getUsername().substring(0,2));
		}
		entity = this.userCrud.save(entity);
		return (UserBoundary) toBoundary(entity);
	}

	@Override
	public UserBoundary login(String userSuperApp, String userEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update) {
//		UserEntity outdated = this.userCrud.
//				findById(userEmail).
//				orElseThrow(()->new UserNotFoundException("Could not find user to update by this email: " + userEmail));
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers() {
		Iterable<UserEntity> iterable = this.userCrud.findAll();
		Iterator<UserEntity> iterator = iterable.iterator();
		List<UserBoundary> list = new ArrayList<>();
		while (iterator.hasNext()) {
			UserBoundary boundary = (UserBoundary) toBoundary(iterator.next());
			list.add(boundary);
		}
		return list;
	}

	@Override
	@Transactional
	public void deleteAllUsers() {
		userCrud.deleteAll();
	}

	@Override
	public Object toBoundary(Object entity) {
		UserEntity ue = (UserEntity) entity;
		UserBoundary ub = new UserBoundary();		
		ub.setAvatar(ue.getAvatar());
		ub.setEmail(ue.getEmail());
		ub.setRole(ue.getRole());
		ub.setUsername(ue.getUsername());
		ub.setUserId(new UserId(ue.getEmail()));
		return ub;
	}

	@Override
	public Object toEntity(Object boundary) {
		UserBoundary ub = (UserBoundary) boundary;		
		UserEntity ue = new UserEntity();
		
		if(ub.getAvatar() == null) {
			ue.setAvatar(null);
		}else {
			ue.setAvatar(ub.getAvatar());			
		}
		if(ub.getRole() == null) {
			ue.setRole(defaultRole.toString());
		}else {
			ue.setRole(ub.getRole().toString());
		}
		if(ub.getUsername() == null) {
			ue.setUsername(null);
		}else {
			ue.setUsername(ub.getUsername());
		}
		if(ub.getEmail() == null) {
			ue.setEmail(ub.getUsername() + "@email.com");
		}else {
			ue.setEmail(ub.getEmail());
		}
		return ue;
	}
		
}
