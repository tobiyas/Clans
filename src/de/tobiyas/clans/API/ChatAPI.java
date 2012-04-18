package de.tobiyas.clans.API;

import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.datacontainer.clan.Clan;
import de.tobiyas.clans.exceptions.ClanNotFoundException;

public class ChatAPI {

	public static void sendMessageToClan(String clanName, String message) throws ClanNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) throw new ClanNotFoundException(clanName);
		
		plugin.getChatManager().sendMessageToClan(clanName, message);
	}
	
	public static void sendMessageToClanOfPlayer(Player player, String message) throws ClanNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null) throw new ClanNotFoundException(player);
		
		plugin.getChatManager().sendMessageToClanOfPlayer(player, message);
	}
	
	public static void playerSendMessageToHisClan(Player player, String message)throws ClanNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null) throw new ClanNotFoundException(player);
		message = clan.parseChatMessage(player, message);
		
		for(Player clanPlayer : clan.getOnlineMembers()){
			clanPlayer.sendMessage(message);
		}
	}
}
