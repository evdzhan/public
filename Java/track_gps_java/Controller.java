package cs22510_2014;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * The Controller class to work with two streams.
 * 
 * @author Evdzhan Mustafa enm3@aber.ac.uk
 * 
 */
public class Controller {
	// stream 1 and stream 2
	private GPSReader strm_1, strm_2; 
	
	// saving good locations here
	private LinkedList<GPSReader.Location> locs;

	private long lat_offset; // the offsets
	private long lng_offset;

	// used in rounding
	private final static double MIL = 1000000.0; 

	/** 
	 * Construct new controller with the file names
	 * 
	 * @param file1	 the first file
	 * @param file2  the second file
	 */
	private Controller(String file1, String file2) {
		strm_1 = new GPSReader(file1);
		strm_2 = new GPSReader(file2);
		locs = new LinkedList<>();
	}

	/**
	 * Try to synchronise the two streams, 
	 * so that both have good satellite fix. 
	 * @return did the synchronisation succeed ?
	 */

	private boolean syncSatelite() {
		int lineRead = 0;
		if (!strm_1.satellitesOK) {
			while (!strm_1.satellitesOK) {
				lineRead = strm_1.read();
				if (lineRead == GPSReader._EOF)
					return false;
			}
		}
		if (!strm_2.satellitesOK) {
			while (!strm_2.satellitesOK) {
				lineRead = strm_2.read();

				if (lineRead == GPSReader._EOF)
					return false;
			}
		}

		return true;
	}

	/**
	 * Try to synchronise the two streams, 
	 * so that both have same time. 
	 * @return did the synchronisation succeed ?
	 */
	private boolean syncTimesGPS() {
		int lineRead = 0;
		while (lineRead != GPSReader.GPS_TIME && lineRead != GPSReader._EOF) {
			lineRead = strm_1.read();

		}
		if (lineRead == GPSReader._EOF) {
			return false;
		}

		lineRead = 0;
		while (lineRead != GPSReader.GPS_TIME && lineRead != GPSReader._EOF) {
			lineRead = strm_2.read();
		}
		if (lineRead == GPSReader._EOF) {
			return false;
		}

		int timeDiff = strm_1.currTime.compareTo(strm_2.currTime);

		if (timeDiff == -1) {
			do {
				lineRead = strm_1.read();
				if (lineRead == GPSReader._EOF)
					return false;
				if (lineRead == GPSReader.GPS_TIME) {
					timeDiff = strm_1.currTime.compareTo(strm_2.currTime);
				}

			} while (timeDiff != 0);

		} else if (timeDiff == 1) {
			do {
				lineRead = strm_2.read();
				if (lineRead == GPSReader._EOF)
					return false;
				if (lineRead == GPSReader.GPS_TIME) {
					timeDiff = strm_1.currTime.compareTo(strm_2.currTime);

				}
			} while (timeDiff != 0);

		}

		return true;
	}

	/**
	 * Calculates the offset between two locations.
	 * 
	 * @param one location one
	 * @param two  location two
	 */
	private void getoffset(GPSReader.Location one, GPSReader.Location two) {

		this.lat_offset = (long) (one.lat * MIL) - (long) (two.lat * MIL);

		this.lng_offset = (long) (one.lng * MIL) - (long) (two.lng * MIL);

	}

	/**
	 * Fixes the location of the badFix Location by 
     * adding the offset to the
	 * good location, and applying that to the bad fix.
	 * 
	 * @param goodFix  good satellites location
	 * @param badFix  bad satellites location
	 */
	private void addoffset(GPSReader.Location goodFix,
                           GPSReader.Location badFix) {

		badFix.lat = Math.round(goodFix.lat * MIL + this.lat_offset) / MIL;

		badFix.lng = Math.round(goodFix.lng * MIL + this.lng_offset) / MIL;

	}

	/**
	 * All the magic starts here...
	 */
	private void start() {
		// synchronise the streams first
		this.syncSatelite();
		this.syncTimesGPS();

		// infinite loop, breaks only of one of the streams 
		//returns end of file
		while (true) {

			// check if stream1 has good fix
			if (strm_1.satellitesOK) {

				locs.add(strm_1.currLoc);

				// check if the stream2 has good fix
				if (strm_2.satellitesOK) {

					// offset can be updated
					this.getoffset(strm_1.currLoc, strm_2.currLoc);
				}

				else {

					// stream two was bad, fix it
					this.addoffset(strm_1.currLoc, strm_2.currLoc);
				}

				// if stream1 fails ,try stream2
			} else if (strm_2.satellitesOK) {

				locs.add(strm_2.currLoc);

				// location one was bad, fix it
				this.addoffset(strm_2.currLoc, strm_1.currLoc);

			} else {
				// do nothing , both streams failed,
				// read the next two pair of
				// coordinates
				// maybe new gsvs will be read and good fix will be obtained
			}

			// now read lines until new time and coordinates are met.
			int lineRead = 0;
			do {
				lineRead = strm_1.read();
				if (lineRead == GPSReader._EOF)
					return; // stream 1 ended , exit

			} while (lineRead != GPSReader.GPS_TIME);

			// now do that again for stream 2
			lineRead = 0;
			do {
				lineRead = strm_2.read();
				if (lineRead == GPSReader._EOF)
					return; // stream 2 ended , exit

			} while (lineRead != GPSReader.GPS_TIME);

		}

	}

	/**
	 * Simply go ! Read the two streams , and out put to the file !
	 */
	public static void go() {

		String file1 = "gps_1.dat";
		String file2 = "gps_2.dat";

		Controller controller = new Controller(file1, file2);

		controller.start();

		PrintWriter pw = null;
		try {

			pw = new PrintWriter("data.gpx");
			pw.write("<?xml version=\"1.0\"?>\n"
					+ "<gpx "
					+ "version=\"1.0\"\n"
					+ "creator=\"Evdzhan Mustafa\"\n"
					+ "xmlns:xsi=\""
					+ "http://www.w3.org/2001/XMLSchema-instance\">\n");

			for (GPSReader.Location l : controller.locs) {
				pw.write("<wpt lat=\"" + l.lat + "\" lon=\"" + l.lng + "\">\n"
						+ "<time>" + l.date.toString() + "\n</time>"
						+ "\n</wpt>\n");
			}

			pw.write("</gpx>");

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		pw.close();

	}

}
