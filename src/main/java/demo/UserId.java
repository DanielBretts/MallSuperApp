package demo;

public class UserId {
	
	private final  String  superapp = "2023b.shir.zur";
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


	@Override
	public String toString() {
		return "UserId [superapp=" + superapp + ", email=" + email + "]";
	}
	
	
	
	
	
	

}
