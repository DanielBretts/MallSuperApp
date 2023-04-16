package superapp;

public class UserBoundary {
	String email;
	String role; 
	String username;
	String avatar; 
	UserId userId;
	
	public UserBoundary() {
	
	}

	public UserBoundary(String email, String role, String username, String avatar, UserId userId) {
		super();
		this.email = email;
		this.role = role;
		this.username = username;
		this.avatar = avatar;
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public UserId getUserId() {
		return userId;
	}

	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserBoundary [email=" + email + ", role=" + role + ", username=" + username + ", avatar=" + avatar
				+ ", userId=" + userId + "]";
	}
	
	

}
