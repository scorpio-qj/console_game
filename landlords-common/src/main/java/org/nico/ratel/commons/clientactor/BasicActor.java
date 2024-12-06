package org.nico.ratel.commons.clientactor;

import io.netty.channel.Channel;

/**
 * 客户端基类
 */
public abstract class BasicActor {

    protected long id;

    protected String nickName;

    protected transient Channel channel;

    protected String version;

    public abstract int getActorType();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
