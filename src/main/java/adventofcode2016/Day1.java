package adventofcode2016;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day1 {
	public static final String INPUT = "L1, L5, R1, R3, L4, L5, R5, R1, L2, L2, L3, R4, L2, R3, R1, L2, R5, R3, L4, R4, L3, R3, R3, L2, R1, L3, R2, L1, R4, L2, R4, L4, R5, L3, R1, R1, L1, L3, L2, R1, R3, R2, L1, R4, L4, R2, L189, L4, R5, R3, L1, R47, R4, R1, R3, L3, L3, L2, R70, L1, R4, R185, R5, L4, L5, R4, L1, L4, R5, L3, R2, R3, L5, L3, R5, L1, R5, L4, R1, R2, L2, L5, L2, R4, L3, R5, R1, L5, L4, L3, R4, L3, L4, L1, L5, L5, R5, L5, L2, L1, L2, L4, L1, L2, R3, R1, R1, L2, L5, R2, L3, L5, L4, L2, L1, L2, R3, L1, L4, R3, R3, L2, R5, L1, L3, L3, L3, L5, R5, R1, R2, L3, L2, R4, R1, R1, R3, R4, R3, L3, R3, L5, R2, L2, R4, R5, L4, L3, L1, L5, L1, R1, R2, L1, R3, R4, R5, R2, R3, L2, L1, L5";

	enum Direction {
		north, south, east, west
	};

	public static class Position {
		public Position(Direction direction, int x, int y) {
			this.direction = direction;
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Position [direction=" + direction + ", x=" + x + ", y=" + y + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			return x == other.x && y == other.y;
		}

		int x = 0; // positive X is east of original direction
		int y = 0;// positive Y is north of original direction
		Direction direction = Direction.north;

		List<Position> moveThrough(char turn, int distance) {
			ArrayList<Position> moves = new ArrayList<>();
			switch (direction) {
			case north: {
				if (turn == 'L') {
					for (int i = 1; i <= distance; ++i) {
						moves.add(new Position(Direction.west, x - i, y));
					}
				} else {
					for (int i = 1; i <= distance; ++i) {
						moves.add(new Position(Direction.east, x + i, y));
					}
				}
				return moves;
			}
			case south:
				if (turn == 'R') {
					for (int i = 1; i <= distance; ++i) {
						moves.add(new Position(Direction.west, x - i, y));
					}
				} else {
					for (int i = 1; i <= distance; ++i) {
						moves.add(new Position(Direction.east, x + i, y));
					}
				}
				return moves;
			case east:
				if (turn == 'L') {
					for (int i = 1; i <= distance; ++i) {
						moves.add(new Position(Direction.north, x, y + i));
					}
				} else {
					for (int i = 1; i <= distance; ++i) {
						moves.add(new Position(Direction.south, x, y - i));
					}
				}
				return moves;
			case west:
				if (turn == 'R') {
					for (int i = 1; i <= distance; ++i) {
						moves.add(new Position(Direction.north, x, y + i));
					}
				} else {
					for (int i = 1; i <= distance; ++i) {
						moves.add(new Position(Direction.south, x, y - i));
					}
				}
				return moves;
			}

			throw new RuntimeException();
		}

		Position move(char turn, int distance) {
			Position next = new Position(Direction.north, 0, 0);
			switch (direction) {
			case north: {
				if (turn == 'L') {
					next.direction = Direction.west;
					next.x = x - distance;
					next.y = y;
				} else {
					next.direction = Direction.east;
					next.x = x + distance;
					next.y = y;
				}
				return next;
			}
			case south:
				if (turn == 'R') {
					next.direction = Direction.west;
					next.x = x - distance;
					next.y = y;
				} else {
					next.direction = Direction.east;
					next.x = x + distance;
					next.y = y;
				}
				return next;
			case east:
				if (turn == 'L') {
					next.direction = Direction.north;
					next.x = x;
					next.y = y + distance;
				} else {
					next.direction = Direction.south;
					next.x = x;
					next.y = y - distance;
				}
				return next;
			case west:
				if (turn == 'R') {
					next.direction = Direction.north;
					next.x = x;
					next.y = y + distance;
				} else {
					next.direction = Direction.south;
					next.x = x;
					next.y = y - distance;
				}
				return next;
			}

			throw new RuntimeException();
		}

		int blocksAway() {
			return Math.abs(x) + Math.abs(y);
		}
	}

	public static Position followDirections(String directions) {
		Position pos = new Position(Direction.north, 0, 0);
		String[] steps = directions.split(",", 0);
		for (String step : steps) {
			step = step.strip();
			pos = pos.move(step.charAt(0), Integer.parseInt(step.substring(1)));
		}
		return pos;
	}

	public static Position followDirectionsUntilDuplicate(String directions) {
		Set<Position> positions = new HashSet<>();
		Position pos = new Position(Direction.north, 0, 0);
		positions.add(pos);
		String[] steps = directions.split(",", 0);
		for (String step : steps) {
			step = step.strip();
			List<Position> nextSteps = pos.moveThrough(step.charAt(0), Integer.parseInt(step.substring(1)));
			pos = nextSteps.get(nextSteps.size() - 1);
			for (var nextStep : nextSteps) {
				if (positions.contains(nextStep))
					return nextStep;
				positions.add(nextStep);
			}
		}

		throw new RuntimeException("never it same position twice");
	}

	public static void main(String[] args) {
		System.out.println("Part 1: Blocks away = " + followDirections(INPUT).blocksAway());
		System.out.println("Part 2: Blocks away = " + followDirectionsUntilDuplicate(INPUT).blocksAway());
	}
}
