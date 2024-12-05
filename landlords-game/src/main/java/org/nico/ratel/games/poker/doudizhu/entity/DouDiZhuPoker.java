package org.nico.ratel.games.poker.doudizhu.entity;

import org.nico.ratel.commons.GameInfos;
import org.nico.ratel.games.poker.common.Poker;
import org.nico.ratel.games.poker.common.PokerDesc;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuGameRule;

/**
 * @author 柴奇君
 * @create 2024/12/5
 * @desc 斗地主实体类
 */
public class DouDiZhuPoker extends Poker {


    @Override
    public int createLevel(PokerDesc desc) {
        DouDiZhuGameRule rule= GameInfos.DOU_DI_ZHU.getGameRule(DouDiZhuGameRule.class);
        return rule.getCompareLevel(desc);
    }
}
