/*
 * @author Evdzhan Mustafa
 * 
 *  Why to keep in mind the short circuit.
 */
#include <stdlib.h>
#include <iostream>

using namespace std;


int main() {
   
    int i = 11;

    int n = 5;

   if ( (i<10) && (++i<n) ) {
       
       cout << R"(This line will never get printed out.)";
       
   }
    cout << R"(Some code here relying on the fact that i would be incremented will fail.)";
    
    
}
        
   
