package de.tobiyas.clans.chatmanager;

import java.util.Set;

import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.datacontainer.clan.Clan;

public class ChatManager {
	
	private Clans plugin;
	
	public ChatManager(){
		plugin = Clans.getPlugin();
	}
	
	public void sendMessageToClan(Player sender, Clan clan, String message){
		message = clan.parseChatMessage(sender, message);
		
		Set<Player> onlinePlayer = clan.getOnlineMembers();
		for(Player player : onlinePlayer){
			player.sendMessage(message);
		}
	}

}
