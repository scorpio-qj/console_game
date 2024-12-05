package org.nico.ratel.games.poker;

/**
 * Poker type Spade、 Heart、 Diamond、 Club
 *
 * @author nico
 */
public enum PokerSuit {

	BLANK(" "),

	DIAMOND("♦"),

	CLUB("♣"),

	SPADE("♠"),

	HEART("♥");

	private final String name;

	PokerSuit(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}
}
