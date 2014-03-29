/* 
 * File:   gps_reader.h
 * Author: evdjoint
 *
 * Created on 22 March 2014, 01:05
 */

#ifndef GPS_READER_H
#define	GPS_READER_H

#ifdef	__cplusplus
extern "C" {
#endif

    /* Define the string and buffer sizes*/
#define STRING_SIZE 64
#define BUFFER_SIZE 512
    /* Define the file names*/
#define FILE_NAME_1 "gps_1.dat"
#define FILE_NAME_2 "gps_2.dat"
#define FILE_NAME_3 "data.gpx"

    /*Define the return values of the read function*/
#define SKIP_LINE 1
#define GPS_TIME 2 
#define SATELITE 3
#define _EOF -1

    /* A million used in rounding up coordinate values*/
#define MIL 1000000.0

    
    
    /* Data structure to store time,
     *  latitude and longitude of a location*/
    typedef struct location {
        double latitude;
        double longitude;
        struct tm time;
    } loc_t;

    
    
    /*Data structure to represent a stream with current 
     * location and boolean indicating the satellite status */
    typedef struct stream {
        loc_t location;
        _Bool satelitesOK;

    } stream_t;

    
    
    /* node used for the linked list,
     *  where the good locations are stored*/
    typedef struct node {
        loc_t loc;
        struct node * next;
    } node_t;





    /* Add element to a linked list, starting from head */
    void add_element(node_t ** head, loc_t loc);
    
    
    
    
    /*Loops through the linked list and 
     * writes to the the output file */
    void out_to_file(node_t * head);
  

    
    
    /* Starts the program*/
    void start();

    
    
    /* Tries to synchronise the two streams, 
     * so that both have the same time*/
    int sync_time_gps(FILE * file1, FILE * file2,
            stream_t * strm_1, stream_t * strm_2);
    
    
    
    
    /* Tries to synchronise the two
     *  streams so that both have good satellite fix*/
    int sync_satelites(FILE * file1, FILE * file2,
            stream_t * strm_1, stream_t * strm_2);

    
    
    /* Reads a line from a stream , 
     * returns what kind of sentence was read*/
    int read_line(FILE * file, stream_t * data);
    
    
    
    
    /* processes a rmc sentence */
    void proccess_rmc(char * buffer, stream_t * data);
    
    
    
    
    /* processes   gsv sentences*/
    void proccess_gsv(char lines[][BUFFER_SIZE],
            int num_lines, stream_t * data);

    
    
    /* turn degrees to a decimal representation ,
     *  and put it to the loc pointer passed */
    void degrees_to_decimal(loc_t * loc, char * lat, char * lng);

    
    
    /* Offset functions */
    void get_offset(long * lat_offset, long * lng_offset,
            loc_t loc_1, loc_t loc_2);
    
    void add_offset(long lat_offset, long lng_offset,
            loc_t good, loc_t * bad);


#ifdef	__cplusplus
}
#endif

#endif	/* GPS_READER_H */

