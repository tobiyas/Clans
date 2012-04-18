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
	
	public void playerSendMessageToClan(Player sender, Clan clan, String message){
		message = clan.parseChatMessage(sender, message);
		
		Set<Player> onlinePlayer = clan.getOnlineMembers();
		for(Player player : onlinePlayer){
			player.sendMessage(message);
		}
	}
	
	public void sendMessageToClanOfPlayer(Player player, String message){
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null) return;
		
		for(Player clanPlayer : clan.getOnlineMembers()){
			clanPlayer.sendMessage(message);
		}
	}

	public void sendMessageToClan(String clanName, String message) {
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) return;
		
		for(Player player : clan.getOnlineMembers()){
			player.sendMessage(message);
		}
	}

}
