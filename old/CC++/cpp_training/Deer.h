/* 
 * File:   Deer.h
 * Author: evdjoint
 *
 * Created on 25 March 2014, 23:20
 */

#ifndef DEER_H
#define	DEER_H
using namespace std;
class Deer: public Animal {
public:
    string * the_color;
    Deer();
    void print();
    Deer(const string& color);
    Deer(const string& color,const string& type);
    Deer(const string& color,const string& type,const int& age);
    Deer(const Deer& orig);
    Deer& operator=(const Deer& orig);
    virtual ~Deer();
private:

};

#endif	/* DEER_H */

