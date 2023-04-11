package superapp;

import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.PostConstruct;

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
	
	@PostConstruct
	public void setUp() {
		System.err.println(this.superapp);
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
	
	@Value("${spring.application.name}")
	public void setSuperapp(String superapp) {
		this.superapp = superapp;
	}


	@Override
	public String toString() {
		return "UserId [superapp=" + superapp + ", email=" + email + "]";
	}
	
	
	
	
	
	

}
