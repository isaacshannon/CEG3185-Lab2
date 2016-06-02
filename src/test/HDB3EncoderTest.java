package test;

import static org.junit.Assert.*;

import org.junit.Test;

import lab.HDB3Encoder;

public class HDB3EncoderTest {
	
	private HDB3Encoder encoder = new HDB3Encoder();

	@Test
	public void testAlternatePolarity() {
		String lastPolarity = encoder.lastPolarity;
		String polarity = encoder.alternatePolarity();
		assertNotEquals(lastPolarity,polarity);
	}
	
	@Test
	public void testEncodeZeroString() {
		encoder.lastPolarity = "-";
		encoder.lastViolationPolarity = null;
		
		String testStr =   "00000000000000";
		String resultStr = "000-+00+-00-00";
		String testResult = encoder.encodeStringOfZeroes(testStr);
		assertEquals(resultStr,testResult);
		
		encoder.lastPolarity = "+";
		encoder.lastViolationPolarity = null;
		
		testStr =   "0000";
		resultStr = "000+";
		testResult = encoder.encodeStringOfZeroes(testStr);
		assertEquals(resultStr,testResult);
		
		encoder.lastPolarity = "+";
		encoder.lastViolationPolarity = "-";
		
		testStr =   "0000000000";
		resultStr = "000+-00-00";
		testResult = encoder.encodeStringOfZeroes(testStr);
		assertEquals(resultStr,testResult);
		
		encoder.lastPolarity = "+";
		encoder.lastViolationPolarity = "+";
		
		testStr =   "0000000000";
		resultStr = "-00-+00+00";
		testResult = encoder.encodeStringOfZeroes(testStr);
		assertEquals(resultStr,testResult);
	}
	
	@Test
	public void testEncodeString() {
		encoder.lastPolarity = "-";
		encoder.lastViolationPolarity = null;
		
		String testStr =   "00000000000000";
		String resultStr = "000-+00+-00-00";
		String testResult = encoder.encode(testStr);
		assertEquals(resultStr,testResult);
		
		encoder = new HDB3Encoder();
		testStr =   "1010000100001100001110000111100001010000";
		resultStr = "+0-000-+000+-+-00--+-000-+-+-+00++0-000-";
		testResult = encoder.encode(testStr);
		System.out.println("String  : "+testStr);
		System.out.println("Expected: "+resultStr);
		System.out.println("Actual  : "+testResult);
		assertEquals(resultStr,testResult);
		
		
	}

}
