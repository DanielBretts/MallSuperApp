package demo;

public class Location {

	private Double lat;
	private Double lng;

	public Location() {
		super();
	}

	public Location(Double lat, Double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public Location setLat(Double lat) {
		this.lat = lat;
		return this;
	}

	public Double getLng() {
		return lng;
	}

	public Location setLng(Double lng) {
		this.lng = lng;
		return this;
	}

}
