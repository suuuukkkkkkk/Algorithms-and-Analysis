import java.io.PrintStream;
import java.util.*;

public class LinkedListMultiset<T> extends Multiset<T> {
    
    // Reference to head of list
    protected Node<T> mHead;
    //Reference to tail of list
    protected Node<T> mTail;
    
    //Length of list
    protected int mLength;
    
    protected Node<T> currentCase;
    
    public LinkedListMultiset() {
        
        mHead = null;
        mTail = null;
        mLength = 0;
    } // end of LinkedListMultiset()
    
    public void add(T item) {
        Node<T> newNode = new Node<T>(item);
        
        //if head is empty, then list is empty and head and tail references need to be initialised.
        if (mHead == null) {
            
            mHead = newNode;
            mTail = newNode;
            mLength++;
            
            newNode.addCount(1);
            newNode.setUnique(true);
            // System.out.println(mHead.isUnique());
            
        } //otherwise, add node to the head of list.
        else {
            newNode.setNext(mHead);
            mHead.setPrev(newNode);
            mHead = newNode;
            mLength++;
            int countNumber = search(item);
            
            
            if (countNumber == 1){
                mHead.addCount(1);
                mHead.setUnique(true);
                
            }
            
            else{
                currentCase.addCount(1);
                Node<T> currNode = mHead;
                for (int i = 0; i < mLength; i++){
                    if(currNode.getValue().equals(item)){
                        currNode.setCount(currentCase.getCount());
                    }
                }
                
            }
        }
        
        
    } // end of add()
    
    public int search(T item) {
        
        Node<T> currNode = mHead;
        int count = 0;
        
        for (int i = 0; i < mLength; i++) {
            
            if (currNode.getValue().equals(item)) {
                
                count++;
                if(currNode.isUnique() == true){
                    
                    currentCase = currNode;
                }
                
            }
            
            currNode = currNode.getNext();
            
        }
        
        return count;
    } // end of add()
    
    public void removeOne(T item) {
        Node<T> currNode = mHead;
        if(currNode != null){
        if (currNode.getValue().equals(item)) {
            if (mLength == 1) {
                
                mHead = mTail = null;
            } else {
                mHead = currNode.getNext();
                mHead.setPrev(null);
                currNode = null;
                
            }
            
            mLength--;
        } else {
            currNode = currNode.getNext();
            
            while (currNode != null) {
                
                if (currNode.getValue().equals(item)) {
                    
                    Node<T> prevNode = currNode.getPrev();
                    if (currNode.getNext() == null) {
                        
                        prevNode.setNext(null);
                        mTail = prevNode;
                        
                    } else {
                        
                        prevNode.setNext(currNode.getNext());
                        currNode.getNext().setPrev(prevNode);
                        
                        currNode.setNext(null);
                        
                    }
                    
                    currNode.setPrev(null);
                    currNode = null;
                    mLength--;
                    
                    break;
                    
                }
                
                currNode = currNode.getNext();
            }
            
        }
		}
        switchUnique(item);
    } // end of removeOne()
    
    public void removeAll(T item) {
        
        Node<T> currNode = mHead;
        
        while (currNode != null){
            
            if(currNode.getValue().equals(item)){
                
                if(mLength == 1){
                    
                    
                    mHead = mTail = null;
                    currNode = null;
                    mLength = 0;
                    break;
                    
                }
                
                if(currNode.getPrev() == null){
                    
                    mHead = currNode.getNext();
                    mHead.setPrev(null);
                    currNode.setValue(null);
                    
                    mLength--;
                }
                
                else if (currNode.getNext() == null){
                    
                    currNode.getPrev().setNext(null);
                    mTail = currNode.getPrev();
                    currNode.setPrev(null);
                    currNode = null;
                    mLength--;
                    break;
                }
                
                else {
                    Node<T> prevNode = currNode.getPrev();
                    prevNode.setNext(currNode.getNext());
                    currNode.getNext().setPrev(prevNode);
                    currNode.setPrev(null);
                    mLength--;
                }
                
                
                
            }
            
            currNode = currNode.getNext();
            
        }
        
        
    } // end of removeAll()
    
    public void print(PrintStream out) {
        System.out.println(getPrintList());
        
    } // end of print()
    
    
    public String getPrintList(){
        
        String str = "";
        
        Node<T> currentNode = mHead;
        
        while (currentNode!=null){
            
            if (currentNode.isUnique()== true){
                
                
                str = str + currentNode.getValue() + " | " + currentNode.getCount() + "\n";
            }
            currentNode = currentNode.getNext();
            
        }
        
        return str;
        
        
        
        
        
        
        
    }
    
    public void switchUnique(T item){
        
        currentCase = null;
        
        Node<T> currNode = mHead;
        
        int tempCount = search(item);
        
        if (currentCase!= null){
            currentCase.setCount(tempCount);
            
        }
        
        else{
            for (int i = 0; i< mLength; i ++){
                
                if(currNode.getValue().equals(item)){
                    
                    currNode.setUnique(true);
                    currNode.setCount(tempCount);
                    break;
                    
                }
                
                
            }
        }
        
        currNode = mHead;
        for (int i = 0; i< mLength; i ++){
            
            if(currNode.getValue().equals(item)){
                
                currNode.setCount(currentCase.getCount());
                
            }
            
            
        }
        
        
    }
    private class Node<T> {
        
        // Stored value of node.
        private T mValue;
        // Reference to next node.
        private Node<T> mNext;
        // Reference to previous node.
        private Node<T> mPrev;
        
        private boolean unique;
        
        private int count;
        
        public Node(T value) {
            
            mValue = value;
            mNext = null;
            mPrev = null;
            count = 0;
            unique = false;
            
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
        
        public int getCount(){
            
            return count;
            
        }
        
        public void addCount(int number){
            
            count = count + number;
            
        }
        
        public void setCount(int count){
            
            this.count = count;
        }
        
        public boolean isUnique() {
            return unique;
        }
        
        public void setUnique(boolean unique) {
            this.unique = unique;
        }
        
    }//end of inner class Node
    
} // end of class LinkedListMultiset