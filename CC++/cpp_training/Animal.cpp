/* 
 * File:   Animal.cpp
 * Author: evdjoint
 * 
 * Created on 25 March 2014, 22:56
 */
#include <iostream>
#include <string>
#include "Animal.h"
using namespace std;

  void Animal::print() {
    cout << "\nThis " << *the_type << " animal is " << *the_age << " years old.";

}

Animal::Animal(const int& age) {
    the_age = new int(age);
    the_type = new string("default_type");
}

Animal::Animal(const string& type) {
    the_age = new int(0);
    the_type = new string(type);
}

Animal::Animal(const string& type, const int& age) {
    the_age = new int(age);
    the_type = new string(type);
}

Animal::Animal() {
    the_age = new int(0);
    the_type = new string("default_type");
}

Animal::Animal(const Animal& orig) {
    the_age = new int(*orig.the_age);
    the_type = new string(*orig.the_type);
    cout << "copy constructor called ! \n";
}

Animal& Animal::operator=(const Animal& orig) {
    delete the_age;
    delete the_type;
    the_age = new int(*orig.the_age);
    the_type = new string(*orig.the_type);
    cout << "\nassignment copy called ! \n";
}

Animal::~Animal() {
    delete the_age;
    delete the_type;
}

