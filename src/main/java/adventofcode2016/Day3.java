package adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day3 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/day3.txt"));
		long numPossibleTriangles = lines.stream().map(Day3::asSortedIntegers).filter(Day3::isPossibleTriangle).count();

		System.out.println("Part 1:" + numPossibleTriangles);

		numPossibleTriangles = 0;
		for (int i = 0; i < lines.size(); i += 3) {
			int[] line1 = asIntegers(lines.get(i));
			int[] line2 = asIntegers(lines.get(i + 1));
			int[] line3 = asIntegers(lines.get(i + 2));
			
			int[] triangle1 = new int[] {line1[0], line2[0], line3[0]};
			int[] triangle2 = new int[] {line1[1], line2[1], line3[1]};
			int[] triangle3 = new int[] {line1[2], line2[2], line3[2]};
			Arrays.sort(triangle1);
			Arrays.sort(triangle2);
			Arrays.sort(triangle3);
			if (isPossibleTriangle(triangle1))
				numPossibleTriangles++;
			if (isPossibleTriangle(triangle2))
				numPossibleTriangles++;
			if (isPossibleTriangle(triangle3))
				numPossibleTriangles++;
		}
		System.out.println("Part 2:" + numPossibleTriangles);
	}

	private static int[] asIntegers(String l) {
		return Arrays.stream(l.split(" +")).filter(f -> !f.isBlank()).mapToInt(Integer::parseInt).toArray();
	}

	private static int[] asSortedIntegers(String l) {
		return Arrays.stream(l.split(" +")).filter(f -> !f.isBlank()).mapToInt(Integer::parseInt).sorted().toArray();
	}

	private static boolean isPossibleTriangle(int[] sortedSides) {
		return sortedSides[0] + sortedSides[1] > sortedSides[2];
	}
}
