package adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7 {
	public static boolean supportsTls(String ip) {
		List<String> sequences = getSequences(ip);
		List<String> hyperSequences = getHyperSequences(ip);
		return sequences.stream().anyMatch(Day7::containsAbba) && hyperSequences.stream().noneMatch(Day7::containsAbba);

	}

	public static boolean supportsSsl(String ip) {
		List<String> sequences = getSequences(ip);
		List<String> hyperSequences = getHyperSequences(ip);

		HashSet<String> allAbas = new HashSet<>();
		for (var s : sequences)
			allAbas.addAll(getAbas(s));

		for (String aba : allAbas) {
			String bab = "" + aba.charAt(1) + aba.charAt(0) + aba.charAt(1);
			for (String h : hyperSequences)
				if (h.contains(bab))
					return true;
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		List<String> ips = Files.readAllLines(Paths.get("src/main/resources/day7.txt"));
		System.out.println("Part 1:" + ips.stream().filter(Day7::supportsTls).count());
		System.out.println("Part 2:" + ips.stream().filter(Day7::supportsSsl).count());
	}

	private static List<String> getSequences(String string) {
		ArrayList<String> sequences = new ArrayList<>();
		int start = 0;
		int end = 0;
		while (true) {
			end = string.indexOf("[", start);
			if (end < 0) {
				sequences.add(string.substring(start));
				break;
			} else {
				sequences.add(string.substring(start, end));
				start = string.indexOf("]", end) + 1;
			}
		}
		return sequences;
	}

	private static List<String> getHyperSequences(String string) {
		ArrayList<String> sequences = new ArrayList<>();
		int start = string.indexOf("[");
		if (start < 0)
			return sequences;
		start++;
		int end = 0;
		while (true) {
			end = string.indexOf("]", start);
			sequences.add(string.substring(start, end));
			start = string.indexOf("[", end);
			if (start < 0)
				break;
			start++;
		}
		return sequences;
	}

	private static boolean containsAbba(String s) {
		for (int i = 0; i < s.length() - 3; ++i) {
			if (s.charAt(i) == s.charAt(i + 3) && s.charAt(i + 1) == s.charAt(i + 2) && s.charAt(i) != s.charAt(i + 1))
				return true;
		}
		return false;
	}

	private static Set<String> getAbas(String s) {
		HashSet<String> abas = new HashSet<>();
		for (int i = 0; i < s.length() - 2; ++i) {
			if (s.charAt(i) == s.charAt(i + 2) && s.charAt(i) != s.charAt(i + 1)) {
				abas.add(s.substring(i, i + 2));
			}
		}
		return abas;
	}

}
