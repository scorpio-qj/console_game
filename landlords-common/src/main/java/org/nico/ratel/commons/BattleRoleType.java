package org.nico.ratel.commons;

public enum BattleRoleType {

	PLAYER("玩家"),

	ROBOT("机器人"),
	;
	private String desc;

	BattleRoleType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
