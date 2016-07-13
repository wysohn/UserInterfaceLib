package org.userinterfacelib.constants.button;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.userinterfacelib.constants.frame.Frame;
import org.userinterfacelib.constants.frame.PageNodeFrame;
import org.userinterfacelib.constants.handlers.button.ButtonLeftClickEventHandler;
import org.userinterfacelib.constants.handlers.button.ButtonRightClickEventHandler;
import org.userinterfacelib.main.LanguageSupport.Languages;
import org.userinterfacelib.main.UserInterfaceLib;

public class NextButton extends Button {
	public NextButton(Frame parent, int index) {
		super(parent, index, new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData()));
		
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
			PageNodeFrame nextFrame = pagedFrame.getNext();
			if(nextFrame != null){
				nextFrame.showTo(player);
			}else{
				UserInterfaceLib.sendMessage(player, Languages.Button_PagedFrame_OutOfBound);
			}
		}
	}
}
