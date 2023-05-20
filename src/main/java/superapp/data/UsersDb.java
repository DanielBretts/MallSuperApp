package superapp.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import superapp.logic.UserCrud;
import superapp.logic.UsersQueries;
import superapp.restApi.boundaries.UserBoundary;
import superapp.data.exceptions.UserParamException;
import superapp.data.exceptions.UserNotFoundException;

@Service
public class UsersDb implements UsersQueries {

	private UserCrud userCrud;
	private UserRole defaultRole;
	private String superapp;
	private char delimeter = '_';

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
			throw new UserParamException("Avatar can not be empty");
		}
		entity = this.userCrud.save(entity);
		return (UserBoundary) toBoundary(entity);
	}

	@Override
	public Optional<UserBoundary> login(String userSuperApp, String userEmail) {
		return this.userCrud.findById(userSuperApp + delimeter + userEmail).map(this::toBoundary);
	}

	@Override
	public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update) {
		System.err.println(update.toString());
		UserEntity entity = this.userCrud.findById(userSuperApp + delimeter + userEmail).orElseThrow(
				() -> new UserNotFoundException("could not find URL with path /" + userSuperApp + "/" + userEmail));
		entity.setRole(toEntityAsEnum(update.getRole()));
		if (update.getAvatar() != null) {
			entity.setAvatar(update.getAvatar());
		}
		if (update.getUsername() != null) {
			entity.setUsername(update.getUsername());
		}
		UserId updatedUserId = update.getUserId();
		if (updatedUserId != null && updatedUserId.getEmail() != null) {
			System.err.println(superapp);
			entity.setId(this.superapp + delimeter + userEmail);
		} else {
			throw new UserNotFoundException("User id field is not valid");
		}
		entity = this.userCrud.save(entity);
		return this.toBoundary(entity);
	}

	@Deprecated
	@Override
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

	@Deprecated
	@Override
	public void deleteAllUsers() {
		userCrud.deleteAll();
	}

	public UserBoundary toBoundary(UserEntity entity) {
		UserBoundary ub = new UserBoundary();
		ub.setAvatar(entity.getAvatar());
		ub.setRole(entity.getRole().toString());
		ub.setUsername(entity.getUsername());
		ub.setUserId(new UserId(getEmailFromId(entity.getId())));
		ub.getUserId().setSuperapp(superapp);
		return ub;
	}

	public String getEmailFromId(String originalString) {
		// String originalString = "superappName_email";
		int index = originalString.indexOf(delimeter);
		if (index != -1) { // Check if the delimiter is found
			return originalString.substring(index + 1);
			// newString is now "email"
		}
		return null;
	}

	public UserEntity toEntity(UserBoundary boundary) {
		UserEntity ue = new UserEntity();

		if (boundary.getAvatar() == null) {
			ue.setAvatar(null);
		} else {
			ue.setAvatar(boundary.getAvatar());
		}
		ue.setRole(toEntityAsEnum(boundary.getRole()));
		if (boundary.getUsername() == null) {
			throw new UserParamException("Username can not be empty");
		} else {
			ue.setUsername(boundary.getUsername());
		}
		if (boundary.getUserId() == null || boundary.getUserId().getEmail() == null) {
			throw new UserParamException("User id field is not valid");
		} else {
			ue.setId(this.superapp + delimeter + boundary.getUserId().getEmail());
		}
		return ue;
	}

	private UserRole toEntityAsEnum(String value) {
		if (value != null) {
			return UserRole.valueOf(value);
		} else {
			return this.defaultRole;
		}
	}

	@Override
	public void deleteAllUsersAdminOnly(String superapp, String email) {
		UserEntity userEntity =  this.userCrud
								.findById(superapp + delimeter + email)
								.orElseThrow(
									() -> new UserNotFoundException("could not find User with superapp = " + superapp + " and email = " + email));
		if(userEntity.getRole() == UserRole.ADMIN)
			this.userCrud.deleteAll();
		else
			throw new UserNotFoundException("This user does not have permission to do this");
	}

	@Override
	public List<UserBoundary> getAllUsersAdminOnly(String superapp, String email, int size, int page) {
		UserEntity userEntity =  this.userCrud
				.findById(superapp + delimeter + email)
				.orElseThrow(
						() -> new UserNotFoundException("could not find User with superapp = " + superapp + " and email = " + email));
		if(userEntity.getRole() == UserRole.ADMIN)
			return this.userCrud
					.findAll(PageRequest.of(page, size, Direction.DESC, "username"))
					.stream()
					.map(this::toBoundary)
					.toList();
		else
			throw new UserNotFoundException("This user does not have permission to do this");
	}

}
