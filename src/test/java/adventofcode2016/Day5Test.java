package adventofcode2016;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class Day5Test {

	@Test
	public void testMd5() throws NoSuchAlgorithmException {
		assertThat(Day5.md5("abc3231929")).startsWith("00000");
		assertThat(Day5.md5("abc5017308")).startsWith("00000");
		assertThat(Day5.md5("abc3231928")).doesNotStartWith("00000");
	}

}
