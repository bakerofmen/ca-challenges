import java.math.BigInteger;

public class Challenge2 {
	
	/**
	 * Print a usage string for this program.
	 */
	private static void printHelp() {
		System.err.println("Challenge2 will print the maximal " +
						   "product of a set of integers whose sum " +
						   "is the given integer.");
		System.err.println("Usage:");
		System.err.println("    java Challenge2 INTEGER");
		System.err.println();
		System.err.println("Where:");
		System.err.println("    INTEGER is an integer in the range " +
		                   "[0, 2^31-1]");
	}
	
	/**
	 * Take an integer from the CLI and print the maximal product
	 * of a set of integers whose sum is the given integer or 0
	 * when infeasible
	 */
    public static void main(String args[]) {
		// make sure we got an input argument
		if (args.length < 1) {
			printHelp();
			return;
		}
		
		// parse the integer
		int input;
		try {
			input = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.err.printf("Input INTEGER must be an integer in " +
							  "the range [-2^31, 2^31-1], not <%s>\n", 
							  args[0]);
			return;
		}
		
		// invoke the function and print the output
		BigInteger output = Challenge2.maximizeSumProduct(input);
        System.out.println(output);
    }

	/**
	 * Given an integer, return the maximal product of a set of integers
	 * whose sum the input.
	 * 
	 * We return a BigInteger because this number can easily exceed
	 * 64 bits, e.g, given some integer 3*n, we'll return 3^n.
	 * 
	 * Answer largely based on:
	 * https://www.geeksforgeeks.org/breaking-integer-to-get-maximum-product
	 *
	 * The OpenJDK implementation of BigInteger.pow uses repeated
	 * squaring, so this should only take a number of multiply
	 * operations logarithmic in N, so there is no need to write our
	 * own pow function like the geeksforgeeks article did.
	 */
    public static BigInteger maximizeSumProduct(int input) {
		// no two positive addends exist for n < 2. Return 0
		if (input < 2) {
			return BigInteger.ZERO;
		}
		
		// 2 is only 1+1, and 3 is at best 2+1.
		if (input < 4) {
			return BigInteger.valueOf(input - 1);
		}
		
		// a switch spares us one branch
		switch (input % 3) {
			case 0:
				// input = 3*n. Maximal product is 3^n
				return BigInteger.valueOf(3)
				                 .pow(input / 3);
			case 1:
				// input = 3*n+4. Maximal product is 4*3^n
				return BigInteger.valueOf(3)
				                 .pow(input / 3 - 1)
				                 .shiftLeft(2); // shiftLeft(2) is multiply by 4
			case 2:
				// input = 3*n+2. Maximal product is 2*3^n
				return BigInteger.valueOf(3)
				                 .pow(input / 3)
				                 .shiftLeft(1); // shiftLeft(2) is multiply by 2
			default:
				// this shouldn't be possible for input >= 0
				return BigInteger.ZERO;
		}
    }
}
