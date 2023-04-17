package superapp.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import superapp.utils.ConverterOfMapToJson;


@Entity
@Table(name = "SuperAppObjectEntity")
public class ObjectEntity {
	@Id
	private String id;
	private String internalObjectId;
	private String type;
	private String alias;
	private boolean active;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTimestamp;
	private Double lat;
	private Double lng;
	private String createdBy;
	
	@Convert(converter = ConverterOfMapToJson.class)
	@Lob
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

	public String getInternalObjectId() {
		return internalObjectId;
	}

	public void setInternalObjectId(String internalObjectId) {
		this.internalObjectId = internalObjectId;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserId createdBy) {
		this.createdBy = createdBy.getEmail();
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
				+ ", creationTimestamp=" + creationTimestamp + ", lat=" + lat + ", lng=" + lng + ", createdBy="
				+ createdBy + ", objectDetails=" + objectDetails + "]";
	}

	
	

}
