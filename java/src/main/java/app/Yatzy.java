package app;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

class DiceHand implements Iterable<Integer> {
	private final int[] dice;

	public DiceHand(int d1, int d2, int d3, int d4, int d5) {
		this.dice = new int[]{d1, d2, d3, d4, d5};
		if (stream().anyMatch(die -> die < 1 || die > 6)) {
			throw new IllegalArgumentException("Dice must be between 1 and 6");
		}
	}

	public Map<Integer, Long> getCountMap() {
		return stream()
			.collect(groupingBy(die -> die, counting()));
	}

	int sumOfDice(int dieValue) {
		return stream()
			.filter(n -> n == dieValue)
			.mapToInt(Integer::intValue)
			.sum();
	}

	public int getDie(int index) {
		return dice[index];
	}

	// Use iterator to enhance the for loop
	@Override
	public Iterator<Integer> iterator() {
		return stream().iterator();
	}

	public Stream<Integer> stream() {
		return IntStream.of(dice).boxed();
	}
}

public class Yatzy {

	protected int[] dice;

	public Yatzy(int d1, int d2, int d3, int d4, int d5) {
		dice = new int[5];
		dice[0] = d1;
		dice[1] = d2;
		dice[2] = d3;
		dice[3] = d4;
		dice[4] = d5;
	}

	public static int chance(DiceHand diceHand) {
		return diceHand.stream().mapToInt(Integer::intValue).sum();
	}

	public static int yatzy(DiceHand dice) {
//		if (dice.stream().allMatch(n -> n == dice.getDie(0))) {
//		if (dice.stream().distinct().count() == 1) {
		if (dice.stream().collect(toSet()).size() == 1) {
			return 50;
		}
		return 0;
	}

	public static int ones(DiceHand diceHand) {
		return diceHand.sumOfDice(1);
	}

	public static int twos(DiceHand diceHand) {
		return diceHand.sumOfDice(2);
	}

	public static int threes(DiceHand diceHand) {
		return diceHand.sumOfDice(3);
	}

	public static int fours(DiceHand diceHand) {
		return diceHand.sumOfDice(4);
	}

	public static int fives(DiceHand diceHand) {
		return diceHand.sumOfDice(5);
	}

	public static int sixes(DiceHand diceHand) {
		return diceHand.sumOfDice(6);
	}

	public static int scorePair(DiceHand diceHand) {
		// TODO try Collections.frequency();
		Map<Integer, Long> diceCounts = diceHand.getCountMap();
		OptionalInt maxDie = diceCounts.entrySet().stream()
			.filter(entry -> entry.getValue() >= 2)
			.mapToInt(Map.Entry::getKey)
			.max();
		return maxDie.orElse(0) * 2;
	}

	public static int twoPair(DiceHand diceHand) {
		Map<Integer, Long> diceCounts = diceHand.getCountMap();
		List<Integer> diceTwoOrMore = diceCounts.entrySet().stream()
			.filter(entry -> entry.getValue() >= 2)
			.map(Map.Entry::getKey)
			.collect(toList());
		if (diceTwoOrMore.size() >= 2) {
			return diceTwoOrMore.stream()
				.mapToInt(Integer::intValue)
				.sum() * 2;
		}
		return 0;
	}

	public static int threeOfAKind(DiceHand diceHand) {
		return nOfAKind(diceHand, 3);
	}

	public static int fourOfAKind(DiceHand diceHand) {
		return nOfAKind(diceHand, 4);
	}

	private static int nOfAKind(DiceHand diceHand, int n) {
		for (Map.Entry<Integer, Long> entry : diceHand.getCountMap().entrySet()) {
			if (entry.getValue() >= n) {
				return entry.getKey() * n;
			}
		}
		return 0;
	}

	public static int smallStraight(DiceHand diceHand) {
		List<Integer> sorted = diceHand.stream()
			.sorted()
			.collect(toList());
		if (Arrays.asList(1, 2, 3, 4, 5).equals(sorted)) {
			return 15;
		}
		return 0;
	}

	public static int largeStraight(DiceHand diceHand) {
		List<Integer> sorted = diceHand.stream()
			.sorted()
			.collect(toList());
		if (Arrays.asList(2, 3, 4, 5, 6).equals(sorted)) {
			return 20;
		}
		return 0;
	}

	public static int fullHouse(int d1, int d2, int d3, int d4, int d5) {
		int[] tallies;
		boolean _2 = false;
		int i;
		int _2_at = 0;
		boolean _3 = false;
		int _3_at = 0;


		tallies = new int[6];
		tallies[d1 - 1] += 1;
		tallies[d2 - 1] += 1;
		tallies[d3 - 1] += 1;
		tallies[d4 - 1] += 1;
		tallies[d5 - 1] += 1;

		for (i = 0; i != 6; i += 1)
			if (tallies[i] == 2) {
				_2 = true;
				_2_at = i + 1;
			}

		for (i = 0; i != 6; i += 1)
			if (tallies[i] == 3) {
				_3 = true;
				_3_at = i + 1;
			}

		if (_2 && _3) return _2_at * 2 + _3_at * 3;
		else return 0;
	}
}



