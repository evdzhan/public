package evdzhan_mustafa.adt;
/**
 * (LIFO) - LAST-IN-FIRST-OUT Data Structure .
 */
@SuppressWarnings({"unused", "unchecked"})
public class Stack<T> {

  private static final int DEFAULT_MAX_SIZE = 10;
  private T[] stack;
  private int nextFree = 0;

  public Stack() throws Exception {
    this(DEFAULT_MAX_SIZE);
  }

  public Stack(int size) throws Exception {
    if (size < 0) {
      throw new Exception("Invalid initial size.");
    }
    stack = (T[]) new Object[size];
  }

  public void push(T element) throws Exception {
    if (stack.length == nextFree) {
      throw new Exception("Stack full.");
    }

    stack[nextFree++] = element;
  }

  public T pop() throws Exception {
    if (nextFree == 0) {
      throw new Exception("Stack empty.");
    }
    return stack[--nextFree];
  }
}
