/*
 * @author Evdzhan Mustafa
 * 
 *  
 */
#include <stdlib.h>
#include <iostream>

using namespace std;


int main() {
   
    
   int a = 5; 
    
   const int * ptr = &a ; // pointer to constant int . OK to convert.
    
   int * ptr2 =  (int * ) ptr ; // explicit cast - OK .
   
// int * ptr3 = ptr ;  /* error */
   
   *ptr2 = 6; // OK since we used explicit cast.
    
// *ptr = 6; /* error * /
    
    cout << a ;
    
    
}
        
   
