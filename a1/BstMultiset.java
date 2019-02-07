import java.io.PrintStream;
import java.util.*;

public class BstMultiset<T> extends Multiset<T>
{
	Node root;
	
	/*implementation node*/
	
	protected class Node
	{
		
		Node leftSdie;
		T item;
		int count;
		Node rightSide;
		
		/*constructor*/
		
		public Node(T item)
		{
			
			leftSdie = null;
			this.item = item;
			count = 1;
			rightSide = null;
			
		}
		public void add(T item)
		{
			
			if(this.item.equals(item))/*same item*/
			{
				
				count++;
				
			}
			else if(this.item.toString().compareTo(item.toString())>0)
			{
				
				if(leftSdie == null) /*add smaller one*/
				{
					
					leftSdie = new Node(item);
					
				}else{
					
					leftSdie.add(item);
				}
				
			}else{
				
				if(rightSide == null)   /*add bigger one*/
				{
					
					rightSide = new Node(item);
					
				}else{
					
					rightSide.add(item);
					
				}
			}
		}
		public int search(T item)
		{
			
			if(this.item.equals(item))	
			{
				return count;
				
			}else if(this.item.toString().compareTo(this.toString())>0)	
			{
				if(leftSdie == null)
				{
					
					return 0;
					
				}else{
					
					return leftSdie.search(item);
					
				}
				
			}else{
				
				if(rightSide == null)
				{
					
					return 0;
					
				}else{
					
					return rightSide.search(item);
					
				}
			}
		}
		


		public void removeOne(T item) 
		{
			
			if(this.item.toString().compareTo(item.toString())>0)
			{
				
				if(leftSdie == null)
					
				{
					
					return;
					
				}
				if(leftSdie.item.equals(item))
				{
					
					leftSdie.count--;
					
					if(leftSdie.count==0&& !leftSdie.full())	
					{
						
						leftSdie = null;
						
					}
				}else
				{
					
					leftSdie.removeOne(item);
					
				}
			}else{
				
				if(rightSide == null)
				{
					
					return;
					
				}
				if(rightSide.item.equals(item))
					
				{
					
					rightSide.count--;
					
					if(rightSide.count==0&& !rightSide.full())
					{
						
						rightSide = null;
						
					}
				}else
				{
					
					rightSide.removeOne(item);
					
				}
			}
		}
		
		
		
		public void removeAll(T item) {
			if(this.item.toString().compareTo(item.toString())>0)
			{
				
				if(leftSdie == null)		
				{
					
					return;
					
				}
				if(leftSdie.item.equals(item))
				{
					
					if(!leftSdie.full())
					{
						
						leftSdie = null;
						
					}
				}else
				{
					
					leftSdie.removeAll(item);
					
				}
			}else{
				
				if(rightSide == null)
				{
					
					return;
					
				}
				if(rightSide.item.equals(item))
				{
					
					if(!rightSide.full())
					{
						
						rightSide = null;
						
					}
				}else
				{
					
					rightSide.removeAll(item);
					
				}
			}
		}
		
		public void print(PrintStream out)
		{
			
			if(leftSdie != null)
			{
				
				leftSdie.print(out);
				
			}
			
			System.out.println(item + " | " + count);
			
			if(rightSide != null)
			{
				
				rightSide.print(out);
				
			}
		}
		
		public boolean full()
		{
			
			if(leftSdie != null)
			{
				
				if(leftSdie.rightSide == null)
				{
					
					item = leftSdie.item;
					count = leftSdie.count;
					leftSdie = leftSdie.leftSdie;
					
				}else{
					
					Node m = this.leftSdie;
					for(;m.rightSide.rightSide!=null;m=m.rightSide){}
					item = m.rightSide.item;
					count = m.rightSide.count;
					m.rightSide = m.rightSide.leftSdie;
					
				}
			}else if(rightSide != null)
			{
				
				if(rightSide.leftSdie == null)
					
				{
					item = rightSide.item;
					count = rightSide.count;
					rightSide = rightSide.rightSide;
					
				}else{
					
					Node m = this.rightSide;
					for(;m.leftSdie.leftSdie!=null;m=m.leftSdie){}
					item = m.leftSdie.item;
					count = m.leftSdie.count;
					m.leftSdie = m.leftSdie.rightSide;
					
				}
			}else{
				
				return false;
				
			}
			
			return true;
			
		}
	}
		
	
	public BstMultiset() {
		
		root = null;
		
	} 

	public void add(T item) {
		if(root == null) /*check if root empty*/
		{
			
			root = new Node(item);
			
		}else{
			
			root.add(item);
			
		}
	} 


	public int search(T item) {
		if(root == null)
		{
			
			return 0;
			
		}
		
		return root.search(item);
		
	} 


	public void removeOne(T item) {
		if(root == null)
		{
			
			return;
			
		}
		if(root.item.equals(item))
		{
			
			root.count--;
			if(root.count==0&& !root.full())
			{
				
				root = null;
				
			}
			return;
			
		}
		
		root.removeOne(item);
		
	} 
	
	
	public void removeAll(T item) {
		if(root == null)
		{
			
			return;
			
		}
		if(root.item.equals(item))
		{
			
			if(!root.full())
			{
				
				root = null;
				
			}
			return;
		}
		root.removeAll(item);
	} 


	public void print(PrintStream out) {
		if(root == null)
		{
			
			return;
			
		}
		root.print(out);/*print the root*/
	} 

} 