#include <stdio.h>
int main ( ) {
int x;
int &y = x;
x = 5;
y = 6;
y++;
return printf ("x is: %d\n", x);
}
