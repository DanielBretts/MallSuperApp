package superapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import superapp.logic.UserCrud;
import superapp.logic.UsersService;
import superapp.data.UserRole;
import superapp.data.UserEntity;

@Service
public class UsersRdb implements UsersService{
	
	private UserCrud userCrud;
	private UserRole defaultRole;
	private String superapp;
	
	@Autowired
	public void setUserCrud(UserCrud userCrud) {
		this.userCrud = userCrud;
	}
	
	@Value("${mallApp.default.role:MINIAPP_USER}")
	public void setDefaultRole(UserRole defaultRole) {
		this.defaultRole = defaultRole;
	}
	
	@Value("${spring.application.name:2023b.shir.zur}")
	public void setSuperapp(String name) {
		this.superapp = name;
	}

	@Override
	public UserBoundary createUser(UserBoundary user) {
		UserEntity entity = (UserEntity) this.toEntity(user);
		if (entity.getAvatar() == null) {
			entity.setAvatar(user.getUsername().substring(0,2));
		}
		entity = this.userCrud.save(entity);
		return (UserBoundary) toBoundary(entity);
	}

	@Override
	public UserBoundary login(String userSuperApp, String userEmail) {
		UserEntity existing = this.userCrud
				.findById(userSuperApp + userEmail)
				.orElseThrow(()->new UserNotFoundException("Could not find user by this id: " + userSuperApp + userEmail));
		return toBoundary(existing);
	}

	@Override
	public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update) {
		UserEntity entity = this.userCrud
				.findById(userSuperApp+userEmail)
				.orElseThrow(()->new UserNotFoundException("could not find user with mail " + userEmail));
			
		if (update.getEmail() != null) {
			entity.setEmail(update.getEmail());
		}
		if(update.getRole() != null) {
			entity.setRole(update.getRole().toString());
		}
		if(update.getAvatar() != null) {
			entity.setAvatar(update.getAvatar());
		}
		if(update.getUsername() != null) {
			entity.setUsername(update.getUsername());
		}
		UserId updatedUserId = update.getUserId();
		if(updatedUserId != null) {
			entity.setId(updatedUserId.getSuperapp()+updatedUserId.getEmail());
		}else {
			//entity.setId(updatedUserId.getSuperapp()+updatedUserId.getEmail());
		}
		entity = this.userCrud.save(entity);
		return this.toBoundary(entity);
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

	public UserBoundary toBoundary(UserEntity entity) {
		UserBoundary ub = new UserBoundary();		
		ub.setAvatar(entity.getAvatar());
		ub.setEmail(entity.getEmail());
		ub.setRole(entity.getRole());
		ub.setUsername(entity.getUsername());
		ub.setUserId(new UserId(entity.getEmail()));
		ub.getUserId().setSuperapp(superapp);
		return ub;
	}

	public UserEntity toEntity(UserBoundary boundary) {	
		UserEntity ue = new UserEntity();
	
		if(boundary.getAvatar() == null) {
			ue.setAvatar(null);
		}else {
			ue.setAvatar(boundary.getAvatar());			
		}
		if(boundary.getRole() == null) {
			ue.setRole(defaultRole.toString());
		}else {
			ue.setRole(boundary.getRole().toString());
		}
		if(boundary.getUsername() == null) {
			ue.setUsername(null);
		}else {
			ue.setUsername(boundary.getUsername());
		}
		if(boundary.getEmail() == null) {
			ue.setEmail(boundary.getUsername() + "@email.com");
		}else {
			ue.setEmail(boundary.getEmail());
		}	
		if (boundary.getUserId().getSuperapp() == null) {
			ue.setId(this.superapp + ue.getEmail());
		}
		else {
			ue.setId(boundary.getUserId().getSuperapp() + boundary.getUserId().getEmail()); 
		}
		return ue;
	}
		
}
