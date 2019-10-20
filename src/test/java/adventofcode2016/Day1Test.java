package adventofcode2016;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;

public class Day1Test {

	@Test
	public void testData1() {
		assertThat(Day1.followDirections("R2, L3").blocksAway()).isEqualTo(5);
	}

}
