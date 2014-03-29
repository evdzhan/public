/* 
 * File:   GPSReader.h
 * Author: evdjoint
 *
 * Created on 25 March 2014, 16:39
 */

#ifndef GPSREADER_H
#define	GPSREADER_H
using namespace std;

class GPSReader {
public:


    /* Return value integers for read() function */
    const static int SKIP_LINE = 1;
    const static int GPS_TIME = 2;
    const static int SATELITE = 3;
    const static int _EOF = -1;

    /* Used when rounding up coordinates. */
    const static double MIL = 1000000.0;

    /* Initialises the stream with the file name.*/
    GPSReader(const string& file);

    /*Reads a sentence and puts it processes it.*/
    int read();

    /* Converts the coordinates lat and lng to decimal  */
    void degrees_to_decimal(string lat, string lng);

    /* Processes the array of  gsv sentences .*/
    void process_gsv(string data[], int num_sentences);

    /*Processes a single rmc sentence. */
    void process_rmc(string& data);

    /* Not really used... no resources to manage.*/
    GPSReader(const GPSReader& orig);
    virtual ~GPSReader();

    /* Flag to tell if the satellite fix is good .*/
    bool satelitesOK;
    /* The current location.*/
    Location loc;

private:
    ifstream reader; // the file reader.
};

#endif	/* GPSREADER_H */

