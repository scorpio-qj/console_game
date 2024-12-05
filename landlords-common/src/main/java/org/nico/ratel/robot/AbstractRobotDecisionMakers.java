package org.nico.ratel.robot;

import java.util.List;

import org.nico.ratel.client.ClientSide;
import org.nico.ratel.games.poker.doudizhu.entity.Poker;
import org.nico.ratel.games.poker.doudizhu.entity.PokerSell;

/** 
 * 
 * @author nico
 * @version createTime：2018年11月15日 上午12:12:15
 */

public abstract class AbstractRobotDecisionMakers {

	public abstract PokerSell howToPlayPokers(PokerSell lastPokerSell, ClientSide robot);
	
	public abstract int getLandlordScore(List<Poker> leftPokers, List<Poker> rightPokers, List<Poker> myPokers);
}
