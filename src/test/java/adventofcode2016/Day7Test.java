package adventofcode2016;

import static org.junit.Assert.*;

import org.junit.Test;

public class Day7Test {

	@Test
	public void testSampleData() {
		assertTrue(Day7.supportsTls("abba[mnop]qrst"));
		assertFalse(Day7.supportsTls("abcd[bddb]xyyx"));
		assertFalse(Day7.supportsTls("aaaa[qwer]tyui"));
		assertTrue(Day7.supportsTls("ioxxoj[asdfgh]zxcvbn"));
	}
	
	@Test
	public void testSampleSslData() throws Exception {
		assertTrue(Day7.supportsSsl("aba[bab]xyz"));
	}

}
