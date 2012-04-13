package de.tobiyas.clans.commands.command.commandpackage;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.commands.CommandDelegator;
import de.tobiyas.clans.commands.command.CommandInterface;
import de.tobiyas.clans.commands.command.CommandParameter;
import de.tobiyas.clans.datacontainer.clan.Clan;
import de.tobiyas.clans.datacontainer.rank.Rank;

public class CommandInfo implements CommandInterface, Observer {

	private Clans plugin;
	private final String identString = "info";
	
	public CommandInfo(CommandDelegator delegator){
		plugin = Clans.getPlugin();
		delegator.addObserver(this);
	}
	
	@Override
	public boolean run(Player player, String[] args) {
		if(args.length != 1) return false;
		
		Clan clan = plugin.getClanController().getClan(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You are not in a clan.");
			return true;
		}
		
		if(!clan.hasPermission(player, "info")){
			player.sendMessage(ChatColor.RED + "Your rank does not have the Permission to use this command.");
			return true;
		}
		
		String command = args[0];
		if(command.equalsIgnoreCase("money")) return moneyCommand(player);
		if(command.equalsIgnoreCase("online")) return onlineCommand(player);
		if(command.equalsIgnoreCase("members")) return membersCommand(player);
		if(command.equalsIgnoreCase("permissions")) return permissionsCommand(player); 
		
		player.sendMessage("No known command. use '/clan help info' if you search a command.");
		return true;
	}
	
	private boolean moneyCommand(Player player){
		Clan clan = plugin.getClanController().getClan(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You are not in any clan.");
			return true;
		}
		
		double bankBalance = plugin.getMoneyManager().getBankBalance(clan.getName());
		
		player.sendMessage(ChatColor.YELLOW + "The clan: " + ChatColor.LIGHT_PURPLE + clan.getName() + 
				ChatColor.YELLOW + " has currently " + ChatColor.GREEN + bankBalance +
				ChatColor.YELLOW + " money.");
		return true;
	}
	
	private boolean onlineCommand(Player player){
		Clan clan = plugin.getClanController().getClan(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You are not in any clan.");
			return true;
		}
		
		player.sendMessage(ChatColor.AQUA + "Currently online in: " + ChatColor.DARK_PURPLE + clan.getName());
		HashMap<String, Set<String>> rankedMembers = clan.getMembersRanked();
		for(String rank : rankedMembers.keySet()){
			Set<String> members = rankedMembers.get(rank);
			Rank rankObj = clan.getRankByName(rank);
			
			String allMembers = "";
			for(String member : members){
				Player tempPlayer = Bukkit.getPlayer(member);
				if(tempPlayer == null) continue;
				allMembers += member + ",";
			}
			
			if(allMembers == "") continue;
			allMembers = allMembers.substring(0, allMembers.length() - 1);
			
			player.sendMessage(ChatColor.BLUE + rankObj.getTag() + ": " + ChatColor.GREEN + allMembers);
		}
		
		return true;
	}
	
	private boolean membersCommand(Player player){
		Clan clan = plugin.getClanController().getClan(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You are not in any clan.");
			return true;
		}
		
		player.sendMessage(ChatColor.AQUA + "Members of: " + ChatColor.DARK_PURPLE + clan.getName());
		HashMap<String, Set<String>> rankedMembers = clan.getMembersRanked();
		for(String rank : rankedMembers.keySet()){
			Set<String> members = rankedMembers.get(rank);
			Rank rankObj = clan.getRankByName(rank);
			
			String allMembers = "";
			
			for(String member : members)
				allMembers += member + ", ";
			
			allMembers = allMembers.substring(0, allMembers.length() - 2);
			player.sendMessage(ChatColor.BLUE + rankObj.getTag() + ": " + ChatColor.GREEN + allMembers);
		}
		return true;
	}
	
	private boolean permissionsCommand(Player player){
		Clan clan = plugin.getClanController().getClan(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You are not in any clan.");
			return true;
		}
		
		Rank rank = clan.getRankOfPlayer(player);
		if(rank == null){
			player.sendMessage(ChatColor.RED + "A Strange Error Accured. You have no Rank? Please contact the support.");
			return true;
		}
		
		player.sendMessage(ChatColor.YELLOW + "Your Rank: " + ChatColor.LIGHT_PURPLE + rank.getRankName());
		player.sendMessage(ChatColor.YELLOW + "Your Permissions: " + ChatColor.LIGHT_PURPLE + rank.getPermissionString());
		
		return true;
	}
	
	

	@Override
	public void update(Observable commandDelegator, Object args) {
		CommandParameter parameter = (CommandParameter) args;
		CommandDelegator delegator = (CommandDelegator) commandDelegator;
			
		Player player = parameter.getPlayer();
		String[] arg = parameter.getArgs();
		delegator.addUnknown(player, 10);

		if(!parameter.getCategory().equals(identString)) return;	
		if(run(player, arg)) delegator.addUnknown(player, 1);
	}

}
