#ifndef SUDOKU_CELLSGROUP_H
#define SUDOKU_CELLSGROUP_H

#include "Cell.h"
#include "SudokuUtil.h"
struct CellsGroup {

  Cell ***group;

  CellsGroup(Cell *cells, TYPE type) {
    switch (type) {
      case (ROW) :
        setUpRow(cells);
        break;
      case (COLUMN) :
        setUpColumn(cells);
        break;
      case (BLOCK) :
        setUpBlock(cells);
        break;

      default:
        // no op
        break;
    }
  }

  void setUpRow(Cell *cells) {
    group = new Cell **[9];
    for (int i = 0; i < 9; ++i) {
      group[i] = new Cell *[9];
      for (int u = 0; u < 9; ++u) {
        group[i][u] = &(cells[i * 9 + u]);
      }
    }
  }

  void setUpColumn(Cell *cells) {
    group = new Cell **[9];
    for (int i = 0; i < 9; ++i) {
      group[i] = new Cell *[9];
      for (int u = 0; u < 9; ++u) {
        group[i][u] = &(cells[i + (u * 9)]);
      }
    }
  }

  void setUpBlock(Cell *cells) {
    group = new Cell **[9];
    for (int i = 0; i < 9; ++i) {
      group[i] = new Cell *[9];

      int groupIndex = 0;
      int rowStart = (i / int(3)) * int(27);
      int colStart = (i % int(3)) * int(3);

      for (int row = rowStart; row < rowStart + 27; row += 9) {
        for (int col = colStart; col < colStart + 3; ++col) {
          group[i][groupIndex++] = &(cells[row + col]);
        }
      }
    }
  }

  void print(int index) {
    for (int i = 0; i < 9; ++i) {
      cout << group[index][i]->value() << " ";
    }
  }


  Cell **get_at_index(int index) {
    return group[index];
  }


  ~CellsGroup() {
    if (group != nullptr) {
      for (int i = 0; i < 9; i++) {
        if (group[i] != nullptr) {
          delete[] group[i];
        }
      }
      delete[] group;
    }
  }
};

#endif //SUDOKU_CELLSGROUP_H
