package org.userinterfacelib.constants.handlers.frame;

import org.bukkit.entity.Player;

public interface FrameOpenEventHandler extends FrameEvent {
	/**
	 * DO NOT CALL showTo() in this method
	 * @param player
	 */
	void onOpen(Player player);
}
