/* 
 * File:   Controller.h
 * Author: evdjoint
 *
 * Created on 27 March 2014, 11:49
 */

#ifndef CONTROLLER_H
#define	CONTROLLER_H
using namespace std;

class Controller {
public:
    /* Used when rounding coordinate values.*/
    const static double MIL = 1000000.0;

    /* Declare the file names. */
    const static string FILE_1;
    const static string FILE_2;
    const static string FILE_3;

    /* Creates object of this class and runs it appropriately. */
    static void go();

    /* Starts reading from the two streams*/
    void start();

    /* Synchronises the streams so that both have good fix.*/
    bool sync_satelite();

    /* Synchronises the streams so that both have the same initial time.*/
    bool sync_time_gps();

    /* Writes the contents of the locations in the vector to a file.*/
    void write(vector<Location> locs);

    /* Offset functions.*/
    void get_offset(const Location& one, const Location& two);
    void add_offset(const Location& good, Location& bad);
    
    /* Default constructor, taking two files with the file names. */
    Controller(const string& file_1, const string& file_2);
    
    virtual ~Controller();
private:
    GPSReader strm_1; // stream 1
    GPSReader strm_2; // stream 2
    long lat_offset; // latitude offset
    long lng_offset; // longitude offset
    ofstream writer; // the writer

};

#endif	/* CONTROLLER_H */

