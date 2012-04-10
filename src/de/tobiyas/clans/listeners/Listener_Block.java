/*
 * CastleSiege - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */

package de.tobiyas.clans.listeners;


import org.bukkit.event.Listener; 
import org.bukkit.event.EventHandler; 

import org.bukkit.event.block.BlockBreakEvent;

import de.tobiyas.clans.Clans;


public class Listener_Block implements Listener {
	private Clans plugin;

	public Listener_Block(Clans plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		// TODO handle that event
	}


}
