package org.userinterfacelib.constants.button;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.userinterfacelib.constants.frame.Frame;
import org.userinterfacelib.constants.frame.PageNodeFrame;
import org.userinterfacelib.constants.handlers.button.ButtonLeftClickEventHandler;
import org.userinterfacelib.constants.handlers.button.ButtonRightClickEventHandler;
import org.userinterfacelib.main.UserInterfaceLib;
import org.userinterfacelib.main.LanguageSupport.Languages;

public class FirstButton extends Button {

	public FirstButton(Frame parent, int index) {
		super(parent, index, new ItemStack(Material.WOOL, 1, DyeColor.RED.getData()));

		ClickEventHandler handler = new ClickEventHandler();
		this.setLeftClickEventHandler(handler);
		this.setRightClickEventHandler(handler);
	}
	
	private class ClickEventHandler implements ButtonLeftClickEventHandler, ButtonRightClickEventHandler{
		@Override
		public void onClick(Player player) {
			Frame frame = getParent();
			if(!(frame instanceof PageNodeFrame))
				return;
			
			PageNodeFrame pagedFrame = (PageNodeFrame) frame;
			PageNodeFrame firstFrame = PageNodeFrame.getFirst(pagedFrame);
			if(firstFrame != null){
				firstFrame.showTo(player);
			}else{
				UserInterfaceLib.sendMessage(player, Languages.Button_PagedFrame_OutOfBound);
			}
		}
	}
}
