package de.tobiyas.clans.chatmanager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.datacontainer.clan.Clan;

public class CommandClanChat implements CommandExecutor{
	
	private Clans plugin;
	
	public CommandClanChat(){
		plugin = Clans.getPlugin();
		try{
			plugin.getCommand("cc").setExecutor(this);
		}catch(Exception e){
			plugin.log("could not register command /cc");
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "Only players can use this command.");
			return true;
		}
		
		Player player = (Player) sender;
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You don't have a clan.");
			return true;
		}
		
		if(args.length == 0)
			return true;
		
		if(!clan.getRankOfPlayer(player).hasPermission("chat")){
			player.sendMessage(ChatColor.RED + "You don't have permission to use the chat.");
			return true;
		}
		
		String message = "";
		for(String messageSnippet : args)
			message += messageSnippet + " ";
		
		plugin.getChatManager().playerSendMessageToClan(player, clan, message);
		return true;
	}

}
