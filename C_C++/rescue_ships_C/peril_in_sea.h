
#ifndef NEWFILE_H
#define	NEWFILE_H

#ifdef	__cplusplus
extern "C" {
#endif
    /* DEFINE THE BORDERS OF OUR SHIPPING AREA*/
#define BORDER_WEST -6.667
#define BORDER_EAST -3.833
#define BORDER_SOUTH 51.667
#define BORDER_NORTH 52.833

    
#define MAX_STRING_SIZE 75
#define BUFFER_SIZE 250

/*  Exit values for my load functions */
#define SUCCESS_LOAD 1
#define FAIL_LOAD 0
#define FAIL_COUNT_LINES -1

    
#define LOG_FILE_NAME "log.txt"

    /* Start defining the structures */
typedef struct{
        int second;
        int minute;
        int hour;
        int day;
        int month;
        int year;
    } time ;
typedef struct{
        char callsign[MAX_STRING_SIZE];
        char type;
        char place_name[MAX_STRING_SIZE];
        location loc;
        double speed;
        int max_deployment_time;
        int reload_time;
        time t_bussy_until;
    } rescue_asset ;

typedef struct{
        char ais_id[MAX_STRING_SIZE];
        location loc;
        double course;
        double speed;
    } ship;
typedef struct{
        time the_time;
        char ais_id[MAX_STRING_SIZE];
        int t_heli;
        int t_boat;
        
    } mayday_call;
    typedef struct{
        int hour;
        int min;
        double dist;
        char ais_id[MAX_STRING_SIZE];
        location loc;
        char h_send[MAX_STRING_SIZE];
        char b_send[MAX_STRING_SIZE];
        char type;
    } rescue_report;

/*  Those functions count the number of lines in the specified file
     They return the number of lines, 
     * or  FAIL_COUNT_LINES if the file is bad*/
int count_mayday_calls(char filename[]);
int count_assets(char filename[]);
int count_ships(char filename[]);
    
    
/* Loading functions 
     All of them take string for the file name,
     the number of records to be loaded,
     and a pointer to a pointer to the type of record they are loading.
     If the functions success, they will make the pointer passed to them,
     point at the array being created for the records and return LOAD_SUCCESS,
      other wise they will return FAIL_LOAD .
      Note that the load ships also takes pointer to a time struct, 
      since from that file we load the initial time too.
     */
int load_rescue_assets(char *filename,int num_assets,rescue_asset ** the_array);
int load_ships(char * filename,int num_ships,ship **the_array,time *initial_time) ;
int load_mayday(char *filename,int num_mayday_calls,mayday_call **the_array);
   
/*Finds closest rescue asset for the ship ship_in_trouble.
 All the parameters are required for the algorithm to find the closest asset*/
void find_best_match(rescue_asset *assets,ship ship_in_trouble,int num_assets,mayday_call md_call,char type);
    
/* Calculate the total minutes in the given parameters days hours and minutes
     Returns the total sum of minutes*/
int time_in_minutes(int days,int hours,int minutes);

/*  Returns the difference in minutes between time A and B*/
int  time_difference(time a,time b);

/*Gives new time after adding certain amount of minutes to it.
     Returns the new time.*/
time time_addition(time time_to_increment,int minutes_passed);
  
/*Calculates the required time to save a ship in trouble.
 The returned double represents the time in minutes that will take to 
 reach the ship,help it , and return back to the base.
 If the argument passed is type is equal to 'H' 
 The time spent helping this ship will also be counted.*/
double calculate_required_time(double distance,double speed,int help_time,char type);

/* Calculate the time required for an asset to reach a ship in trouble.
 The distance represents the distance between the base and the ship,
 the speed represents the operational speed of the rescue asset*/
double time_to_arrive(double ditance,double speed);

/* Check if a ship is inside our borders.
 _ship is the ship  that we check .
 returns 0 if the ships is out of our area 
 returns 1 if the ship is inside our area*/
int is_inside_borders (ship _ship);

 /* Updates ship's location based on the parameter time_passed */
void update_ship_location(int time_passed,ship *the_ship);

/*Searches the array of ships based on the ais_id parameter.
 If the ships is found , the passed pointer ship_in_trouble will point
 at newly created ship with the same details.
 Returns 0 if the ships is not in the array.
 Returns 1 if the ship is found.*/
int find_ship(ship *ship_in_trouble,char *ais_id,int array_size,ship *ship_array);

/* Function that estimates a position. 
     * Location "a" represents the location at some point of time. 
     * CG represents the course over the ground.
     * K represents the speed in Knots(nautical miles/hour).
     * D represents the time passed in minutes .
     * The function will return a location after the passed time D. */
location get_location(location a,double CG,double K,double D);



/* Various print functions used to provide to print the results*/
void print_out_of_borders(void);
void print_the_call(mayday_call md_call);
void print_coordinates (ship the_ship);
void print_best_asset(char *basename,double best_distance,time arrive_time,char type);
void print_ship_not_found(void);
#ifdef	__cplusplus
}
#endif

#endif	/* NEWFILE_H */

