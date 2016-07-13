package org.userinterfacelib.constants.handlers.frame;

import org.bukkit.entity.Player;

public interface FrameCloseEventHandler extends FrameEvent {
	void onClose(Player player);
}
