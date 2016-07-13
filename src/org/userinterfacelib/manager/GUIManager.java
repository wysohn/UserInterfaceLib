package org.userinterfacelib.manager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.userinterfacelib.constants.frame.Frame;
import org.userinterfacelib.main.UserInterfaceLib;

public class GUIManager extends Manager implements Listener{
	private static final Map<InventoryWrapper, Frame> registeredFrames = new ConcurrentHashMap<InventoryWrapper, Frame>();
	public void registerFrame(Frame frame){
		registeredFrames.put(new InventoryWrapper(frame.getInstance()), frame);
		UserInterfaceLib.logDebug("GUI ["+frame.getInstance().hashCode()+"] is registered!");
	}
	public void unregisterFrame(Frame frame){
		registeredFrames.remove(new InventoryWrapper(frame.getInstance()));
		UserInterfaceLib.logDebug("GUI ["+frame.getInstance().hashCode()+"] is unregistered!");
	}
	
	public GUIManager(Plugin plugin) {
		super(plugin);
	}

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e){
		InventoryWrapper inv = new InventoryWrapper(e.getInventory());
		if(!registeredFrames.containsKey(inv))
			return;
		
		Frame frame = registeredFrames.get(inv);
		frame.handleEvent(e);
	}
	
	@EventHandler
	public void onInventoryCloseEvent(InventoryCloseEvent e){
		InventoryWrapper inv = new InventoryWrapper(e.getInventory());
		if(!registeredFrames.containsKey(inv))
			return;
		
		Frame frame = registeredFrames.get(inv);
		frame.handleEvent(e);
		
		unregisterFrame(frame);
	}
	
	@Override
	public void onDisable() {

	}
	
	private static class InventoryWrapper{
		Inventory inv;

		InventoryWrapper(Inventory inv) {
			this.inv = inv;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + inv.getName().hashCode();
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof InventoryWrapper))
				return false;
			InventoryWrapper other = (InventoryWrapper) obj;
			if (inv == null) {
				if (other.inv != null)
					return false;
			} else if (!inv.equals(other.inv))
				return false;
			
			return true;
		}

		
	}

}
