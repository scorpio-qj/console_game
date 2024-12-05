package org.nico.ratel.games.poker.doudizhu;

import org.nico.ratel.clientactor.ActorInRoomState;

public enum DouDiZhuActorRoomState implements ActorInRoomState<DouDiZhuActorRoomState> {

	TO_CHOOSE,

	NO_READY,

	READY,

	WAIT,

	CALL_LANDLORD,

	PLAYING


}
