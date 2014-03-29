#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include <time.h>
#include <math.h>
#include "gps_reader.h"

void start() {

    // open the two files
    FILE * file_1 = fopen(FILE_NAME_1, "r");
    FILE * file_2 = fopen(FILE_NAME_2, "r");
    if (file_1 == NULL || file_2 == NULL) {

        printf("Invalid file name(s).");
        return;
    }

    stream_t strm_1, strm_2; // stream 1 and stream 2 
    strm_1.satelitesOK = 0;
    strm_2.satelitesOK = 0;


    /* Synchronise the streams */
    sync_satelites(file_1, file_2, &strm_1, &strm_2);
    sync_time_gps(file_1, file_2, &strm_1, &strm_2);

    /* Declare the head of the linked list*/
    node_t * head = NULL;

    /* initialise the offset values*/
    long lat_offset = 0;
    long lng_offset = 0;


    /* infinite loop, will break only if one of the stream ends */
    while (1) {

        if (strm_1.satelitesOK) {

            // stream 1 satellite one was ok ,
            //add the its location to the list
            add_element(&head, strm_1.location);

            if (strm_2.satelitesOK) {

                // if both streams are ok, calculate the offset
                get_offset(&lat_offset, &lng_offset,
                        strm_1.location, strm_2.location);


            } else { // stream 2 failed, fix its location
                add_offset(lat_offset, lng_offset,
                        strm_1.location, &strm_2.location);
            }

        } else if (strm_2.satelitesOK) {

            //stream 2 add to good location to the list
            add_element(&head, strm_2.location);

            // stream 1 failed , fix its location,
            add_offset(lat_offset, lng_offset,
                    strm_2.location, &strm_1.location);

        }
        // status integer to see what we have read
        int line_read = 0;

        // read from stream 1 until new 
        // coordinates and time fix is obtained
        while (line_read != GPS_TIME
                && line_read != _EOF) {

            line_read = read_line(file_1, &strm_1);

        }
        if (line_read == _EOF) {
            break; // stream 1 ended  
        }

        line_read = 0;


        // read from stream 2 until new 
        //coordinates and time fix is obtained
        while (line_read != GPS_TIME
                && line_read != _EOF) {

            line_read = read_line(file_2, &strm_2);

        }
        if (line_read == _EOF) {
            break; // stream 2 ended  
        }

    }
    // output the data in the linked list to a file
    out_to_file(head);


    fclose(file_1);
    fclose(file_2);

}

/* Calculates the offset between the two locations */
void get_offset(long * lat_offset, long * lng_offset,
        loc_t one, loc_t two) {

    *lat_offset = (long) (one.latitude * MIL) -
            (long) (two.latitude * MIL);


    *lng_offset = (long) (one.longitude * MIL)
            -(long) (two.longitude * MIL);

}

/* Fixes the bad location passed in*/
void add_offset(long lat_offset, long lng_offset,
        loc_t good, loc_t * bad) {

    bad->latitude = (round(good.latitude * MIL)
            + lat_offset) / MIL;


    bad->longitude = (round(good.longitude * MIL)
            + lng_offset) / MIL;

}

/* Try to synchronise the the streams 
 * so that both are at equal time */
int sync_time_gps(FILE * file1, FILE * file2,
        stream_t * strm_1, stream_t * strm_2) {
    int line_read = 0;

    // until time sentence is met
    while (line_read != GPS_TIME) {
        line_read = read_line(file1, strm_1);
    }

    line_read = 0;
    // until time sentence is met
    while (line_read != GPS_TIME) {
        line_read = read_line(file2, strm_2);
    }

    //get the times read from the two streams
    time_t time_1 = mktime(&strm_1->location.time);
    time_t time_2 = mktime(&strm_2->location.time);

    //calculate their difference
    double time_difference = difftime(time_1, time_2);

    // if one of the streams starts earlier than the other,
    //read from it until they both have the same time
    if (time_difference < 0) {
        while (time_difference != 0) {
            line_read = 0;
            while (line_read != GPS_TIME) {
                line_read = read_line(file1, strm_1);
            }
            time_1 = mktime(&strm_1->location.time);
            time_2 = mktime(&strm_2->location.time);
            time_difference = difftime(time_1, time_2);
        }
    } else if (time_difference > 0) {
        while (time_difference != 0) {
            line_read = 0;
            while (line_read != GPS_TIME) {
                line_read = read_line(file2, strm_2);
            }
            time_1 = mktime(&strm_1->location.time);
            time_2 = mktime(&strm_2->location.time);
            time_difference = difftime(time_1, time_2);
        }
    }
    return EXIT_SUCCESS;

}

/* Try to synchronise the two streams
 *  so that both have good satellite fix*/
int sync_satelites(FILE * file1, FILE * file2,
        stream_t * strm_1, stream_t * strm_2) {

    int line_read = 0;


    while (line_read != SATELITE) {
        line_read = read_line(file1, strm_1);
        if (strm_1->satelitesOK != 0) {
            break;
        }
    }
    line_read = 0;
    while (line_read != SATELITE) {
        line_read = read_line(file2, strm_2);
        if (strm_2->satelitesOK != 0) {
            break;
        }
    }

    return EXIT_SUCCESS;

}
