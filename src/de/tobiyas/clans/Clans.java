/*
 * CastleSiege - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */

package de.tobiyas.clans;


import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;
import org.bukkit.plugin.PluginDescriptionFile;
import de.tobiyas.clans.configuration.Config;
import de.tobiyas.clans.listeners.Listener_Block;
import de.tobiyas.clans.listeners.Listener_Entity;
import de.tobiyas.clans.listeners.Listener_Player;


public class Clans extends JavaPlugin{
	private Logger log;
	private PluginDescriptionFile description;

	private String prefix;
	private Config config;

	private static Clans plugin;
	
	@Override
	public void onEnable(){
		plugin = this;
		log = Logger.getLogger("Minecraft");
		description = getDescription();
		prefix = "["+description.getName()+"] ";

		log("loading "+description.getFullName());

		setupConfiguration();
		registerEvents();

		registerCommands();
	}
	
	@Override
	public void onDisable(){
		log("disabled "+description.getFullName());

	}
	public void log(String message){
		log.info(prefix+message);
	}


	private void registerEvents(){
		new Listener_Block(this);
		new Listener_Player(this);
		new Listener_Entity(this);
	}
	
	private void registerCommands(){
		
	}


	private void setupConfiguration(){
		config = new Config(this);
	}

	
	public Config interactConfig(){
		return config;
	}

	public static Clans getPlugin() {
		return plugin;
	}

}
