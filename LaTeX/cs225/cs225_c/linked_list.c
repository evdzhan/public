
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "gps_reader.h"

/* Adds element  to the linked list. 
 * the_head should be the first value in the ll*/
void add_element(node_t ** the_head, loc_t the_loc) {
    if (*the_head == NULL) {
        *the_head = (node_t*) malloc(sizeof (node_t));
        (**the_head).loc = the_loc;
        (**the_head).next = NULL;
    } else {
        node_t * current = *the_head;
        while (current->next != NULL) {
            current = current->next;
        }
        current->next = (node_t*) malloc(sizeof (node_t));
        current->next->loc = the_loc;
        current->next->next = NULL;
    }
}

/* Outputs to a file  the linked 
 * list contents in a GPX format.*/
void out_to_file(node_t * head) {
    FILE *f = fopen(FILE_NAME_3, "w");

    


      fprintf(f, "%s", "<?xml version=\"1.0\"?>\n"
             "<gpx "
             "version=\"1.0\"\n"
             "creator=\"Evdzhan Mustafa\"\n"
             "xmlns:xsi=\"http://www.w3.org/2001"
             "/XMLSchema-instance\">\n");

     node_t * current = head;
     while (current != NULL) {
         fprintf(f,"<wpt lat=\"%lf\" lon=\"%lf\">"
                 "\n<time>%s</time>\n</wpt>\n",
                 current->loc.latitude,
                 current->loc.longitude, 
                 asctime(&current->loc.time));

         current = current->next;
     }
     fprintf(f, "%s", "</gpx>");
     fclose(f);
      


}

