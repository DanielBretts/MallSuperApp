package demo;

//import org.springframework.beans.factory.annotation.Value;

public class ObjectId {
	
	private final String superapp = "2023b.shir.zur";
	private String internalObjectId;

	private static int counterObjectID = 0;
	
	public ObjectId() {
		super();
		this.internalObjectId = String.valueOf(++counterObjectID);
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
