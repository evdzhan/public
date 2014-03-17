package cs22510_2014;

import java.util.Date;

public class Location {
	public double latitude;
	public double longitude;

	public String toString() {
		return latitude + " " + longitude;
	}

	 
	public Location offset(Location l) {
		Location loc = new Location();

		long lata = (long) (l.latitude * 100000);
		long latb = (long) (this.latitude * 100000);

		loc.latitude = (lata - latb);

		long lnga = (long) (l.longitude * 100000);
		long lngb = (long) (this.longitude * 100000);

		loc.longitude = (lnga - lngb);

		return loc;
	}

	public void addOffset(Location offset) {
		latitude += offset.latitude / 100000.0;
		longitude += offset.longitude / 100000.0;
	}
}
