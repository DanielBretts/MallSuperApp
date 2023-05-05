package superapp.data;

public class SuperAppObjectIdBoundary {
	private String internalObjectId;
	private String superapp;

	public SuperAppObjectIdBoundary() {
	}

	public SuperAppObjectIdBoundary(String internalObjectId, String superapp) {
		super();
		this.internalObjectId = internalObjectId;
		this.superapp = superapp;
	}

	public String getInternalObjectId() {
		return internalObjectId;
	}

	public void setInternalObjectId(String internalObjectId) {
		this.internalObjectId = internalObjectId;
	}

	public String getSuperapp() {
		return superapp;
	}

	public void setSuperapp(String superapp) {
		this.superapp = superapp;
	}

	@Override
	public String toString() {
		return "SuperAppObjectIdBoundary [internalObjectId=" + internalObjectId + ", superapp=" + superapp + "]";
	}
	
	
	

}