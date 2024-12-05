package org.nico.ratel.games.poker.doudizhu;

import org.nico.ratel.commons.BasicGameRule;
import org.nico.ratel.games.poker.common.PokerDesc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 柴奇君
 * @create 2024/12/5
 * @desc  斗地主规则
 */
public class DouDiZhuGameRule implements BasicGameRule {

    private final Map<PokerDesc,Integer> pokerLevelMap=new HashMap<>();

    public DouDiZhuGameRule() {

        pokerLevelMap.put(PokerDesc.P_3,3);
        pokerLevelMap.put(PokerDesc.P_4,4);
        pokerLevelMap.put(PokerDesc.P_5,5);
        pokerLevelMap.put(PokerDesc.P_6,6);
        pokerLevelMap.put(PokerDesc.P_7,7);
        pokerLevelMap.put(PokerDesc.P_8,8);
        pokerLevelMap.put(PokerDesc.P_9,9);
        pokerLevelMap.put(PokerDesc.P_10,10);
        pokerLevelMap.put(PokerDesc.P_J,11);
        pokerLevelMap.put(PokerDesc.P_Q,12);
        pokerLevelMap.put(PokerDesc.P_K,13);
        pokerLevelMap.put(PokerDesc.P_A,14);
        pokerLevelMap.put(PokerDesc.P_2,15);
        pokerLevelMap.put(PokerDesc.P_SMALL_KING,16);
        pokerLevelMap.put(PokerDesc.P_BIG_KING,17);

    }

    public int getCompareLevel(PokerDesc desc){
        return pokerLevelMap.get(desc);
    }
}
