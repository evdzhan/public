#include "SudokuModel.h"
using namespace std;

void SudokuModel::loadFromFile(const string &filename) {
  ifstream input(filename);
  if (!input.good()) {
    cerr << "Could not open the Sudoku file. " << endl;
    return;
  }
  string lines[9];
  for (int i = 0; i < 9; ++i) {
    getline(input, lines[i]);
    if (lines[i].length() != 9) {
      cerr << "Input file is not a valid sudoku[0]." << filename << endl;
      return;
    }
  }
  input.close();
  for (int row = 0; row < 9; row++) {
    for (int col = 0; col < 9; col++) {
      const char val = lines[row].at((unsigned long) col);
      int val_ = ((val == ' ') ? 0 : val - '0');
      if (val_ >= 0 && val_ < 10) {
        cells[(row * 9) + col].init_cell(val_, row, col);
      } else {
        cerr << "Input file is not a valid sudoku[1]." << filename << endl;
        cerr << "Invalid token " << val_ << endl;
        cerr << "At line " << lines[row] << endl;
        return;
      }
    }
  }
  input_ok = true;
}

SudokuModel::SudokuModel(const string &filename) : rows(cells, ROW), columns(cells, COLUMN), blocks(cells, BLOCK) {
  input_ok = false;
  loadFromFile(filename);
}

void SudokuModel::simple_print() {
  for (int i = 0; i < 9; ++i) {
    if (i % 3 == 0) cout << endl;
    for (int u = 0; u < 9; ++u) {
      if (u % 3 == 0) cout << " ";
      cout << (int) cells[i * 9 + u].value() << " ";
    }
    cout << endl;
  }
}
void SudokuModel::update_print_buffer() {
  for (int i = 0; i < 9; ++i) {
    for (int u = 0; u < 9; ++u) {
      Cell &thecell = cell(i, u);
      if (thecell.is_solved()) {
        int index = (i * 81) + (u * 3);

        print_buffer[index] = -1;
        print_buffer[index + 1] = -1;
        print_buffer[index + 2] = -1;

        print_buffer[index + 27] = -1;
        print_buffer[index + 28] = thecell.value(); //((i * 81) + 27) + (u * 3) + 1;
        print_buffer[index + 29] = -1;

        print_buffer[index + 54] = -1;
        print_buffer[index + 55] = -1;
        print_buffer[index + 56] = -1;

      } else {
        int index = (i * 81) + (u * 3);
        int count = index;
        int previous = count;
        int *cands = thecell.candidates();
        for (int cand_index = 0; cand_index < 9; ++cand_index) {
          int value = cands[cand_index];
          print_buffer[count++] = value;
          if (previous + 3 == count) {
            count = count + 24;
            previous = count;
          }
        }
      }
    }
  }
}
void SudokuModel::full_print() {
  update_print_buffer();
  for (int i = 0; i < 81 * 9; ++i) {
    print_format(i);
    if (print_buffer[i] == 0) cout << NOT_CAND << " ";
    else if (print_buffer[i] == -1) cout << " " << " ";
//      else cout << "\033[1;31m" << (print_buffer[i]) << "\033[0m" << " "; // output with colors...
    else cout << (print_buffer[i]) << KNOWN_VAL; // uncomment this, and comment out the above

  }
  cout << endl;
}
void SudokuModel::print_format(int i) {
  if (i % 27 == 0 && 0 != i) {
    cout << " |" << endl;
  }
  if (i % 81 == 0 && 0 != i) {
    cout << " |                          |                          |                          |" << endl;
  }
  if (i % 243 == 0 && 0 != i) {
    cout << " |--------------------------------------------------------------------------------|" << endl;
  }
  if (i % 9 == 0) { cout << " | "; }
  if (i % 3 == 0) { cout << "  "; }
}

Cell &SudokuModel::cell(int row, int col) {
  return cells[row * 9 + col];
}

outcome SudokuModel::excludeKnownValues() {
  outcome final_outcome = NOTHING;
  for (int index = 0, row = 0, col = 0, block = 0;
       index < 81;
       ++index, row = index / 9, col = index % 9, block = (index / 27) * 3 + (index % 9) / 3) {
    Cell *known_cell = &(cells[index]);


    if (known_cell->value() != 0) {
      Cell **known_cell_row = rows.get_at_index(row);
      Cell **known_cell_col = columns.get_at_index(col);
      Cell **known_cell_block = blocks.get_at_index(block);


      for (int i = 0; i < 9; i++) {
        Cell *curr_cell = known_cell_row[i];
        if (curr_cell == known_cell || curr_cell->is_solved()) continue;
        outcome current_outcome = curr_cell->rm_cand(known_cell->value());
        final_outcome = max(current_outcome, final_outcome);
      }


      for (int i = 0; i < 9; i++) {
        Cell *curr_cell = known_cell_col[i];
        if (curr_cell == known_cell || curr_cell->is_solved()) continue;
        outcome current_outcome = curr_cell->rm_cand(known_cell->value());
        final_outcome = max(current_outcome, final_outcome);

      }


      for (int i = 0; i < 9; i++) {
        Cell *curr_cell = known_cell_block[i];
        if (curr_cell == known_cell || curr_cell->is_solved()) continue;
        outcome current_outcome = curr_cell->rm_cand(known_cell->value());
        final_outcome = max(current_outcome, final_outcome);
      }

    }
  }
  return final_outcome;
}
void SudokuModel::verify() {
  for (int i = 0; i < 9; ++i) {
    int occur_row[9]{0};
    int occur_column[9]{0};
    for (int o = 0; o < 9; ++o) {
      Cell &cell1 = cell(i, o);
      Cell &cell2 = cell(o, i);

      if (not cell1.is_solved() or not cell2.is_solved()) {
        cout << endl << "Cannot verify. There are unsolved cells" << endl;
        return;
      }
      ++occur_row[cell1.is_solved() ? cell1.value() - 1 : 0];

      ++occur_column[cell2.is_solved() ? cell2.value() - 1 : 0];
    }

    for (int o = 0; o < 9; ++o) {
      if (occur_row[o] != 1) {
        cout << "Verify failed..." << endl;
        cout << i << " row, " << o + 1 << " has occurrence > 1" << endl;
        return;
      } else if (occur_column[o] != 1) {
        cout << "Verify failed..." << endl;
        cout << i << " column, " << o + 1 << " has occurrence > 1" << endl;
        return;
      }
    }
  }
  for (int i = 0; i < 7; i += 3) {
    for (int o = 0; o < 7; o += 3) {

      int occur_square[9]{0};
      for (int z = i; z < i + 3; ++z) {
        for (int q = o; q < o + 3; ++q) {
          ++occur_square[cell(z, q).value() - 1];
        }
      }

      for (int o = 0; o < 9; ++o) {
        if (occur_square[o] != 1) {
          cout << "Verify failed..." << endl;
          cout << i << o << " square , ";
          cout << o + 1 << " has occurrence > 1" << endl;
          return;
        }
      }
    }
  }
  cout << "Sudoku verified - OK." << endl;

}
outcome SudokuModel::naked_group(CellsGroup &cellsGroup) {
  for (int group_index = 0; group_index < 9; group_index++) {
    Cell **group_cells = cellsGroup.get_at_index(group_index);
    occurences grp_occurs(group_index);
    for (int cell_at = 0; cell_at < 9; cell_at++) {
      Cell *cell = group_cells[cell_at];
      if (cell->is_solved()) continue;
      grp_occurs.add_occurences(cell);
    }

    for (int z = 0; z < 9; ++z) {
      list<pair<int, int>> &list = grp_occurs.occur_at[z].pos;
      if (list.size() == 1) {
        int val = grp_occurs.occur_at[z].m_value;
        int row = list.front().first;
        int col = list.front().second;
        cout << "Naked single found." << row << "x" << col << " = " << val << endl << endl;
        cell(row, col).solve(val);
        while (excludeKnownValues() != NOTHING);
        return NEW_VAL;
      }
    }
  }
  return NOTHING;
}
outcome SudokuModel::naked_series() {
  outcome _row = naked_group(rows);
  outcome _column = naked_group(columns);
  outcome _block = naked_group(blocks);

  return max(_row, max(_column, _block));
}
