package de.tobiyas.clans.commands.command.singlecommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.datacontainer.clan.Clan;
import de.tobiyas.clans.permissions.PermissionNode;

public class CommandClanJoinLeader implements CommandExecutor{
	
	private Clans plugin;
	
	public CommandClanJoinLeader(){
		plugin = Clans.getPlugin();
		try{
			plugin.getCommand("clanjoinleader").setExecutor(this);
		}catch(Exception e){
			plugin.log("Could not registe /clanjoinleader");
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
		if(!plugin.getPermissionManager().checkPermissions(sender, PermissionNode.joinLeader))
			return true;
		
		if(args.length == 0){
			player.sendMessage(ChatColor.RED + "You have to specify a clan.");
			return true;
		}
		
		Clan tempClan = plugin.getClanController().getClan(player);
		if(tempClan != null){
			player.sendMessage(ChatColor.RED + "You still have a clan. You have to leave it first.");
			return true;
		}
		
		String clanString = "";
		for(String arg : args)
			clanString += arg + " ";
		
		if(clanString.length() == 0)
			return true;
		
		clanString = clanString.substring(0, clanString.length() - 1);
		Clan clan = plugin.getClanController().getClan(clanString);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "The clan: " + ChatColor.LIGHT_PURPLE + clanString + ChatColor.RED + " does not exist.");
			return true;
		}
		
		clan.addMember(player, clan.getLeaderRank());
		player.sendMessage(ChatColor.GREEN + "You are now leader of: " + ChatColor.LIGHT_PURPLE + clanString);
		return true;
	}

}
