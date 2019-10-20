package adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9 {
	public static void main(String[] args) throws IOException {
		System.out.println(decompress("ADVENT"));
		System.out.println(decompress("A(1x5)BC"));
		System.out.println(decompress("(3x3)XYZ"));
		System.out.println(decompress("(3x3)XYZ "));
		System.out.println(decompress("X(8x2)(3x3)ABCY"));

		String input = Files
				.readString(Paths.get("src/main/resources/day9.txt"));
		System.out.println("Part 1:" + decompress(input).length());
		System.out.println("Part 2:" + decompressLen(input));
	}

	public static long decompressLen(String string) {
		Pattern metaPattern = Pattern.compile("([0-9]+)x([0-9]+)");
		long outLen = 0;
		int i = 0;
		while (i < string.length()) {
			if (string.charAt(i) == '(') {
				int metaEnd = string.indexOf(')', i);
				Matcher matcher = metaPattern
						.matcher(string.substring(i + 1, metaEnd));
				matcher.matches();
				int len = Integer.parseInt(matcher.group(1));
				int repeat = Integer.parseInt(matcher.group(2));
				String toBeRepeated = string.substring(metaEnd + 1,
						metaEnd + 1 + len);
				outLen += decompressLen(toBeRepeated) * repeat;
				i = metaEnd + 1 + len;
			} else if (Character.isWhitespace(string.charAt(i))) {
				i++;
			} else {
				outLen++;
				i++;
			}
		}
		return outLen;
	}

	private static String decompress(String string) {
		Pattern metaPattern = Pattern.compile("([0-9]+)x([0-9]+)");
		StringBuffer out = new StringBuffer();
		int i = 0;
		while (i < string.length()) {
			if (string.charAt(i) == '(') {
				int metaEnd = string.indexOf(')', i);
				Matcher matcher = metaPattern
						.matcher(string.substring(i + 1, metaEnd));
				matcher.matches();
				int len = Integer.parseInt(matcher.group(1));
				int repeat = Integer.parseInt(matcher.group(2));
				String toBeRepeated = string.substring(metaEnd + 1,
						metaEnd + 1 + len);
				out.append(toBeRepeated.repeat(repeat));
				i = metaEnd + 1 + len;
			} else if (Character.isWhitespace(string.charAt(i))) {
				i++;
			} else {
				out.append(string.charAt(i++));
			}
		}
		return out.toString();
	}
}
