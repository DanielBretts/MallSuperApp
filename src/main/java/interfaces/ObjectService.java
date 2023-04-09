package interfaces;

import demo.ObjectBoundary;

public interface ObjectService {
	
	public ObjectBoundary creatObject(ObjectBoundary object);

	public void updatObject(String superApp, String id, ObjectBoundary ob);
}
