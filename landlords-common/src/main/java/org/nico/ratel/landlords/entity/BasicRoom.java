package org.nico.ratel.landlords.entity;

import org.nico.ratel.landlords.enums.BattleType;
import org.nico.ratel.landlords.enums.RoomStatus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 房间基类
 */
public abstract class BasicRoom {

    /**
     * 房间id
     */
    private long roomId;

    /**
     * 房间类型
     */
    private int roomType;

    /**
     * 房主
     */
    private long ownerId;

    /**
     * 玩家字典
     */
    private Map<Long,BasicClient> clientMap;

    /**
     * 玩家列表
     */
    private LinkedList<BasicClient> clientLinkedList;

    /**
     * 房间非游玩玩家
     */
    private List<BasicClient> watcherList;

    /**
     * 房间阶段状态
     */
    private RoomStatus status;

    /**
     * 对战类型
     */
    private BattleType battleType;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 逻辑更新方法
     * @param deltaTime
     */
    public abstract void update(int deltaTime);


    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public Map<Long, BasicClient> getClientMap() {
        return clientMap;
    }

    public void setClientMap(Map<Long, BasicClient> clientMap) {
        this.clientMap = clientMap;
    }

    public LinkedList<BasicClient> getClientLinkedList() {
        return clientLinkedList;
    }

    public void setClientLinkedList(LinkedList<BasicClient> clientLinkedList) {
        this.clientLinkedList = clientLinkedList;
    }

    public List<BasicClient> getWatcherList() {
        return watcherList;
    }

    public void setWatcherList(List<BasicClient> watcherList) {
        this.watcherList = watcherList;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public BattleType getBattleType() {
        return battleType;
    }

    public void setBattleType(BattleType battleType) {
        this.battleType = battleType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
