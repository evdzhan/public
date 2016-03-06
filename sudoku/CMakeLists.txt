cmake_minimum_required(VERSION 3.3)
project(sudoku)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp SudokuModel.h Cell.h CellsGroup.h SudokuUtil.h SudokuModel.cpp Occurrences.h)
add_executable(sudoku ${SOURCE_FILES})