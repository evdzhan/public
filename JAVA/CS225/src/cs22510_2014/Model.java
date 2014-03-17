package cs22510_2014;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Model {

	public final static String EOF = "EOF";
	public final static String GPS_TIME = "GPS_TIME";
	public final static String SATELITE = "SATELITE";

	private final static String GSV = "GSV";
	private final static String RMC = "RMC";
	private BufferedReader br;

	public Location currentLoc;
	public boolean satelitesAreOK;
	public Date time;

	public Model(String fileName) {

		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (IOException e) {
		}
		satelitesAreOK = false;

	}

	public String read() {
		String data = "";
		try {
			while (true) {
				data = br.readLine();
				if (data == null)
					return EOF;
				if (data.equals(""))
					continue;

				// System.out.println(data);
				String type = data.substring(3, 6);

				if (type.equals(GSV)) {
					int linesNum = Integer.parseInt(data.substring(7, 8));

					String[] sateliteData = new String[linesNum];

					sateliteData[0] = data;

					for (int i = 1; i < linesNum; i++) {
						sateliteData[i] = br.readLine();
					}
					proccessGSV(sateliteData);

					// System.out.println("Satelite data updated.");
					return SATELITE;
				} else if (type.equals(RMC)) {
					proccessRMC(data);
					// System.out.println("Time and coordinates updated.");
					return GPS_TIME;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return EOF;

	}

	public void proccessGSV(String[] sateliteData) {

		int satelitesNum = Integer.parseInt(sateliteData[0].substring(11, 13));
		if (satelitesNum < 3) {
			this.satelitesAreOK = false;
			return;
		}

		int count = 0;

		for (int i = 0; i < sateliteData.length; i++) {
			int end = sateliteData[i].indexOf('*');
			String[] data = sateliteData[i].substring(14, end).split(",");
			for (int o = 3; o < 16 && satelitesNum > 0; o += 4) {
				if (!data[o].equals("") && Integer.parseInt(data[o]) > 30) {
					count++;
					if (count == 3) {
						this.satelitesAreOK = true;
						System.out.println("STATUS OK - ");
						return;
					}
				}
				satelitesNum--;
			}

		}
		System.out.println("NOT OKAY - ");

	}

	public void proccessRMC(String data) {

		String[] thedata = data.split(",");
		String thetime = thedata[1].substring(0, 6);
		String thedate = thedata[9];

		try {
			time = new SimpleDateFormat("HHmmssddMMyy", Locale.ENGLISH)
					.parse(thetime + thedate);
		} catch (ParseException e) {
			System.out.println("lol");
		}
		this.currentLoc = degreesToDecimal(thedata[3],thedata[5]);

	}

	public Location degreesToDecimal(String latitude, String longitude) {

		Location loc = new Location();
		double data1 = Double.parseDouble(latitude);
		int degrees1 = (int) data1 / 100;
		double minutes1 = (data1 % 100) /   60.0; 
		double minutes = data1 - (degrees1 * 100); 
	    minutes1 = (double)Math.round(minutes1 * 100000) /  100000;
		
		loc.latitude = degrees1 + minutes1;
		 
		double data2 = Double.parseDouble(longitude);
		int degrees2 = (int) data2 / 100;
		double minutes2 = data2 % 100 / 60.0;
		 minutes2 = (double)Math.round(minutes2 * 100000 ) /  100000 ;
		loc.longitude = degrees2 + minutes2;
 
		return loc;
	}
}
