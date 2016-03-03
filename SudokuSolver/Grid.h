#ifndef GRID_H
#define	GRID_H
#include <string>
#include <iostream>
#include <fstream>
#include "Cell.h"
using namespace std;

struct niner {
    Cell ** cells;
    niner() {
        cells = new Cell*[9];
    }
    virtual ~niner() {
        delete[] cells;
    }
    Cell*& operator[](const u index) const {
        return cells[index];
    }

};

class Grid final {
public:
    Cell& cell(const u& row, const u& column);
    void verify();
    bool is_good();
    explicit Grid(const string& filename);
    virtual ~Grid();
    void print() {
        if (the_sudoku == nullptr) return;
        for (u row = 0; row < 9; row++) {
            for (u column = 0; column < 9; column++) {
                cout << cell(row, column).get_value();
                // print space each three columns
                if ((8 - column) % 3 == 0) cout << " ";
            }
            cout << endl;
            // print new line each three columns
            if ((8 - row) % 3 == 0) cout << endl;
        }
    }
    void print_possible_values() {
        if (the_sudoku == nullptr) return;
        for (u row = 0; row < 9; row++) {
            cout << endl;
            for (u column = 0; column < 9; column++) {
                cout << '[' << row;
                cout << "x";
                cout << column << ']';
                cout << "=";
                if (!cell(row, column).is_solved())
                    cell(row, column).print_possible_values();
                else {
                    for (u i = 1; i < 10; i++)
                        if (i == cell(row, column).get_value())
                            cout << " "; // << cell_val(row, column);
                        else
                            cout << " ";
                }
                cout << " ";
                // print space each three columns
                if ((8 - column) % 3 == 0) cout << "   ";
            }
            // print new line each three columns
            if ((8 - row) % 3 == 0) cout << endl << endl;
        }
    }
    niner get_niner(iter_over what, u index) {
        niner n;
        if (ROWS == what) {
            for (u i = 0; i < 9; ++i) {
                n[i] = &(the_sudoku[index][i]);
            }
        } else if (COLUMNS == what) {
            for (u i = 0; i < 9; ++i) {
                n[i] = &(the_sudoku[i][index]);
            }
        } else if (SQUARES == what) {
            u begin_row = (index / 3) * 3;
            u begin_col = (index % 3) * 3;
            //            print_possible_values();
            u i = 0;
            for (u rw = begin_row; rw < begin_row + 3; ++rw) {
                for (u cl = begin_col; cl < begin_col + 3; ++cl) {
                    n[i] = &(the_sudoku[rw][cl]);
                    ++i;
                }
            }
        } else {
            throw logic_error("WHAT");
        }

        return n;
    }

private:
    Cell ** the_sudoku = nullptr;
    bool good = false;
};



#endif	/* GRID_H */