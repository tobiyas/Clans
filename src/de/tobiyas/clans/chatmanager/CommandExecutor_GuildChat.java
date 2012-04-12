package de.tobiyas.clans.chatmanager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.tobiyas.clans.Clans;

public class CommandExecutor_GuildChat implements CommandExecutor{
	
	private Clans plugin;
	
	public CommandExecutor_GuildChat(){
		plugin = Clans.getPlugin();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		
		
		return false;
	}

}
