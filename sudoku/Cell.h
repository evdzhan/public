#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCUnusedStructInspection"
#pragma ide diagnostic ignored "OCUnusedGlobalDeclarationInspection"
#ifndef SUDOKU_CELL_H
#define SUDOKU_CELL_H
#include <stdint.h>
#include <iostream>
#include <stdexcept>
#include "SudokuUtil.h"

using namespace std;
struct Cell final {

 private:
  int m_value = 0;
  int *m_candidates = nullptr;
  int m_candidates_count = 9;
  int m_row = 0;
  int m_col = 0;

 public:
  int row() { return m_row; }
  int col() { return m_col; }
  int *candidates() {
    if (m_candidates)
      return m_candidates;
    throw logic_error("cands is null!");
  }
  int find_last_candidate() {
    int last_cand = 0;
    int assert_count = 0;
    for (int i = 0; i < 9; ++i) {
      if (m_candidates[i] != 0) {
        last_cand = m_candidates[i];
        assert_count++;
      }
    }
    if (assert_count != 1 || last_cand == 0) crash();
    return last_cand;
  }
  int candidates_count() {
    return m_candidates_count;
  }
  int &value() { return m_value; }
  void solve(int new_value) {
    value() = new_value;
    delete[] m_candidates;
    m_candidates = nullptr;
  }
  outcome rm_cand(int value) {
    if (m_candidates == nullptr) crash();

    if (m_candidates[value - 1] == value) {
      m_candidates[value - 1] = 0;
      if (--m_candidates_count == 1) {
        m_value = find_last_candidate();
        delete[] m_candidates;
        m_candidates = nullptr;
        return NEW_VAL;
      }
      return RM_CAND;
    } else {
      return NOTHING;
    }

  }
  bool is_solved() { return m_value != 0; }
  void crash() {
    throw logic_error("CRASH, please debug me. <3 :) ");
  }
  void init_cell(int value, int row, int col) {

    if (!(0 <= value && value < 10)) { crash(); }

    m_value = value;
    m_row = row;
    m_col = col;
    if (m_value == 0) {
      m_candidates = new int[9];
      for (int i = 0; i < 9; ++i)
        m_candidates[i] = i + 1;
    } else {
      m_candidates = nullptr;
    }
  }
  ~Cell() {
    if (m_candidates != nullptr)
      delete[] m_candidates;
  }
  void print_possible_values() {
    if (is_solved()) {
      cout << m_value;
      return;
    }

    for (int i = 0; i < 9; i++)
      if ((m_candidates)[i] != 0) cout << (m_candidates)[i];
      else cout << " ";
  }
};
#endif //SUDOKU_CELL_H

#pragma clang diagnostic pop