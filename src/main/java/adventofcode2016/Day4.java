package adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
	static class LetterCount implements Comparable<LetterCount> {
		@Override
		public String toString() {
			return "LetterCount [count=" + count + ", letter=" + letter + "]";
		}

		int count;
		char letter;

		public LetterCount(char letter, int count) {
			this.letter = letter;
			this.count = count;
		}

		@Override
		public int compareTo(LetterCount other) {
			if (count == other.count)
				return Character.compare(letter, other.letter);

			// higher count comes first
			else if (count > other.count)
				return -1;
			else
				return 1;
		}
	}

	public static int getSectorId(String roomId) {
		Matcher matcher = Pattern.compile("([0-9]+)").matcher(roomId);
		matcher.find();
		return Integer.parseInt(matcher.group(1));
	}

	public static String recalculateChecksum(String input) {
		int checksumLocation = input.indexOf('[');
		if (checksumLocation >= 0) {
			input = input.substring(0, checksumLocation);
		}
		return input + '[' + toChecksumString(getSortedCharacterCounts(input)) + ']';
	}

	public static boolean isValueRoomId(String id) {
		return recalculateChecksum(id).equals(id);
	}

	public static void main(String[] args) throws IOException {
		int sumOfSectorIds = 0;
		for (var roomId : Files.readAllLines(Paths.get("src/main/resources/day4.txt"))) {
			if (isValueRoomId(roomId)) {
				sumOfSectorIds += getSectorId(roomId);
				String decryptRoomId = decryptRoomId(roomId);
				if (decryptRoomId.contains("pole"))
					System.out.println("Day4 part 2:" + decryptRoomId + " sector id " + getSectorId(roomId));
			}
		}
		System.out.println("Day4 part 1: num valid room ids = " + sumOfSectorIds);
	}

	public static String decryptRoomId(String roomID) {
		int sectorId = getSectorId(roomID);
		StringBuffer out = new StringBuffer();
		for (var c : roomID.toCharArray()) {
			if (c == '-') {
				out.append(' ');
			} else if (c >= 'a' && c <= 'z') {
				char plainC = (char) (((c - 'a') + sectorId) % 26 + 'a');
				out.append(plainC);
			} else
				break;
		}
		return out.toString().stripTrailing();
	}

	private static String toChecksumString(ArrayList<LetterCount> counts) {
		StringBuffer checksum = new StringBuffer();
		counts.stream().limit(5).forEach(c -> checksum.append(c.letter));
		return checksum.toString();
	}

	private static ArrayList<LetterCount> getSortedCharacterCounts(String string) {
		HashMap<Character, Integer> counts = new HashMap<>();
		for (char c : string.toCharArray()) {
			if (c < 'a' || c > 'z')
				continue;

			if (!counts.containsKey(c))
				counts.put(c, 0);
			counts.put(c, counts.get(c) + 1);
		}

		ArrayList<LetterCount> out = new ArrayList<>();
		counts.forEach((letter, count) -> out.add(new LetterCount(letter, count)));
		out.sort(LetterCount::compareTo);
		return out;
	}
}
