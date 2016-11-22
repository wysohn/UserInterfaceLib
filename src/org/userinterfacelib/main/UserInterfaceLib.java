package org.userinterfacelib.main;

import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.userinterfacelib.constants.button.Button;
import org.userinterfacelib.constants.frame.ButtonPageFrame;
import org.userinterfacelib.constants.frame.ItemPageFrame;
import org.userinterfacelib.constants.frame.PageNodeFrame;
import org.userinterfacelib.constants.handlers.button.ButtonLeftClickEventHandler;
import org.userinterfacelib.constants.handlers.frame.FrameCloseEventHandler;
import org.userinterfacelib.main.LanguageSupport.Languages;
import org.userinterfacelib.manager.GUIManager;
import org.userinterfacelib.manager.Manager;

public class UserInterfaceLib extends JavaPlugin{
	private static String PLUGIN_NAME;
	
	public static UserInterfaceLibConfig config;
	private static LanguageSupport lang;
	private static CommandExecutor cmdExe;
	
	private static GUIManager guiManager;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if(sender.isOp() && command.getName().equals("uil")){
/*			ItemStack[] items = new ItemStack[100];
			for(int i = 0; i < 100; i++){
				if(i % 10 == 0)
					items[i] = null;
				else
					items[i] = new ItemStack(Material.APPLE);
			}
			final ItemPageFrame f = new ItemPageFrame("test", items);
			f.setCloseEventHandler(new FrameCloseEventHandler(){
				@Override
				public void onClose(Player player) {
					ItemStack[] items2 = f.getItems();
					
					for(int i = 0; i < items2.length - 1; i += 9){
						String temp = "";
						for(int j = i; j < i + 9; j++)
							temp += items2[j] != null ? items2[j].getTypeId()+" " : "-1 ";
						player.sendMessage(i+". "+temp);
					}
				}
			});
			f.showTo((Player) sender);*/
			
			Button[] items = new Button[100];
			for(int i = 0; i < 100; i++){
				if(i % 10 == 0)
					items[i] = null;
				else
					items[i] = new Button(null, new ItemStack(Material.APPLE)){{
						this.setLeftClickEventHandler(new ButtonLeftClickEventHandler(){
							@Override
							public void onClick(Player player) {
								player.sendMessage("click!");
							}
						});
					}};
			}
			final ButtonPageFrame f = new ButtonPageFrame("test", items);
			f.setCloseEventHandler(new FrameCloseEventHandler(){
				@Override
				public void onClose(Player player) {
					Button[] items2 = f.getItems();
					
					for(int i = 0; i < items2.length - 1; i += 9){
						String temp = "";
						for(int j = i; j < i + 9; j++)
							temp += items2[j] != null ? items2[j].getIS().getTypeId()+" " : "-1 ";
						player.sendMessage(i+". "+temp);
					}
				}
			});
			f.showTo((Player) sender);
		}
		
		return true;
	}
	
	private void debug(){
		ItemStack[] items = new ItemStack[100];
		for(int i = 0; i < 100; i++){
			if(i % 10 == 0)
				items[i] = null;
			else
				items[i] = new ItemStack(Material.APPLE);
		}
		
		ItemPageFrame f = new ItemPageFrame("test", items);
		
		PageNodeFrame node = f.getHead();
		prettyPrint(node.getInventory());
		while((node = node.getNext()) != null)
			prettyPrint(node.getInventory());
	}
	
	private void prettyPrint(Inventory inv){
		getLogger().info("----------"+inv.getName()+"----------");
		StringBuilder builder = new StringBuilder();;
		for(int i = 0; i < inv.getSize(); i++){
			if(inv.getItem(i) != null)
				builder.append(inv.getItem(i).getTypeId()+" ");
			else
				builder.append("-1 ");
			
			if(i % 9 == 0){
				getLogger().info(builder.toString());
				builder = new StringBuilder();
			}
		}
		getLogger().info("----------"+inv.getName()+"----------");
	}

	@Override
	public void onDisable() {
		for(Manager manager : Manager.getModules()){
			try{
				manager.onDisable();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onEnable() {
		PLUGIN_NAME = this.getDescription().getFullName();
		
		config = new UserInterfaceLibConfig(this);

		try {
			lang = new LanguageSupport(this, config.lang);
		} catch (IOException | InvalidConfigurationException e1) {
			e1.printStackTrace();
		}

		cmdExe = new CommandExecutor(this);
		
		if(!config.isPluginEnabled){
			this.setEnabled(false);
			UserInterfaceLib.logInfo("["+PLUGIN_NAME+"] "+lang.parseFirstString(Languages.Plugin_NotEnabled));
			UserInterfaceLib.logInfo(lang.parseFirstString(Languages.Plugin_SetEnableToTrue));
			UserInterfaceLib.logInfo(lang.parseFirstString(Languages.Plugin_WillBeDisabled));
			return;
		}
		
		PluginManager pm = getServer().getPluginManager();
		//init goes here
		guiManager = new GUIManager(this);
		
		//init ends
		for(Manager manager : Manager.getModules()){
			if(manager == null){
				logDebug("Null manager found. skipped.");
				continue;
			}
			
			try {
				pm.registerEvents((Listener) manager, this);
			} catch (Exception e) {
				logDebug("Exception : "+manager.getClass().getSimpleName());
			} catch (Error e){
				logDebug("Error : "+manager.getClass().getSimpleName());
			}
		}

		super.onEnable();
	}

	public static void sendMessage(Player player, Languages lang){
		player.sendMessage(getLang().parseFirstString(lang));
	}
	
	public static void logDebug(String str){
		if(!config.isDebugging)
			return;
		
		Bukkit.getLogger().warning("["+PLUGIN_NAME+"(Debug)] "+str);
	}
	
	public static void logInfo(String str){
		Bukkit.getLogger().info("["+PLUGIN_NAME+"] "+str);
	}
	
	public static void invokeJS(String js){
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("nashorn");
		try {
			engine.eval(js);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public static LanguageSupport getLang() {
		return lang;
	}

	public static CommandExecutor getCmdExe() {
		return cmdExe;
	}

	public static GUIManager getGuiManager() {
		return guiManager;
	}
	
	
}
