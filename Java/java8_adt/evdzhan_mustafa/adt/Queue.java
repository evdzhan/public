package evdzhan_mustafa.adt;
/**
 * (FIFO) - FIRST-IN-FIRST-OUT Data Structure.
 */
@SuppressWarnings({"unused", "unchecked"})
public class Queue<T> {

  private static final int DEFAULT_MAX_SIZE = 10;
  private T[] queue;
  private int nextFree = 0;
  private int nextToPop = 0;
  private int size = 0;

  public Queue() throws Exception {
    this(DEFAULT_MAX_SIZE);
  }

  public Queue(int size) throws Exception {
    if (size < 1) {
      throw new Exception("Invalid initial size.");
    }
    queue = (T[]) new Object[size];
  }

  public void push(T element) throws Exception {
    if (size >= queue.length) {
      throw new Exception("Queue full.");
    }

    queue[nextFree++] = element;

    if (nextFree == queue.length) {
      nextFree = 0;
    }
    size++;

  }

  public T pop() throws Exception {
    if (size == 0) throw new Exception("Queue empty.");

    T el = queue[nextToPop];
    nextToPop++;
    if (nextToPop == queue.length) {
      nextToPop = 0;
    }
    size--;
    return el;

  }

  public static void main(String[] args) throws Exception {
    Queue<Integer> qi = new Queue<>(3);
    qi.push(1);
    qi.push(2);
    qi.push(3);
    qi.push(4);
  }
}
