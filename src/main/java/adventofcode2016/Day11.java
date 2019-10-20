package adventofcode2016;

import static adventofcode2016.Day11.Item.curiumChip;
import static adventofcode2016.Day11.Item.curiumGenerator;
import static adventofcode2016.Day11.Item.dilithiumChip;
import static adventofcode2016.Day11.Item.dilithiumGenerator;
import static adventofcode2016.Day11.Item.eleriumChip;
import static adventofcode2016.Day11.Item.eleriumGenerator;
import static adventofcode2016.Day11.Item.plutoniumChip;
import static adventofcode2016.Day11.Item.plutoniumGenerator;
import static adventofcode2016.Day11.Item.rutheniumChip;
import static adventofcode2016.Day11.Item.rutheniumGenerator;
import static adventofcode2016.Day11.Item.strontiumChip;
import static adventofcode2016.Day11.Item.strontiumGenerator;
import static adventofcode2016.Day11.Item.thuliumChip;
import static adventofcode2016.Day11.Item.thuliumGenerator;
import static com.google.common.collect.Sets.combinations;
import static com.google.common.collect.Sets.intersection;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Sets;

public class Day11 {
	static HashSet<Item> setOf(Item... items) {
		HashSet<Item> set = new HashSet<Item>();
		for (var i : items)
			set.add(i);
		return set;
	}

	enum Item {
		strontiumGenerator, strontiumChip, plutoniumGenerator, plutoniumChip,
		thuliumGenerator, thuliumChip, curiumGenerator, curiumChip,
		rutheniumGenerator, rutheniumChip, eleriumChip, eleriumGenerator,
		dilithiumChip, dilithiumGenerator
	};

	static HashSet<HashSet<Item>> matchingItems = new HashSet<>();
	static HashSet<Item> generators = new HashSet<>();
	static HashSet<Item> chips = new HashSet<>();
	static {
		matchingItems.add(setOf(curiumChip, curiumGenerator));
		matchingItems.add(setOf(plutoniumChip, plutoniumGenerator));
		matchingItems.add(setOf(rutheniumChip, rutheniumGenerator));
		matchingItems.add(setOf(strontiumChip, strontiumGenerator));
		matchingItems.add(setOf(thuliumChip, thuliumGenerator));
		matchingItems.add(setOf(eleriumChip, eleriumGenerator));
		matchingItems.add(setOf(dilithiumChip, dilithiumGenerator));

		generators.add(curiumGenerator);
		generators.add(plutoniumGenerator);
		generators.add(rutheniumGenerator);
		generators.add(strontiumGenerator);
		generators.add(thuliumGenerator);
		generators.add(eleriumGenerator);
		generators.add(dilithiumGenerator);
		chips.add(curiumChip);
		chips.add(plutoniumChip);
		chips.add(rutheniumChip);
		chips.add(strontiumChip);
		chips.add(thuliumChip);
		chips.add(dilithiumChip);
		chips.add(eleriumChip);
	}

	static class State implements Comparable<State> {
		int elevatorFloor;
		ArrayList<Item> floor1 = new ArrayList<Item>();
		ArrayList<Item> floor2 = new ArrayList<Item>();
		ArrayList<Item> floor3 = new ArrayList<Item>();
		ArrayList<Item> floor4 = new ArrayList<Item>();
		private LinkedList<State> previousStates;

		public State(int elevatorFloor, ArrayList<Item> floor1,
				ArrayList<Item> floor2, ArrayList<Item> floor3,
				ArrayList<Item> floor4, LinkedList<State> previousStates) {
			this.elevatorFloor = elevatorFloor;
			this.floor1 = floor1;
			this.floor2 = floor2;
			this.floor3 = floor3;
			this.floor4 = floor4;
			this.previousStates = previousStates;
		}

		int numSteps() {
			return previousStates.size();
		}

		void printChain(PrintStream out) {
			previousStates.forEach(s -> {
				out.println(s.print());
				out.println();
			});
		}

		public String print() {
			StringWriter out = new StringWriter();
			for (int floor = 4; floor >= 1; --floor) {
				// @formatter:off
				out.append("" + floor);
				out.append(elevatorFloor == floor ? "E" : ".");
				out.append(floorItems.get(floor).contains(Item.curiumChip) ? "CC" : ". ");
				out.append(floorItems.get(floor).contains(Item.curiumGenerator) ? "CG" : ". ");
				out.append(floorItems.get(floor).contains(Item.plutoniumChip) ? "PC" : ". ");
				out.append(floorItems.get(floor).contains(Item.plutoniumGenerator) ? "PG" : ". ");
				out.append(floorItems.get(floor).contains(Item.rutheniumChip) ? "RC" : ". ");
				out.append(floorItems.get(floor).contains(Item.rutheniumGenerator) ? "RG" : ". ");
				out.append(floorItems.get(floor).contains(Item.strontiumChip) ? "SC" : ". ");
				out.append(floorItems.get(floor).contains(Item.strontiumGenerator) ? "SG" : ". ");
				out.append(floorItems.get(floor).contains(Item.thuliumChip) ? "TC" : ". ");
				out.append(floorItems.get(floor).contains(Item.thuliumGenerator) ? "TG" : ". ");
				out.append(floorItems.get(floor).contains(Item.eleriumChip) ? "EC" : ". ");
				out.append(floorItems.get(floor).contains(Item.eleriumGenerator) ? "EG" : ". ");
				out.append(floorItems.get(floor).contains(Item.dilithiumChip) ? "DC" : ". ");
				out.append(floorItems.get(floor).contains(Item.dilithiumGenerator) ? "DG" : ". ");
				// @formatter:on
				out.append("\n");
			}
			return out.toString();
		}

		boolean isValid() {
			for (HashSet<Item> itemsOnFloor : floorItems.values()) {
				HashSet<Item> temp = new HashSet<>(itemsOnFloor);

				// if a matching set exists, they safely cancel themselves out
				for (var matching : matchingItems) {
					if (temp.containsAll(matching))
						temp.removeAll(matching);
				}

				// if any generators remain and any chips remain, it's not valid
				if (!intersection(generators, temp).isEmpty()
						&& !intersection(chips, temp).isEmpty()) {
					return false;
				}
			}
			return true;
		}

		boolean isCompletePart() {
			return floorItems.get(1).isEmpty() && floorItems.get(2).isEmpty()
					&& floorItems.get(3).isEmpty();
		}

		ArrayList<State> moveUp(Solver solver) {
			ArrayList<State> moves = new ArrayList<>();
			if (elevatorFloor == 4) {
				// can't move up
				return moves;
			}

			// can carry either 1 or 2 items
			HashSet<Item> itemsOnCurrentFloor = floorItems.get(elevatorFloor);
			HashSet<Set<Item>> itemMoves = new HashSet<>();
			if (itemsOnCurrentFloor.size() >= 2)
				itemMoves.addAll(combinations(itemsOnCurrentFloor, 2));
			if (itemsOnCurrentFloor.size() >= 1)
				itemMoves.addAll(combinations(itemsOnCurrentFloor, 1));

			for (var itemMove : itemMoves) {
				// move items from current floor to floor above
				var floorAbove = new HashSet<Item>(
						floorItems.get(elevatorFloor + 1));
				var currentFloor = new HashSet<Item>(itemsOnCurrentFloor);
				floorAbove.addAll(itemMove);
				currentFloor.removeAll(itemMove);

				// create a new state with moved items
				HashMap<Integer, HashSet<Item>> nextFloorState = new HashMap<>(
						floorItems);
				nextFloorState.put(elevatorFloor, currentFloor);
				nextFloorState.put(elevatorFloor + 1, floorAbove);
				State move = new State(elevatorFloor + 1, nextFloorState,
						appendToPreviousStates(this));

				// only return it if it's new and valid
				if (move.isValid() && !solver.alreadySeen(move))
					moves.add(move);
			}

			return moves;
		}

		private LinkedList<State> appendToPreviousStates(State state) {
			LinkedList<State> states = new LinkedList<State>();
			states.addAll(previousStates);
			states.add(this);
			return states;
		}

		ArrayList<State> moveDown(Solver solver) {
			ArrayList<State> moves = new ArrayList<>();

			// can't move down
			if (elevatorFloor == 1) {
				return moves;
			}

			// can move one or two items
			HashSet<Item> itemsOnCurrentFloor = floorItems.get(elevatorFloor);
			HashSet<Set<Item>> itemMoves = new HashSet<>();
			if (itemsOnCurrentFloor.size() >= 2)
				itemMoves.addAll(Sets.combinations(itemsOnCurrentFloor, 2));
			if (itemsOnCurrentFloor.size() >= 1)
				itemMoves.addAll(Sets.combinations(itemsOnCurrentFloor, 1));
			for (var itemMove : itemMoves) {

				// update floor contents
				HashSet<Item> floorBelow = new HashSet<>(
						floorItems.get(elevatorFloor - 1));
				HashSet<Item> currentFloor = new HashSet<>(itemsOnCurrentFloor);
				floorBelow.addAll(itemMove);
				currentFloor.removeAll(itemMove);

				// create a new state with new elevator position and floor
				// contents, but only use it if its new and valid
				HashMap<Integer, HashSet<Item>> nextFloorState = new HashMap<>(
						floorItems);
				nextFloorState.put(elevatorFloor, currentFloor);
				nextFloorState.put(elevatorFloor - 1, floorBelow);
				State move = new State(elevatorFloor - 1, nextFloorState,
						appendToPreviousStates(this));
				if (move.isValid() && !solver.alreadySeen(move))
					moves.add(move);
			}

			return moves;
		}

		ArrayList<State> moves(Solver solver) {
			ArrayList<State> moves = moveUp(solver);
			moves.addAll(moveDown(solver));
			Collections.sort(moves);
			return moves;
		}

		@Override
		public int hashCode() {
			return Objects.hash(elevatorFloor, floorItems);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			State other = (State) obj;
			return elevatorFloor == other.elevatorFloor
					&& Objects.equals(floorItems, other.floorItems);
		}

		@Override
		public String toString() {
			return "State [elevatorFloor=" + elevatorFloor + ", floorItems="
					+ floorItems + "]";
		}

		@Override
		public int compareTo(State o) {
			return Integer.compare(o.score(), score());
		}

		private int score() {
			return floorItems.get(4).size() * 1000
					+ floorItems.get(3).size() * 100
					+ floorItems.get(2).size() * 10 + floorItems.get(1).size();
		}
	}

	static class Solver {
		public HashSet<State> seenMoves = new HashSet<>(500000);

		public boolean alreadySeen(State move) {
			if (seenMoves.contains(move)) {
				return true;
			}
			seenMoves.add(move);
			return false;
		}

		State solve(State startState) throws Exception {
			LinkedList<State> moves = new LinkedList<>();
			moves.add(startState);
			while (moves.size() > 0) {
				State test = moves.remove(0);
				if (test.isCompletePart()) {
					return test;
				}
				ArrayList<State> nextMoves = test.moves(this);
				moves.addAll(nextMoves);
			}

			throw new Exception("No solution found");
		}
	}

	public static void main(String[] args) throws Exception {
		HashMap<Integer, HashSet<Item>> floorContents = new HashMap<>();
		floorContents.put(1, setOf(strontiumChip, strontiumGenerator,
				plutoniumGenerator, plutoniumChip));
		floorContents.put(2, setOf(thuliumGenerator, rutheniumGenerator,
				rutheniumChip, curiumGenerator, curiumChip));
		floorContents.put(3, setOf(thuliumChip));
		floorContents.put(4, new HashSet<>());
		State startState = new State(1, floorContents, new LinkedList<>());

		var timer = Stopwatch.createStarted();
		State solution = new Solver().solve(startState);
		System.out.println("Part 1:" + solution.numSteps());
		System.out.println(timer.elapsed());

		floorContents.get(1).addAll(Set.of(dilithiumChip, dilithiumGenerator,
				eleriumChip, eleriumGenerator));
		timer = Stopwatch.createStarted();
		solution = new Solver().solve(startState);
		System.out.println("Part 2:" + solution.numSteps());
		System.out.println(timer.elapsed());
	}

}