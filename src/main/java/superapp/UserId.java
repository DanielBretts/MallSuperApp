package superapp;

public class UserId {
	
	private String superapp;
	private String email;
	
	
	public UserId(String email) {
		super();
		this.email = email;
	}

	public UserId() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSuperapp() {
		return superapp;
	}
	
	public void setSuperapp(String superapp) {
		this.superapp = superapp;
	}


	@Override
	public String toString() {
		return "UserId [superapp=" + superapp + ", email=" + email + "]";
	}
	
	
	
	
	
	

}
