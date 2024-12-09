package org.nico.ratel.client.entity;

public class User {
    public static final User INSTANCE = new User();

    private long id;

    private String nickname = "player";

    /** 是否游戏中 */
    private volatile boolean isPlaying = false;

    /** 是否观战中 */
    private volatile boolean isWatching = false;

    private User() {}

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isWatching() {
        return isWatching;
    }

    public void setWatching(boolean watching) {
        isWatching = watching;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
