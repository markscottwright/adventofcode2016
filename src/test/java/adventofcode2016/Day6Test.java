package adventofcode2016;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class Day6Test {

	@Test
	public void testSample() {
		// @formatter:off
		String INPUT = "eedadn\n" + 
				"drvtee\n" + 
				"eandsr\n" + 
				"raavrd\n" + 
				"atevrs\n" + 
				"tsrnev\n" + 
				"sdttsa\n" + 
				"rasrtv\n" + 
				"nssdts\n" + 
				"ntnada\n" + 
				"svetve\n" + 
				"tesnvt\n" + 
				"vntsnd\n" + 
				"vrdear\n" + 
				"dvrsen\n" + 
				"enarar";
		// @formatter:on
		ArrayList<String> input = new ArrayList<String>(Arrays.asList(INPUT.split("\n")));
		List<Map<Character, Integer>> occurrances = Day6.getOccurrances(input);

		assertThat(Day6.mostLikelyString(occurrances)).isEqualTo("easter");
		assertThat(Day6.leastLikelyString(occurrances)).isEqualTo("advent");
	}

}
