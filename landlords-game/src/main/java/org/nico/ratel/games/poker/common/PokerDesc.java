package org.nico.ratel.games.poker.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Poker level
 *
 * @author nico
 */
public enum PokerDesc {

	P_A( "A", new Character[]{'A', 'a', '1'},14),
	P_2( "2", new Character[]{'2'},15),
	P_3( "3", new Character[]{'3'},3),
	P_4( "4", new Character[]{'4'},4),
	P_5( "5", new Character[]{'5'},5),
	P_6( "6", new Character[]{'6'},6),
	P_7( "7", new Character[]{'7'},7),
	P_8( "8", new Character[]{'8'},8),
	P_9( "9", new Character[]{'9'},9),
	P_10("10", new Character[]{'T', 't', '0'},10),
	P_J( "J", new Character[]{'J', 'j'},11),
	P_Q( "Q", new Character[]{'Q', 'q'},12),
	P_K( "K", new Character[]{'K', 'k'},13),
	P_SMALL_KING("S", new Character[]{'S', 's'},16),
	P_BIG_KING( "X", new Character[]{'X', 'x'},17),
	;


	private String name;

	private Character[] alias;

	private int defaultLevel;

	private static Set<Character> aliasSet = new HashSet<>();

	static {
		for (PokerDesc level : PokerDesc.values()) {
			PokerDesc.aliasSet.addAll(Arrays.asList(level.getAlias()));
		}
	}

	PokerDesc(String name, Character[] alias,int defaultLevel) {
		this.name = name;
		this.alias = alias;
		this.defaultLevel=defaultLevel;
	}

	public static boolean aliasContains(char key) {
		return aliasSet.contains(key);
	}

	public final Character[] getAlias() {
		return alias;
	}

	public final void setAlias(Character[] alias) {
		this.alias = alias;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public int defaultLevel() {
		return defaultLevel;
	}


	public static final PokerDesc parseByName(String name) {
		if(name == null) {
			return null;
		}
		for(PokerDesc level: PokerDesc.values()) {
			if(level.name.equals(name.toUpperCase())) {
				return level;
			}
		}
		return null;
	}

}
