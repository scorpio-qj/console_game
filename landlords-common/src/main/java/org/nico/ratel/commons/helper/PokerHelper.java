package org.nico.ratel.commons.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nico.ratel.commons.utils.ListUtils;

public class PokerHelper {

	/*
	//Print the type of poker style
	public static int pokerPrinterType = 0;

	public static int totalPrinters = 5;

	//The list of all pokers, by 54
	private static final List<Poker> basePokers = new ArrayList<>(54);

	private static final Comparator<Poker> pokerComparator = (o1, o2) -> o1.getDesc().getLevel() - o2.getDesc().getLevel();

	static {
		PokerDesc[] pokerDescs = PokerDesc.values();
		PokerSuit[] pokerSuits = PokerSuit.values();

		for (PokerDesc level : pokerDescs) {
			if (level == PokerDesc.LEVEL_BIG_KING) {
				basePokers.add(new Poker(level, PokerSuit.BLANK));
				continue;
			}
			if (level == PokerDesc.LEVEL_SMALL_KING) {
				basePokers.add(new Poker(level, PokerSuit.BLANK));
				continue;
			}
			for (PokerSuit type : pokerSuits) {
				if (type == PokerSuit.BLANK) {
					continue;
				}
				basePokers.add(new Poker(level, type));
			}
		}
	}

	public static void sortPoker(List<Poker> pokers) {
		pokers.sort(pokerComparator);
	}

	public static List<Poker> clonePokers(List<Poker> pokers){
		List<Poker> newPokers = new ArrayList<Poker>(pokers.size());
		for(Poker poker: pokers) {
			newPokers.add(new Poker(poker.getDesc(), poker.getSuit()));
		}
		return newPokers;
	}

	public static List<DouDiZhuPokerSell> validSells(DouDiZhuPokerSell lastDouDiZhuPokerSell, List<Poker> pokers) {
		List<DouDiZhuPokerSell> sells = PokerHelper.parsePokerSells(pokers);
		if(lastDouDiZhuPokerSell == null) {
			return sells;
		}

		List<DouDiZhuPokerSell> validSells = new ArrayList<DouDiZhuPokerSell>();
		for(DouDiZhuPokerSell sell: sells) {
			if(sell.getSellType() == lastDouDiZhuPokerSell.getSellType()) {
				if(sell.getScore() > lastDouDiZhuPokerSell.getScore() && sell.getSellPokers().size() == lastDouDiZhuPokerSell.getSellPokers().size()) {
					validSells.add(sell);
				}
			}
			if(sell.getSellType() == DouDiZhuSellType.KING_BOMB) {
				validSells.add(sell);
			}
		}
		if(lastDouDiZhuPokerSell.getSellType() != DouDiZhuSellType.BOMB) {
			for(DouDiZhuPokerSell sell: sells) {
				if(sell.getSellType() == DouDiZhuSellType.BOMB) {
					validSells.add(sell);
				}
			}
		}
		return validSells;
	}

	public static int[] getIndexes(Character[] options, List<Poker> pokers) {
		List<Poker> copyList = new ArrayList<>(pokers.size());
		copyList.addAll(pokers);
		int[] indexes = new int[options.length];
		for (int index = 0; index < options.length; index++) {
			char option = options[index];
			boolean isTarget = false;
			for (int pi = 0; pi < copyList.size(); pi++) {
				Poker poker = copyList.get(pi);
				if (poker == null) {
					continue;
				}
				if (Arrays.asList(poker.getDesc().getAlias()).contains(option)) {
					isTarget = true;
					//Index start from 1, not 0
					indexes[index] = pi + 1;
					copyList.set(pi, null);
					break;
				}
			}
			if (!isTarget) {
				return null;
			}
		}
		Arrays.sort(indexes);
		return indexes;
	}

	public static boolean checkPokerIndex(int[] indexes, List<Poker> pokers) {
		if (indexes == null || indexes.length == 0) {
			return false;
		}
		for (int index : indexes) {
			if (index > pokers.size() || index < 1) {
				return false;
			}
		}
		return true;
	}

	public static DouDiZhuPokerSell checkPokerType(List<Poker> pokers) {
		if (pokers == null || pokers.isEmpty()) {
			return new DouDiZhuPokerSell(DouDiZhuSellType.ILLEGAL, null, -1);
		}
		sortPoker(pokers);

		int[] levelTable = new int[20];
		for (Poker poker : pokers) {
			levelTable[poker.getDesc().getLevel()]++;
		}

		int startIndex = -1;
		int endIndex = -1;
		int count = 0;

		int singleCount = 0;
		int doubleCount = 0;
		int threeCount = 0;
		int threeStartIndex = -1;
		int threeEndIndex = -1;
		int fourCount = 0;
		int fourStartIndex = -1;
		int fourEndIndex = -1;
		for (int index = 0; index < levelTable.length; index++) {
			int value = levelTable[index];
			if (value == 0) {
				continue;
			}
			endIndex = index;
			count++;
			if (startIndex == -1) {
				startIndex = index;
			}
			if (value == 1) {
				singleCount++;
			} else if (value == 2) {
				doubleCount++;
			} else if (value == 3) {
				if (threeStartIndex == -1) {
					threeStartIndex = index;
				}
				threeEndIndex = index;
				threeCount++;
			} else if (value == 4) {
				if (fourStartIndex == -1) {
					fourStartIndex = index;
				}
				fourEndIndex = index;
				fourCount++;
			}
		}

		if (singleCount == doubleCount && singleCount == threeCount && singleCount == 0 && fourCount == 1) {
			return new DouDiZhuPokerSell(DouDiZhuSellType.BOMB, pokers, startIndex);
		}

		if (singleCount == 2 && startIndex == PokerDesc.LEVEL_SMALL_KING.getLevel() && endIndex == PokerDesc.LEVEL_BIG_KING.getLevel()) {
			return new DouDiZhuPokerSell(DouDiZhuSellType.KING_BOMB, pokers, PokerDesc.LEVEL_SMALL_KING.getLevel());
		}

		if (startIndex == endIndex) {
			if (levelTable[startIndex] == 1) {
				return new DouDiZhuPokerSell(DouDiZhuSellType.SINGLE, pokers, startIndex);
			} else if (levelTable[startIndex] == 2) {
				return new DouDiZhuPokerSell(DouDiZhuSellType.DOUBLE, pokers, startIndex);
			} else if (levelTable[startIndex] == 3) {
				return new DouDiZhuPokerSell(DouDiZhuSellType.THREE, pokers, startIndex);
			}
		}
		if (endIndex - startIndex == count - 1 && endIndex < PokerDesc.LEVEL_2.getLevel()) {
			if (levelTable[startIndex] == 1 && singleCount > 4 && doubleCount + threeCount + fourCount == 0) {
				return new DouDiZhuPokerSell(DouDiZhuSellType.SINGLE_STRAIGHT, pokers, endIndex);
			} else if (levelTable[startIndex] == 2 && doubleCount > 2 && singleCount + threeCount + fourCount == 0) {
				return new DouDiZhuPokerSell(DouDiZhuSellType.DOUBLE_STRAIGHT, pokers, endIndex);
			} else if (levelTable[startIndex] == 3 && threeCount > 1 && doubleCount + singleCount + fourCount == 0) {
				return new DouDiZhuPokerSell(DouDiZhuSellType.THREE_STRAIGHT, pokers, endIndex);
			} else if (levelTable[startIndex] == 4 && fourCount > 1 && doubleCount + threeCount + singleCount == 0) {
				return new DouDiZhuPokerSell(DouDiZhuSellType.FOUR_STRAIGHT, pokers, endIndex);
			}
		}

		if (threeCount != 0) {
			if (singleCount != 0 && singleCount == threeCount && doubleCount == 0 && fourCount == 0) {
				if (threeCount == 1) {
					return new DouDiZhuPokerSell(DouDiZhuSellType.THREE_ZONES_SINGLE, pokers, threeEndIndex);
				}
				if (threeEndIndex - threeStartIndex + 1 == threeCount && threeEndIndex < PokerDesc.LEVEL_2.getLevel()) {
					return new DouDiZhuPokerSell(DouDiZhuSellType.THREE_STRAIGHT_WITH_SINGLE, pokers, threeEndIndex);
				}
			} else if (doubleCount != 0 && doubleCount == threeCount && singleCount == 0 && fourCount == 0) {
				if (threeCount == 1) {
					return new DouDiZhuPokerSell(DouDiZhuSellType.THREE_ZONES_DOUBLE, pokers, threeEndIndex);
				}
				if (threeEndIndex - threeStartIndex + 1 == threeCount && threeEndIndex < PokerDesc.LEVEL_2.getLevel()) {
					return new DouDiZhuPokerSell(DouDiZhuSellType.THREE_STRAIGHT_WITH_DOUBLE, pokers, threeEndIndex);
				}
			} else if (singleCount + doubleCount * 2 == threeCount && fourCount == 0) {
				return new DouDiZhuPokerSell(DouDiZhuSellType.THREE_STRAIGHT_WITH_SINGLE, pokers, threeEndIndex);
			}
		}

		if (fourCount != 0) {
			if (singleCount != 0 && singleCount == fourCount * 2 && doubleCount == 0 && threeCount == 0) {
				if (fourCount == 1) {
					return new DouDiZhuPokerSell(DouDiZhuSellType.FOUR_ZONES_SINGLE, pokers, fourEndIndex);
				}
				if (fourEndIndex - fourStartIndex + 1 == fourCount && fourEndIndex < PokerDesc.LEVEL_2.getLevel()) {
					return new DouDiZhuPokerSell(DouDiZhuSellType.FOUR_STRAIGHT_WITH_SINGLE, pokers, fourEndIndex);
				}
			} else if (doubleCount != 0 && doubleCount == fourCount * 2 && singleCount == 0 && threeCount == 0) {
				if (fourCount == 1) {
					return new DouDiZhuPokerSell(DouDiZhuSellType.FOUR_ZONES_DOUBLE, pokers, fourEndIndex);
				}
				if (fourEndIndex - fourStartIndex + 1 == fourCount && fourEndIndex < PokerDesc.LEVEL_2.getLevel()) {
					return new DouDiZhuPokerSell(DouDiZhuSellType.FOUR_STRAIGHT_WITH_DOUBLE, pokers, fourEndIndex);
				}
			}
		}
		return new DouDiZhuPokerSell(DouDiZhuSellType.ILLEGAL, null, -1);
	}

	public static int parseScore(DouDiZhuSellType douDiZhuSellType, int level) {
		if (douDiZhuSellType == DouDiZhuSellType.BOMB) {
			return level * 4 + 999;
		} else if (douDiZhuSellType == DouDiZhuSellType.KING_BOMB) {
			return Integer.MAX_VALUE;
		} else if (douDiZhuSellType == DouDiZhuSellType.SINGLE || douDiZhuSellType == DouDiZhuSellType.DOUBLE || douDiZhuSellType == DouDiZhuSellType.THREE) {
			return level;
		} else if (douDiZhuSellType == DouDiZhuSellType.SINGLE_STRAIGHT || douDiZhuSellType == DouDiZhuSellType.DOUBLE_STRAIGHT || douDiZhuSellType == DouDiZhuSellType.THREE_STRAIGHT || douDiZhuSellType == DouDiZhuSellType.FOUR_STRAIGHT) {
			return level;
		} else if (douDiZhuSellType == DouDiZhuSellType.THREE_ZONES_SINGLE || douDiZhuSellType == DouDiZhuSellType.THREE_STRAIGHT_WITH_SINGLE || douDiZhuSellType == DouDiZhuSellType.THREE_ZONES_DOUBLE || douDiZhuSellType == DouDiZhuSellType.THREE_STRAIGHT_WITH_DOUBLE) {
			return level;
		} else if (douDiZhuSellType == DouDiZhuSellType.FOUR_ZONES_SINGLE || douDiZhuSellType == DouDiZhuSellType.FOUR_STRAIGHT_WITH_SINGLE || douDiZhuSellType == DouDiZhuSellType.FOUR_ZONES_DOUBLE || douDiZhuSellType == DouDiZhuSellType.FOUR_STRAIGHT_WITH_DOUBLE) {
			return level;
		}
		return -1;
	}

	public static List<Poker> getPoker(int[] indexes, List<Poker> pokers) {
		List<Poker> resultPokers = new ArrayList<>(indexes.length);
		for (int index : indexes) {
			resultPokers.add(pokers.get(index - 1));
		}
		sortPoker(resultPokers);
		return resultPokers;
	}

	public static boolean comparePoker(List<Poker> pres, List<Poker> currents) {

		return true;
	}

	public static List<List<Poker>> distributePoker() {
		Collections.shuffle(basePokers);
		List<List<Poker>> pokersList = new ArrayList<List<Poker>>();
		List<Poker> pokers1 = new ArrayList<>(17);
		pokers1.addAll(basePokers.subList(0, 17));
		List<Poker> pokers2 = new ArrayList<>(17);
		pokers2.addAll(basePokers.subList(17, 34));
		List<Poker> pokers3 = new ArrayList<>(17);
		pokers3.addAll(basePokers.subList(34, 51));
		List<Poker> pokers4 = new ArrayList<>(3);
		pokers4.addAll(basePokers.subList(51, 54));
		pokersList.add(pokers1);
		pokersList.add(pokers2);
		pokersList.add(pokers3);
		pokersList.add(pokers4);
		for (List<Poker> pokers : pokersList) {
			sortPoker(pokers);
		}
		return pokersList;
	}

	public static String printPoker(List<Poker> pokers) {
		sortPoker(pokers);
		switch (pokerPrinterType) {
			case 0:
				return buildHandStringSharp(pokers);
			case 1:
				return buildHandStringRounded(pokers);
			case 2:
				return textOnly(pokers);
			case 3:
				return textOnlyNoType(pokers);
			default:
				return buildHandStringSharp(pokers);

		}

	}

	private static String buildHandStringSharp(List<Poker> pokers) {
		StringBuilder builder = new StringBuilder();
		if (pokers != null && pokers.size() > 0) {

			for (int index = 0; index < pokers.size(); index++) {
				if (index == 0) {
					builder.append("┌──┐");
				} else {
					builder.append("──┐");
				}
			}
			builder.append(System.lineSeparator());
			for (int index = 0; index < pokers.size(); index++) {
				if (index == 0) {
					builder.append("│");
				}
				String name = pokers.get(index).getDesc().getName();
				builder.append(name).append(name.length() == 1 ? " " : "").append("|");
			}
			builder.append(System.lineSeparator());
			for (int index = 0; index < pokers.size(); index++) {
				if (index == 0) {
					builder.append("│");
				}
				builder.append(pokers.get(index).getSuit().getName()).append(" |");
			}
			builder.append(System.lineSeparator());
			for (int index = 0; index < pokers.size(); index++) {
				if (index == 0) {
					builder.append("└──┘");
				} else {
					builder.append("──┘");
				}
			}
		}
		return builder.toString();
	}

	private static String buildHandStringRounded(List<Poker> pokers) {
		StringBuilder builder = new StringBuilder();
		if (pokers != null && pokers.size() > 0) {

			for (int index = 0; index < pokers.size(); index++) {
				if (index == 0) {
					builder.append("┌──╮");
				} else {
					builder.append("──╮");
				}
			}
			builder.append(System.lineSeparator());
			for (int index = 0; index < pokers.size(); index++) {
				if (index == 0) {
					builder.append("│");
				}
				String name = pokers.get(index).getDesc().getName();
				builder.append(name).append(name.length() == 1 ? " " : "").append("|");
			}
			builder.append(System.lineSeparator());
			for (int index = 0; index < pokers.size(); index++) {
				if (index == 0) {
					builder.append("│");
				}
				builder.append(pokers.get(index).getSuit().getName()).append(" |");
			}
			builder.append(System.lineSeparator());
			for (int index = 0; index < pokers.size(); index++) {
				if (index == 0) {
					builder.append("└──╯");
				} else {
					builder.append("──╯");
				}
			}
		}
		return builder.toString();
	}

	private static String textOnly(List<Poker> pokers) {
		StringBuilder builder = new StringBuilder();
		if (pokers != null && pokers.size() > 0) {
			for (Poker poker : pokers) {
				String name = poker.getDesc().getName();
				String type = poker.getSuit().getName();

				builder.append(name).append(type);
			}
		}
		return builder.toString();
	}

	public static String textOnlyNoType(List<Poker> pokers) {
		StringBuilder builder = new StringBuilder();
		if (pokers != null && pokers.size() > 0) {
			for (Poker poker : pokers) {
				String name = poker.getDesc().getName();
				builder.append(name).append(" ");
			}
		}
		return builder.toString();
	}

	public static int parsePokerColligationScore(List<Poker> pokers) {
		int score = 0;
		int count = 0;
		int increase = 0;
		int lastLevel = -1;
		if (pokers != null && !pokers.isEmpty()) {
			for (int index = 0; index < pokers.size(); index++) {
				int level = pokers.get(index).getDesc().getLevel();
				if (lastLevel == -1) {
					increase++;
					count++;
					score += lastLevel;
				} else {
					if (level == lastLevel) {
						++count;
					} else {
						count = 1;
					}
					if (level < PokerDesc.LEVEL_2.getLevel() && level - 1 == lastLevel) {
						++increase;
					} else {
						increase = 1;
					}

					score += (count + (increase > 4 ? increase : 0)) * level;
				}

				if (level == PokerDesc.LEVEL_2.getLevel()) {
					score += level * 2;
				} else if (level > PokerDesc.LEVEL_2.getLevel()) {
					score += level * 3;
				}
				lastLevel = level;
			}
		}
		return score;
	}

	public static List<DouDiZhuPokerSell> parsePokerSells(List<Poker> pokers) {
		List<DouDiZhuPokerSell> douDiZhuPokerSells = new ArrayList<>();
		int size = pokers.size();

		//all single or double
		{
			int count = 0;
			int lastLevel = -1;
			List<Poker> sellPokers = new ArrayList<>(4);
			for (Poker poker : pokers) {
				int level = poker.getDesc().getLevel();
				if (lastLevel == -1) {
					++count;
				} else {
					if (level == lastLevel) {
						++count;
					} else {
						count = 1;
						sellPokers.clear();
					}
				}
				sellPokers.add(poker);
				if (count == 1) {
					douDiZhuPokerSells.add(new DouDiZhuPokerSell(DouDiZhuSellType.SINGLE, ListUtils.getList(sellPokers), poker.getDesc().getLevel()));
				} else if (count == 2) {
					douDiZhuPokerSells.add(new DouDiZhuPokerSell(DouDiZhuSellType.DOUBLE, ListUtils.getList(sellPokers), poker.getDesc().getLevel()));
				} else if (count == 3) {
					douDiZhuPokerSells.add(new DouDiZhuPokerSell(DouDiZhuSellType.THREE, ListUtils.getList(sellPokers), poker.getDesc().getLevel()));
				} else if (count == 4) {
					douDiZhuPokerSells.add(new DouDiZhuPokerSell(DouDiZhuSellType.BOMB, ListUtils.getList(sellPokers), poker.getDesc().getLevel()));
				}

				lastLevel = level;
			}
		}
		//Shunzi
		{
			parsePokerSellStraight(douDiZhuPokerSells, DouDiZhuSellType.SINGLE);
			parsePokerSellStraight(douDiZhuPokerSells, DouDiZhuSellType.DOUBLE);
			parsePokerSellStraight(douDiZhuPokerSells, DouDiZhuSellType.THREE);
			parsePokerSellStraight(douDiZhuPokerSells, DouDiZhuSellType.BOMB);
		}

		//Shunzi with args
		{
			for (int index = 0; index < douDiZhuPokerSells.size(); index++) {
				DouDiZhuPokerSell sell = douDiZhuPokerSells.get(index);
				if (sell.getSellType() == DouDiZhuSellType.THREE) {
					parseArgs(douDiZhuPokerSells, sell, 1, DouDiZhuSellType.SINGLE, DouDiZhuSellType.THREE_ZONES_SINGLE);
					parseArgs(douDiZhuPokerSells, sell, 1, DouDiZhuSellType.DOUBLE, DouDiZhuSellType.THREE_ZONES_DOUBLE);
				} else if (sell.getSellType() == DouDiZhuSellType.BOMB) {
					parseArgs(douDiZhuPokerSells, sell, 2, DouDiZhuSellType.SINGLE, DouDiZhuSellType.FOUR_ZONES_SINGLE);
					parseArgs(douDiZhuPokerSells, sell, 2, DouDiZhuSellType.DOUBLE, DouDiZhuSellType.FOUR_ZONES_DOUBLE);
				} else if (sell.getSellType() == DouDiZhuSellType.THREE_STRAIGHT) {
					int count = sell.getSellPokers().size() / 3;
					parseArgs(douDiZhuPokerSells, sell, count, DouDiZhuSellType.SINGLE, DouDiZhuSellType.THREE_STRAIGHT_WITH_SINGLE);
					parseArgs(douDiZhuPokerSells, sell, count, DouDiZhuSellType.DOUBLE, DouDiZhuSellType.THREE_STRAIGHT_WITH_DOUBLE);
				} else if (sell.getSellType() == DouDiZhuSellType.FOUR_STRAIGHT) {
					int count = (sell.getSellPokers().size() / 4) * 2;
					parseArgs(douDiZhuPokerSells, sell, count, DouDiZhuSellType.SINGLE, DouDiZhuSellType.FOUR_STRAIGHT_WITH_SINGLE);
					parseArgs(douDiZhuPokerSells, sell, count, DouDiZhuSellType.DOUBLE, DouDiZhuSellType.FOUR_STRAIGHT_WITH_DOUBLE);
				}
			}
		}

		//king boom
		{
			if (size > 1) {
				if (pokers.get(size - 1).getDesc() == PokerDesc.LEVEL_BIG_KING && pokers.get(size - 2).getDesc() == PokerDesc.LEVEL_SMALL_KING) {
					douDiZhuPokerSells.add(new DouDiZhuPokerSell(DouDiZhuSellType.KING_BOMB, ListUtils.getList(new Poker[]{pokers.get(size - 2), pokers.get(size - 1)}), PokerDesc.LEVEL_BIG_KING.getLevel()));
				}
			}
		}

		return douDiZhuPokerSells;
	}

	private static void parseArgs(List<DouDiZhuPokerSell> douDiZhuPokerSells, DouDiZhuPokerSell douDiZhuPokerSell, int deep, DouDiZhuSellType douDiZhuSellType, DouDiZhuSellType targetDouDiZhuSellType) {
		Set<Integer> existLevelSet = new HashSet<>();
		for (Poker p : douDiZhuPokerSell.getSellPokers()) {
			existLevelSet.add(p.getDesc().getLevel());
		}
		parseArgs(existLevelSet, douDiZhuPokerSells, new HashSet<>(), douDiZhuPokerSell, deep, douDiZhuSellType, targetDouDiZhuSellType);
	}

	private static void parseArgs(Set<Integer> existLevelSet, List<DouDiZhuPokerSell> douDiZhuPokerSells, Set<List<Poker>> pokersList, DouDiZhuPokerSell douDiZhuPokerSell, int deep, DouDiZhuSellType douDiZhuSellType, DouDiZhuSellType targetDouDiZhuSellType) {
		if (deep == 0) {
			List<Poker> allPokers = new ArrayList<>(douDiZhuPokerSell.getSellPokers());
			for (List<Poker> ps : pokersList) {
				allPokers.addAll(ps);
			}
			douDiZhuPokerSells.add(new DouDiZhuPokerSell(targetDouDiZhuSellType, allPokers, douDiZhuPokerSell.getCoreLevel()));
			return;
		}

		for (int index = 0; index < douDiZhuPokerSells.size(); index++) {
			DouDiZhuPokerSell subSell = douDiZhuPokerSells.get(index);
			if (subSell.getSellType() == douDiZhuSellType && !existLevelSet.contains(subSell.getCoreLevel())) {
				pokersList.add(subSell.getSellPokers());
				existLevelSet.add(subSell.getCoreLevel());
				parseArgs(existLevelSet, douDiZhuPokerSells, pokersList, douDiZhuPokerSell, deep - 1, douDiZhuSellType, targetDouDiZhuSellType);
				existLevelSet.remove(subSell.getCoreLevel());
				pokersList.remove(subSell.getSellPokers());
			}
		}
	}

	private static void parsePokerSellStraight(List<DouDiZhuPokerSell> douDiZhuPokerSells, DouDiZhuSellType douDiZhuSellType) {
		int minLength = -1;
		int width = -1;
		DouDiZhuSellType targetDouDiZhuSellType = null;
		if (douDiZhuSellType == DouDiZhuSellType.SINGLE) {
			minLength = 5;
			width = 1;
			targetDouDiZhuSellType = DouDiZhuSellType.SINGLE_STRAIGHT;
		} else if (douDiZhuSellType == DouDiZhuSellType.DOUBLE) {
			minLength = 3;
			width = 2;
			targetDouDiZhuSellType = DouDiZhuSellType.DOUBLE_STRAIGHT;
		} else if (douDiZhuSellType == DouDiZhuSellType.THREE) {
			minLength = 2;
			width = 3;
			targetDouDiZhuSellType = DouDiZhuSellType.THREE_STRAIGHT;
		} else if (douDiZhuSellType == DouDiZhuSellType.BOMB) {
			minLength = 2;
			width = 4;
			targetDouDiZhuSellType = DouDiZhuSellType.FOUR_STRAIGHT;
		}

		int increase_1 = 0;
		int lastLevel_1 = -1;
		List<Poker> sellPokers_1 = new ArrayList<>(4);
		for (int index = 0; index < douDiZhuPokerSells.size(); index++) {
			DouDiZhuPokerSell sell = douDiZhuPokerSells.get(index);

			if (sell.getSellType() != douDiZhuSellType) {
				continue;
			}
			int level = sell.getCoreLevel();
			if (lastLevel_1 == -1) {
				++increase_1;
			} else {
				if (level - 1 == lastLevel_1 && level != PokerDesc.LEVEL_2.getLevel()) {
					++increase_1;
				} else {
					addPokers(douDiZhuPokerSells, minLength, width, targetDouDiZhuSellType, increase_1, sellPokers_1);

					increase_1 = 1;
				}
			}
			sellPokers_1.addAll(sell.getSellPokers());
			lastLevel_1 = level;
		}
		addPokers(douDiZhuPokerSells, minLength, width, targetDouDiZhuSellType, increase_1, sellPokers_1);
	}

	private static void addPokers(List<DouDiZhuPokerSell> douDiZhuPokerSells, int minLenght, int width, DouDiZhuSellType targetDouDiZhuSellType, int increase_1, List<Poker> sellPokers_1) {
		if (increase_1 >= minLenght) {
			for (int s = 0; s <= increase_1 - minLenght; s++) {
				int len = minLenght + s;
				for (int subIndex = 0; subIndex <= increase_1 - len; subIndex++) {
					List<Poker> pokers = ListUtils.getList(sellPokers_1.subList(subIndex * width, (subIndex + len) * width));
					douDiZhuPokerSells.add(new DouDiZhuPokerSell(targetDouDiZhuSellType, pokers, pokers.get(pokers.size() - 1).getDesc().getLevel()));
				}
			}
		}
		sellPokers_1.clear();
	}
	*/

}
