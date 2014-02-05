#include <stdio.h>
#include <stdlib.h>
#include "navigation.h"
#include "peril_in_sea.h"
void  print_the_call(mayday_call md_call) {
    FILE *f=fopen(LOG_FILE_NAME,"ab+");
    fprintf(f,"\nTime: %d/%d/%d  %d:%d:%d *** Ship in trouble! \"%s\" sent mayday call.*** ",
         md_call.the_time.day,
         md_call.the_time.month,
         md_call.the_time.year,
         md_call.the_time.hour,
         md_call.the_time.minute,
         md_call.the_time.second,
         md_call.ais_id);
    fclose(f);
printf("\nTime: %d/%d/%d  %d:%d:%d *** Ship in trouble! \"%s\" sent mayday call.*** \n",
         md_call.the_time.day,
         md_call.the_time.month,
         md_call.the_time.year,
         md_call.the_time.hour,
         md_call.the_time.minute,
         md_call.the_time.second,
         md_call.ais_id);
}
void print_coordinates(ship the_ship) {
      FILE *f=fopen(LOG_FILE_NAME,"ab+");
    fprintf(f,"Estimated coordinates of \"%s\" are %lf LAT , %lf LNG \n",
            the_ship.ais_id,the_ship.loc.lat,the_ship.loc.lng);
    fclose(f);
    
    printf("Estimated coordinates of \"%s\" are %.3lf LAT , %.3lf LNG \n",
            the_ship.ais_id,the_ship.loc.lat,the_ship.loc.lng);
}
void print_best_asset(char *basename,double best_distance,time arrive_time,char type) {
    
         FILE *f=fopen(LOG_FILE_NAME,"ab+");
    fprintf(f,"Best %c to send : %s, %.2lf miles away. Approx. arrive time  %02d:%02d \n",
       type,basename,best_distance,arrive_time.hour,arrive_time.minute);
    fclose(f);
    
  printf("Best %c to send : %s, %.2lf miles away. Approx. arrive time  %02d:%02d \n",
       type,basename,best_distance,arrive_time.hour,arrive_time.minute);
 }
void print_out_of_borders(void){
    FILE *f=fopen(LOG_FILE_NAME,"ab+");
    fprintf(f,"The ship is out of our borders. "
           "As it is some other organisation's responsibility to help them we will ignore it ! \n");
    fclose(f);
    
    
     printf("The ship is out of our borders. "
           "As it is some other organisation's responsibility to help them we will ignore it ! \n");
    
}
void print_ship_not_found(void){
    FILE *f=fopen(LOG_FILE_NAME,"ab+");
    fprintf(f,"This ship is not in our list... cannot estimate location...\n\n");
    fclose(f);
    printf("This ship is not in our list... cannot estimate location...\n\n");
}