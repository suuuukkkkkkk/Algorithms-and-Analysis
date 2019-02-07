import java.io.PrintStream;
import java.util.*;

public class SortedLinkedListMultiset<T> extends Multiset<T> {

    protected Node<T> mHead;

    protected Node<T> mTail;

    protected int mLength;

    public SortedLinkedListMultiset() {

        mHead = null;
        mTail = null;
        mLength = 0;
    } // end of SortedLinkedListMultiset()

    public void add(T item) {

        Node<T> newNode = new Node<T>(item);
        Node<T> currNode = mHead;

        //situation: empty list
        if (mHead == null) {

            mHead = mTail = newNode;
            mLength++;
            mHead.setCount(1);
        } //start comparing string,no empty list
        else {

            while (currNode != null) {

                //case need to be added before the head (and only one in the list)
                if (((String) currNode.getValue()).compareTo((String) item) > 0&& currNode.getPrev() == null) {

                    mHead.setPrev(newNode);
                    newNode.setNext(mHead);
                    mHead = newNode;
                    mLength++;
                    mHead.setCount(1);
                    break;

                }
                //case need to be added before the head, and head is also the same case.
                if (((String) currNode.getValue()).compareTo((String) item) == 0 && currNode.getPrev() == null) {

                    mHead.setPrev(newNode);
                    newNode.setNext(mHead);
                    mHead = newNode;
                    mLength++;
                    //not only one in the list
                    newNode.setCount(currNode.getCount() + 1);

                    //add count for each same item
                    while (currNode != null && ((String) currNode.getValue()).compareTo((String) item) == 0) {
                        currNode.setCount(currNode.getCount() + 1);
                        currNode = currNode.getNext();

                    }

                    break;
                }

                //case need to be added at the tail,and only one in the list.
                if (((String) currNode.getValue()).compareTo((String) item) < 0
                        && currNode.getNext() == null) {

                    mTail.setNext(newNode);
                    newNode.setPrev(mTail);
                    mTail = newNode;
                    mLength++;
                    mTail.setCount(1);
                    break;

                }

                //general case. Should be added at the first of the same item.
                if (((String) currNode.getValue()).compareTo((String) item) >= 0) {
                    currNode.getPrev().setNext(newNode);
                    newNode.setPrev(currNode.getPrev());
                    newNode.setNext(currNode);
                    currNode.setPrev(newNode);
                    mLength++;
                    //not only one in the list
                    if (((String) currNode.getValue()).compareTo((String) item) == 0) {
                        newNode.setCount(currNode.getCount() + 1);

                        //add count for each same item
                        while (currNode != null && ((String) currNode.getValue()).compareTo((String) item) == 0) {
                            currNode.setCount(currNode.getCount() + 1);
                            currNode = currNode.getNext();

                        }
                    } //only one in the list
                    else {
                        newNode.setCount(1);

                    }

                    break;
                }

                currNode = currNode.getNext();
            }

        }

    } // end of add()

    public int search(T item) {
        Node<T> currNode = mHead;
        Node<T> temp = mHead;

        while (temp != null) {

            temp = temp.getNext();
        }

        for (int i = 0; i < mLength; i++) {

		if (currNode == null){
			
			break;
			
		}
            if (currNode.getValue().equals(item)) {

                return currNode.getCount();

            }

            currNode = currNode.getNext();

        }

        return 0;
    } // end of search()

    public void removeOne(T item) {
        Node<T> currNode = mHead;

        //case 1 length list
        if (mLength == 1 && currNode.getValue().equals(item)) {

            mHead = mTail = null;

            mLength = 0;
        } //case head
		
		else if (currNode == null){
			
			
		}
        else if (currNode.getValue().equals(item) && currNode.getPrev() == null) {

            currNode.getNext().setPrev(null);
            mHead = currNode.getNext();
            mLength--;

            //case not only one
            currNode = mHead;
            while (currNode != null && currNode.getValue().equals(item)) {
                currNode.setCount(currNode.getCount() - 1);
                currNode = currNode.getNext();

            }

        } else {
            while (currNode != null) {
                //case tail
                if (currNode.getNext() == null && currNode.getValue().equals(item)) {
                    mTail = currNode.getPrev();
                    mTail.setNext(null);
                    mLength--;
                    break;
                } else {

                    //general case
                    if (currNode.getValue().equals(item)) {

                        currNode.getPrev().setNext(currNode.getNext());

                        currNode.getNext().setPrev(currNode.getPrev());

                        mLength--;

                        currNode = currNode.getNext();

                        while (currNode != null && currNode.getValue().equals(item)) {

                            currNode.setCount(currNode.getCount() - 1);
                            currNode = currNode.getNext();

                        }

                        break;
                    }

                }

                currNode = currNode.getNext();

            }

        }

    } // end of removeOne()

    public void removeAll(T item) {
        Node<T> currNode = mHead;

        while (currNode != null) {

            if (currNode.getValue().equals(item) && mLength == 1) {

                //case 1 item list
                mHead = mTail = null;
                mLength = 0;
                break;

            } else if (currNode.getPrev() == null && currNode.getValue().equals(item)) {

                //case head
                while (currNode != null && currNode.getValue().equals(item)) {
                    currNode = currNode.getNext();

                    mLength--;
                }

                mHead = currNode;

                //case reached tail
                if (mHead == null) {
                    mTail = null;
                }

                break;

            } else {
                //case tail

                if (currNode.getNext() == null && currNode.getValue().equals(item)) {

                    mTail = currNode.getPrev();
                    mTail.setNext(null);

                    mLength--;
                    break;
                }
                //general case;

                if (currNode.getValue().equals(item)) {
                    Node<T> tempNode = currNode;

                    while (currNode != null && currNode.getValue().equals(item)) {

                        currNode = currNode.getNext();
                        mLength--;

                    }

                    if (currNode == null) {
                        mTail = tempNode.getPrev();
                        tempNode.getPrev().setNext(null);

                        break;
                    } else {

                        tempNode.getPrev().setNext(currNode);
                        currNode.setPrev(tempNode.getPrev());

                        break;
                    }

                }

            }

            currNode = currNode.getNext();

        }
    } // end of removeAll()

    public void print(PrintStream out) {
        Node<T> currNode = mHead;
        String str = "";

        if (currNode != null) {

            str = str + currNode.getValue() + " | " + currNode.getCount() + "\n";

            currNode = currNode.getNext();
        }

        while (currNode != null) {

            if (currNode.getValue().equals(currNode.getPrev().getValue())) {

                currNode = currNode.getNext();

                continue;
            } else {

                str = str + currNode.getValue() + " | " + currNode.getCount() + "\n";
            }
            currNode = currNode.getNext();

        }

        System.out.println(str);

    } // end of print()

    private class Node<T> {

        // Stored value of node.
        private T mValue;
        // Reference to next node.
        private Node<T> mNext;
        // Reference to previous node.
        private Node<T> mPrev;

        private int count;

        public Node(T value) {

            mValue = value;
            mNext = null;
            mPrev = null;
            count = 0;

        }

        public T getValue() {

            return mValue;
        }

        public void setValue(T value) {

            mValue = value;
        }

        public Node<T> getNext() {

            return mNext;
        }

        public Node<T> getPrev() {

            return mPrev;
        }

        public void setNext(Node<T> next) {

            mNext = next;
        }

        public void setPrev(Node<T> prev) {

            mPrev = prev;
        }

        public int getCount() {

            return count;

        }

        public void setCount(int count) {

            this.count = count;
        }

    }//end of inner class Node

} // end of class SortedLinkedListMultiset