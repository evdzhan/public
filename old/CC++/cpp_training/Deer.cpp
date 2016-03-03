/* 
 * File:   Deer.cpp
 * Author: evdjoint
 * 
 * Created on 25 March 2014, 23:20
 */
#include <iostream>
#include <string>
#include "Animal.h"
#include "Deer.h"
using namespace std;

Deer::Deer(const string& color) {
    the_color = new string(color);
}

Deer::Deer(const string& color, const string& type) : Animal(type) {
    the_color = new string(color);
}

Deer::Deer(const string& color, const string& type, const int& age) : Animal(type, age) {
    the_color = new string(color);
}

Deer::Deer() {
    the_color = new string("");
}

void Deer::print() {
    Animal::print();
    cout << " It's color is " << *the_color;
}

Deer::Deer(const Deer& orig) {
    the_color = new string(*orig.the_color);    
}

Deer& Deer::operator =(const Deer& orig) {
    Animal::operator =(orig);
    delete the_color;
    the_color = new string(*orig.the_color);
    
}

Deer::~Deer() {
    delete the_color;
}

