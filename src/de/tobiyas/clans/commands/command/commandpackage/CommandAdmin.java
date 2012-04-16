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
		if(args.length == 0) return false;
		
		String command = args[0];
		String[] newArgs = new String[args.length - 1];
		System.arraycopy(args, 1, newArgs, 0, args.length - 1);
		
		if(command.equalsIgnoreCase("promote")) return promoteCommand(player, newArgs);
		if(command.equalsIgnoreCase("editrank")) return editRankCommand(player, newArgs);
		if(command.equalsIgnoreCase("invite")) return inviteCommand(player, newArgs);
		if(command.equalsIgnoreCase("kick")) return kickCommand(player, newArgs);
		
		player.sendMessage(ChatColor.RED + "Could not find command. Use '/clan help' for help.");
		return true;
	}
	
	private boolean promoteCommand(Player player, String[] args){
		Clan clan = plugin.getClanController().getClan(player);
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
		player.sendMessage(ChatColor.RED + "NOT YET IMPLEMENTED");
		//TODO
		return true;
	}
	
	private boolean inviteCommand(Player player, String[] args){
		Clan clan = plugin.getClanController().getClan(player);
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
		
		Clan invClan = plugin.getClanController().getClan(invPlayer);
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
		
		Clan clan = plugin.getClanController().getClan(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You don't have a clan.");
			return true;
		}
		
		if(args.length != 1){
			player.sendMessage(ChatColor.RED + "Usage is: " + ChatColor.LIGHT_PURPLE + "/clan admin kick <playername>");
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
		player.sendMessage(ChatColor.LIGHT_PURPLE + kickName + ChatColor.GREEN + " has been kicked out of your clan.");
		
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
