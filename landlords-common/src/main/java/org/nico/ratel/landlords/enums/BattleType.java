package org.nico.ratel.landlords.enums;

public enum BattleType {

	PVP("玩家对战"),

	PVE("人机对战"),

	;
	private String msg;

	BattleType(String msg) {
		this.msg = msg;
	}

	public final String getMsg() {
		return msg;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}

}
