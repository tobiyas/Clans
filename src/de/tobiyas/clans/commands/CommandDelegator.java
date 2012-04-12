package de.tobiyas.clans.commands;

import java.util.HashMap;
import java.util.Observable;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.commands.command.CommandParameter;
import de.tobiyas.clans.commands.command.commandpackage.CommandAdmin;
import de.tobiyas.clans.commands.command.commandpackage.CommandHelp;
import de.tobiyas.clans.commands.command.commandpackage.CommandInfo;
import de.tobiyas.clans.commands.command.commandpackage.CommandMember;

public class CommandDelegator extends Observable implements CommandExecutor{
	
	private Clans plugin;
	private HashMap<String, Integer> callMap;
	
	public CommandDelegator(){
		plugin = Clans.getPlugin();
		initCommands();
		try{
			plugin.getCommand("clan").setExecutor(this);
		}catch(Exception e){
			plugin.log("Could not register /clan");
		}
		
		callMap = new HashMap<String, Integer>();
		
		notifyObservers(new CommandParameter(null, null, null));
		setChanged();
	}
	
	private void initCommands(){
		new CommandAdmin(this);
		new CommandMember(this);
		new CommandInfo(this);
		new CommandHelp(this);
	}
	
	public boolean ExecuteCommand(Player player, String[] args){
		if(args.length == 0) return false;
		
		String[] args2 = new String[args.length - 1];
		System.arraycopy(args, 1, args2, 0, args.length - 1);
		
		CommandParameter params = new CommandParameter(player, args[0], args2);
		callMap.put(player.getName(), 0);
		
		notifyObservers(params);
		setChanged();
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
		if(!ExecuteCommand(player, args)){
			player.sendMessage(ChatColor.RED + "Wrong usage. If you need help, type /clan help");
		}
		return true;
	}

	public void addUnknown(Player player, int value) {
		int tempValue = callMap.get(player.getName());
		tempValue += value;
		callMap.remove(player.getName());
		callMap.put(player.getName(), tempValue);
		
		if(tempValue == 40)
			player.sendMessage(ChatColor.RED + "The Command is unknown by Clans.");
		
		if(tempValue >= 40)
			callMap.remove(player.getName());
	}
}
