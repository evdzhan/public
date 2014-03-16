package linearStructures;
public class Queue
{
   private Object[] queue;

   private int head;
   private int tail;
   private final int SIZE;


   public Queue (int size)
   {
      // head==tail could mean either full or empty,
      // increase the size of the array and always keep one
      // empty space between head and tail is one solution.
      this.SIZE=(size+1);
      queue=new Object[SIZE];
      head=0;
      tail=0;
   }

   public boolean isEmpty()
   {
      return (head==tail);
   }

   public boolean isFull()
   {
      return (size() == capacity());
   }

   public void add(Object o)
   {
      if (isFull())
      {
         throw new IndexOutOfBoundsException("Queue Full");
      }
      queue[tail]=o;
      tail = (tail+1)%(SIZE);
   }

   public Object remove()
   {
      if (isEmpty())
      {
         throw new IndexOutOfBoundsException("Empty Queue");
      }
      Object o = queue[head];
      head = (head+1)%(SIZE);
      return o;
   }

   public Object front()
   {
	if (isEmpty())
	{
	   throw new IndexOutOfBoundsException("Empty Queue");
	}
	Object o = queue[head];
	return o;
   }

   public int capacity()
   {
      return SIZE-1;
   }

   public int size()
   {
      int i=tail-head;
      if (i<0)
      {
         i=i+SIZE;
      }
      return i;
   }

   public String toString()
   {
      StringBuffer buffer = new StringBuffer( );

      for( int pos = head;
           pos != tail;
           pos = ( pos + 1 ) % queue.length
	 )
      {
         buffer.append( "Item: " + queue[pos] + "\n" );
      }

      return ">>Details: \n----------\n\n" + buffer + " \n\n" +
             "head:"+head+
             ",tail:"+tail+
             ",capacity:"+capacity()+
             ",size:"+size();
   }


   public static void main( String[] args )
   {
      Queue q = new Queue( 3 );

      q.add("Hello");
      q.add("World");
      q.add("Richard");

      System.out.println(q);
      System.out.println( "remove: " + q.remove( ) );
      System.out.println( "remove: " + q.remove( ) );
      System.out.println(q);
      System.out.println( "remove: " + q.remove( ) );
      q.add("Hello");
      q.add("Hello");
      System.out.println(q);
      System.out.println( "remove: " + q.remove( ) );
      System.out.println( "remove: " + q.remove( ) );
      q.add("Richard");
      System.out.println( "remove: " + q.remove( ) );
      System.out.println(q);

   }

}