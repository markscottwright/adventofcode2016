package adventofcode2016;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {

	public static HashMap<Integer, Bot> makeBots(List<String> instructions) {
		HashMap<Integer, Bot> bots = new HashMap<>();
		Pattern botInstructionPattern = Pattern.compile(
				"bot ([0-9]+) gives low to (bot|output) ([0-9]+) and high to (bot|output) ([0-9]+)");
		for (var instruction : instructions) {
			Matcher matcher = botInstructionPattern.matcher(instruction);
			if (matcher.matches()) {
				int botId = Integer.parseInt(matcher.group(1));
				String lowOutputType = matcher.group(2);
				int lowIndex = Integer.parseInt(matcher.group(3));
				String highOutputType = matcher.group(4);
				int highIndex = Integer.parseInt(matcher.group(5));
				Bot bot = new Bot(botId);
				if (lowOutputType.equals("bot"))
					bot.lowBotDestination = lowIndex;
				else
					bot.lowOutputDestination = lowIndex;
				if (highOutputType.equals("bot"))
					bot.highBotDestination = highIndex;
				else
					bot.highOutputDestination = highIndex;
				bots.put(botId, bot);
			}
		}
		return bots;
	}

	static class Bot {
		private int botId;
		Integer value1 = null;
		Integer value2 = null;

		Integer lowOutputDestination = null;
		Integer lowBotDestination = null;
		Integer highOutputDestination = null;
		Integer highBotDestination = null;

		public Bot(int botId) {
			this.botId = botId;
		}

		boolean hasTwoChips() {
			return value1 != null && value2 != null;
		}

		void takeValue(Integer value, HashMap<Integer, Bot> bots,
				ArrayList<ArrayList<Integer>> outputBins) {
			if (value1 == null) {
				value1 = value;
			} else {
				value2 = value;
			}

			if (hasTwoChips()) {
				Integer min = Math.min(value1, value2);
				Integer max = Math.max(value1, value2);

				if (min == 17 && max == 61)
					System.out.println(this);
				value1 = value2 = null;
				if (highBotDestination != null) {
					bots.get(highBotDestination).takeValue(max, bots,
							outputBins);
				} else {
					ensureCapacity(highOutputDestination, outputBins);
					outputBins.get(highOutputDestination).add(max);
				}

				if (lowBotDestination != null) {
					bots.get(lowBotDestination).takeValue(min, bots,
							outputBins);
				} else {
					ensureCapacity(lowOutputDestination, outputBins);
					outputBins.get(lowOutputDestination).add(min);
				}
			}
		}

		private void ensureCapacity(Integer n,
				ArrayList<ArrayList<Integer>> outputBins) {
			if (outputBins.size() <= n) {
				for (int i = outputBins.size(); i <= n; ++i) {
					outputBins.add(new ArrayList<Integer>());
				}
			}
		}

		@Override
		public String toString() {
			return "Bot [botId=" + botId + ", "
					+ (value1 != null ? "value1=" + value1 + ", " : "")
					+ (value2 != null ? "value2=" + value2 + ", " : "")
					+ (lowOutputDestination != null
							? "lowOutputDestination=" + lowOutputDestination
									+ ", "
							: "")
					+ (lowBotDestination != null
							? "lowBotDestination=" + lowBotDestination + ", "
							: "")
					+ (highOutputDestination != null
							? "highOutputDestination=" + highOutputDestination
									+ ", "
							: "")
					+ (highBotDestination != null
							? "highBotDestination=" + highBotDestination
							: "")
					+ "]";
		}
	}

	public static void main(String[] args) throws IOException {
		List<String> instructions = Files
				.readAllLines(Paths.get("src/main/resources/day10.txt"));
		HashMap<Integer, Bot> bots = makeBots(instructions);
		// bots.values().forEach(System.out::println);

		ArrayList<ArrayList<Integer>> outputBins = new ArrayList<ArrayList<Integer>>();
		Pattern commandPattern = Pattern
				.compile("value ([0-9]+) goes to bot ([0-9]+)");
		for (String instruction : instructions) {
			Matcher matcher = commandPattern.matcher(instruction);
			if (matcher.matches()) {
				int value = Integer.parseInt(matcher.group(1));
				int botNum = Integer.parseInt(matcher.group(2));
				bots.get(botNum).takeValue(value, bots, outputBins);
			}
		}

		System.out.println(outputBins.get(0).get(0) * outputBins.get(1).get(0)
				* outputBins.get(2).get(0));
	}
}
