package org.nico.ratel.commons.clientactor;

import io.netty.channel.Channel;
import org.nico.ratel.commons.BattleRoleType;

/**
 * @author 柴奇君
 * @create 2024/12/6
 * @desc  玩家角色
 */
public class PlayerActor extends BasicActor{




    public PlayerActor(long id, String nickName, Channel channel) {
        this.id = id;
        this.nickName = nickName;
        this.channel = channel;
    }

    @Override
    public int getActorType() {
        return BattleRoleType.PLAYER.getType();
    }
}
