#include <cstdlib>
#include "Solver.h"
using namespace std;

int main(int argc, char** argv) {
    for (u i = 0; i < 10; ++i) {
        Solver solver(SUDOKU_FILES[i]);
        solver.solve();
    }
    return 0;
}

