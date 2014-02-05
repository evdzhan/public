#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "navigation.h"
#include "peril_in_sea.h"
/* Update  location field of "the_ship" 
 * based on the time given by time_passed */
void update_ship_location(int time_passed,ship *the_ship){
    location new_location=get_location(the_ship->loc,the_ship->course,the_ship->speed,time_passed);
            the_ship->loc = new_location;
}
/*Calculate the new location and return it.*/
location get_location(location a,double CG,double K,double D) {
    double CGR=0;
    double LTS=a.lat;
    double LTSR=LTS * M_PI /180.0;
    double LGS=a.lng;
    double LTF=0;
    double LGF=0;
    CGR= CG * M_PI / 180.0;
    LTF=LTS+ (K *cos(CGR)*D)/3600.0;
    LGF=LGS+ (K *sin(CGR)*D/ cos(LTSR)) / 3600.0;
    location loc;
    loc.lat=LTF;
    loc.lng=LGF;
    return loc;
    }
/*Check if ship exists in our array of ships. 
 Return 1 if it does.
 Return 0 if it doesn't.*/
int find_ship(ship *ship_in_trouble,char *ais_id,int array_size,ship *ship_array){
    int exit_value=0;
    int i=0;
    for(i;i<array_size;i++){
  if(strncmp(ais_id,ship_array[i].ais_id,BUFFER_SIZE)==0) {
           strcpy(ship_in_trouble->ais_id,ship_array[i].ais_id);
           ship_in_trouble->course=ship_array[i].course;
           ship_in_trouble->speed=ship_array[i].speed;
           ship_in_trouble->loc=ship_array[i].loc;
           exit_value=1;
           return exit_value;
        } 
 }
    return exit_value;
}
/*Check if a ship is inside our area.
 Return 1 if it is.
 Return 0 if it's not. */
int is_inside_borders (ship _ship){
        int exit_value=0;
    if(_ship.loc.lng > BORDER_WEST  &&
       _ship.loc.lng < BORDER_EAST  &&
       _ship.loc.lat < BORDER_NORTH &&
       _ship.loc.lat > BORDER_SOUTH) 
    {
        exit_value=1;
    } 
        return exit_value;
}
