package org.nico.ratel.commons;

public enum BattleRoleType {

	PLAYER(1,"玩家"),

	ROBOT(2,"机器人"),
	;

	private int type;

	private String desc;

	BattleRoleType(int type,String desc) {
		this.type=type;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
