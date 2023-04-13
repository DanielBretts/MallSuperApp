package superapp.logic;

import java.util.List;

import superapp.UserBoundary;

public interface UsersService {
	
	public UserBoundary createUser(UserBoundary user);
	
	public UserBoundary login(String userSuperApp, String userEmail);
	
	public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update);
	
	public List<UserBoundary> getAllUsers();
	
	public void deleteAllUsers();
	
	
}
