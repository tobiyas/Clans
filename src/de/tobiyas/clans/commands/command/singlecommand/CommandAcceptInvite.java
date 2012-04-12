package de.tobiyas.clans.commands.command.singlecommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.tobiyas.clans.Clans;

public class CommandAcceptInvite implements CommandExecutor {

	private Clans plugin;
	
	public CommandAcceptInvite(){
		plugin = Clans.getPlugin();
		try{
			plugin.getCommand("acceptinvite").setExecutor(this);
		}catch(Exception e){
			plugin.log("Could not register command: /acceptinvite");
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
