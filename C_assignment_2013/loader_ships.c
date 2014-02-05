#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "navigation.h"
#include "peril_in_sea.h"
/* Loads ships and the initial time. 
 * Follows the same procedure as loading rescue assets function*/
int load_ships(char * filename,int num_ships,ship **the_array,time *initial_time) {
    
FILE * ships_file; 
ships_file = fopen(filename, "r"); 

if (ships_file == NULL) { 
      printf("Error loading ships...");
      return FAIL_LOAD;   
}

char buffer[BUFFER_SIZE];

char *status_1;

int status_2;

status_1 = fgets(buffer,BUFFER_SIZE,ships_file);
     if(status_1 == NULL) {
         printf("Error loading the initial time...");
         fclose(ships_file);
         return FAIL_LOAD;
     } else {
         status_2=sscanf(buffer,"%d %d %d %d %d %d",
         &initial_time->day,&initial_time->month,&initial_time->year,
         &initial_time->hour,&initial_time->minute,&initial_time->second);
         if(status_2 != 6) {
             fclose(ships_file);
             printf("Error loading the initial time...");
             return FAIL_LOAD;
         } 
     }
ship *array=(malloc(num_ships* sizeof(ship))); 
int o=0;  
char *status_3;
int status_4;
for(o;o<num_ships;o++) {
        status_3=fgets(buffer,BUFFER_SIZE,ships_file);
        if(status_3 == NULL) {
            printf("Error loading ships...");
            fclose(ships_file);
            free(array);
            return FAIL_LOAD;
        } else {
            status_4=sscanf(buffer,"%s %lf %lf %lf %lf",
                    array[o].ais_id,
                    &array[o].loc.lat,
                    &array[o].loc.lng,
                    &array[o].course,
                    &array[o].speed);
            if(status_4 !=  5){
                printf("Error loading ships...");
                fclose(ships_file);
                free(array);
                return FAIL_LOAD;
            }
        }
    }
    
fclose(ships_file);
    
*the_array=array;
return SUCCESS_LOAD;
}

