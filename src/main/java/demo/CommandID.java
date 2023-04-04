package demo;

public class CommandID {
	private String miniApp;
	private final String superapp = "2023b.shir.zur";
	private String internalCommandID;
	static int counterCommandID = 0;

	public CommandID() {

	}

	public CommandID(String miniApp) {
		super();
		this.miniApp = miniApp;
		this.internalCommandID = String.valueOf(++counterCommandID);
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

}
