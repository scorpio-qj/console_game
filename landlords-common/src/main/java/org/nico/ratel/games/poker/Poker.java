package org.nico.ratel.games.poker;

/**
 * Poke, with {@link PokerDesc} and {@link PokerSuit}
 *
 * @author nico
 */
public class Poker implements Comparable<Poker>{

	private PokerDesc desc;

	private PokerSuit suit;

	private int level;


	public Poker() {
	}

	public Poker(PokerDesc desc, PokerSuit suit) {
		this.desc = desc;
		this.suit = suit;
		level=createLevel(desc);
	}


	public int createLevel(PokerDesc desc){
		return desc.defaultLevel();
	}


	public final PokerDesc getDesc() {
		return desc;
	}

	public final PokerSuit getSuit() {
		return suit;
	}

	public int getLevel() { return level;}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + level;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poker other = (Poker) obj;
		if (level != other.level)
			return false;
		return suit == other.suit;
	}

	@Override
	public String toString() {
		return "suit: "+ suit.getName() + " name: "+ desc.getName()+" level: "+level;
	}

	@Override
	public int compareTo(Poker o) {

		if(this.level<o.getLevel())
			return -1;
		else if(this.level==o.getLevel())
			return 0;
		else
			return 1;

	}
}
