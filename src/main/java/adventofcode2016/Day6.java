package adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6 {

	static Map<Character, Integer> occurrances(List<String> strings, int pos) {
		Map<Character, Integer> occurrancesMap = new HashMap<>();
		for (var s : strings) {
			Character c = s.charAt(pos);
			occurrancesMap.put(c, occurrancesMap.getOrDefault(c, 0) + 1);
		}
		return occurrancesMap;
	}

	static Character mostCommonChar(Map<Character, Integer> occurrance) {
		Character maxChar = 'a';
		Integer maxCount = 0;
		for (Character c : occurrance.keySet()) {
			Integer count = occurrance.get(c);
			if (count > maxCount) {
				maxCount = count;
				maxChar = c;
			}
		}
		return maxChar;
	}

	static Character leastCommonChar(Map<Character, Integer> occurrance) {
		Character minChar = 'a';
		Integer minCount = Integer.MAX_VALUE;
		for (Character c : occurrance.keySet()) {
			Integer count = occurrance.get(c);
			if (count < minCount) {
				minCount = count;
				minChar = c;
			}
		}
		return minChar;
	}
	public static List<Map<Character, Integer>> getOccurrances(List<String> input) {
		List<Map<Character, Integer>> out = new ArrayList<Map<Character, Integer>>();
		for (int i = 0; i < input.get(0).length(); ++i) {
			Map<Character, Integer> occurrancesForPosition = occurrances(input, i);
			out.add(occurrancesForPosition);
		}
		return out;
	}

	public static String mostLikelyString(List<Map<Character, Integer>> occurrances) {
		StringBuffer out = new StringBuffer();
		for (var occurrancesAtPosition : occurrances) {
			out.append(mostCommonChar(occurrancesAtPosition));
		}
		return out.toString();
	}

	public static String leastLikelyString(List<Map<Character, Integer>> occurrances) {
		StringBuffer out = new StringBuffer();
		for (var occurrancesAtPosition : occurrances) {
			out.append(leastCommonChar(occurrancesAtPosition));
		}
		return out.toString();
	}
	public static void main(String[] args) throws IOException {
		List<String> part1Input = Files.readAllLines(Paths.get("src/main/resources/day6.txt"));
		System.out.println("Day 6 part 1 = " + mostLikelyString(getOccurrances(part1Input)));
		System.out.println("Day 6 part 2 = " + leastLikelyString(getOccurrances(part1Input)));
	}
}
