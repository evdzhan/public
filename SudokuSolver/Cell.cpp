#include <new>

#include "Cell.h"

Cell::Cell() { }

Cell::~Cell() {
    if (m_candidates != nullptr) {
        delete[] m_candidates;
        m_candidates = nullptr;
    }
}

void Cell::initilize(u initial_value, u row, u column) {
    m_node.cm = column;
    m_node.rw = row;
    m_node.val = initial_value;
    if (initial_value == CELL_UNKNOWN_VAL) {
        m_candidates = new u[9];
        for (u i = 0; i < 9; ++i) {
            m_candidates[i] = i + 1;
            m_candidates_count = 9;
        }
    } else {
        assert_value_in_range(initial_value);
        m_candidates = nullptr;
    }
}

u Cell::get_value() {
    return m_node.val;
}

void Cell::solve_cell(u value) {
    assert_is_not_solved();
    m_node.val = value;
    delete[] m_candidates;
    m_candidates = nullptr;
}

u Cell::last_candidate() {
    for (u i = 0; i < 9; ++i) {
        if (m_candidates[i] != 0) {
            return m_candidates[i];
        }
    }
    throw logic_error("Could not find the last candidate!");
}

sud_list<u> Cell::candidates() {
    assert_is_not_solved();
    sud_list<u> candidates;
    for (u i = 0; i < 9; ++i) {
        if (m_candidates[i] != 0) {
            candidates.add(m_candidates[i]);
        }
    }
    return candidates;
}

bool Cell::is_candidate(u value_to_check) {
    assert_is_not_solved();
    assert_value_in_range(value_to_check);
    return m_candidates[value_to_check - 1] == value_to_check;
}

bool Cell::is_solved() {
    return m_node.val != 0 && m_candidates == nullptr;
}

outcome Cell::remove_candidate(u value_to_remove) {
    assert_is_not_solved();
    assert_value_in_range(value_to_remove);
    if (m_candidates[value_to_remove - 1] != 0) {
        m_candidates[value_to_remove - 1] = 0;
        m_candidates_count--;
        if (m_candidates_count == 1) {
            m_node.val = last_candidate();
            delete[] m_candidates; // guaranteed not to be null by the assertions
            m_candidates = nullptr;
            return NEW_VALUE;
        } else {
            return EXCLUDED_CAND;
        }
    } else {
        return NOTHING;
    }
}

void Cell::assert_value_in_range(u value) {
    if (value > CELL_MAX_VAL || value < CELL_MIN_VAL) {
        throw invalid_argument("The value must be in the range 1-9");
    }
}

void Cell::assert_is_not_solved() {
    if (is_solved()) {
        throw logic_error("The cell is solved!");
    }
}

void Cell::print_possible_values() {
    if (is_solved()) {
        cout << m_node.val;
        return;
    }

    for (u i = 0; i < 9; i++)
        if ((m_candidates)[i] != 0) cout << (m_candidates)[i];
        else cout << " ";
}

sud_node Cell::node() {
    return m_node;
}

u Cell::cand_count() {
    assert_is_not_solved();
    return m_candidates_count;
}