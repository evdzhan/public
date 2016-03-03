/*
 * File:   CellTest.cpp
 * Author: evdjoint
 *
 * Created on May 17, 2015, 6:01:06 PM
 */

#include "CellTest.h"


CPPUNIT_TEST_SUITE_REGISTRATION(CellTest);

CellTest::CellTest() { }

CellTest::~CellTest() { }

void CellTest::setUp() {
    cell = new Cell();
}

void CellTest::tearDown() {
    delete cell;
}

void CellTest::init_bad_values_throws_invalid_argument() {
    CPPUNIT_ASSERT_THROW(cell->initilize(10), invalid_argument);

    tearDown();
    setUp();
    CPPUNIT_ASSERT_THROW(cell->initilize(-10), invalid_argument);

    tearDown();
    setUp();
    CPPUNIT_ASSERT_THROW(cell->initilize(-1), invalid_argument);
}

void CellTest::init_good_value() {
    cell->initilize(5);
    u actual = cell->get_value();
    CPPUNIT_ASSERT_EQUAL(actual, static_cast<u> (5));
}

void CellTest::remove_bad_values_throws_invalid_argument() {
    cell->initilize(0);
    CPPUNIT_ASSERT_THROW(cell->remove_candidate(10), invalid_argument);
    CPPUNIT_ASSERT_THROW(cell->remove_candidate(0), invalid_argument);
    CPPUNIT_ASSERT_THROW(cell->remove_candidate(-1), invalid_argument);
}

void CellTest::remove_good_values_cell_solved_logic_error() {
    cell->initilize(5);
    CPPUNIT_ASSERT_THROW(cell->remove_candidate(1), logic_error);
    CPPUNIT_ASSERT_THROW(cell->remove_candidate(2), logic_error);
    CPPUNIT_ASSERT_THROW(cell->remove_candidate(8), logic_error);
    CPPUNIT_ASSERT_THROW(cell->remove_candidate(9), logic_error);
}

void CellTest::remove_last_value_solves_the_cell() {
    cell->initilize(0);
    for (u i = 1; i < 8; ++i) {
        cell->remove_candidate(i);
    }
    CPPUNIT_ASSERT_EQUAL(cell->remove_candidate(9), NEW_VALUE);
    CPPUNIT_ASSERT_EQUAL(cell->get_value(), static_cast<u> (8));

}

void CellTest::remove_not_candidate_values_nothing_found() {
    cell->initilize(0);
    cell->remove_candidate(1);
    cell->remove_candidate(5);
    cell->remove_candidate(9);

    CPPUNIT_ASSERT_EQUAL(cell->remove_candidate(1), NOTHING);
    CPPUNIT_ASSERT_EQUAL(cell->remove_candidate(5), NOTHING);
    CPPUNIT_ASSERT_EQUAL(cell->remove_candidate(9), NOTHING);
}

void CellTest::remove_good_values_new_value() {
    cell->initilize(0);
    for (u i = 1; i < 8; ++i) {
        CPPUNIT_ASSERT_EQUAL(cell->remove_candidate(i), EXCLUDED_CAND);
    }
    CPPUNIT_ASSERT_EQUAL(cell->remove_candidate(9), NEW_VALUE);

}

void CellTest::is_candidate_bad_values_throws_invalid_argument() {
    cell->initilize(0);
    CPPUNIT_ASSERT_THROW(cell->is_candidate(10), invalid_argument);
    CPPUNIT_ASSERT_THROW(cell->is_candidate(0), invalid_argument);
    CPPUNIT_ASSERT_THROW(cell->is_candidate(-1), invalid_argument);
}

void CellTest::is_candidate_candidate_values_returns_true() {
    cell->initilize(0);
    CPPUNIT_ASSERT_EQUAL(cell->is_candidate(1), true);
    CPPUNIT_ASSERT_EQUAL(cell->is_candidate(2), true);
    CPPUNIT_ASSERT_EQUAL(cell->is_candidate(8), true);
    CPPUNIT_ASSERT_EQUAL(cell->is_candidate(9), true);
}

void CellTest::is_candidate_non_candidate_values_returns_false() {
    cell->initilize(0);
    cell->remove_candidate(1);
    cell->remove_candidate(5);
    cell->remove_candidate(9);
    CPPUNIT_ASSERT_EQUAL(cell->is_candidate(1), false);
    CPPUNIT_ASSERT_EQUAL(cell->is_candidate(5), false);
    CPPUNIT_ASSERT_EQUAL(cell->is_candidate(9), false);
}

void CellTest::candidates_solved_sudoku_throws_logic_error() {
    cell->initilize(0);
    for (u i = 1; i < 9; ++i) {
        cell->remove_candidate(i);
    }
    CPPUNIT_ASSERT_THROW(cell->candidates(), logic_error);
}

void CellTest::candidates_returns_five_elements() {
    cell->initilize(0);
    cell->remove_candidate(1);
    cell->remove_candidate(2);
    cell->remove_candidate(3);
    cell->remove_candidate(4);

    sud_list<u> cands = cell->candidates();
    CPPUNIT_ASSERT_EQUAL(cands.size(), static_cast<u> (5));
    CPPUNIT_ASSERT_EQUAL(cands[0], static_cast<u> (5));
    CPPUNIT_ASSERT_EQUAL(cands[1], static_cast<u> (6));
    CPPUNIT_ASSERT_EQUAL(cands[2], static_cast<u> (7));
    CPPUNIT_ASSERT_EQUAL(cands[3], static_cast<u> (8));
    CPPUNIT_ASSERT_EQUAL(cands[4], static_cast<u> (9));
}