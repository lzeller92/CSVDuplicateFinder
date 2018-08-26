package main;

public class Utility {

	public static double similarity(String aString, String anotherString) {
		String theString = aString == null ? "" : aString.trim();
		String theOtherString = anotherString == null ? "" : aString.trim();
		if (theString.isEmpty() && theOtherString.isEmpty()) {
			return 0.0;
		}

		if (theString.length() < theOtherString.length()) {
			String tmp = theString;
			theString = theOtherString;
			theOtherString = tmp;
		}
		int theLongerLength = theString.length();
		if (theLongerLength == 0) {
			return 1.0;
		}
		return (theLongerLength - getLevenshteinDistance(theString, theOtherString)) / (double) theLongerLength;
	}

	private static int getLevenshteinDistance(CharSequence s, CharSequence t) {
		if (s == null || t == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}

		int n = s.length(); // length of s
		int m = t.length(); // length of t

		if (n == 0) {
			return m;
		} else if (m == 0) {
			return n;
		}

		if (n > m) { // swap the input strings to consume less memory
			final CharSequence tmp = s;
			s = t;
			t = tmp;
			n = m;
			m = t.length();
		}

		int p[] = new int[n + 1]; // 'previous' cost array, horizontally
		int d[] = new int[n + 1]; // cost array, horizontally
		int _d[]; // placeholder to assist in swapping p and d

		// indexes into strings s and t
		int i; // iterates through s
		int j; // iterates through t

		char t_j; // jth character of t

		int cost; // cost

		for (i = 0; i <= n; i++) {
			p[i] = i;
		}

		for (j = 1; j <= m; j++) {
			t_j = t.charAt(j - 1);
			d[0] = j;

			for (i = 1; i <= n; i++) {
				cost = s.charAt(i - 1) == t_j ? 0 : 1;
				// minimum of cell to the left+1, to the top+1, diagonally left and up +cost
				d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
			}

			// copy current distance counts to 'previous row' distance counts
			_d = p;
			p = d;
			d = _d;
		}
		return p[n];
	}

}
