package de.tobiyas.clans.commands;

import java.util.Observable;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.commands.command.CommandAdmin;
import de.tobiyas.clans.commands.command.CommandInfo;
import de.tobiyas.clans.commands.command.CommandMember;

public class CommandDelegator extends Observable implements CommandExecutor{
	
	private Clans plugin;
	
	public CommandDelegator(){
		plugin = Clans.getPlugin();
		initCommands();
		try{
			plugin.getCommand("clan").setExecutor(this);
		}catch(Exception e){
			plugin.log("Could not register /clan");
		}
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
		
		CommandParameter params = new CommandParameter(player, args[0], args2);
		notifyObservers(params);
		this.setChanged();
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "You must be a Player to use this command.");
			return true;
		}
		
		Player player = (Player) sender;
		return ExecuteCommand(player, args);
	}
}
