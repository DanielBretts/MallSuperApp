package demo;

public class NewUserBoundary{
	
	private String username;
	private String avatar;
	private String role;
	private String email;
	private UserBoundary userBoundary;
	
	public NewUserBoundary() {
	}


	public NewUserBoundary(String username, String avatar, String email, String role) {
		super();
		this.username = username;
		this.avatar = avatar;
		this.email = email;
		this.role = role;
		this.userBoundary = new UserBoundary(email,role,username,avatar,new UserId(email));
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public UserBoundary getUserBoundary() {
		return userBoundary;
	}


	public void setUserBoundary(UserBoundary userBoundary) {
		this.userBoundary = userBoundary;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}






	
	

}
