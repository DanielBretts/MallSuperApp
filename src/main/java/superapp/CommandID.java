package superapp;

import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.PostConstruct;

public class CommandID {
	
	private String superapp;
	private String miniApp;
	private String internalCommandID;
	static int counterCommandID = 0;

	public CommandID() {

	}

	public CommandID(String miniApp) {
		super();
		this.miniApp = miniApp;
		this.internalCommandID = String.valueOf(++counterCommandID);
	}
	
	@PostConstruct
	public void setUp() {
		System.err.println(this.superapp);
	}

	public String getMiniApp() {
		return miniApp;
	}

	public void setMiniApp(String miniApp) {
		this.miniApp = miniApp;
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
