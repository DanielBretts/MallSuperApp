package superapp;
public class ObjectId {
	
	private final String superapp = "2023b.shir.zur";
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

	@Override
	public String toString() {
		return "ObjectId [superapp=" + superapp + ", internalObjectId=" + internalObjectId + "]";
	}
}
