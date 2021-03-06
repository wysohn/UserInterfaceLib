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
import org.userinterfacelib.main.UserInterfaceLib;
import org.userinterfacelib.main.LanguageSupport.Languages;

public class LastButton extends Button {

	@SuppressWarnings("deprecation")
	public LastButton(Frame parent) {
		super(parent, new ItemStack(Material.WOOL, 1, (short) 5));

		ClickEventHandler handler = new ClickEventHandler();
		this.setLeftClickEventHandler(handler);
		this.setRightClickEventHandler(handler);
		
		this.updateDisplayName(ChatColor.RED+">>");
	}
	
	private class ClickEventHandler implements ButtonLeftClickEventHandler, ButtonRightClickEventHandler{
		@Override
		public void onClick(Player player) {
			Frame frame = getParent();
			if(!(frame instanceof PageNodeFrame))
				return;
			
			PageNodeFrame pagedFrame = (PageNodeFrame) frame;
			PageNodeFrame lastFrame = pagedFrame.manager.getTail();
			if(lastFrame != null){
				lastFrame.showTo(player);
			}else{
				UserInterfaceLib.sendMessage(player, Languages.Button_PagedFrame_OutOfBound);
			}
		}
	}
}
