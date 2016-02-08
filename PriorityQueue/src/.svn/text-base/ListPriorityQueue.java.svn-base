
public class ListPriorityQueue implements PriorityQueue{
	private int[] queue; 
	private int head; 
	private int tail;
	
	public int next (int i) {
		return (i + 1) % queue.length;
	}
	
    private int prev (int i) { 
     	if(i-- == -1) {
     		return queue.length - 1;
     	}
    	return i; 
    }
	
	public ListPriorityQueue (int size) {
	    this.queue = new int[size]; 
	    this.head = 0; 
	    this.tail = 0; 
	}
	
	private void doubleSize() {
		
    	int[] Array = new int[queue.length*2]; 
    	int i = 0; 
    	int j = head;
    	
    	while(i < queue.length) {
    		Array[i] = queue[j]; 
    		j = next(j); 
    		i++; 
    	}
    	
    	head = 0; 
    	tail = i; 
    	queue = Array; 
    }
	
	@Override
	public int removeSmallest() {
		if(head != tail) { 
	    	int remove = queue[head]; 
	        queue[head] = 0; 
	        head = next(head);
	        return remove;
		}
		return 0; 
	}
	
	@Override
	public void insert(int elem) {
    	if(head == tail) {
    		queue[head] = elem; 
    		tail = next(tail);   		
    	} 
    	else { 
    		int i = head; 
    		while(i != tail) { 
    			if(queue[i] > elem) {
    				break;
    			}
    			i = next(i);
    		}
    		
    		int j = tail; 
    		while(j != i) { 
    			queue[j] = queue[prev(j)]; 
    			j = prev(j); 
    		}
    		tail = next(tail); 
    		queue[i] = elem;
    	}

    	if(tail == head) {
      		doubleSize();  		
    	}
	}
}
