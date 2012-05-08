package de.tobiyas.clans.commands.command.commandpackage;

import java.util.Observable;
import java.util.Observer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.commands.CommandDelegator;
import de.tobiyas.clans.commands.command.CommandInterface;
import de.tobiyas.clans.commands.command.CommandParameter;
import de.tobiyas.clans.datacontainer.InviteContainer;
import de.tobiyas.clans.datacontainer.clan.Clan;
import de.tobiyas.clans.datacontainer.rank.Rank;

public class CommandAdmin implements CommandInterface, Observer{

	private Clans plugin;
	private final String identString = "admin";
	
	public CommandAdmin(CommandDelegator delegator){
		plugin = Clans.getPlugin();
		delegator.addObserver(this);
	}
	
	@Override
	public boolean run(Player player, String[] args) {
		if(args.length == 0) return postUsage(player);
		
		String command = args[0];
		String[] newArgs = new String[args.length - 1];
		System.arraycopy(args, 1, newArgs, 0, args.length - 1);
		
		if(command.equalsIgnoreCase("promote")) return promoteCommand(player, newArgs);
		if(command.equalsIgnoreCase("editrank")) return editRankCommand(player, newArgs);
		if(command.equalsIgnoreCase("invite")) return inviteCommand(player, newArgs);
		if(command.equalsIgnoreCase("kick")) return kickCommand(player, newArgs);
		
		return postUsage(player);
	}
	
	private boolean postUsage(Player player) {
		player.sendMessage(ChatColor.YELLOW + "===USAGE:" + ChatColor.RED + " /clan admin" + ChatColor.YELLOW + "===");
		player.sendMessage(ChatColor.YELLOW + "/clan admin [" + ChatColor.RED + "promote" + 
				ChatColor.YELLOW + ";" + ChatColor.RED + "editrank" + ChatColor.YELLOW + ";" + 
				ChatColor.RED + "invite" + ChatColor.YELLOW + ";" + ChatColor.RED + "kick" +
				ChatColor.YELLOW + "]");
		return true;
	}

	private boolean promoteCommand(Player player, String[] args){
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You don't have a clan.");
			return true;
		}
		
		if(!(clan.hasPermission(player, "promote"))) {
			player.sendMessage(ChatColor.RED + "Your rank does not have Permission to this command.");
			return true;
		}
		
		if(args.length != 2){
			player.sendMessage(ChatColor.RED + "You have to specify 1 Player and 1 Rank.");
			return true;
		}
		
		String playerName = args[0];
		String rankName = args[1];
		
		if(!(clan.hasMember(playerName))){
			player.sendMessage(ChatColor.RED + "There is no Player named: " + ChatColor.LIGHT_PURPLE + playerName + ChatColor.RED + " in your clan.");
			return true;
		}
		
		Rank rank = clan.getRankByName(rankName);
		if(rank == null){
			player.sendMessage(ChatColor.RED + "The rank: " + ChatColor.LIGHT_PURPLE + rankName + ChatColor.RED + " does not exist in the clan.");
			return true;
		}
		
		clan.changeRank(playerName, rankName);
		return true;
	}
	
	private boolean editRankCommand(Player player, String[] args){
		if(args.length == 0){
			player.sendMessage(ChatColor.YELLOW + "===USAGE: " + ChatColor.RED + "/clan admin editrank" + ChatColor.YELLOW + "===");
			player.sendMessage(ChatColor.YELLOW + "Usage: /clan admin editrank " + "<permissions;name;tag;showtag;add;remove> <rankName> [value]");
			return true;
		}
		
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You have no clan.");
			return true;
		}
		
		Rank rank = clan.getRankOfPlayer(player);
		if(rank == null){
			player.sendMessage(ChatColor.RED + "Your rank can't be resolved.");
			return true;
		}
		
		if(!rank.hasPermission("editrank")){
			player.sendMessage(ChatColor.RED + "Your Rank does not have the Permission to edit ranks.");
			return true;
		}
		
		String command = args[0];
		if(command.equalsIgnoreCase("permissions")) return editPermissions(player, clan, args);
		if(command.equalsIgnoreCase("name")) return editName(player, clan, args);
		if(command.equalsIgnoreCase("tag")) return editTag(player, clan, args);
		if(command.equalsIgnoreCase("showtag")) return editShowTag(player, clan, args);
		if(command.equalsIgnoreCase("add")) return addRank(player, clan, args);
		if(command.equalsIgnoreCase("remove")) return removeRank(player, clan, args);
		
		player.sendMessage(ChatColor.YELLOW + "===USAGE: " + ChatColor.RED + "/clan admin editrank" + ChatColor.YELLOW + "===");
		player.sendMessage(ChatColor.YELLOW + "Usage: /clan admin editrank " + "<permissions;name;tag;showtag;add;remove> <rankName> [value]");
		return true;
	}
	
	private boolean removeRank(Player player, Clan clan, String[] args) {
		if(args.length < 2){
			player.sendMessage(ChatColor.YELLOW + "===USAGE: " + ChatColor.RED + "/clan admin editrank remove" + ChatColor.YELLOW + "===");
			player.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.RED + "/clan admin editrank remove " + ChatColor.LIGHT_PURPLE +
					" <rankname>");
			return true;
		}
		
		String rankName = "";
		
		for(int i = 1; i < args.length; i++)
			rankName += args[i] + " ";
		
		rankName = rankName.substring(0, rankName.length() - 1);
		
		if(clan.removeRank(rankName)){
			player.sendMessage(ChatColor.GREEN + "Rank: " + ChatColor.LIGHT_PURPLE + rankName + ChatColor.GREEN + " has been removed.");
			return true;
		}
		player.sendMessage(ChatColor.RED + "Could not find Rank: " + ChatColor.LIGHT_PURPLE + rankName);
		return true;
	}

	private boolean addRank(Player player, Clan clan, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean editShowTag(Player player, Clan clan, String[] args) {
		if(args.length < 3){
			player.sendMessage(ChatColor.YELLOW + "===USAGE: " + ChatColor.RED + "/clan admin editrank showtag" + ChatColor.YELLOW + "===");
			player.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.RED + "/clan admin editrank showtag " + ChatColor.LIGHT_PURPLE +
					" <rankname> <true/false/on/off>");
			return true;
		}
		
		String rankName = "";
		for(int i = 1; i < args.length - 1; i++)
			rankName += args[i] + " ";
		
		rankName = rankName.substring(0, rankName.length() - 1);
		
		Rank rank = clan.getRankByName(rankName);
		if(rank == null){
			player.sendMessage(ChatColor.RED + "The rank: " + ChatColor.LIGHT_PURPLE + rankName + ChatColor.RED + " was not found.");
			return true;
		}
		
		String value = args[args.length - 1];
		if(value == "0" || value == "false" || value == "off"){
			rank.changeTagVisible(false);
			player.sendMessage(ChatColor.GREEN + "Changed ShowTag of Rank: " + ChatColor.LIGHT_PURPLE + rankName + ChatColor.GREEN + " to false.");
			return true;
		}
		
		if(value == "1" || value == "true" || value == "on"){
			rank.changeTagVisible(true);
			player.sendMessage(ChatColor.GREEN + "Changed ShowTag of Rank: " + ChatColor.LIGHT_PURPLE + rankName + ChatColor.GREEN + " to true.");
			return true;
		}
		
		
		player.sendMessage(ChatColor.YELLOW + "===USAGE: " + ChatColor.RED + "/clan admin editrank showtag" + ChatColor.YELLOW + "===");
		player.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.RED + "/clan admin editrank showtag " + ChatColor.LIGHT_PURPLE +
				" <rankname> <true/false/on/off>");
		return true;
	}

	private boolean editTag(Player player, Clan clan, String[] args) {
		if(args.length < 3){
			player.sendMessage(ChatColor.YELLOW + "===USAGE: " + ChatColor.RED + "/clan admin editrank edittag" + ChatColor.YELLOW + "===");
			player.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.RED + "/clan admin editrank edittag " + ChatColor.LIGHT_PURPLE +
					" <rankname> <tag>");
			return true;
		}
		
		String rankName = "";
		
		int i;
		for(i = 1; i < args.length - 1; i++){
			rankName += args[i];
			if(clan.getRankByName(rankName) != null) break;
			rankName += " ";
		}
		
		Rank rank = clan.getRankByName(rankName);
		if(rank == null){
			player.sendMessage(ChatColor.RED + "The rank: " + ChatColor.LIGHT_PURPLE + rankName + ChatColor.RED + " was not found.");
			return true;
		}
		
		String newTag = "";
		for(int j = i; j < args.length - 1; j++){
			newTag += args[j];
			if(j != args.length - 1) newTag += " ";
		}
		
		rank.changeTag(newTag);
		player.sendMessage(ChatColor.GREEN + "The Tag of: " + ChatColor.LIGHT_PURPLE + rank.getRankName() + ChatColor.GREEN + " has been changed to: " + ChatColor.LIGHT_PURPLE + newTag);
		
		return true;
	}

	private boolean editName(Player player, Clan clan, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean editPermissions(Player player, Clan clan, String[] args) {
		if(args.length < 3){
			player.sendMessage(ChatColor.YELLOW + "===USAGE: " + ChatColor.RED + "/clan admin editrank editpermissions" + ChatColor.YELLOW + "===");
			player.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.RED + "/clan admin editrank editpermissions " + ChatColor.LIGHT_PURPLE +
					" <rankname> <+permission/-permission>");
			return true;
		}
		
		String rankName = "";
		for(int i = 1; i < args.length - 1; i++)
			rankName += args[i] + " ";
		
		rankName = rankName.substring(0, rankName.length() - 1);
		
		Rank rank = clan.getRankByName(rankName);
		if(rank == null){
			player.sendMessage(ChatColor.RED + "The rank: " + ChatColor.LIGHT_PURPLE + rankName + ChatColor.RED + " was not found.");
			return true;
		}
		
		String value = args[args.length - 1];
		
		if(value.charAt(0) == '-'){
			if(rank.removePermission(value)){
				player.sendMessage(ChatColor.GREEN + "Permission " + ChatColor.LIGHT_PURPLE + value + ChatColor.GREEN + " removed.");
			}else{
				player.sendMessage(ChatColor.GREEN + "Permission " + ChatColor.LIGHT_PURPLE + value + ChatColor.GREEN + " not found.");
			}
			return true;
		}
		
		if(value.charAt(0) == '+'){
			if(rank.addPermission(value)){
				player.sendMessage(ChatColor.GREEN + "Permission " + ChatColor.LIGHT_PURPLE + value + ChatColor.GREEN + " added.");
			}else{
				player.sendMessage(ChatColor.GREEN + "Permission " + ChatColor.LIGHT_PURPLE + value + ChatColor.GREEN + " not found.");
			}
			return true;
		}
		
		
		player.sendMessage(ChatColor.YELLOW + "===USAGE: " + ChatColor.RED + "/clan admin editrank editpermissions" + ChatColor.YELLOW + "===");
		player.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.RED + "/clan admin editrank editpermissions " + ChatColor.LIGHT_PURPLE +
				" <rankname> <+permission/-permission>");
		return true;
	}

	private boolean inviteCommand(Player player, String[] args){
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You don't have a clan.");
			return true;
		}
		
		if(!(clan.hasPermission(player, "invite"))) {
			player.sendMessage(ChatColor.RED + "Your rank does not have Permission to this command.");
			return true;
		}
		
		if(args.length != 1){
			player.sendMessage(ChatColor.RED + "You have to specify 1 Player.");
			return true;
		}
		
		Player invPlayer = Bukkit.getPlayer(args[0]);
		if(invPlayer == null){
			player.sendMessage(ChatColor.LIGHT_PURPLE + args[0] + " could not be found. You can only invite online players.");
			return true;
		}
		
		Clan invClan = plugin.getClanController().getClanOfPlayer(invPlayer);
		if(invClan != null){
			player.sendMessage(ChatColor.LIGHT_PURPLE + invPlayer.getName() + ChatColor.RED + " already has a clan.");
			return true;
		}
		
		InviteContainer invContainer = plugin.getClanController().getInvContainer();
		if(invContainer.hasInvite(invPlayer))
			invContainer.removeInvite(invPlayer);
		
		invContainer.addInvite(invPlayer, clan, player);
		
		invPlayer.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.GREEN + " invited you to the Clan: " + ChatColor.LIGHT_PURPLE + clan.getName());
		invPlayer.sendMessage(ChatColor.GREEN + "You can accept it by typing: " + ChatColor.LIGHT_PURPLE + "/acceptinvite");
		return true;
	}
	
	private boolean kickCommand(Player player, String[] args){
		
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You don't have a clan.");
			return true;
		}
		
		if(args.length != 1){
			player.sendMessage(ChatColor.YELLOW + "===USAGE: " + ChatColor.RED + "/clan admin kick" + ChatColor.YELLOW + "===");
			player.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.RED + "/clan admin kick" + ChatColor.LIGHT_PURPLE +" <playername>");
			return true;
		}
		
		if(!(clan.hasPermission(player, "kick"))) {
			player.sendMessage(ChatColor.RED + "Your rank does not have Permission to this command.");
			return true;
		}
		
		String kickName = args[0];		
		if(!clan.hasMember(kickName)){
			player.sendMessage(ChatColor.RED + "There is no Player named: " + ChatColor.LIGHT_PURPLE + kickName + ChatColor.RED + " in the clan.");
			return true;
		}
		
		clan.removeMember(kickName);
		plugin.getChatManager().sendMessageToClan(clan.getName(), ChatColor.LIGHT_PURPLE + kickName + 
				ChatColor.GREEN + " has been kicked out of your clan by " + 
				ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.GREEN +".");
		
		Player kickPlayer = Bukkit.getPlayer(kickName);
		if(kickPlayer != null){
			kickPlayer.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.RED + " has kicked you out of the clan.");
		}
		return true;
		
	}

	@Override
	public void update(Observable commandDelegator, Object args) {
		CommandParameter parameter = (CommandParameter) args;
		CommandDelegator delegator = (CommandDelegator) commandDelegator;
			
		Player player = parameter.getPlayer();
		String[] arg = parameter.getArgs();

		if(parameter.getCategory().equals(identString))
			if(run(player, arg)){
				delegator.addUnknown(player, 11);
				return;
			}
		
		delegator.addUnknown(player, 10);
	}

}
