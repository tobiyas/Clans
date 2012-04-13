package de.tobiyas.clans.datacontainer;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.tobiyas.clans.datacontainer.clan.Clan;

public class InviteContainer {

	private HashMap<String, Clan> inviteList;
	private HashMap<String, String> inviterList;
	
	public InviteContainer(){
		inviteList = new HashMap<String, Clan>();
		inviterList = new HashMap<String, String>();
	}
	
	public void addInvite(Player player, Clan clan, Player inviter){
		if(inviteList.containsKey(player.getName())) 
			removeInvite(player);
		
		inviteList.put(player.getName(), clan);
		inviterList.put(player.getName(), inviter.getName());
	}
	
	public Clan getInviteOfPlayer(Player player){
		return inviteList.get(player.getName());
	}
	
	public Player getInviterOfPlayer(Player player){
		String playerName =inviterList.get(player.getName());
		Player inviter = Bukkit.getPlayer(playerName);
		return inviter;
	}
	
	public boolean hasInvite(Player player){
		return inviteList.containsKey(player.getName());
	}
	
	public void removeInvite(Player player){
		inviteList.remove(player.getName());
		inviterList.remove(player.getName());
	}
}
