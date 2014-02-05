#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "navigation.h"
#include "peril_in_sea.h"

/*Load the rescue assets from the filename parameter, 
 num_assets defines the number of assets to load 
 the_array is the address of the pointer
 *  that will point to the array if  we succeed*/
int load_rescue_assets(char *filename,int num_assets,rescue_asset ** the_array) {
  
    FILE * rescue_asset_file; 
    
    /* Open the file , defined by the filename parameter */
    rescue_asset_file = fopen(filename, "r"); 
   /* Check if the file we tried to read from was invalid.*/
    if (rescue_asset_file == NULL) {    
        printf("Invalid file name.");
        return FAIL_LOAD; 
    }
     /* Allocate enough memory for all the assets */
    rescue_asset *array=(malloc(num_assets * sizeof(rescue_asset)));
    
    /* Integer for our loop below */
    int i=0;
    
    /* In this int we store the number of successful  */
    /* strings and/or numbers read by sscanf */
    int status_1=0; 
    
    /* A pointer to check if we read a line with fgets */
    char *status_2=NULL;
    
    /* Buffer string to store each line*/
    char buffer[BUFFER_SIZE];
 
    
for (i;i<num_assets;i++) {
    /* Store a line from the file in the buffer*/
    status_2=fgets(buffer,BUFFER_SIZE,rescue_asset_file);
   
    /* Check if a line was successfully read */
    if(status_2 == NULL)  {
        printf("Error occurred while loading assets...");
        free(array); // free the memory allocated for the assets
        array=NULL; // make sure no "DOUBLE FREE" occurs
        fclose(rescue_asset_file); // close the file
        return FAIL_LOAD;
        
    } else  {
        /* Scan the buffer , read it in the format we need */
       status_1= sscanf(buffer,"%s %c %s %lf %lf %lf %d %d",
                array[i].callsign,
               &array[i].type,
                array[i].place_name,
               &array[i].loc.lat,
               &array[i].loc.lng,
               &array[i].speed,
               &array[i].max_deployment_time,
               &array[i].reload_time);
       
       /* Check if the line was in the required format*/
   /* Bad input could still bypass but at least it's in the correct types !*/
       if(status_1 != 8) {
          fclose(rescue_asset_file);
          printf("Bad file... exiting.");
          free(array); // give back the memory to the system
          array=NULL; // make sure no "DOUBLE FREE" occurs
          return FAIL_LOAD;
       }
    }
 }  
    /* ALL WAS OK */    
fclose(rescue_asset_file);
/* Make the_array's value point at the array we allocated for the  ships*/
*the_array=array;
return SUCCESS_LOAD;
}
    
    
