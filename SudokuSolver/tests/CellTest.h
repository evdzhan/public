/*
 * File:   CellTest.h
 * Author: evdjoint
 *
 * Created on May 17, 2015, 6:01:06 PM
 */

#ifndef CELLTEST_H
#define	CELLTEST_H
#include "../Cell.h"
#include "../SudokuUtils.h"
#include <stdexcept>
#include <cppunit/extensions/HelperMacros.h>

using namespace std;

class CellTest : public CPPUNIT_NS::TestFixture {
    CPPUNIT_TEST_SUITE(CellTest);

    CPPUNIT_TEST(init_bad_values_throws_invalid_argument);
    CPPUNIT_TEST(init_good_value);

    CPPUNIT_TEST(remove_bad_values_throws_invalid_argument);
    CPPUNIT_TEST(remove_good_values_cell_solved_logic_error);
    CPPUNIT_TEST(remove_not_candidate_values_nothing_found);
    CPPUNIT_TEST(remove_good_values_new_value);
    CPPUNIT_TEST(remove_last_value_solves_the_cell);

    CPPUNIT_TEST(is_candidate_bad_values_throws_invalid_argument);
    CPPUNIT_TEST(is_candidate_candidate_values_returns_true);
    CPPUNIT_TEST(is_candidate_non_candidate_values_returns_false);

    CPPUNIT_TEST(candidates_solved_sudoku_throws_logic_error);
    CPPUNIT_TEST(candidates_returns_five_elements);

    CPPUNIT_TEST_SUITE_END();

public:
    CellTest();
    virtual ~CellTest();
    void setUp();
    void tearDown();

private:
    void init_bad_values_throws_invalid_argument();
    void init_good_value();

    void remove_bad_values_throws_invalid_argument();
    void remove_good_values_cell_solved_logic_error();
    void remove_good_values_new_value();
    void remove_not_candidate_values_nothing_found();
    void remove_last_value_solves_the_cell();

    void is_candidate_bad_values_throws_invalid_argument();
    void is_candidate_candidate_values_returns_true();
    void is_candidate_non_candidate_values_returns_false();

    void candidates_solved_sudoku_throws_logic_error();
    void candidates_returns_five_elements();
    Cell * cell;

};

#endif	/* CELLTEST_H */

