package de.tobiyas.clans.commands.command.singlecommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.datacontainer.InviteContainer;
import de.tobiyas.clans.datacontainer.clan.Clan;

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
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "You have to be a player to use this command.");
			return true;
		}
		
		Player player = (Player) sender;		
		InviteContainer invContainer = plugin.getClanController().getInvContainer();
		
		if(!invContainer.hasInvite(player)){
			player.sendMessage(ChatColor.RED + "You have not recieved an Invite.");
			return true;
		}
		
		Clan actClan = plugin.getClanController().getClanOfPlayer(player);
		if(actClan != null){
			player.sendMessage(ChatColor.RED + "You can't accept the request. You are already in an clan: " + ChatColor.LIGHT_PURPLE + actClan.getName());
			return true;
		}
		
		Clan clan = invContainer.getInviteOfPlayer(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "Something has goen wrong. Could not retrieve clan.");
			return true;
		}
		clan.addMember(player, clan.getDefaultRank());
		
		invContainer.removeInvite(player);
		return true;
	}

}
