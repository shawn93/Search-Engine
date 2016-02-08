import java.util.ArrayList;
import java.util.Collections;

class LinkedList
{
    protected ListNode head;
    protected ListNode tail;
    
    public LinkedList()
    {
        head = new ListNode(null);
        tail = head;
    }
    
    // Method add(Object o)
    // Adds the object at the end of the linked list
    public void add(Object o)
    {
        ListNode newElem = new ListNode(o);
        tail.setNext(newElem);
        tail = tail.next();
    }
    // Method get(int index)
    //  Returns the object at the specified index in the list
    //  The first element in the list is at index 0, the second is at index 1, etc.
    //  No error checking is done for the range of index -- elements outside the range
    //  will throw a null pointer exception
    public Object get(int index)
    {
           ListNode tmp = head.next();
           for (int i = 0; i < index; i++)
           {
              tmp = tmp.next();
           }
           return tmp.data();
    }
    
    // Method find(Object o)
    //  Returns true if the Object o is in the list, and false otherwise.
    //  Uses .equals to check for equality (as opposed to ==)
    public boolean find(Object o)
    {
        ListNode tmp = head.next();
        while (tmp != null)
        {
            if (tmp.data().equals(o))
            {
                return true;
            }
            tmp = tmp.next();
        }
        return false;
    }

    // Method reverse
    // Reverses the string
    public void reverse()
    {
        ListNode curr = head.next();
        ArrayList<Object> tmp = new ArrayList<Object>();
        while (curr != null) {
            tmp.add(curr.data());
            remove(curr.data());
            curr = curr.next();
        }
        Collections.reverse(tmp);
        for (Object o : tmp) {
            add(o);
        }
    }
    
    // Method toString()
    // Creates a String representation of the list:
    //  Left parenthesis, followed by by concatenating the result of toString() 
    //  called on each element of the list (separated by commas), fooled by a 
    //  right parenthesis.
    //  Empty list:  toString => "()"
    //  List containing the single Integer 3: toString => "(3)"
    //  List containing three integers 1, 2, 3:  toString => "(1,2,3)"
    @Override
    public String toString()
    {
        String result = "";
        ListNode tmp1 = head.next();
        if (tmp1 != null) {
            result = tmp1.data().toString(); 
            ListNode tmp2 = tmp1.next();
            while (tmp2 != null) {
                result += "," + tmp2.data().toString();
                tmp2 = tmp2.next();
            }       
        }
        return "(" + result + ")";
    }
    
    // Method remove(Object o)
    //  Removes the first occurrence of the Object o from the list.  If
    //    the object appears more than once, only the first occurrence is
    //    removed.  If the object does not occur in the list, the method
    //    does nothing. 
    public void remove(Object o)
    {
        ListNode tmp = head.next();
        if (tmp.data().equals(o)) {
            head = head.next();
        }
        else {  
            while (tmp.next() != null)
            {   
                if (tmp.next().data().equals(o)) {
                    tmp.setNext(tmp.next().next());
                    if (tmp.next() == null) {
                        tail = tmp;
                    }
                    return;
                }
                tmp = tmp.next();   
            }
        }
    }
    
    public static void main(String args[])
    {
        LinkedList l = new LinkedList();
        for (int i = 0; i < 10; i++)
        {
            l.add(new Integer(i));
        }
        System.out.println(l);
        l.reverse();
        System.out.println(l);
        l.remove(new Integer(5));
        System.out.println(l);
        l.remove(new Integer(0));
        System.out.println(l);
        l.add(new Integer(11));
        System.out.println(l);
        l.remove(new Integer(5));
        System.out.println(l);

        LinkedList l2 = new LinkedList();
        System.out.println(l2);
        l2.add(new Integer(2));
        System.out.println(l2);
        l2.remove(new Integer(2));
        System.out.println(l2);

    }

}