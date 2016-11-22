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
	private transient Frame parent;
	@Deprecated
	private transient int index;
	private transient final ItemStack IS;

	private transient ButtonLeftClickEventHandler leftClickEventHandler;
	private transient ButtonRightClickEventHandler rightClickEventHandler;
	private transient ButtonShiftLeftClickEventHandler shiftLeftClickEventHandler;
	private transient ButtonShiftRightClickEventHandler shiftRightClickEventHandler;
	
	/**
	 * @deprecated now don't save index in Button. use {@link #Button(Frame, ItemStack)}
	 * @param parent
	 * @param index
	 * @param IS
	 */
	@Deprecated
	public Button(Frame parent, int index, ItemStack IS){
		this.parent = parent;
		this.index = index;
		this.IS = IS;
	}
	
	public Button(Frame parent, ItemStack IS){
		this.parent = parent;
		this.IS = IS;
	}
	
	public void setParent(Frame parent){
		this.parent = parent;
	}
	
	public Frame getParent() {
		return parent;
	}
	
	@Deprecated
	public int getIndex() {
		return index;
	}
	
	public ItemStack getIS() {
		return IS;
	}
	public Button updateDisplayName(String displayName){
		ItemMeta IM = IS.getItemMeta();
		IM.setDisplayName(displayName);
		IS.setItemMeta(IM);
		return this;
	}
	public Button updateLore(List<String> lore){
		ItemMeta IM = IS.getItemMeta();
		IM.setLore(lore);
		IS.setItemMeta(IM);
		return this;
	}
	public Button setLeftClickEventHandler(ButtonLeftClickEventHandler leftClickEventHandler) {
		this.leftClickEventHandler = leftClickEventHandler;
		return this;
	}
	public Button setRightClickEventHandler(ButtonRightClickEventHandler rightClickEventHandler) {
		this.rightClickEventHandler = rightClickEventHandler;
		return this;
	}
	public Button setShiftLeftClickEventHandler(ButtonShiftLeftClickEventHandler shiftLeftClickEventHandler) {
		this.shiftLeftClickEventHandler = shiftLeftClickEventHandler;
		return this;
	}
	public Button setShiftRightClickEventHandler(ButtonShiftRightClickEventHandler shiftRightClickEventHandler) {
		this.shiftRightClickEventHandler = shiftRightClickEventHandler;
		return this;
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
