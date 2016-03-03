/* 
 * File:   Location.cpp
 * Author: evdjoint
 * 
 * Created on 25 March 2014, 22:21
 */
#include <ctime>
#include "Location.h"

/* Initialises the time, and coordinates to zeroes.*/
Location::Location() {
    latitude = 0;
    longitude = 0;
    the_time.tm_hour = 0;
    the_time.tm_mday = 0;
    the_time.tm_min = 0;
    the_time.tm_sec = 0;
    the_time.tm_year = 0;
    the_time.tm_mon = 0;
    the_time.tm_gmtoff = 0;
}

/* Copy assignment. The compiler provides this by default,
 *  but I like having on my own.*/
Location& Location::operator=(const Location& orig) {
    latitude = orig.latitude;
    longitude = orig.longitude;
    the_time = orig.the_time;
}

/* Copy constructor. Same story here as above.*/
Location::Location(const Location& orig) {
    latitude = orig.latitude;
    longitude = orig.longitude;
    the_time = orig.the_time;
}

/* Nothing to delete here, no memory resources managed.*/
Location::~Location() {
}

