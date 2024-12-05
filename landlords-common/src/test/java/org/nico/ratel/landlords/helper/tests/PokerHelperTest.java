package org.nico.ratel.landlords.helper.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.nico.ratel.games.poker.Poker;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuPokerSell;
import org.nico.ratel.games.poker.PokerDesc;
import org.nico.ratel.games.poker.PokerSuit;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuSellType;
import org.nico.ratel.commons.helper.PokerHelper;

import java.util.ArrayList;

public class PokerHelperTest {

	private ArrayList<Poker> pokers = new ArrayList<>();

	@Before
	public void setUp() {
		pokers.add(new Poker(PokerDesc.LEVEL_3, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_4, PokerSuit.DIAMOND));
		pokers.add(new Poker(PokerDesc.LEVEL_4, PokerSuit.DIAMOND));
		pokers.add(new Poker(PokerDesc.LEVEL_5, PokerSuit.CLUB));
		pokers.add(new Poker(PokerDesc.LEVEL_5, PokerSuit.CLUB));
		pokers.add(new Poker(PokerDesc.LEVEL_5, PokerSuit.CLUB));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.SPADE));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
	}

	@Test
	public void testComparePoker() {
		Assert.assertTrue(PokerHelper.comparePoker(new ArrayList<>(), new ArrayList<>()));
	}

	@Test
	public void testCheckPokerIndex() {
		Assert.assertFalse(PokerHelper.checkPokerIndex(new int[]{}, new ArrayList<>()));
		Assert.assertFalse(PokerHelper.checkPokerIndex(new int[]{2, -4_194_302}, new ArrayList<>()));
	}

	@Test
	public void testGetIndexes() {
		Assert.assertNull(PokerHelper.getIndexes(new Character[]{'3', '4', '5', '6', '7', '8'}, pokers));
		Assert.assertNotNull(PokerHelper.getIndexes(new Character[]{}, new ArrayList<>()));
		Assert.assertEquals(0, PokerHelper.getIndexes(new Character[]{}, new ArrayList<>()).length);
	}

	@Test
	public void testGetPoker() {
		Assert.assertEquals(PokerDesc.LEVEL_3, PokerHelper.getPoker(new int[]{1, 2}, pokers).get(0).getDesc());
		Assert.assertEquals(PokerSuit.BLANK, PokerHelper.getPoker(new int[]{1, 2}, pokers).get(0).getSuit());
		Assert.assertEquals(PokerDesc.LEVEL_4, PokerHelper.getPoker(new int[]{1, 2}, pokers).get(1).getDesc());
		Assert.assertEquals(PokerSuit.DIAMOND, PokerHelper.getPoker(new int[]{1, 2}, pokers).get(1).getSuit());
	}

	@Test
	public void testPrintPoker() {
		Assert.assertNotNull(PokerHelper.printPoker(pokers));
	}

	@Test
	public void testDistributePoker() {
		Assert.assertNotNull(PokerHelper.distributePoker());
	}

	@Test
	public void testCheckPokerType1() {
		pokers.clear();
		Assert.assertNull(PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(-1, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.ILLEGAL, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerDesc.LEVEL_SMALL_KING, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_BIG_KING, PokerSuit.DIAMOND));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(2147483647, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.KING_BOMB, PokerHelper.checkPokerType(pokers).getSellType());
	}

	@Test
	public void testCheckPokerType2() {
		pokers.clear();
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(7, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.SINGLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(7, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.DOUBLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(7, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.THREE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(1027, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.BOMB, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.remove(pokers.size() - 1);
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(7, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.THREE_ZONES_SINGLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(7, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.THREE_ZONES_DOUBLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_9, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_10, PokerSuit.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.THREE_STRAIGHT_WITH_SINGLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerDesc.LEVEL_9, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_10, PokerSuit.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.THREE_STRAIGHT_WITH_DOUBLE, PokerHelper.checkPokerType(pokers).getSellType());
	}

	@Test
	public void testCheckPokerType3() {
		pokers.clear();
		pokers.add(new Poker(PokerDesc.LEVEL_6, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_9, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_10, PokerSuit.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(10, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.SINGLE_STRAIGHT, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerDesc.LEVEL_6, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_9, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_10, PokerSuit.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(10, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.DOUBLE_STRAIGHT, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerDesc.LEVEL_6, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_9, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_10, PokerSuit.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(10, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.THREE_STRAIGHT, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerDesc.LEVEL_6, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_9, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_10, PokerSuit.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(10, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.FOUR_STRAIGHT, PokerHelper.checkPokerType(pokers).getSellType());
	}

	@Test
	public void testCheckPokerType4() {
		pokers.clear();
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_9, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_10, PokerSuit.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.FOUR_ZONES_SINGLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerDesc.LEVEL_5, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_6, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.FOUR_STRAIGHT_WITH_SINGLE, PokerHelper.checkPokerType(pokers).getSellType());
	}

	@Test
	public void testCheckPokerType5() {
		pokers.clear();
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_9, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_9, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_10, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_10, PokerSuit.BLANK));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.FOUR_ZONES_DOUBLE, PokerHelper.checkPokerType(pokers).getSellType());

		pokers.add(new Poker(PokerDesc.LEVEL_5, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_5, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_6, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_6, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.HEART));
		Assert.assertEquals(pokers, PokerHelper.checkPokerType(pokers).getSellPokers());
		Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.FOUR_STRAIGHT_WITH_DOUBLE, PokerHelper.checkPokerType(pokers).getSellType());
	}

	@Test
	public void testCheckPokerType6() {
		pokers.clear();
		pokers.add(new Poker(PokerDesc.LEVEL_6, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_6, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_6, PokerSuit.HEART));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_7, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_8, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_4, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_4, PokerSuit.BLANK));
		pokers.add(new Poker(PokerDesc.LEVEL_5, PokerSuit.BLANK));
		DouDiZhuPokerSell douDiZhuPokerSell = PokerHelper.checkPokerType(pokers);
		Assert.assertEquals(pokers, douDiZhuPokerSell.getSellPokers());
        Assert.assertEquals(8, PokerHelper.checkPokerType(pokers).getScore());
		Assert.assertEquals(DouDiZhuSellType.THREE_STRAIGHT_WITH_SINGLE, douDiZhuPokerSell.getSellType());
	}
}
