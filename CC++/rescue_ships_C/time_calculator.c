#include <stdio.h>
#include <stdlib.h>
#include "navigation.h"
#include "peril_in_sea.h"
/*Calculate the minutes difference between two times.
 a is the first time.
 b is the second time.
 returned value is the difference in minutes*/
int  time_difference(time a,time b) {
    int min=0,hour=0,day=0;
   
    
    min=a.minute - b.minute;
    if(min < 0 ) {
        hour--;
        min=min + 60;
    }
    hour= hour + a.hour - b.hour;
    if( hour < 0 ) {
        day--;
        hour=hour+24;
    }
    day= day + a.day - b.day;
   int total_time = time_in_minutes(day,hour,min);
    return total_time;
    
    }
/* Convert a time to minutes */
int time_in_minutes(int days,int hours,int minutes) {
    int total_time=0;
    total_time+=days * 24 * 60 ;
    total_time+=hours * 60 ;
    total_time+=minutes;
    return total_time;

    }
/* Calculates a time .
 time_to_increment is some time in the past.
 minutes_passed is the time in minutes passed since time_to_increment.
 returns the time after those minutes has passed*/
time time_addition(time time_to_increment,int minutes_passed) {
    int min=0,hour=0,day=0;
    int tempTime=0;
    
    tempTime=minutes_passed;
    day = minutes_passed/(24*60);
    tempTime %=24*60;
    hour = tempTime/60;
    tempTime %=60;
    min=tempTime;
    
    min += time_to_increment.minute;
    if (min > 59) {
        hour++;
        min -= 60;
    } 
    hour +=time_to_increment.hour ;
    if (hour > 24) {
            day++;
            hour -= 24;
    }
    day +=time_to_increment.day;
    
   
    
    time new_time;
    new_time.day=day;
    new_time.hour=hour;
    new_time.minute=min;
    new_time.year=time_to_increment.year;
    new_time.month=time_to_increment.month;
    new_time.second=0;
    return new_time;
}

