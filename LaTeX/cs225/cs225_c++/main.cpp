/* 
 * File:   main.cpp
 * Author: evdjoint
 *
 * Created on 25 March 2014, 14:34
 */

#include <cstdlib>
#include <fstream>
#include <iostream>
#include <string>
#include <ctime>
#include <vector>
#include "Location.h"
#include "GPSReader.h"
#include "Controller.h"
using namespace std;

/*
 * 
 */
int main() {
    
    Controller::go();
    return 0;
    
}

