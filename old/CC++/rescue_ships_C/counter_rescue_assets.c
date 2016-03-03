#include <stdio.h>
#include <stdlib.h>
#include "navigation.h"
#include "peril_in_sea.h"
/* This function counts the number of assets from a file 
 * specified by the filename parameter.
 * It will return FAIL_COUNT_LINES if the filename is bad.
 * It will return FAIL_COUNT_LINES if the format in the file is bad. 
 * It will return the number of lines in the file, if the file is good. */

int count_assets(char filename[]) {
    
FILE *file=fopen(filename, "r");  

if (file == NULL) {
return FAIL_COUNT_LINES;    
} 

/*Dummy variables for ais_id and basename */
char a[MAX_STRING_SIZE],b[MAX_STRING_SIZE]; 

/* Dummy variable for the type */
char c; 

/* Dummy variables for the latitude, longitude and speed */
double d,e,f; 

/* Dummy variable for max operational time and refuel time */
int g,h; 

/* The lines counter*/
int lines=0;

/* Integer to store the number of successfully read elements.*/
int status=0; 

while (1) {
      status  =  fscanf(file,"%s %c %s %lf %lf %lf %d %d",a,&c,b,&d,&e,&f,&g,&h);
      
      /* Check if the end of file is reached . If it is break the loop */
      if (status==EOF) {
          break;
      }
      /* Check if the formating of the line was ok. If not , return FAIL_COUNT_LINES*/
      else if (status != 8) {
          return FAIL_COUNT_LINES;
      } 
       /* The line meets our requirements . Increment the counter.*/
      else {
       lines++;
           } 
       }
fclose(file);
return lines;

}
