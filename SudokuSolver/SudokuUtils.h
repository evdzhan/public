#ifndef SUDOKUUTILS_H
#define	SUDOKUUTILS_H
#include <iostream>
#include <string>
#include <stdexcept>
using namespace std;

typedef unsigned short u;

static string SUDOKU_FILES[]{
                             "input/book55.sud",
                             "input/book56.sud",
                             "input/book57.sud",
                             "input/book58.sud",
                             "input/book62.sud",
                             "input/book63.sud",
                             "input/book64.sud",
                             "input/book65.sud",
                             "input/book67.sud",
                             "input/hard1.sud"
};

#define CELL_MAX_VAL 9
#define CELL_MIN_VAL 1
#define CELL_UNKNOWN_VAL 0
#define zero(index) (((index) / 3) * 3)

#define square_index(row,col) (zero(row) + (zero(col) / 3))

enum outcome {
    NEW_VALUE = 2,
    EXCLUDED_CAND = 1,
    NOTHING = 0
};

enum iter_over {
    ROWS,
    COLUMNS,
    SQUARES
};

template<class T> class sud_list final {

    class list_node {
        friend class sud_list;
    public:
        list_node(const T& elem) : element(elem) { };
        T element;
        list_node * next;
    };

    u m_count = 0;
    list_node * m_first = nullptr;
    list_node ** m_next_free = &m_first;
public:
    sud_list() { }
    sud_list(const sud_list& other) {
        for (u i = 0; i < other.size(); ++i) {
            this->add(other[i]);
        }
    }
    sud_list(sud_list&& other) :
    m_first(other.m_first),
    m_count(other.m_count),
    m_next_free(other.m_next_free) {
        other.m_first = nullptr;
        other.m_count = 0;
        other.m_next_free = nullptr;
    }
    u size() const {
        return m_count;
    }
    //    void add(const T& element) {
    //        *m_next_free = new list_node(element);
    //        m_next_free = &((*m_next_free)->next);
    //        *m_next_free = nullptr;
    //        ++m_count;
    //    }
    sud_list<T>& add(const T& element) {
        *m_next_free = new list_node(element);
        m_next_free = &((*m_next_free)->next);
        *m_next_free = nullptr;
        ++m_count;
        return *this;
    }
    sud_list<T>& add_if_not_contained(const T& element) {
        if (contains(element)) {
            return *this;
        }
        return add(element);
    }
    void add_all_if_not_contained(sud_list<T> added) {
        for (u i = 0; i < added.size(); ++i) {
            add_if_not_contained(added[i]);
        }
    }
    T& operator[](const u& index) const {
        if (index < 0 || index >= m_count) {
            throw invalid_argument("Out of bound! Requested element index=" +
                                   std::to_string(index) +
                                   ", list size=" +
                                   std::to_string(m_count));
        }
        list_node * current = m_first;
        u i = 0;
        while (current != nullptr && i++ != index && (current = current->next));
        return (current->element);
    }
    bool operator==(const sud_list&other) const {
        if (m_count == other.m_count) {
            list_node * this_node = m_first;
            list_node * other_node = other.m_first;
            for (u i = 0; i < m_count; ++i) {
                if (this_node->element != other_node->element) {
                    return false;
                }
                this_node = this_node->next;
                other_node = other_node->next;
            }
            return true;
        }
        return false;
    }
    bool operator!=(const sud_list&other) const {
        return !(operator==(other));
    }
    void print() const {
        for_each([](list_node * node){
            cout << node->element;
        });
    }
    friend std::ostream& operator<<(std::ostream& out, const sud_list& list) {
        for (u p = 0; p < list.size(); ++p) {
            out << list[p];
        }
        out << endl;
        return out;
    }
    virtual ~sud_list() {
        for_each([](list_node * node){
            delete node;
        });
    }
    void for_each(void(*function) (list_node*)) const {
        list_node * current = m_first;
        while (current != nullptr) {
            list_node * temp = current->next;
            function(current);
            current = temp;
        }
    }
    bool contains(const T& element) const {
        return contains([](const T& elem1, const T & elem2){
            return elem1 == elem2;
        }, element);
    }
    bool contains(bool(*cmpr)(const T&, const T&), const T& element) const {
        list_node * current = m_first;
        while (current != nullptr) {
            list_node * temp = current->next;
            if (cmpr(current->element, element)) return true;
            current = temp;
        }
        return false;
    }
};

struct sud_node {
    u rw = 0;
    u cm = 0;
    u val = 0;
    //    sud_node operator=(const sud_node& other) {
    //        rw=other.rw;
    //        cm=other.cm;
    //        val=other.val;
    //    }
    //    sud_node(const sud_node& other)
    //    : rw(other.rw), cm(other.cm), val(other.val) { }
    //    sud_node(sud_node&& other) : rw(other.rw), cm(other.cm), val(other.val) { }
    sud_node(const u& row = 0, const u& column = 0, const u& value = 0) :
    rw(row), cm(column), val(value) { }
    bool operator==(const sud_node& other) const {
        return rw == other.rw && cm == other.cm && val == other.val;
    }
    bool equals_row(const sud_node& other) {
        return rw == other.rw && val == other.val;
    }
    bool equals_column(const sud_node& other) {
        return val == other.val && cm == other.cm;
    }
    bool operator!=(const sud_node& other) {
        return !(*this == (other));
    }
    friend std::ostream& operator<<(std::ostream& out, const sud_node& n) {
        out << "[" << n.rw << "x" << n.cm << "]" << "=" << n.val << "   ";
        return out;
    }
};
#endif	/* SUDOKUUTILS_H */