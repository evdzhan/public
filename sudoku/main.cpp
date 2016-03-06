#include <cstdlib>
#include <iostream>
#include "SudokuModel.h"
using namespace std;

int main(int argc, char **args) {

  SudokuModel sm(SUDOKU_FILES[1]);
  if (sm.input_ok) {
    while (sm.excludeKnownValues() != NOTHING);
    while (sm.naked_series());

    sm.full_print();
    sm.verify();
  }
}




