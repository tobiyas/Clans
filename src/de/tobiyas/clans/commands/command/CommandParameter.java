package de.tobiyas.clans.commands.command;

import org.bukkit.entity.Player;

public class CommandParameter {

	private Player player;
	private String[] args;
	private String category;
	
	public CommandParameter(Player player, String category, String[] args){
		this.player = player;
		this.args = args;
		this.category = category;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public String[] getArgs(){
		return args;
	}
	
	public String getCategory(){
		return category;
	}
	
}
