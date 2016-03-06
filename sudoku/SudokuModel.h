#ifndef SUDOKU_SUDOKUMODEL_H
#define SUDOKU_SUDOKUMODEL_H
#include <stdint.h>
#include <string>
#include <fstream>
#include <iostream>
#include "Cell.h"
#include "CellsGroup.h"
#include "Occurrences.h"

using namespace std;
class SudokuModel final {

  Cell cells[81];
  CellsGroup rows;
  CellsGroup columns;
  CellsGroup blocks;
  int print_buffer[9 * 9 * 9]{};
  static const char NOT_CAND = '_';
  static const char KNOWN_VAL = ' ';


 public:
  bool input_ok = false;
  explicit SudokuModel(const string &filename);
  void loadFromFile(const string &filename);
  Cell &cell(int row, int col);


  // print stuff
  void update_print_buffer();
  void full_print();
  void print_format(int i);
  void verify();
  void simple_print();

  // algorithms
  outcome excludeKnownValues();
  outcome naked_series();
  outcome naked_group(CellsGroup &cellsGroup);


};


#endif //SUDOKU_SUDOKUMODEL_H

