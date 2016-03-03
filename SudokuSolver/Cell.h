#ifndef CELL_H
#define	CELL_H
#include "SudokuUtils.h"
#include <iostream>
#include <string>
#include <stdexcept>

class Cell final {
public:
    Cell();
    void initilize(u initial_value, u row, u column);

    void solve_cell(u value);
    u get_value();
    sud_list<u> candidates();

    bool is_candidate(u value_to_check);
    bool is_solved();
    outcome remove_candidate(u value_to_remove);
    void print_possible_values();
    u cand_count();

    sud_node node();

    virtual ~Cell();
private:
    sud_node m_node;
    u * m_candidates = nullptr;
    u m_candidates_count = 0;
    void assert_value_in_range(u value);
    void assert_is_not_solved();
    u last_candidate();
};

#endif	/* CELL_H */

