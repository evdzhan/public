/* 
 * File:   main.cpp
 * Author: evdjoint
 *
 * Created on 25 March 2014, 22:56
 */
#include <iostream>
#include <cstdlib>
#include <string>
#include "Animal.h"
#include "Deer.h"

using namespace std;


 

/*
 * 
 */
int main(int argc, char** argv) {
    
    


    Deer * d = new Deer("red", "bad-ass type", 69);

    Deer x;
    x = *d;
    *d->the_color = "green";
    x.print();
    d->print();

}

