package org.nico.ratel.commons.event.entity;

import org.nico.ratel.commons.event.ProtocolData;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RoomListData implements ProtocolData {

    private List<Map<String,String>> rooms;
    private int gameId;
    private String gameName;

    public List<Map<String, String>> getRooms() {
        return rooms;
    }

    public void setRooms(List<Map<String, String>> rooms) {
        this.rooms = rooms;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
