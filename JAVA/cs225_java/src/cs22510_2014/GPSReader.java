package cs22510_2014;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class representing a stream. Contains current location, 
 * current time and is responsible for reading from the file. 
 * Holds a boolean flag to indicate
 * whether the current satellite fix is good.
 * 
 * @author Evdzhan Mustafa enm3@aber.ac.uk
 * 
 */
public class GPSReader {

	/**
	 * Nested class to represent a location and the time.
	 * 
	 * @author Evdzhan Mustafa enm3@aber.ac.uk
	 * 
	 */
	protected class Location {
		protected double lat; // latitude
		protected double lng; // longitude
		protected Date date;

	}

	/* A various static integers for the return value of read function. */
	protected final static int _EOF = -1;
	protected final static int SKIP_LINE = 1;
	protected final static int GPS_TIME = 2;
	protected final static int SATELITE = 3;

	/* Strings to detect the lines we need. */
	protected final static String GSV = "GSV";
	protected final static String RMC = "RMC";

	protected final static double MIL = 1000000.0; // used in rounding

	protected Location currLoc;
	protected Date currTime;
	protected boolean satellitesOK;

	private BufferedReader br;

	/**
	 * @param fileName  The file to be opened
	 */

	public GPSReader(String fileName) {
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// assume satellite fix is bad at the beginning
		satellitesOK = false; 

	}

	/**
	 * Reads a sentence from the file.
	 * 
	 * @return integer corresponding to the type of sentence read
	 */
	protected int read() {
		String data = "";
		try {

			data = br.readLine();
			if (data == null)
				return _EOF; // end of file reached

			if (data.equals("")){
				return SKIP_LINE; // empty line
				} 
			
            // see what type of sentence was  read
			String type = data.substring(3, 6); 
												

			if (type.equals(GSV)) {

				// get the number of gsv sentences
				int linesNum = Integer.parseInt(data.substring(7, 8));

				// array to insert the gsv sentences
				String[] sateliteData = new String[linesNum];

				// place the already read gsv line to the array
				sateliteData[0] = data;
				
				
				// store each gsv sentence in the array
				for (int i = 1; i < linesNum; i++) { 
														 
					sateliteData[i] = br.readLine();
				}

				// this will update the satellitesOK boolean
				processGSV(sateliteData); 
											 
				// satellite info obtained
				return SATELITE; 

			} else if (type.equals(RMC)) {

				processRMC(data);

				return GPS_TIME; // location and time updated

			} else {

				return SKIP_LINE; // skip any other line

			}

		} catch (Exception e) {
			e.printStackTrace();
			return _EOF; // if error better exit
		}

	}

	/**
	 * Works on the data given by GSV sentence(s)
	 * 
	 * @param gsvData  array containing all the gsv sentences
	 */
	private void processGSV(String[] gsvData) {

		int satNum = Integer.parseInt(gsvData[0].substring(11, 13));

		int count = 0;

		
		// loop through the gsv sentences
		for (int i = 0; i < gsvData.length; i++) { 
												 

			// get the index of the asterix
			int asterixGetter = gsvData[i].indexOf('*'); 
															  

			// split the current sentence to tokens
			String[] tokens = gsvData[i].substring(14, asterixGetter)
					.split(",");

			// loop through the SNR values
			for (int o = 3; o < 16 && satNum > 0 && 
					o < tokens.length; o += 4, satNum--) {

				// check if snr is good  
				if (!tokens[o].equals("") &&
						Integer.parseInt(tokens[o]) >= 30) {
					count++;
					if (count == 3) {

						// satellites are ok
						this.satellitesOK = true; 
						
						// no point looking for further SNR sentences 
						return; 							 
					}
				}
			}
		}
		 // if this line is reached, satellites are NOT ok
		this.satellitesOK = false;
	}

	/**
	 * Processes a RMC sentence. Gets the date time and the new coordinates.
	 * 
	 * @param rmcData
	 */
	private void processRMC(String rmcData) {

		String[] thedata = rmcData.split(",");
		
		// drop the milliseconds
		String thetime = thedata[1].substring(0, 6); 
		String thedate = thedata[9];

		try {
			currTime = new SimpleDateFormat("HHmmssddMMyy", Locale.ENGLISH)
					.parse(thetime + thedate);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		// turn the degrees to decimal and place it to current loc
		this.currLoc = degreesToDecimal(thedata[3], thedata[5]);

		if (thedata[4].equals("S")) // if in the south hemisphere
			this.currLoc.lat *= -1;

		if (thedata[6].equals("W")) // if in the west hemisphere
			this.currLoc.lng *= -1;

		this.currLoc.date = currTime;

	}

	/**
	 * Converts degrees and minute representation 
	 * of latitude and longitude, to a decimal form.
	 * 
	 * @param latitude  string holding latitude
	 * @param longitude string holding longitude
	 * @return the location with decimal coordinates
	 */
	private Location degreesToDecimal(String latitude, String longitude) {

		Location loc = new Location();

		// parse the value first
		double lat = Double.parseDouble(latitude);

		// truncate to get the degrees
		int lat_degrees = (int) lat / 100;

		// transform the minutes to decimal
		double lat_minutes = (lat - lat_degrees * 100) / 60.0;

		
		
		// repeat for longitude
		double lng = Double.parseDouble(longitude);
		int lng_degrees = (int) lng / 100;
		double lng_minutes = (lng - lng_degrees * 100) / 60.0;

		
		// do some rounding at place the new data to the location
		loc.lat = Math.round((lat_minutes + lat_degrees) * MIL) / MIL;
		loc.lng = Math.round((lng_minutes + lng_degrees) * MIL) / MIL;

		return loc;
	}
}
