/* 
 * File:   Controller.cpp
 * Author: evdjoint
 * 
 * Created on 27 March 2014, 11:49
 */
#include <stdlib.h>
#include <math.h>
#include <iostream>
#include <vector>
#include <iomanip>
#include <string>
#include <fstream>
#include "Location.h"
#include "GPSReader.h"
#include "Controller.h"
using namespace std;

const string Controller::FILE_1 = "gps_1.dat";
const string Controller::FILE_2 = "gps_2.dat";
const string Controller::FILE_3 = "data.gpx";

bool Controller::sync_satelite() {

    // read until good satellite fix is obtained
    do {

        int status = strm_1.read();

        if (status == GPSReader::_EOF)
            return false; // file ended , exit

    } while (!strm_1.satelitesOK);


    // repeat for the second stream
    do {

        int status = strm_2.read();

        if (status == GPSReader::_EOF)
            return false; // file ended , exit

    } while (!strm_2.satelitesOK);


    return true; // synchronisation succeeded

}

bool Controller::sync_time_gps() {

    int status = 0;
    // read from   stream 1 until time sentence  is reached
    do {
        status = strm_1.read();
        if (status == GPSReader::_EOF)
            return false;

    } while (status != GPSReader::GPS_TIME);
    status = 0;



    // repeat for the second stream
    do {
        status = strm_2.read();
        if (status == GPSReader::_EOF)
            return false;

    } while (status != GPSReader::GPS_TIME);



    // now compare the two times

    time_t time_1 = mktime(&strm_1.loc.the_time);
    time_t time_2 = mktime(&strm_2.loc.the_time);

    // this is the time difference
    double time_difference = difftime(time_1, time_2);

    // if the first starts before the second
    if (time_difference < 0) {
        
        // until the two times are equal
        while (time_difference != 0) { 
            status = 0;
            
            // until new time is reached
            while (status != GPSReader::GPS_TIME) { 
                status = strm_1.read();
                if (status == GPSReader::_EOF)
                    return false; // stream ended , exit
            }
            time_1 = mktime(&strm_1.loc.the_time);
            time_2 = mktime(&strm_2.loc.the_time);
            
            // update the time diff
            time_difference = difftime(time_1, time_2); 
        }

        //if the second starts before the first
    } else if (time_difference > 0) {
        
        // until the two times are equal
        while (time_difference != 0) { 
            status = 0;
            
            // until new time is reached
            while (status != GPSReader::GPS_TIME) { 
                status = strm_2.read();
                if (status == GPSReader::_EOF)
                    return false; // stream ended , exit
            }
            time_1 = mktime(&strm_1.loc.the_time);
            time_2 = mktime(&strm_2.loc.the_time);
            
            // update the time diff
            time_difference = difftime(time_1, time_2); 
        }
    }
    return true; // synchronisation  succeeded
}

/* Instatiate a object of the class and start reading from the two
 predifined file names, namely FILE_1 and FILE_2 */
void Controller::go() {
    Controller ctrl(FILE_1, FILE_2);

    // synchronise the streams first
    if (!(ctrl.sync_satelite() && ctrl.sync_time_gps()))
        return;


    //container to store 
    vector<Location> locs; 



    // infinite loop, will exit only if one of the stream finishes
    while (true) {

        if (ctrl.strm_1.satelitesOK) {

            // stream 1 is ok , add its location
            locs.push_back(ctrl.strm_1.loc);

            if (ctrl.strm_2.satelitesOK) {
                // stream 2 is ok too , update the offset
                ctrl.get_offset(ctrl.strm_1.loc, ctrl.strm_2.loc);

            } else {
                // stream 2 failed, fix it using the offset
                ctrl.add_offset(ctrl.strm_1.loc, ctrl.strm_2.loc);
            }
        } else if (ctrl.strm_2.satelitesOK) {
            //stream 1 failed, fix it with  the offset ,
            // add  the second stream's location to the vector

            locs.push_back(ctrl.strm_2.loc);

            ctrl.add_offset(ctrl.strm_2.loc, ctrl.strm_1.loc);

        }


        // now get the next two locations from the streams
        int line_read = 0;


        // get the next location from stream 1
        while (line_read != GPSReader::GPS_TIME 
                && line_read != GPSReader::_EOF) {
            
            line_read = ctrl.strm_1.read();
            
        }
        
        if (line_read == GPSReader::_EOF)
            break;



        line_read = 0;
        // get the next location from stream 2
        while (line_read != GPSReader::GPS_TIME 
                && line_read != GPSReader::_EOF) {
            
            line_read = ctrl.strm_2.read();
            
        }
        
        if (line_read == GPSReader::_EOF)
            break;
    }

    //one of the streams ended , output to a file
    ctrl.write(locs);
}

/*Writes the contents of locs to a file in GPX format.*/
void Controller::write(vector<Location> locs) {

    writer.open(FILE_3.c_str());
    writer << "<?xml version=\"1.0\"?>\n";
    writer << "<gpx version=\"1.0\"";
    writer << "\ncreator=\"Evdzhan Mustafa\"";
    writer << "\nxmlns:xsi=\"";
    writer <<"http://www.w3.org/2001/XMLSchema-instance\">\n";

    for (int i = 0; i < locs.size(); ++i) {

        writer << setprecision(10) <<
                "<wpt lat=\"" << locs.at(i).latitude << 
                "\" ";
        
        
        writer << setprecision(10) <<
                "lon=\"" << locs.at(i).longitude << 
                "\">";
        
        
        writer << "\n<time>" << 
                asctime(&locs.at(i).the_time) << 
                "</time>\n</wpt>\n";

    }
    writer << "</gpx>";
    writer.close();
}

/*Gets the offset between two locations. */
void Controller::get_offset(const Location& one, const Location& two) {

    lat_offset = (long) (one.latitude * MIL) 
            - (long) (two.latitude * MIL);
    
    
    lng_offset = (long) (one.longitude * MIL)
            -(long) (two.longitude * MIL);

}

/* Fixes the coordinates of a bad location 
 * using the offset and a good location */
void Controller::add_offset(const Location& good, Location& bad) {

    bad.latitude = (round(good.latitude * MIL) + lat_offset) / MIL;
    bad.longitude = (round(good.longitude * MIL) + lng_offset) / MIL;

}

Controller
::Controller(const string& file_1, const string& file_2)
: strm_1(file_1), strm_2(file_2) {

    strm_1.satelitesOK = 0;
    strm_2.satelitesOK = 0;
}

/* Nothing to delete here, the class doesn't manage any memory resources*/
Controller::~Controller() {
}

