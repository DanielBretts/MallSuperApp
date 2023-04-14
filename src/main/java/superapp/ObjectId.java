package superapp;
public class ObjectId {
	
	private String superapp;
	private String internalObjectId;
	
	public ObjectId() {
		super();
	}

	public String getInternalObjectId() {
		return internalObjectId;
	}

	public ObjectId setInternalObjectId(String internalObjectId) {
		this.internalObjectId = internalObjectId;
		return this;
	}

	public String getSuperapp() {
		return superapp;
	}
	

	public ObjectId setSuperapp(String superapp) {
		this.superapp = superapp;
		return this;
	}

	@Override
	public String toString() {
		return "ObjectId [superapp=" + superapp + ", internalObjectId=" + internalObjectId + "]";
	}
}
