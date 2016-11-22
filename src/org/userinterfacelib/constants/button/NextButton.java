package org.userinterfacelib.constants.button;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.userinterfacelib.constants.frame.Frame;
import org.userinterfacelib.constants.frame.PageNodeFrame;
import org.userinterfacelib.constants.handlers.button.ButtonLeftClickEventHandler;
import org.userinterfacelib.constants.handlers.button.ButtonRightClickEventHandler;

public class NextButton extends Button {
	@SuppressWarnings("deprecation")
	public NextButton(Frame parent) {
		super(parent, new ItemStack(Material.WOOL, 1, (short) 13));

		
		ClickEventHandler handler = new ClickEventHandler();
		this.setLeftClickEventHandler(handler);
		this.setRightClickEventHandler(handler);
		
		this.updateDisplayName(ChatColor.RED+">");
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
				//UserInterfaceLib.sendMessage(player, Languages.Button_PagedFrame_OutOfBound);
			}
		}
	}
}
