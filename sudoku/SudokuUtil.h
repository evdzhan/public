#ifndef SUDOKU_SUDOKUUTIL_H
#define SUDOKU_SUDOKUUTIL_H
#include <iostream>
#include "Cell.h"

using namespace std;


#define max(outcome_1,outcome_2) outcome_1 > outcome_2 ? outcome_1 : outcome_2

enum TYPE {
  ROW,
  COLUMN,
  BLOCK
};

enum outcome {
  NEW_VAL = 2,
  RM_CAND = 1,
  NOTHING = 0
};

static string SUDOKU_FILES[]{
    "../../input/book55.sud",
    "../../input/book56.sud",
    "../../input/book57.sud",
    "../../input/book58.sud",
    "../../input/book62.sud",
    "../../input/book63.sud",
    "../../input/book64.sud",
    "../../input/book65.sud",
    "../../input/book67.sud",
    "../../input/hard1.sud"
};


#endif //SUDOKU_SUDOKUUTIL_H

