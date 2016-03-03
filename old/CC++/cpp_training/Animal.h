/* 
 * File:   Animal.h
 * Author: evdjoint
 *
 * Created on 25 March 2014, 22:56
 */

#ifndef ANIMAL_H
#define	ANIMAL_H
using namespace std;

class Animal {
public:
    string * the_type;
    int * the_age;
    virtual void print();
    Animal();
    Animal(const string& type, const int& age);
    Animal(const string& type);
    Animal(const int& age);
    Animal(const Animal& orig);
    Animal& operator=(const Animal& orig);
    virtual ~Animal();
private:

};

#endif	/* ANIMAL_H */

