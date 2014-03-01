#include <stdlib.h>
#include <iostream>
using namespace std;



int main(int argc, char** argv) {

   cout << "\nShow array of pointers using malloc...\n";
    int ** array;
    array = (int**)malloc(5*sizeof(int*));

    array[0] = (int*)malloc(sizeof(int));
    array[1] = (int*)malloc(sizeof(int));
    *array[0] = 42;
    *array[1] = 99;

    cout << "*array[0] is " << *array[0] << '\n';
    cout << "*array[1] is " << *array[1] << '\n';
    
    cout << "array[0] is " << array[0] << '\n';
    cout << "array[1] is " << array[1] << '\n';
    
    cout << "&array[0] is " << &array[0] << '\n';
    cout << "&array[1] is " << &array[1] << '\n';
    
    cout << "&(&array)[0] is " << &(&array)[0] << '\n';
    cout << "&(&array)[1] is " << &(&array)[1] << '\n';
    
  
}
