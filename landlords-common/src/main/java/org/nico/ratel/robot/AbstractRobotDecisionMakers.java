package org.nico.ratel.robot;

import java.util.List;

import org.nico.ratel.clientactor.ClientSide;
import org.nico.ratel.games.poker.Poker;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuPokerSell;

/** 
 * 
 * @author nico
 * @version createTime：2018年11月15日 上午12:12:15
 */

public abstract class AbstractRobotDecisionMakers {

	public abstract DouDiZhuPokerSell howToPlayPokers(DouDiZhuPokerSell lastDouDiZhuPokerSell, ClientSide robot);
	
	public abstract int getLandlordScore(List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers);
}
