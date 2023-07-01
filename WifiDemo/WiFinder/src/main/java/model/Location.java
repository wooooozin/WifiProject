package model;

public class Location {
	private String locationId;
	private String latitude;
	private String longitude;
	private String confirmDate;

	
	public String getLocationId() {
		return locationId;
	}
	
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getConfirmDate() {
		return confirmDate;
	}
	
	public void setConfirmDate(String confirmDate) {
		this.confirmDate = confirmDate;
	}	

}
