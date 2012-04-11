package de.tobiyas.clans.commands;

import java.util.Observable;
import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.commands.command.CommandAdmin;
import de.tobiyas.clans.commands.command.CommandInfo;
import de.tobiyas.clans.commands.command.CommandMember;

public class CommandDelegator extends Observable{
	
	private Clans plugin;
	
	public CommandDelegator(){
		plugin = Clans.getPlugin();
		initCommands();
	}
	
	private void initCommands(){
		new CommandAdmin(this);
		new CommandMember(this);
		new CommandInfo(this);
	}
	
	public boolean ExecuteCommand(Player player, String[] args){
		if(args.length == 0) return false;
		
		String[] args2 = new String[args.length - 1];
		System.arraycopy(args, 1, args2, 0, args.length - 1);
		
		CommandParameter params = new CommandParameter(player, args[0], args);
		notifyObservers(params);
		return true;
	}
}
