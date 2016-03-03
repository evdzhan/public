#include "Grid.h"

Grid::~Grid() {
    if (the_sudoku == nullptr) return;
    for (u row = 0; row < 9; row++) {
        if (the_sudoku[row] != nullptr)
            delete[] the_sudoku[row];
    }
    if (the_sudoku != nullptr)
        delete[] the_sudoku;
}

void Grid::verify() {
    if (the_sudoku == nullptr) cerr << "Can't verify" << endl;

    for (u i = 0; i < 9; ++i) {
        u occur_row[9]{0};
        u occur_column[9]{0};
        for (u o = 0; o < 9; ++o) {
            ++occur_row[cell(i, o).get_value() - 1];
            ++occur_column[cell(o, i).get_value() - 1];
        }

        for (u o = 0; o < 9; ++o) {
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
    for (u i = 0; i < 7; i += 3) {
        for (u o = 0; o < 7; o += 3) {

            u occur_square[9]{0};
            for (u z = i; z < i + 3; ++z) {
                for (u q = o; q < o + 3; ++q) {
                    ++occur_square[cell(z, q).get_value() - 1];
                }
            }

            for (u o = 0; o < 9; ++o) {
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

Grid::Grid(const string & filename) {
    ifstream input(filename);
    if (!input.good()) {
        cerr << "Could not open the Sudoku file. " << endl;
        return;
    }

    string lines[9];
    for (u i = 0; i < 9; ++i) {
        getline(input, lines[i]);
        if (lines[i].length() != 9) {
            cerr << "Input file is not a valid sudoku[0]." << filename << endl;
            return;
        }
    }
    try {
        the_sudoku = new Cell*[9];
        for (u row = 0; row < 9; row++) {
            the_sudoku[row] = new Cell[9];
            for (u column = 0; column < 9; column++) {
                const char val = lines[row].at(column);
                u val_ = (val == ' ') ? 0 : val - '0';
                if (val_ >= 0 && val_ < 10) {
                    the_sudoku[row][column].initilize(val_, row, column);
                } else {
                    cerr << "Input file is not a valid sudoku[1]." << filename << endl;
                    return;
                }
            }
        }
        good = true;
    } catch (const bad_alloc& ba) {
        cerr << "Could not allocate memory for the sudoku..." << endl;
        throw ba;
    }
}

bool Grid::is_good() {
    return good;
}

Cell& Grid::cell(const u& row, const u& column) {
    return (the_sudoku[row][column]);
}