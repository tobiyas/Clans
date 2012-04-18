package de.tobiyas.clans.commands.command.singlecommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.datacontainer.clan.Clan;
import de.tobiyas.clans.permissions.PermissionNode;

public class CommandClanCreate implements CommandExecutor{

	private Clans plugin;
	
	public CommandClanCreate(){
		plugin = Clans.getPlugin();
		plugin.getCommand("clancreate").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "You have to be a Player.");
			return true;
		}
		
		Player player = (Player) sender;
		if(!(plugin.getPermissionManager().checkPermissions(player, PermissionNode.createClan)))
			return true;
		
		if(args.length == 0){
			player.sendMessage(ChatColor.RED + "The Clan name can not be empty.");
			return true;
		}
		
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan != null){
			player.sendMessage(ChatColor.RED + "You are already in a clan: " + ChatColor.LIGHT_PURPLE + clan.getName() + ChatColor.RED + ". You have to leave it first.");
			return true;
		}
		
		String clanName = "";
		
		for(int i = 0; i < args.length; i++){
			clanName += args[i] + " ";
		}
		clanName = clanName.substring(0, clanName.length() - 1);
		
		boolean worked = plugin.getClanController().createNewClan(player, clanName);
		
		if(worked){
			player.sendMessage(ChatColor.GREEN + "Your clan: " + ChatColor.AQUA + clanName + ChatColor.GREEN + " has been created.");
		}else{
			player.sendMessage(ChatColor.RED + "Your clan: " + ChatColor.AQUA + clanName + ChatColor.RED + " could not be created.");
		}
		return true;
	}

}
