/*
 * CastleSiege - by tobiyas
 * http://
 *
 * powered by Kickstarter
 */

package de.tobiyas.clans.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.Listener; 
import org.bukkit.event.EventHandler; 

import org.bukkit.event.player.PlayerChatEvent;
import de.tobiyas.clans.Clans;
import de.tobiyas.clans.datacontainer.clan.Clan;


public class Listener_Player implements Listener {
	private Clans plugin;

	public Listener_Player(Clans plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent event){
		Player player = event.getPlayer();
		Clan clan = plugin.getClanController().getClan(player.getName());
		if(clan == null) return;
		//handle chat stuff
	}
}
