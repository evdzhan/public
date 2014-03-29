package cs225.assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class representing a stream.
 * @author Martin Zokov mvz@aber.ac.uk
 *
 */
public class StreamHandler {

	private BufferedReader readStream = null;

	/**
	 * Delimiter  for the split function.
	 */
	private static final String SPLITTER = ",";
	private static final int MAX_GSV_SENTANCES = 3;

	
	/**
	 * Flag to indicate the satellite fix's quality.
	 */
	private boolean isSignalGood;

	private int gsvCounter = 0;
	private int gsvExpected = 0;
	private String GSVSequence[] = new String[MAX_GSV_SENTANCES];
	
// return values for the process sentence method
	public static final int STREAM_END = 0;
	public static final int EXIT_SUCCESS = 1;
	public static final int LOCATION_RECEIVED = 2;
	public static final int GSV_RECEIVED = 3;

	private Location currentLocation;

	public StreamHandler(String filename) {
		try {

			readStream = new BufferedReader(new FileReader(filename));
			currentLocation = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isSignalGood(){
		return isSignalGood;
	}
/**
 * Reads a single line from the stream
 * The method processes the data, and returns 
 * an integer value that tells the caller what was processed.
 *  
 */
	public int processSentance() {
		String line;
		String tempGSV[], tempRMC[];
		try {
			line = readStream.readLine();
			if (line == null) {
				return -1;
			}
			if (!line.isEmpty()) {
				if (line.contains("GSV")) {
					
					tempGSV = line.split(SPLITTER);
					gsvExpected = Integer.parseInt(tempGSV[1]);
					if (Integer.parseInt(tempGSV[2]) <= gsvExpected) {
						GSVSequence[gsvCounter++] = line;
						if (Integer.parseInt(tempGSV[2]) == gsvExpected) {
							gsvCounter = 0;
							readGSVSequence();
							return GSV_RECEIVED;
						}
					}
				} else if (line.contains("RMC")) {
					tempRMC = line.split(SPLITTER);
					readRMC(tempRMC);
					return LOCATION_RECEIVED;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return EXIT_SUCCESS;
	}

	/**
	 * Works on   the GSV sentences.
	 */
	public void readGSVSequence() {
		int satelitesOK = 0;
		 

		for (int i = 0; i < gsvExpected; i++) {
			int checksumIndex = GSVSequence[i].indexOf("*");
			String[] singleSentance = GSVSequence[i]
					.substring(0, checksumIndex).split(SPLITTER);
		 

			for (int k = 7; k < singleSentance.length && k <= 19; k += 4) {
				if (!singleSentance[k].equals("")
						&& Integer.parseInt(singleSentance[k]) > 30) {
					satelitesOK++;
				}
			}
		}
		if (satelitesOK >= 3) {
			isSignalGood = true;
		} else {
			isSignalGood = false;
		}

	}

	/**
	 *  Works on a RMC line, the data is stored in the stream's 
	 *  instance variables.
	 * @param RMCSentance
	 */
	public void readRMC(String RMCSentance[]) {
		String time = RMCSentance[1].substring(0, 6);
		String date = RMCSentance[9];
		
		Date locationDate = null;
		try {
			locationDate = new SimpleDateFormat("HHmmssddMMyy").parse(time+date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Location newLoc = new Location(Double.parseDouble(RMCSentance[3]),
				Double.parseDouble(RMCSentance[5]), locationDate);
		currentLocation = RMCToDecimal(newLoc);
		
		if(RMCSentance[4].equals("S")){
			currentLocation.setLatitude(currentLocation.getLatitude()*-1);
		}
		if(RMCSentance[6].equals("W")){
			currentLocation.setLongitude(currentLocation.getLongitude() * -1);
		}
	}
	/**
	 * Turns a degree-minute  representation of coordinates,
	 * to a decimal one.	 
	 */
	public Location RMCToDecimal(Location loc){
		int latDegree = (int)loc.getLatitude()/100 ;
		double latMin = (loc.getLatitude() - (latDegree * 100))/60.0;
		double lat = (latDegree + latMin);
		loc.setLatitude((double)Math.round(lat*1000000)/1000000.0);
		
		int lngDegree = (int)loc.getLongitude()/100 ;
		double lngMin = (loc.getLongitude() - (lngDegree * 100))/60.0;
		double lng = (lngDegree + lngMin);
		loc.setLongitude((double)Math.round(lng*1000000)/1000000.0);
		return loc;
	}
	
	 
	public Location getCurrentLocation(){
		return currentLocation;
	}
	
}
