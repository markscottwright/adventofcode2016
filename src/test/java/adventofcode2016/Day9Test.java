package adventofcode2016;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;

public class Day9Test {

	@Test
	public void testSampleLengths() {
		assertThat(Day9.decompressLen("ADVENT")).isEqualTo(6);
		assertThat(Day9.decompressLen("(3x3)XYZ")).isEqualTo(9);
		assertThat(Day9.decompressLen("(27x12)(20x12)(13x14)(7x10)(1x12)A")).isEqualTo(241920);
		
	}

}
