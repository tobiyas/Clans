/*
 * CastleSiege - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */

package de.tobiyas.clans.listeners;


import org.bukkit.event.Listener; 
import org.bukkit.event.EventHandler; 

import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.tobiyas.clans.Clans;


public class Listener_Player implements Listener {
	private Clans plugin;

	public Listener_Player(Clans plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event){
		// TODO handle that event
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event){
		// TODO handle that event
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		// TODO handle that event
	}


}
