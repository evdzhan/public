#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "navigation.h"
#include "peril_in_sea.h"

/* Load mayday , follows the same procedure as loading rescue assets .*/
int load_mayday(char *filename,int num_mayday_calls,mayday_call **the_array){
int  exit_value=0;
FILE * maydaycalls_file;
       

maydaycalls_file = fopen(filename, "r");


if (maydaycalls_file == NULL) {    
        printf("Invalid file name.");
        return FAIL_LOAD; 
    }

mayday_call *array=malloc(num_mayday_calls* sizeof(mayday_call));

int next_free=0;

char buffer[BUFFER_SIZE];

int status_1; 

char *status_2;

int i=0;

for(i;i<num_mayday_calls;i++) {
    
    status_2=fgets(buffer,BUFFER_SIZE,maydaycalls_file);
    
    if(status_2 == NULL){
        
        printf("Error occurred while loading mayday call...");
        fclose(maydaycalls_file);
        free(array);
        array=NULL;
        return exit_value;
        
    } else {
        
        status_1=sscanf(buffer,"%d %d %d %d %d %d %s %d %d",
                &array[i].the_time.day,
                &array[i].the_time.month,
                &array[i].the_time.year,
                &array[i].the_time.hour,
                &array[i].the_time.minute,
                &array[i].the_time.second,
                 array[i].ais_id,
                &array[i].t_boat,
                &array[i].t_heli
                );
        if(status_1 !=9){
            
            printf("Error loading mayday call...");
            fclose(maydaycalls_file);
            free(array);
            array=NULL;
            return exit_value;
            
        }
    }
    }

fclose(maydaycalls_file);
exit_value=1;
*the_array=array;
return exit_value;

}
