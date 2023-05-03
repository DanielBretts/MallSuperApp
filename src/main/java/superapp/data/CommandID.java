package superapp.data;

import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.PostConstruct;

public class CommandID {
	
	private String superapp;
	private String miniapp;
	private String internalCommandID;

	public CommandID() {

	}

	public CommandID(String miniApp) {
		super();
		this.miniapp = miniApp;
	}
	
	@PostConstruct
	public void setUp() {
		System.err.println(this.superapp);
	}

	public String getMiniApp() {
		return miniapp;
	}

	public void setMiniApp(String miniApp) {
		this.miniapp = miniApp;
	}

	public String getInternalCommandID() {
		return internalCommandID;
	}

	public void setInternalCommandID(String internalCommandID) {
		this.internalCommandID = internalCommandID;
	}

	public String getSuperapp() {
		return superapp;
	}

	@Value("${spring.application.name}")
	public void setSuperapp(String superapp) {
		this.superapp = superapp;
	}
	
	

}
