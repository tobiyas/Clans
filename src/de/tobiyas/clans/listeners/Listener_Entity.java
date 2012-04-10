/*
 * CastleSiege - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */

package de.tobiyas.clans.listeners;


import org.bukkit.event.Listener; 
import org.bukkit.event.EventHandler; 

import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.tobiyas.clans.Clans;


public class Listener_Entity implements Listener {
	private Clans plugin;

	public Listener_Entity(Clans plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event){
		// TODO handle that event
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		// TODO handle that event
	}


}
