package demo;

public interface ConnectionsDb {
	
	public Object toBoundary(Object entity);
	
	public Object toEntity(Object boundary);

}
