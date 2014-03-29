package cs225.assignment;

 
import java.util.Date;

/**
 * A data structure. Represents a location with its 
 * latitude, longitude and the date .
 * @author Martin Zokov mvz@aber.ac.uk
 *
 */
public class Location {

	private double latitude;
	private double longitude;
	private Date date;
	
	
	public Location(double lat, double lng, Date dt){
		latitude = lat;
		longitude = lng;
		date = dt;
	}
	
	public Date getDateObject(){
		return date;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getDate(){
		return date.toString();
	}
	
	
}
