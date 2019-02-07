import java.io.*;
import java.util.*;
import java.lang.*;


public class MainGenerator
{
	/** Name of class, used in error messages. */
	protected static final String progName = "MultisetTester";
	


	/**
	 * Print help/usage message.
	 */
	public static void usage(String progName) {
		System.err.println(progName + ": <implementation> [fileName to output search results to]");
		System.err.println("<implementation> = <linkedlist | sortedlinkedlist | bst| hash | baltree>");
		System.exit(1);
	} // end of usage


	/**
	 * Process the operation commands coming from inReader, and updates the multiset according to the operations.
	 * 
	 * @param inReader Input reader where the operation commands are coming from.
	 * @param searchOutWriter Where to output the results of search.
	 * @param multiset The multiset which the operations are executed on.
	 * 
	 * @throws IOException If there is an exception to do with I/O.
	 */
	public static void processOperations(ArrayList<String> array, Multiset<String> multiset) 
		throws IOException
	{


		// continue reading in commands until we either receive the quit signal or there are no more input commands
		for (int i = 0; i < array.size(); i++) {
			String[] tokens = array.get(i).split(" ");


			String command = tokens[0];
			// determine which operation to execute
			switch (command.toUpperCase()) {
				// add
				case "A":
					if (tokens.length == 2) {
						multiset.add(tokens[1]);
					}

					break;
				// search
				case "S":
					if (tokens.length == 2) {
						int foundNumber = multiset.search(tokens[1]);

					}

					break;
				// remove one instance
				case "RO":
					if (tokens.length == 2) {
						multiset.removeOne(tokens[1]);
					}

					break;
				// remove all instances
				case "RA":
					if (tokens.length == 2) {
						multiset.removeAll(tokens[1]);
					}

					break;		
				
			}

		}
		

	} // end of processOperations() 


	/**
	 * Main method.  Determines which implementation to test.
	 */
	public static void main(String[] args) {

		// check number of command line arguments
		if (args.length != 3) {
			System.err.println("Incorrect number of arguments.");
			usage(progName);
		}
		
		DataGenerator generator = new DataGenerator(Integer.parseInt(args[1]), args[2]);

		String implementationType = args[0];
		

		
		
		// determine which implementation to test
		Multiset<String> multiset = null;
		switch(implementationType) {
			case "linkedlist":
				multiset = new LinkedListMultiset<String>();
				break;
			case "sortedlinkedlist":
				multiset = new SortedLinkedListMultiset<String>();
				break;
			case "bst":
				multiset = new BstMultiset<String>();
				break;
			case "hash":
				multiset = new HashMultiset<String>();
				break;
			case "baltree":
				multiset = new BalTreeMultiset<String>();
				break;
			default:
				System.err.println("Unknown implmementation type.");
				usage(progName);
		}


		// construct in and output streams/writers/readers, then process each operation.
		try {
			
			ArrayList<String> init = generator.getInitArray();
			ArrayList<String> array = generator.getArray();
		
			// process the operations
			processOperations(init, multiset);
			
			long startTime = System.nanoTime();
			processOperations(array, multiset);
			long endTime = System.nanoTime();
	        System.out.println("time taken = " + ((double)(endTime - startTime)) / Math.pow(10, 9) + " sec");
			
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	} // end of main()

} // end of class MultisetTester
