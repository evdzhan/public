

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define __USE_XOPEN
#include <time.h>
#include <math.h>
#include "gps_reader.h"

/* Reads a line from the read, 
 * if it is RMC or GSV , the data is processed
 returns integer corresponding to the line read*/
int read_line(FILE * file, stream_t * strm) {

    // buffer to store the current line
    char buffer[BUFFER_SIZE]; 

    // read a line
    char * status = fgets(buffer, BUFFER_SIZE, file);


    // duplicate , will be needed later on
    char * temp_buffer = strdup(buffer);


    // check if stream ended
    if (status == NULL) {
        return _EOF;
    }

    // get the type of sentence
    char * temp = strsep(&temp_buffer, ",");

    if (strcmp(temp, "$GPRMC") == 0) {

        proccess_rmc(buffer, strm);

        // new coordinate and time was processed
        return GPS_TIME;

    } else if (strcmp(temp, "$GPGSV") == 0) {

        char * current = strsep(&temp_buffer, ",");

        //parse the num of lines
        int num_lines = atoi(current);

        // array to store the gsv sentences
        char lines[num_lines][BUFFER_SIZE];

        // store the first line 
        strcpy(lines[0], buffer);


        int i;

        // put each gsv sentence to the array
        for (i = 1; i < num_lines; i++) {

            fgets(buffer, BUFFER_SIZE, file);
            strcpy(lines[i], buffer);
        }

        proccess_gsv(lines, num_lines, strm);

        // new satellite fix was processed
        return SATELITE;

    } else {
        return SKIP_LINE;
    }

}

/* Read the GSV sentences passed 
 * and update the stream passed in .*/
void proccess_gsv(char lines[][BUFFER_SIZE],
        int num_lines, stream_t * strm) {
    // how many satellites in  the lines
    int num_satellites;

    // integers used in the loops below
    int i = 0, o = 0, j = 0, k = 0;


    // duplicate used to see 
    //how many satellites are there
    char * first_line = strdup(lines[0]);

    // to store the read satellites number
    char * _num_satellites;

    // skip the first four tokens
    for (i = 0; i < 4; ++i) {
        _num_satellites = strsep(&first_line, ",");
    }

    // now _num_satellites holds the number
    // of satellites , parse that to int
    num_satellites = atoi(_num_satellites);

    // count -  how many good satellites fixes are  there
    int count = 0;


    // loop through the gsv sentences
    for (i = 0; i < num_lines; ++i) {


        // duplicate  the current line
        char * curr_line = strdup(lines[i]);

        // the line without the asterix
        //and the data after it
        char * asterix_getter = strsep(&curr_line, "*");



        char * current;

        // skip the first 4 tokens
        for (o = 0; o < 4; ++o) {
            current = strsep(&asterix_getter, ",");
        }


        // loop through each satellite SNR
        for (k = 0; k < 4 && current != NULL 
                && num_satellites > 0; ++k) {

            // skip each 4 tokens, to the to the next SNR
            for (j = 0; j < 4 && current != NULL
                    && num_satellites > 0; ++j  ) {

                current = strsep(&asterix_getter, ",");
            }

            //current should point to a SNR value
            if (*current != '\0') { // check if is null

                //parse it to integer
                int satelite_status = atoi(current);

                //check if its good
                if (satelite_status >= 30) {
                    ++count;

                    // if count reaches 3 , satellite fix is good
                    if (count == 3) {
                        strm->satelitesOK = 1;
                        return;
                    }
                }
            }
            --num_satellites;
        }
    }
    // no three satellites met the criteria 
    //stream has bad fix 
    strm->satelitesOK = 0;

    return;
}

/* Read the RMC sentence passed 
 * and update the time and coordinates. */
void proccess_rmc(char * buffer, stream_t * data) {

    // arrays of string
    char * tokens[10]; 

    char * temp = strdup(buffer);

    int i;
    // store all the tokens to the array
    for (i = 0; i < 10; ++i) {
        tokens[i] = strsep(&temp, ",");
    }

    // duplicate to use as a temp time
    char * temp_time = strdup(tokens[1]);

    // truncate the milliseconds
    char * the_time = strsep(&temp_time, ".");

    //concatenate  the date and the time
    strcat(the_time, tokens[9]);

    // parse the time
    strptime(the_time, "%H%M%S%d%m%y", &(data->location.time));

    // set this to zero, otherwise bugs occur
    data->location.time.tm_isdst = 0;

    //convert the coordinates from degrees 
    //to decimal, and  put it into the 
    // stream's location
    degrees_to_decimal(&data->location, tokens[3], tokens[5]);

    // check if coordinate is in south hemisphere
    if (*tokens[4] == 'S') {
        data->location.latitude *= -1;
    }
    // check if coordinate is in west hemisphere
    if (*tokens[6] == 'W') {
        data->location.longitude *= -1;
    }



}

/* Transforms coordinates from degrees 
 * to a decimal, and puts the coordinates
 * to the loc passed in */
void degrees_to_decimal(loc_t * loc,
        char * lat, char * lng) {

    // parse the latitude
    double lat_ = atof(lat); 
    
    // truncate the degrees
    int lat_degrees = (int) lat_ / 100; 

    // turn the minutes to decimal
    double lat_minutes = (lat_ - lat_degrees * 100) / 60.0;

    
    
    // repeat for the longitude 
    double lng_ = atof(lng);
    int lng_degrees = (int) lng_ / 100;
    double lng_minutes = (lng_ - lng_degrees * 100) / 60.0;


    //place the values to the loc  
    loc->latitude = round((lat_degrees + lat_minutes) * MIL) / MIL;
    loc->longitude = round((lng_degrees + lng_minutes) * MIL) / MIL;


}





