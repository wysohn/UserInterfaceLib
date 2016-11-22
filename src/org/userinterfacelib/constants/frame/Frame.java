package org.userinterfacelib.constants.frame;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.userinterfacelib.constants.FrameEventHandler;
import org.userinterfacelib.constants.button.Button;
import org.userinterfacelib.constants.handlers.frame.FrameCloseEventHandler;
import org.userinterfacelib.constants.handlers.frame.FrameOpenEventHandler;
import org.userinterfacelib.main.UserInterfaceLib;

/**
 * Frames register itself automatically as constructor is called
 * @author wysohn
 *
 */
public abstract class Frame implements FrameEventHandler{
	private transient final String name;
	private transient final ChestSize size;
	
	private transient final Inventory instance;
	
	private transient Button[] buttons;
	
	private transient FrameOpenEventHandler openEventHandler;
	private transient FrameCloseEventHandler closeEventHandler;
	/**
	 *
	 * @param name unique name for inventory; shouldn't be null
	 * @param size size of inventory; shouldn't be null
	 */
	public Frame(String name, ChestSize size){
		Validate.notNull(name);
		Validate.notNull(size);
		this.name = name;
		this.size = size;
		
		buttons = new Button[size.size];
		
		instance = Bukkit.createInventory(null, size.size, name);
	}
	public String getName() {
		return name;
	}
	
	public Inventory getInstance() {
		return instance;
	}
	/**
	 * 
	 * @param btn
	 * @return magic value
	 * @deprecated use setButton()
	 */
	public int addButton(Button btn){
		return -1;
	}
	
	public ChestSize getSize() {
		return size;
	}
	
	/**
	 * @deprecated now manually specify index {@link #setButton(int, Button)}
	 * @param btn
	 */
	@Deprecated
	public void setButton(Button btn){
		buttons[btn.getIndex()] = btn;
	}
	
	public void setButton(int index, Button btn){
		buttons[index] = btn;
	}
	
	public Button getButton(int index){
		return buttons[index];
	}
	
	public void setOpenEventHandler(FrameOpenEventHandler openEventHandler) {
		this.openEventHandler = openEventHandler;
	}
	public void setCloseEventHandler(FrameCloseEventHandler closeEventHandler) {
		this.closeEventHandler = closeEventHandler;
	}
	
	public void showTo(Player player){
		instance.clear();
		
		if(openEventHandler != null){
			openEventHandler.onOpen(player);
		}
		
		for(int i=0;i<size.size;i++){
			if(buttons[i] == null)
				continue;
			
			instance.setItem(i, buttons[i].getIS());
		}
		
		player.openInventory(instance);
		
		UserInterfaceLib.getGuiManager().registerFrame(this);
	}
	
	public void clear(){
		for(int i = 0; i < buttons.length - 1; i++){
			buttons[i] = null;
		}
	}
	
	public Inventory getInventory(){
		return instance;
	}
	
	public enum ChestSize{
		ONE(9), TWO(18), THREE(27), FOUR(36), FIVE(45), SIX(54);
		private final int size;
		private ChestSize(int size){
			this.size = size;
		}
		public int getSize() {
			return size;
		}
	}
	
	public void handleEvent(InventoryEvent e){
		if(e instanceof InventoryClickEvent){
			int rawSlot = ((InventoryClickEvent) e).getRawSlot();
			if(rawSlot >= 0 && rawSlot < buttons.length){
				Button button = buttons[rawSlot];
				if(button != null)
					button.handleEvent((InventoryClickEvent) e);
			}
		}else if(e instanceof InventoryCloseEvent){
			if(closeEventHandler == null)
				return;
			
			if(!(((InventoryCloseEvent) e).getPlayer() instanceof Player))
				return;
			
			closeEventHandler.onClose((Player) ((InventoryCloseEvent) e).getPlayer());
		}else{
			return;
		}
	}
	
}
