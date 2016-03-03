
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "navigation.h"
#include "peril_in_sea.h"

int main(int argc, char** argv) {
    /*Declare the file name strings*/
    char resc_ass_file[MAX_STRING_SIZE];
    char ships_file[MAX_STRING_SIZE];
    char md_call_file[MAX_STRING_SIZE];
    printf("Enter the rescue assets file : \n");
    scanf("%s", resc_ass_file);
    int num_assets = count_assets(resc_ass_file);
    /* Exit if the file is bad*/
    if (num_assets == FAIL_COUNT_LINES) {
        return 0;
    }
    /* Define the array pointer */
    rescue_asset *assets;
    /* Try to load. If load succeeds  the assets pointer
     *  will point at the array of assets */
    if (load_rescue_assets(resc_ass_file, num_assets, &assets) != SUCCESS_LOAD) {
        return 0;
    }
    printf("Enter the ships file : \n");
    scanf("%s", ships_file);
    int num_ships = count_ships(ships_file);
    if (num_ships == FAIL_COUNT_LINES) {
        return 0;
    }
    /*Declare the ships array pointer and the time */
    ship *ships;
    time initial_time;
    if (load_ships(ships_file, num_ships, &ships, &initial_time) != SUCCESS_LOAD) {
        return 0;
    }
    printf("Enter the mayday calls file : \n");
    scanf("%s", md_call_file);
    mayday_call * mayday_calls;
    int num_mayday_calls = count_mayday_calls(md_call_file);
    if (num_mayday_calls == FAIL_COUNT_LINES) {
        return 0;
    }
    if (load_mayday(md_call_file, num_mayday_calls, &mayday_calls) != SUCCESS_LOAD) {
        return 0;
    }


    /* Start responding mayday calls */
    int i = 0;
    for (i; i < num_mayday_calls; i++) {
        print_the_call(mayday_calls[i]);
        /* Calculate the time between ships initial position and the mayday call time*/
        long time_passed = time_difference(mayday_calls[i].the_time, initial_time);
        /* A ship to store the data about the ship in trouble*/
        ship ship_in_trouble;
        /*Check if the ship is in our array of ships */
        if (find_ship(&ship_in_trouble, mayday_calls[i].ais_id, num_ships, ships) == 1) {
            /* Calculate the ship's new location based on the time passed.*/
            update_ship_location(time_passed, &ship_in_trouble);
            print_coordinates(ship_in_trouble);
        }
        else {
            print_ship_not_found();
            continue;
        }
        /*Check to see if the ship is in our coordinates area*/
        if (is_inside_borders(ship_in_trouble) == 1) {
            /* Launch algorithm to send Helicopter and then Boat*/
            find_best_match(assets, ship_in_trouble, num_assets, mayday_calls[i], 'H');
            find_best_match(assets, ship_in_trouble, num_assets, mayday_calls[i], 'L');
            printf("\n");
        } else {
            print_out_of_borders();
            continue;
        }
    }



    return (EXIT_SUCCESS);
}






