package adventofcode2016;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Day2Test {

	@Test
	public void testSamples() {
		assertThat(Day2.followDirections(Day2.mapping1, 5, "ULL".toCharArray())).isEqualTo(1);
		assertThat(Day2.followDirections(Day2.mapping1, 1, "RRDDD".toCharArray())).isEqualTo(9);
		assertThat(Day2.followDirections(Day2.mapping1, 9, "LURDL".toCharArray())).isEqualTo(8);
		assertThat(Day2.followDirections(Day2.mapping1, 8, "UUUUD".toCharArray())).isEqualTo(5);
	}

}
