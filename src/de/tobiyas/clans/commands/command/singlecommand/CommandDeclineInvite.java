package de.tobiyas.clans.commands.command.singlecommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.datacontainer.InviteContainer;

public class CommandDeclineInvite implements CommandExecutor{

	private Clans plugin;
	
	public CommandDeclineInvite(){
		plugin = Clans.getPlugin();
		try{
			plugin.getCommand("declineInvite").setExecutor(this);
		}catch(Exception e){
			plugin.log("Command: declineInvite could not be created.");
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "You have to be a Player to use this command.");
			return true;
		}
		
		Player player = (Player) sender;
		InviteContainer invContainer = plugin.getClanController().getInvContainer();
		
		if(!invContainer.hasInvite(player)){
			player.sendMessage(ChatColor.RED + "You don't have an invitation to a clan.");
			return true;
		}
		
		Player inviter = invContainer.getInviterOfPlayer(player);
		if(inviter != null)
			inviter.sendMessage(ChatColor.RED + "Player " + ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.RED + " has declined your invitation.");
		
		invContainer.removeInvite(player);		
		return true;
	}

}
