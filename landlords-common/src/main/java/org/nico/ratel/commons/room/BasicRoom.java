package org.nico.ratel.commons.room;

import org.nico.ratel.commons.BattleType;
import org.nico.ratel.commons.clientactor.BasicActor;
import org.nico.ratel.commons.room.enums.RoomStatus;

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
    private Map<Long, BasicActor> clientMap;

    /**
     * 玩家列表
     */
    private LinkedList<BasicActor> clientLinkedList;

    /**
     * 房间非游玩玩家
     */
    private List<BasicActor> watcherList;

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

    public Map<Long, BasicActor> getClientMap() {
        return clientMap;
    }

    public void setClientMap(Map<Long, BasicActor> clientMap) {
        this.clientMap = clientMap;
    }

    public LinkedList<BasicActor> getClientLinkedList() {
        return clientLinkedList;
    }

    public void setClientLinkedList(LinkedList<BasicActor> clientLinkedList) {
        this.clientLinkedList = clientLinkedList;
    }

    public List<BasicActor> getWatcherList() {
        return watcherList;
    }

    public void setWatcherList(List<BasicActor> watcherList) {
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
