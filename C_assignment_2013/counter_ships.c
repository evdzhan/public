#include <stdio.h>
#include <stdlib.h>
#include "navigation.h"
#include "peril_in_sea.h"
/* This function counts the ships in a  file 
 * specified by the filename parameter.
 * It will return FAIL_COUNT_LINES if the filename is bad.
 * It will return FAIL_COUNT_LINES if the format in the file is bad.
 * It will return the number of ships in the file, if the file is good.  */
int count_ships(char filename[]) {
 FILE *file=fopen(filename, "r");   
 
if (file == NULL) {  
 return FAIL_COUNT_LINES;
} 
 
/* Dummy variable for the ais_id */
char a[MAX_STRING_SIZE];
/* Dummy variables for the latitude,longitude, course , and speed */
double b,c,d,e;
/* Dummy integers for the time read in the first line*/
int f,g,h,i,j,k;

/* Variable to keep track of the number of successfully scanned elements*/
int status;

/* Variable to store the number of ships */
int num_ships=0;

/* Read the first line that contains the time */
status = fscanf(file,"%d %d %d %d %d %d",&f,&g,&h,&i,&j,&k);

/* Check to see if it's in the correct format.*/
if (status != 6) {
    return FAIL_COUNT_LINES;
}
/* Now count the ships*/
while (1) {
  status = fscanf(file,"%s %lf %lf %lf %lf",a,&b,&c,&d,&e); 
  
 if (status == EOF) {
    break;
    } 
 else if (status != 5 )  {
           return FAIL_COUNT_LINES;
   } 
 else     {
       num_ships++;
          } 
        }  

fclose(file);
return num_ships;
}



