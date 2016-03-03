/*
 * File:   GridTest.cpp
 * Author: evdjoint
 *
 * Created on 19-Jun-2015, 10:56:59
 */

#include "GridTest.h"


CPPUNIT_TEST_SUITE_REGISTRATION(GridTest);

GridTest::GridTest() : solver(SUDOKU_FILES[0]) { }

GridTest::~GridTest() { }

void GridTest::setUp() {
    while (solver.excludeKnownValues() != NOTHING);
}

void GridTest::test_get_niner_line_ROWS() {
    niner nc = grid.get_niner_line(ROWS, 0);
    sud_list<u> cell_cands;
    cell_cands.add(1).add(2).add(3).add(6).add(7);

    CPPUNIT_ASSERT_EQUAL(cell_cands, nc[0]->candidates());
    CPPUNIT_ASSERT_EQUAL(static_cast<u> (5), nc[3]->get_value());
}

void GridTest::test_get_niner_line_COLUMNS() {
    niner nc = grid.get_niner_line(COLUMNS, 5);
    sud_list<u> cell_cands;
    cell_cands.add(1).add(2).add(3).add(5);

    CPPUNIT_ASSERT_EQUAL(cell_cands, nc[6]->candidates());
    CPPUNIT_ASSERT_EQUAL(static_cast<u> (9), nc[4]->get_value());
}

void GridTest::test_get_niner_square() {
    niner nc = grid.get_niner_square(2, 2);
    sud_list<u> cell_cands;
    cell_cands.add(1).add(3).add(7);

    Cell& cell = *(nc[5]);
    CPPUNIT_ASSERT_EQUAL(cell_cands, nc[5]->candidates());
    CPPUNIT_ASSERT_EQUAL(static_cast<u> (5), nc[4]->get_value());
}


