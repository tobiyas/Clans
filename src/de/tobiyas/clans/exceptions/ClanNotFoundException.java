package de.tobiyas.clans.exceptions;

import org.bukkit.entity.Player;

public class ClanNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;
	
	public ClanNotFoundException(String clanName){
		this.message = clanName + " not found.";
	}
	
	public ClanNotFoundException(Player player){
		this.message = "clan of Player: " + player.getName() + " not found";
	}
	
	public String getMessage(){
		return message;
	}
	
}
