package org.nico.ratel.games.poker.doudizhu;

import org.nico.ratel.games.poker.common.Poker;
import org.nico.ratel.commons.helper.PokerHelper;

import java.util.List;

public class DouDiZhuPokerSell {

	private int score;

	private DouDiZhuSellType douDiZhuSellType;

	private List<Poker> sellPokers;

	private int coreLevel;

	public DouDiZhuPokerSell(DouDiZhuSellType douDiZhuSellType, List<Poker> sellPokers, int coreLevel) {
		this.score = PokerHelper.parseScore(douDiZhuSellType, coreLevel);
		this.douDiZhuSellType = douDiZhuSellType;
		this.sellPokers = sellPokers;
		this.coreLevel = coreLevel;
	}

	public final int getCoreLevel() {
		return coreLevel;
	}

	public final void setCoreLevel(int coreLevel) {
		this.coreLevel = coreLevel;
	}

	public final int getScore() {
		return score;
	}

	public final void setScore(int score) {
		this.score = score;
	}

	public final DouDiZhuSellType getSellType() {
		return douDiZhuSellType;
	}

	public final void setSellType(DouDiZhuSellType douDiZhuSellType) {
		this.douDiZhuSellType = douDiZhuSellType;
	}

	public final List<Poker> getSellPokers() {
		return sellPokers;
	}

	public final void setSellPokers(List<Poker> sellPokers) {
		this.sellPokers = sellPokers;
	}

	@Override
	public String toString() {
		return douDiZhuSellType + "\t| " + score + "\t|" + sellPokers;
	}

}
