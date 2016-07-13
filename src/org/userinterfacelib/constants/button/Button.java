package org.userinterfacelib.constants.button;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.userinterfacelib.constants.frame.Frame;
import org.userinterfacelib.constants.handlers.button.ButtonLeftClickEventHandler;
import org.userinterfacelib.constants.handlers.button.ButtonRightClickEventHandler;
import org.userinterfacelib.constants.handlers.button.ButtonShiftLeftClickEventHandler;
import org.userinterfacelib.constants.handlers.button.ButtonShiftRightClickEventHandler;

public abstract class Button {
	private transient final Frame parent;
	private transient final int index;
	private transient final ItemStack IS;

	private transient ButtonLeftClickEventHandler leftClickEventHandler;
	private transient ButtonRightClickEventHandler rightClickEventHandler;
	private transient ButtonShiftLeftClickEventHandler shiftLeftClickEventHandler;
	private transient ButtonShiftRightClickEventHandler shiftRightClickEventHandler;
	
	public Button(Frame parent, int index, ItemStack IS){
		this.parent = parent;
		this.index = index;
		this.IS = IS;
	}
	public Frame getParent() {
		return parent;
	}
	public int getIndex() {
		return index;
	}
	public ItemStack getIS() {
		return IS;
	}
	public void updateDisplayName(String displayName){
		ItemMeta IM = IS.getItemMeta();
		IM.setDisplayName(displayName);
		IS.setItemMeta(IM);
	}
	public void updateLore(List<String> lore){
		ItemMeta IM = IS.getItemMeta();
		IM.setLore(lore);
		IS.setItemMeta(IM);
	}
	public void setLeftClickEventHandler(ButtonLeftClickEventHandler leftClickEventHandler) {
		this.leftClickEventHandler = leftClickEventHandler;
	}
	public void setRightClickEventHandler(ButtonRightClickEventHandler rightClickEventHandler) {
		this.rightClickEventHandler = rightClickEventHandler;
	}
	public void setShiftLeftClickEventHandler(ButtonShiftLeftClickEventHandler shiftLeftClickEventHandler) {
		this.shiftLeftClickEventHandler = shiftLeftClickEventHandler;
	}
	public void setShiftRightClickEventHandler(ButtonShiftRightClickEventHandler shiftRightClickEventHandler) {
		this.shiftRightClickEventHandler = shiftRightClickEventHandler;
	}
	public void handleEvent(InventoryClickEvent e){
		e.setCancelled(true);
		
		if(!(e.getWhoClicked() instanceof Player))
			return;
		
		if(e.isShiftClick()){
			if(e.isRightClick()){
				if(shiftRightClickEventHandler != null)
					shiftRightClickEventHandler.onClick((Player) e.getWhoClicked());
			}else if(e.isLeftClick()){
				if(shiftLeftClickEventHandler != null)
					shiftLeftClickEventHandler.onClick((Player) e.getWhoClicked());
			}
		}else{
			if(e.isRightClick()){
				if(rightClickEventHandler != null)
					rightClickEventHandler.onClick((Player) e.getWhoClicked());
			}else if(e.isLeftClick()){
				if(leftClickEventHandler != null)
					leftClickEventHandler.onClick((Player) e.getWhoClicked());
			}
		}
	}
	
	public static int getIndex(int row, int col){
		return (row*9) + col;
	}
}
