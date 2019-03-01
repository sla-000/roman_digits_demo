package ga.balalay.romedigits;

public class RomanDigits {

	/**
	 * Get Roman number, 0..100
	 *
	 * @param val Number
	 * @return String
	 */
	public static String toRoman( final int val ) {
		if( val < 0 || val > 100 ) {
			throw new IllegalArgumentException("Value must be in range 0..100");
		}
		StringBuilder sb = new StringBuilder();

		final int val100 = val / 100 % 10;
		final int val10 = val / 10 % 10;
		final int val1 = val % 10;

		if( val100 >= 1 ) {
			sb.append( "C" );
		}

		if( val10 >= 9 ) {
			sb.append( "XC" );
		}
		else if( val10 >= 8 ) {
			sb.append( "LXXX" );
		}
		else if( val10 >= 7 ) {
			sb.append( "LXX" );
		}
		else if( val10 >= 6 ) {
			sb.append( "LX" );
		}
		else if( val10 >= 5 ) {
			sb.append( "L" );
		}
		else if( val10 >= 4 ) {
			sb.append( "XL" );
		}
		else if( val10 >= 3 ) {
			sb.append( "XXX" );
		}
		else if( val10 >= 2 ) {
			sb.append( "XX" );
		}
		else if( val10 >= 1 ) {
			sb.append( "X" );
		}

		if( val1 >= 9 ) {
			sb.append( "IX" );
		}
		else if( val1 >= 8 ) {
			sb.append( "VIII" );
		}
		else if( val1 >= 7 ) {
			sb.append( "VII" );
		}
		else if( val1 >= 6 ) {
			sb.append( "VI" );
		}
		else if( val1 >= 5 ) {
			sb.append( "V" );
		}
		else if( val1 >= 4 ) {
			sb.append( "IV" );
		}
		else if( val1 >= 3 ) {
			sb.append( "III" );
		}
		else if( val1 >= 2 ) {
			sb.append( "II" );
		}
		else if( val1 >= 1 ) {
			sb.append( "I" );
		}

		return sb.toString();
	}
}
