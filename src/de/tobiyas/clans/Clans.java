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

import de.tobiyas.clans.commands.CommandDelegator;
import de.tobiyas.clans.commands.command.singlecommand.CommandAcceptInvite;
import de.tobiyas.clans.commands.command.singlecommand.CommandClanCreate;
import de.tobiyas.clans.commands.command.singlecommand.CommandDeclineInvite;
import de.tobiyas.clans.configuration.Config;
import de.tobiyas.clans.datacontainer.clan.ClanController;
import de.tobiyas.clans.listeners.Listener_Block;
import de.tobiyas.clans.listeners.Listener_Entity;
import de.tobiyas.clans.listeners.Listener_Player;
import de.tobiyas.clans.money.MoneyManager;
import de.tobiyas.clans.permissions.PermissionManager;


public class Clans extends JavaPlugin{
	private Logger log;
	private PluginDescriptionFile description;

	private String prefix;
	private Config config;
	
	private PermissionManager permissionManager;
	private MoneyManager moneyManager;
	
	private ClanController clanController;

	private static Clans plugin;
	
	@Override
	public void onEnable(){
		plugin = this;
		log = Logger.getLogger("Minecraft");
		description = getDescription();
		prefix = "["+description.getName()+"] ";

		permissionManager = new PermissionManager();
		moneyManager = new MoneyManager();
		
		setupConfiguration();
		clanController = new ClanController();
		
		registerEvents();
		registerCommands();
		
		log(description.getFullName() + " fully loaded using: " + permissionManager.getPermissionsName() + " and: " + moneyManager.getActiveEcoName());
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
		new CommandDelegator();
		new CommandClanCreate();
		new CommandAcceptInvite();
		new CommandDeclineInvite();
	}


	private void setupConfiguration(){
		config = new Config(this);
	}
	
	public PermissionManager getPermissionManager(){
		return permissionManager;
	}

	
	public Config interactConfig(){
		return config;
	}

	public static Clans getPlugin() {
		return plugin;
	}
	
	public ClanController getClanController(){
		return clanController;
	}
	
	public MoneyManager getMoneyManager(){
		return moneyManager;
	}

}
