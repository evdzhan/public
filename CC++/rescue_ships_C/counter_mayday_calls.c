
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "navigation.h"
#include "peril_in_sea.h"
/* This function counts the number of maydaycalls from a file 
 * specified by the filename parameter.
 * It will return FAIL_COUNT_LINES if the filename is bad.
 * It will return FAIL_COUNT_LINES if the format in the file is bad.
 * It will return the number of lines in the file, if the file is good.  */
int count_mayday_calls(char filename[]) {
    
FILE *file=fopen(filename, "r");   
if (file == NULL) {
    return FAIL_COUNT_LINES;
}

/* Dummy variables to store the time integers */
/* namely day , month , year, hour , minutes ,seconds*/
int a,b,c,d,e,f;

/* Dummy string for the ais_id of the ship that issued mayday call*/
char g[MAX_STRING_SIZE];

/* Dummy integers for the time it needs help from a helicopter and a boat*/
int h,i;

/* The number mayday calls counter*/
int mayday_calls=0;

/* Integer to store the number of successfully read elements.*/
int status=0;

 while (1) {
     
    status = fscanf(file,"%d %d %d %d %d %d %s %d %d",&a,&b,&c,&d,&e,&f,g,&h,&i);
    
    /* Check if the end of file is reached . If it is break the loop */
    if(status == EOF) {
        break;
    }
    /* Check if the formating of the line was ok. If not , return FAIL_COUNT_LINES*/
    else if (status != 9) {
        return FAIL_COUNT_LINES;
    } 
    /* The line meets our requirements . Increment the counter.*/
    else  {
       mayday_calls++;
   } 
 }

   fclose(file);
   
   return mayday_calls;
}
