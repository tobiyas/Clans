/*
 * CastleSiege - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */

/**
 * Clan Support for Bukkit-Servers
 * Copyright (C) 2012  tobiyas
 *  
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
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
