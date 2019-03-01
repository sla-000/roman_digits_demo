package ga.balalay.romedigits;

import org.junit.Assert;
import org.junit.Test;

public class RomanDigitsTest {

	@Test
	public void toRoman() {
		Assert.assertEquals( "", RomanDigits.toRoman( 0 ) );
		Assert.assertEquals( "I", RomanDigits.toRoman( 1 ) );
		Assert.assertEquals( "III", RomanDigits.toRoman( 3 ) );
		Assert.assertEquals( "IX", RomanDigits.toRoman( 9 ) );
		Assert.assertEquals( "XII", RomanDigits.toRoman( 12 ) );
		Assert.assertEquals( "XXVII", RomanDigits.toRoman( 27 ) );
		Assert.assertEquals( "XXXII", RomanDigits.toRoman( 32 ) );
		Assert.assertEquals( "XLIX", RomanDigits.toRoman( 49 ) );
		Assert.assertEquals( "L", RomanDigits.toRoman( 50 ) );
		Assert.assertEquals( "LI", RomanDigits.toRoman( 51 ) );
		Assert.assertEquals( "LXIX", RomanDigits.toRoman( 69 ) );
		Assert.assertEquals( "LXXIII", RomanDigits.toRoman( 73 ) );
		Assert.assertEquals( "LXXXIV", RomanDigits.toRoman( 84 ) );
		Assert.assertEquals( "XCV", RomanDigits.toRoman( 95 ) );
		Assert.assertEquals( "XCIX", RomanDigits.toRoman( 99 ) );
		Assert.assertEquals( "C", RomanDigits.toRoman( 100 ) );

		try {
			RomanDigits.toRoman( -1 );
			Assert.fail();
		}
		catch ( IllegalArgumentException e ) {
			Assert.assertTrue( true );
		}

		try {
			RomanDigits.toRoman( 101 );
			Assert.fail();
		}
		catch ( IllegalArgumentException e ) {
			Assert.assertTrue( true );
		}
	}
}