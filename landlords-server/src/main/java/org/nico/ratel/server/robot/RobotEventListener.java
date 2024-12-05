package org.nico.ratel.server.robot;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.nico.ratel.commons.clientactor.ClientSide;
import org.nico.ratel.commons.BasicEventCode;

public interface RobotEventListener {

	String LISTENER_PREFIX = "org.nico.ratel.landlords.server.robot.RobotEventListener_";

	Map<BasicEventCode, RobotEventListener> LISTENER_MAP = new HashMap<>();

	void call(ClientSide robot, String data);

	@SuppressWarnings("unchecked")
	static RobotEventListener get(BasicEventCode code) {
		RobotEventListener listener = null;
		try {
			if (RobotEventListener.LISTENER_MAP.containsKey(code)) {
				listener = RobotEventListener.LISTENER_MAP.get(code);
			} else {
				String eventListener = LISTENER_PREFIX + code.name();
				Class<RobotEventListener> listenerClass = (Class<RobotEventListener>) Class.forName(eventListener);
				try {
					listener = listenerClass.getDeclaredConstructor().newInstance();
				} catch (NoSuchMethodException | InvocationTargetException e) {
					e.printStackTrace();
				}
				RobotEventListener.LISTENER_MAP.put(code, listener);
			}
			return listener;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
