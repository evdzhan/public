package cs225.assignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Class that manipulates two streams, extracts the data from
 * the streams and writes it to a file in GPX format.
 * @author Martin Zokov mvz@aber.ac.uk
 *
 */
public class Reader {

	// the streams
	private StreamHandler streamOne, streamTwo;

	// the way points
	private List<Location> route;

	private long latOffset;
	private long lngOffset;

	/**
	 * Initialises the two streams and the locations container.
	 * Then the streams are processed, and the data obtained is 
	 * written to a file .
	 */
	public Reader() {

		route = new ArrayList<Location>();
		streamOne = new StreamHandler("gps_1_dos.dat");
		streamTwo = new StreamHandler("gps_2_dos.dat");
		this.processStreams();

		try {
			saveToGPX();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Checks if a stream has good signal.
	 */
	public boolean isLocationSignalGood(StreamHandler stream) {
		return stream.isSignalGood();

	}

	/**
	 * Updates the offset values by using two good locations.
	 */
	public void calcOffset(Location locOne, Location locTwo) {
		latOffset = (long) (locOne.getLatitude() * 1000000)
				- (long) (locTwo.getLatitude() * 1000000);
		lngOffset = (long) (locOne.getLongitude() * 1000000)
				- (long) (locTwo.getLongitude() * 1000000);
	}

	/**
	 * Fixes a broken location, by replacing it with the good coordinates and
	 * the offset values.
	 * 
	 */
	public Location getLocationFromOffset(Location loc) {
		loc.setLatitude(loc.getLatitude() + ((double) latOffset) / 1000000);
		loc.setLongitude(loc.getLongitude() + ((double) lngOffset) / 1000000);
		return loc;
	}

	/**
	 * Synchronises the times of the two streams.
	 */
	public boolean streamSync(StreamHandler one, StreamHandler two) {

		while (one.processSentance() != one.LOCATION_RECEIVED) {
			one.processSentance();
		}
		while (two.processSentance() != two.LOCATION_RECEIVED) {
			two.processSentance();
		}

		if (getLocationTime(one) < getLocationTime(two)) {
			while (getLocationTime(one) != getLocationTime(two)) {
				one.processSentance();
			}
		} else {
			while (getLocationTime(one) != getLocationTime(two)) {
				two.processSentance();
			}
		}

		return true;
	}

	/**
	 * Get the time in milliseconds since 01/01/1970. (Unix time)
	 */
	public long getLocationTime(StreamHandler stream) {
		return stream.getCurrentLocation().getDateObject().getTime();
	}

	/**
	 * The main function. Continually fetches RMC sentences. After each
	 * iteration new coordinates and time are obtained. Check is done to see if
	 * the satellite fix are OK. If at least one satellite has it's OK, a
	 * location is added to the array list route.
	 * 
	 */
	public void processStreams() {
		int streamOneStatus = 0, streamTwoStatus = 0;
		streamSync(streamOne, streamTwo);
		while (true) {

			if (isLocationSignalGood(streamOne)) {

				this.addLocation(streamOne.getCurrentLocation());
				calcOffset(streamOne.getCurrentLocation(),
						streamTwo.getCurrentLocation());
			} else if (isLocationSignalGood(streamTwo)) {
				this.addLocation(getLocationFromOffset(streamTwo
						.getCurrentLocation()));
			}

			do {
				streamOneStatus = streamOne.processSentance();
				if (streamOneStatus == -1) {
					return;
				}
			} while (streamOneStatus != 2);

			if (streamOneStatus == 0) {
				return;
			}
			do {
				streamTwoStatus = streamTwo.processSentance();
				if (streamTwoStatus == -1) {
					return;
				}
			} while (streamTwoStatus != 2);

			if (streamTwoStatus == 0) {
				return;
			}
		}
	}

	/**
	 * Prints the current coordinates in the stream str. 
	 */
  
	public void printDetails(StreamHandler str) {
		System.out.println(str.getCurrentLocation().getLatitude() + ","
				+ str.getCurrentLocation().getLongitude());
	}

	 
	public void addLocation(Location loc) {
		route.add(loc);
	}

	
	/**
	 * Writes the contents of the array list route to a file.
	 */
	public void saveToGPX() throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				"Route.gpx")));

		writer.write("<?xml version=\"1.0\"?>\n"
				+ "<gpx\n"
				+ "version=\"1.0\"\n"
				+ "creator = \"Martin Zokov\"\n "
				+ "xmlns:xsi = \"http://www.w3.org/2001/XMLSchema-instance\">\n");

		for (Location l : route) {
			writer.write("<wpt lat=\"" + l.getLatitude() + "\" lon=\""
					+ l.getLongitude() + "\">\n " + "<time>" + l.getDate()
					+ "</time>" + "\n</wpt>\n");
		}
		writer.write("</gpx>");

		writer.close();
	}

}
