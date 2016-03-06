#ifndef SUDOKU_OCCURRENCES_H
#define SUDOKU_OCCURRENCES_H
#include <iostream>
#include <list>
#include "Cell.h"

using namespace std;


struct occurence {

  occurence(int value, int count) : m_value(value) { }
  int m_value = -1;
  list<pair<int, int>> pos;

};


struct occurences {

  void add_occurences(Cell *cell) {
    int *candidates = cell->candidates();
    for (int i = 0; i < 9; ++i) {
      if (candidates[i] != 0) {
        occur_at[i].pos.push_back(pair<int, int>(cell->row(), cell->col()));

      }
    }
  }
  void print_status() {
    for (int z = 0; z < 9; ++z) {
      cout << "Group at index " << m_group_index << " has " << occur_at[z].pos.size() << " ";
      cout << occur_at[z].m_value << "s" << endl;
    }
    cout << endl;
  }


  occurences(int index) : m_group_index(index) { };
  int m_group_index;
  occurence occur_at[9]{
      occurence(1, 0), occurence(2, 0), occurence(3, 0),
      occurence(4, 0), occurence(5, 0), occurence(6, 0),
      occurence(7, 0), occurence(8, 0), occurence(9, 0)
  };
};
#endif //SUDOKU_OCCURRENCES_H
