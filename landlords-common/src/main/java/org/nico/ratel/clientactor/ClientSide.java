package org.nico.ratel.clientactor;

import java.util.List;

import org.nico.ratel.BattleRoleType;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuActorRoomState;
import org.nico.ratel.games.poker.doudizhu.DouDiZhuRoleType;

import io.netty.channel.Channel;
import org.nico.ratel.games.poker.Poker;

public class ClientSide {

	private int id;

	private int roomId;

	private int score;

	private int scoreInc;

	private int round;

	private String nickname;

	private List<Poker> pokers;

	private DouDiZhuActorRoomState status;

	private BattleRoleType role;

	private DouDiZhuRoleType type;

	private ClientSide next;

	private ClientSide pre;

	private transient Channel channel;

	private String version;

	public ClientSide() {}

	public ClientSide(int id, DouDiZhuActorRoomState status, Channel channel) {
		this.id = id;
		this.status = status;
		this.channel = channel;
	}

	public void init() {
		roomId = 0;
		pokers = null;
		status = DouDiZhuActorRoomState.TO_CHOOSE;
		type = null;
		next = null;
		pre = null;
		score = 0;
	}

	public final void resetRound() {
		round = 0;
	}

	public final int getRound() {
		return round;
	}

	public final void addRound() {
		round += 1;
	}

	public final BattleRoleType getRole() {
		return role;
	}

	public final void setRole(BattleRoleType role) {
		this.role = role;
	}

	public final String getNickname() {
		return nickname;
	}

	public final void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public final Channel getChannel() {
		return channel;
	}

	public final void setChannel(Channel channel) {
		this.channel = channel;
	}

	public final int getRoomId() {
		return roomId;
	}

	public final void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public final List<Poker> getPokers() {
		return pokers;
	}

	public final void setPokers(List<Poker> pokers) {
		this.pokers = pokers;
	}

	public final int getScore() {
		return score;
	}

	public final void setScore(int score) {
		this.score = score;
	}

	public final void addScore(int score) {
		this.score += score;
		this.scoreInc = score;
	}

	public final void setScoreInc(int scoreInc) {
		this.scoreInc = scoreInc;
	}

	public final int getScoreInc() {
		return this.scoreInc;
	}

	public final DouDiZhuActorRoomState getStatus() {
		return status;
	}

	public final void setStatus(DouDiZhuActorRoomState status) {
		this.status = status;
	}

	public final DouDiZhuRoleType getType() {
		return type;
	}

	public final void setType(DouDiZhuRoleType type) {
		this.type = type;
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final ClientSide getNext() {
		return next;
	}

	public final void setNext(ClientSide next) {
		this.next = next;
	}

	public final ClientSide getPre() {
		return pre;
	}

	public final void setPre(ClientSide pre) {
		this.pre = pre;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientSide other = (ClientSide) obj;
		return id == other.id;
	}

}
