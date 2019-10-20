package adventofcode2016;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day5 {
	private static MessageDigest digest;
	static {
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}
	}

	public static String md5(String s) {
		byte[] digestBytes = digest.digest(s.getBytes());
		StringBuffer out = new StringBuffer();
		for (byte b : digestBytes) {
			out.append(String.format("%02x", b));
		}
		return out.toString();
	}

	public static void main(String[] args) {
		//day1();
		day2();
	}

	private static void day2() {
		int found = 0;
		long i = 0;
		String hash;
		char[] combination = "________".toCharArray();
		while (found < 8) {
			hash = md5("wtnhxymk" + i);
			if (hash.startsWith("00000")) {
				int pos = hash.charAt(5) - '0';
				char digit = hash.charAt(6);
				if (pos < 8 && combination[pos] == '_') {
					combination[pos] = digit;
					found++;
					System.out.println(new String(combination) + " - " + i);
				}
			}
			i++;
		}
	}

	private static void day1() {
		String combination = "";
		String hash;
		int i = 0;
		while (combination.length() < 8) {
			hash = md5("wtnhxymk" + i);
			if (hash.startsWith("00000")) {
				combination += hash.charAt(5);
				System.out.println("combination = " + combination);
			}
			i++;
		}
	}
}
