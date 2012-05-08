package de.tobiyas.clans.commands.command.commandpackage;

import java.util.Observable;
import java.util.Observer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.commands.CommandDelegator;
import de.tobiyas.clans.commands.command.CommandInterface;
import de.tobiyas.clans.commands.command.CommandParameter;
import de.tobiyas.clans.datacontainer.clan.Clan;

public class CommandMoney implements CommandInterface, Observer{
	
	private String identString = "money";
	private Clans plugin;
	
	public CommandMoney(CommandDelegator delegator){
		delegator.addObserver(this);
		plugin = Clans.getPlugin();
	}

	@Override
	public boolean run(Player player, String[] args) {
		if(args.length != 2)
			return postUsage(player);
		
		String command = args[0];
		String[] newArgs = new String[args.length - 1];
		System.arraycopy(args, 1, newArgs, 0, args.length - 1);
		
		if(command.equals("give")) return parseGiveMoney(player, newArgs);
		if(command.equals("take")) return parseTakeMoney(player, newArgs);
		
		
		return postUsage(player);
	}
	
	private boolean postUsage(Player player) {
		player.sendMessage(ChatColor.YELLOW + "===USAGE:" + ChatColor.RED + " /clan admin" + ChatColor.YELLOW + "===");
		player.sendMessage(ChatColor.YELLOW + "/clan money [" + ChatColor.RED + "give" + 
				ChatColor.YELLOW + ";" + ChatColor.RED + "take" + ChatColor.YELLOW + "]");
		return true;
	}
	
	private boolean parseGiveMoney(Player player, String[] args){
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You don't have a clan.");
			return true;
		}
		
		if(!(clan.hasPermission(player, "moneygive"))) {
			player.sendMessage(ChatColor.RED + "Your rank does not have Permission to this command.");
			return true;
		}
		
		if(args.length != 1){
			player.sendMessage(ChatColor.RED + "You have to specify an amount.");
			return true;
		}
		
		double amount = Double.parseDouble(args[0]);
		if(plugin.getMoneyManager().transferPlayerToBank(player, clan.getName(), amount))
			player.sendMessage(ChatColor.GREEN + "You have transfairde " + ChatColor.LIGHT_PURPLE +
					amount + ChatColor.GREEN + " money to the clan: " + ChatColor.LIGHT_PURPLE +
					clan.getName() + ChatColor.GREEN + ". Thank you very much.");
		else
			player.sendMessage(ChatColor.RED + "The transfer did not work. Check if you did everything correct!");
		
		return true;
	}
	
	private boolean parseTakeMoney(Player player, String[] args){
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null){
			player.sendMessage(ChatColor.RED + "You don't have a clan.");
			return true;
		}
		
		if(!(clan.hasPermission(player, "moneytake"))) {
			player.sendMessage(ChatColor.RED + "Your rank does not have Permission to this command.");
			return true;
		}
		
		if(args.length != 1){
			player.sendMessage(ChatColor.RED + "You have to specify an amount.");
			return true;
		}
		
		double amount = Double.parseDouble(args[0]);
		double bankAmount = plugin.getMoneyManager().getBankBalance(clan.getName());
		if(bankAmount < amount){
			player.sendMessage(ChatColor.YELLOW + "The clan: " + ChatColor.LIGHT_PURPLE + 
					clan.getName() + ChatColor.YELLOW + " only has: " + ChatColor.RED + 
					bankAmount + ChatColor.YELLOW + " money left.");
			return true;
		}
		
		if(plugin.getMoneyManager().transferBankToPlayer(player, clan.getName(), amount))
			player.sendMessage(ChatColor.GREEN + "Success. You took " + ChatColor.LIGHT_PURPLE + 
					amount + ChatColor.GREEN + " money out of the clan bank.");
		else
			player.sendMessage(ChatColor.RED + "Some Thing gone wrong.");
		
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
