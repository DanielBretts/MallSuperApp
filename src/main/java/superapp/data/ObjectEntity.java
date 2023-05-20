package superapp.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;


@Document(collection = "SuperAppObject")
public class ObjectEntity {
	@Id
	private String id;
	private String type;
	private String alias;
	private boolean active;
	private Date creationTimestamp;
	private Double lat;
	private Double lng;
	private CreatedBy createdBy;
//	private String email;
//	private String superapp;
	private Map<String, Object> objectDetails;
	@DBRef
	private List<ObjectEntity> childrenObjects;

	public ObjectEntity() {
		this.objectDetails = new HashMap<>();
		this.childrenObjects = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
	

//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getSuperapp() {
//		return this.superapp;
//	}
//
//	public void setSuperapp(String superapp) {
//		this.superapp = superapp;
//	}

	public CreatedBy getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(CreatedBy createdBy) {
		this.createdBy = createdBy;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Map<String, Object> getObjectDetails() {
		return objectDetails;
	}

	public void setObjectDetails(Map<String, Object> objectDetails) {
		this.objectDetails = objectDetails;
	}

	public List<ObjectEntity> getChildrenObjects() {
		return childrenObjects;
	}

	public void setChildrenObjects(List<ObjectEntity> childrenObjects) {
		this.childrenObjects = childrenObjects;
	}
	
	public void addChildren(ObjectEntity children) {
				this.childrenObjects.add(children);
	}

//	@Override
//	public String toString() {
//		return "ObjectEntity [id=" + id + ", type=" + type + ", alias=" + alias + ", active=" + active
//				+ ", creationTimestamp=" + creationTimestamp + ", lat=" + lat + ", lng=" + lng + ", email="
//				+ email + ", superApp=" + superapp + ", objectDetails=" + objectDetails
//				+ ", childrenObjects=" + childrenObjects + "]";
//	}
	
}
