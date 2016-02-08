
public class ArrayQueue implements Queue 
{

    static final int defaultsize = 100;

    Object data[];
    int head;
    int tail;
    int size;

    ArrayQueue(int maxsize) 
    {
        data = new Object[maxsize];
        head = 0;
        tail = 0;
        size = maxsize;
    }

    ArrayQueue() 
    {
        data = new Object[defaultsize];
        head = 0;
        tail = 0;
        size = defaultsize;
    }
    
    @Override
	public void enqueue(Object elem) 
    {
	Assert.notFalse((tail+1)%size != head, "Queue Full");
	data[tail] = elem;
	tail = (tail + 1) % size;
    }
    
    @Override
	public Object dequeue() 
    {
	Object retval;
	
	if (head == tail)
	    return null;
	retval = data[head];
	head = (head + 1) % size;
	return retval;
    }
    
    @Override
	public boolean empty() 
    {
	return head == tail;
    }

    @Override
	public String toString()
    {
	String result = "[";
        int tmpHead = head;
	if (tmpHead != tail)
	{
            result = result + data[tmpHead];
            tmpHead = (tmpHead + 1) % size;
            while (tmpHead != tail)
            {
		result = result + "," + data[tmpHead];
                tmpHead = (tmpHead + 1) % size;
	    }
	}
	result = result + "]";
	return result;
    }

}


