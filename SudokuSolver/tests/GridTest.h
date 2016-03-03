/*
 * File:   GridTest.h
 * Author: evdjoint
 *
 * Created on 19-Jun-2015, 10:56:59
 */

#ifndef GRIDTEST_H
#define	GRIDTEST_H

#include <cppunit/extensions/HelperMacros.h>
#include "../Grid.h"
#include "../Solver.h"
#include "../SudokuUtils.h"

class GridTest : public CPPUNIT_NS::TestFixture {
    CPPUNIT_TEST_SUITE(GridTest);
    CPPUNIT_TEST(test_get_niner_line_ROWS);
    CPPUNIT_TEST(test_get_niner_line_COLUMNS);
    CPPUNIT_TEST(test_get_niner_square);
    CPPUNIT_TEST_SUITE_END();

public:
    GridTest();
    virtual ~GridTest();
    void setUp();


private:
    void test_get_niner_line_ROWS();
    void test_get_niner_line_COLUMNS();
    void test_get_niner_square();

    Solver solver;
    Grid& grid = solver.grid;

};

#endif	/* GRIDTEST_H */

