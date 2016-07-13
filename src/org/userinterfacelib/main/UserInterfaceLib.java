package org.userinterfacelib.main;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.generallib.serializetools.exceptions.FileSerializeException;
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

		cmdExe.onCommand(sender, command, label, args);
		
		return true;
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
		} catch (FileSerializeException e) {
			e.printStackTrace();
			this.setEnabled(false);
			return;
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
