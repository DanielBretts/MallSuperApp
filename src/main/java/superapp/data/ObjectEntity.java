package superapp.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	private String createdBy_email;
	private String createdBy_superApp;

	private Map<String, Object> objectDetails;

	public ObjectEntity() {
		this.objectDetails = new HashMap<>();
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

	public String getCreatedBy_email() {
		return createdBy_email;
	}

	public void setCreatedBy_email(String createdBy_email) {
		this.createdBy_email = createdBy_email;
	}

	public String getCreatedBy_superApp() {
		return createdBy_superApp;
	}

	public void setCreatedBy_superApp(String createdBy_superApp) {
		this.createdBy_superApp = createdBy_superApp;
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

	@Override
	public String toString() {
		return "ObjectEntity [id=" + id + ", type=" + type + ", alias=" + alias + ", active=" + active
				+ ", creationTimestamp=" + creationTimestamp + ", lat=" + lat + ", lng=" + lng + ", createdBy_email="
				+ createdBy_email + ", createdBy_superApp=" + createdBy_superApp + ", objectDetails=" + objectDetails
				+ "]";
	}

	

	
	

}
