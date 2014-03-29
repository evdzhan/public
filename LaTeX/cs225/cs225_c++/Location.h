/* 
 * File:   Location.h
 * Author: evdjoint
 *
 * Created on 25 March 2014, 22:21
 */

#ifndef LOCATION_H
#define	LOCATION_H
/* Represents locations using set of coordinates and a time.*/
class Location {
public:
    double latitude;
    double longitude;
    struct tm the_time;
    Location&operator=(const Location& orig);
    Location();
    Location(const Location& orig);
    virtual ~Location();
};

#endif	/* LOCATION_H */

