#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "navigation.h"
#include "peril_in_sea.h"
/*Find closest asset and print it*/
void find_best_match(rescue_asset *assets,ship ship_in_trouble,int num_assets,mayday_call md_call,char type) {
    /*The time required to help the ship in trouble. */
    int time_to_help;
   
   /* Make it  match the type( helicopter or boat)*/
    time_to_help = (type == 'H') ? md_call.t_heli:md_call.t_boat;
    /*Int to act as a boolean, to see if we have closest asset set.*/
    int best_asset_is_set=0;
    
   /* Initialise the best distance,deploy time and arrive time. */
    double best_distance=0;
    double best_arrive_time=0;
    double best_deploy_time=0;
    rescue_asset *best_match;
    int i=0;
    /*Loop over all the rescue assets*/
    for(i ; i<num_assets; i++){
        /*Filter those that are the type( helicopter or a boat)*/
        if(assets[i].type==type) {
            
                        /*For each asset*/ 
          /*calculate the distance to the ship in trouble*/  
          double distance_to_asset=great_circle(assets[i].loc,ship_in_trouble.loc);
          /*get the max deploy time*/
          double max_deploy_time=(double)assets[i].max_deployment_time ;
          /*calculate the arrive time*/
          double arrive_time=time_to_arrive(distance_to_asset,assets[i].speed);
          /*calculate the deploy time */
          double deploy_time= calculate_required_time(distance_to_asset,assets[i].speed,time_to_help,type); 
          
          /* Check if the deployment time is enough to save the ship */
         if( deploy_time > max_deploy_time  ) {
             continue;
         }
          /*If there is not best, and the deploy time 
           * is enough to return safely set it to be the best match.*/
          if ( best_asset_is_set == 0 ) {
          best_distance=distance_to_asset;
          best_match=&assets[i];
          best_arrive_time=arrive_time;
          best_asset_is_set=1; 
          best_deploy_time=deploy_time;
          }
          /* If the deploy time is enough , and the arrive time is faster,
           * make this asset to be the best match */
          else if(best_arrive_time >= arrive_time)  {
              
            best_distance=distance_to_asset;
            best_match=&assets[i];
            best_arrive_time=arrive_time;
            best_deploy_time=deploy_time;
            
                }
        }
        
    } 
   
      
int arrive_in_minutes=(int)best_arrive_time;
/*Calculate the arrival time*/
time time_of_arrival=time_addition(md_call.the_time,arrive_in_minutes);
/*print the result*/

print_best_asset(best_match->callsign,best_distance,time_of_arrival,type);
 

   
}
    /* Calculates the required time to cover a distance twice.
     * If the asset is of type H , the time in which
     *  the Helicopter helps is also taken in mind*/

double calculate_required_time(double distance,double speed,int help_time,char type) {
    distance *=2.0;
    double time_=distance/speed * 60.0;
    if(type == 'H') {
    time_ +=(double) help_time ;
    }
    return time_;
}
/*Basic function to calculating the required 
 * time to cover a distance for specific speed*/
double time_to_arrive(double distance,double speed) {
    return (distance/speed)*  60.0;
}
