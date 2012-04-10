/*
 * CastleSiege - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */
 
 package de.tobiyas.clans.configuration;

 
 import org.bukkit.configuration.file.FileConfiguration;

import de.tobiyas.clans.Clans;

 
 public class Config{
	private Clans plugin;

	private String config_testnode;


	public Config(Clans plugin){
		this.plugin = plugin;
		setupConfiguration();
		reloadConfiguration();
	}

	private void setupConfiguration(){
		FileConfiguration config = plugin.getConfig();
		config.options().header("MahPluginz configuration xD!!111 s0 l33t:");

		config.addDefault("TestNode", "lol");

		config.options().copyDefaults(true);
		plugin.saveConfig();

	}
	
	
	private void reloadConfiguration(){
		plugin.reloadConfig();
		FileConfiguration config = plugin.getConfig();

		config_testnode = config.getString("TestNode", "lol");

	}
	
	
	public String getconfig_testnode(){
		return config_testnode;
	}

}
