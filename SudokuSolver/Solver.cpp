#include "Solver.h"

Solver::Solver(const string& filename) : grid(filename) { }

Solver::~Solver() { }

outcome Solver::excludeKnownVal(u row, u column, u val) {
    outcome row_otcm = NOTHING;
    for (u cl = 0; cl < 9; ++cl) {
        Cell& cell = grid.cell(row, cl);
        if (cell.is_solved()) continue;
        row_otcm = max(cell.remove_candidate(val), row_otcm);

    }
    if (row_otcm == NEW_VALUE) return NEW_VALUE;

    outcome col_otcm = NOTHING;
    for (u rw = 0; rw < 9; ++rw) {
        Cell& cell = grid.cell(rw, column);
        if (cell.is_solved()) continue;
        col_otcm = max(cell.remove_candidate(val), col_otcm);
    }
    if (col_otcm == NEW_VALUE) return NEW_VALUE;


    outcome sqr_otcm = NOTHING;
    u begin_row = zero(row);
    u begin_col = zero(column);
    for (u rw = begin_row; rw < begin_row + 3; ++rw) {
        for (u cl = begin_col; cl < begin_col + 3; ++cl) {
            Cell& cell = grid.cell(rw, cl);
            if (cell.is_solved()) continue;
            sqr_otcm = max(cell.remove_candidate(val), sqr_otcm);
        }
    }
    if (sqr_otcm == NEW_VALUE) return NEW_VALUE;
    return max(row_otcm, max(col_otcm, sqr_otcm));
}

void Solver::excludeKnownValues(outcome& final_outcome) {
    for (u row = 0; row < 9; ++row) {
        for (u column = 0; column < 9; ++column) {
            u cell_val = grid.cell(row, column).get_value();
            if (cell_val != CELL_UNKNOWN_VAL) {
                final_outcome = max(excludeKnownVal(row, column, cell_val),
                        final_outcome);
            }
        }
    }
}

void Solver::solve() {
    if (!grid.is_good()) {
        return;
    }
    while (true) {
        outcome final_outcome = NOTHING;
        excludeKnownValues(final_outcome);
        if (final_outcome != NOTHING) continue;

        process(ROWS, final_outcome);
        if (final_outcome == NEW_VALUE) continue;

        process(COLUMNS, final_outcome);
        if (final_outcome == NEW_VALUE) continue;

        process(SQUARES, final_outcome);
        if (final_outcome == NEW_VALUE) continue;

        if (final_outcome == NOTHING) break;

    }

    grid.print();
    grid.print_possible_values();
    grid.verify();
}

void Solver::process(iter_over what, outcome& final_outcome) {
    for (u index = 0; index < 9; ++index) {

        niner n = grid.get_niner(what, index);
        sud_list<sud_node> occurrences[9];
        count_occurr(n, occurrences);

        hidden_single(occurrences, final_outcome);
        if (final_outcome == NEW_VALUE) return;

        pointing_pair(occurrences, n, what, final_outcome);
        if (final_outcome == NEW_VALUE) return;

        naked_series(n, what, final_outcome);
        if (final_outcome == NEW_VALUE) return;
    }

}

void Solver::count_occurr(const niner & n, sud_list<sud_node> * occurrences) {
    for (u i = 0; i < 9; ++i) {
        Cell& cell = *(n[i]);
        if (cell.is_solved()) continue;
        for (u candidate = 0; candidate < 9; ++candidate) {
            if (cell.is_candidate(candidate + 1)) {

                occurrences[candidate].add(cell.node());
            }
        }
    }
}

void Solver::hidden_single(const sud_list<sud_node>* occurrences, outcome& hs) {
    for (u i = 0; i < 9; ++i) {
        if (occurrences[i].size() == 1) {
            u cell_row = occurrences[i][0].rw;
            u cell_col = occurrences[i][0].cm;
            u single_candidate = i + 1;
            grid.cell(cell_row, cell_col).solve_cell(single_candidate);
            hs = NEW_VALUE;
            return;
        }
    }
}

void Solver::pointing_pair(const sud_list<sud_node> * occurrences,
        const niner & n, iter_over what, outcome& pp_outcome) {
    for (u i = 0; i < 9; ++i) {
        u candidate = i + 1;
        if (occurrences[i].size() == 2) {
            const u& row_1 = occurrences[i][0].rw;
            const u& row_2 = occurrences[i][1].rw;
            const u& col_1 = occurrences[i][0].cm;
            const u& col_2 = occurrences[i][1].cm;
            const bool same_row = row_1 == row_2;
            const bool same_col = col_1 == col_2;

            //            if (row_1 == 6 && row_2 == 6 && col_1 == 3 && col_2 == 5) {
            //                grid.print_possible_values();
            //                cout << "stop here";
            //            }

            if (what != SQUARES) {
                if (zero(row_1) == zero(row_2) && zero(col_1) == zero(col_2)) {
                    niner sqr_n = grid.get_niner(SQUARES, square_index(row_1, col_1));
                    for (u index = 0; index < 9; ++index) {

                        Cell& cell = *(sqr_n[index]);
                        const sud_node& node = cell.node();

                        if ((node.rw == row_1 && node.cm == col_1) ||
                                (node.rw == row_2 && node.cm == col_2)) {
                            continue;
                        }

                        rm_pointing_pair(cell, candidate, pp_outcome);
                        if (pp_outcome == NEW_VALUE) return;
                    }
                }
            } else if (what == SQUARES) {

                u zero_col = zero(col_1);
                u zero_row = zero(row_1);

                for (u index = 0; index < 9; ++index) {
                    if (same_row || same_col) {
                        // skip the three cells on the same row or column
                        if ((same_row && zero(index) == zero_col) ||
                                (same_col && zero(index) == zero_row)) {
                            continue;
                        }
                        Cell & cell = same_row ? grid.cell(row_1, index) :
                                grid.cell(index, col_1);

                        rm_pointing_pair(cell, candidate, pp_outcome);
                        if (pp_outcome == NEW_VALUE) return;
                    }
                }
            }
        }
    }
}

void Solver::rm_pointing_pair(Cell& cell, u cand, outcome& pp) {
    if (!cell.is_solved()) {
        pp = max(pp, cell.remove_candidate(cand));
    }
}

void Solver::naked_series(niner& n, iter_over what, outcome& ff) {
    sud_list<Cell*> four_or_less;
    for (u i = 0; i < 9; ++i) {
        if (!n[i]->is_solved() && n[i]->cand_count() <= 5) {
            four_or_less.add(n[i]);
        }
    }

    for (u first = 0; first < four_or_less.size(); ++first) {
        Cell& cell_1 = *(four_or_less[first]);
        for (u second = first + 1; second < four_or_less.size(); ++second) {
            Cell& cell_2 = *(four_or_less[second]);
            naked_pair(n, cell_1, cell_2, ff);
            if (ff == NEW_VALUE) return;

            for (u third = second + 1; third < four_or_less.size(); ++third) {
                Cell& cell_3 = *(four_or_less[third]);
                naked_tripple(n, cell_1, cell_2, cell_3, ff);
                if (ff == NEW_VALUE) return;

                for (u fourth = third + 1; fourth < four_or_less.size(); ++fourth) {
                    Cell& cell_4 = *(four_or_less[fourth]);
                    naked_quad(n, cell_1, cell_2, cell_3, cell_4, ff);
                    if (ff == NEW_VALUE) return;
                }
            }
        }
    }
}

void Solver::naked_quad(niner& n, Cell& cell_1,
        Cell& cell_2, Cell& cell_3, Cell& cell_4, outcome & ff) {
    if (cell_1.cand_count() <= 4 && cell_2.cand_count() <= 4 &&
            cell_3.cand_count() <= 4 && cell_4.cand_count() <= 4) {
        sud_list<u> unique_vals;
        unique_vals.add_all_if_not_contained(cell_1.candidates());
        unique_vals.add_all_if_not_contained(cell_2.candidates());
        unique_vals.add_all_if_not_contained(cell_3.candidates());
        unique_vals.add_all_if_not_contained(cell_4.candidates());

        if (unique_vals.size() == 4) {
            for (u i = 0; i < 9; ++i) {
                Cell& current = *(n[i]);
                if (n[i] == &cell_1 || n[i] == &cell_2
                        || n[i] == &cell_3 || n[i] == &cell_4 ||
                        current.is_solved()) {
                    continue;
                }

                ff = max(ff, current.remove_candidate(unique_vals[0]));
                if (ff == NEW_VALUE) {
                    cout << "NewVal naked quad ";
                    return;
                }
                ff = max(ff, current.remove_candidate(unique_vals[1]));
                if (ff == NEW_VALUE) {
                    cout << "NewVal naked quad ";
                    return;
                }
                ff = max(ff, current.remove_candidate(unique_vals[2]));
                if (ff == NEW_VALUE) {
                    cout << "NewVal naked quad ";
                    return;
                }
                ff = max(ff, current.remove_candidate(unique_vals[3]));
                if (ff == NEW_VALUE) {
                    cout << "NewVal naked quad ";
                    return;
                }
            }
        }
    }



}

void Solver::naked_tripple(niner& n, Cell& cell_1,
        Cell& cell_2, Cell& cell_3, outcome & ff) {
    if (cell_1.cand_count() <= 3 && cell_2.cand_count() <= 3 &&
            cell_3.cand_count() <= 3) {
        sud_list<u> unique_vals;
        unique_vals.add_all_if_not_contained(cell_1.candidates());
        unique_vals.add_all_if_not_contained(cell_2.candidates());
        unique_vals.add_all_if_not_contained(cell_3.candidates());

        if (unique_vals.size() == 3) {

            for (u i = 0; i < 9; ++i) {
                Cell& current = *(n[i]);
                if (n[i] == &cell_1 || n[i] == &cell_2
                        || n[i] == &cell_3 || current.is_solved()) {
                    continue;
                }

                ff = max(ff, current.remove_candidate(unique_vals[0]));
                if (ff == NEW_VALUE) {
                    cout << "NewVal naked tripple ";
                    return;
                }
                ff = max(ff, current.remove_candidate(unique_vals[1]));
                if (ff == NEW_VALUE) {
                    cout << "NewVal naked tripple ";
                    return;
                }
                ff = max(ff, current.remove_candidate(unique_vals[2]));
                if (ff == NEW_VALUE) {
                    cout << "NewVal naked tripple ";
                    return;
                }
            }
        }
    }
}

void Solver::naked_pair(niner& n, Cell& cell_1, Cell& cell_2, outcome & ff) {
    if (cell_1.cand_count() == 2 && cell_2.cand_count() == 2
            && cell_1.candidates() == cell_2.candidates()) {
        sud_list<u> cands = cell_1.candidates();
        for (u i = 0; i < 9; ++i) {
            Cell& current = *(n[i]);
            if (n[i] == &cell_1 || n[i] == &cell_2 || current.is_solved())
                continue;
            ff = max(ff, current.remove_candidate(cands[0]));
            if (ff == NEW_VALUE) {
                cout << "NewVal naked pair ";
                return;
            }
            ff = max(ff, current.remove_candidate(cands[1]));
            if (ff == NEW_VALUE) {
                cout << "NewVal naked pair ";
                return;
            }
        }
    }
}