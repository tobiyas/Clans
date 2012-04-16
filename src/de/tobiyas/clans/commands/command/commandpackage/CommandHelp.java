package de.tobiyas.clans.commands.command.commandpackage;

import java.util.Observable;
import java.util.Observer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import de.tobiyas.clans.commands.CommandDelegator;
import de.tobiyas.clans.commands.command.CommandInterface;
import de.tobiyas.clans.commands.command.CommandParameter;

public class CommandHelp implements CommandInterface, Observer{

	private String identString = "help";
	
	public CommandHelp(CommandDelegator delegator){
		delegator.addObserver(this);
	}
	
	
	@Override
	public boolean run(Player player, String[] args) {
		if(args.length == 0) return pasteStdHelp(player);
		
		String command = args[0];
		if(command.equals("admin")) return pasteAdminHelp(player);
		if(command.equals("info")) return pasteInfoHelp(player);
		if(command.equals("kick")) return pasteKickHelp(player);
		if(command.equals("invite")) return pasteInviteHelp(player);
		if(command.equals("promote")) return pastePromoteHelp(player);
		if(command.equals("editrank")) return pasteEditrankHelp(player);
		if(command.equals("moneytake")) return pasteMoneytakeHelp(player);
		
		return pasteStdHelp(player);
	}
	
	private boolean pasteAdminHelp(Player player){ //fix
		player.sendMessage(ChatColor.RED + "Help for" + ChatColor.LIGHT_PURPLE + " admin: " + ChatColor.YELLOW + " /clan");
		player.sendMessage(ChatColor.YELLOW + "");
		return true;
	}
	
	private boolean pasteInfoHelp(Player player){ //fix
		player.sendMessage(ChatColor.RED + "Help for " + ChatColor.LIGHT_PURPLE + "info: " + ChatColor.YELLOW + " /clan");
		return true;
	}
	
	private boolean pasteKickHelp(Player player){ //fix
		player.sendMessage(ChatColor.RED + "Help for " + ChatColor.LIGHT_PURPLE + "kick: " + ChatColor.YELLOW + " /clan");
		return true;
	}
	
	private boolean pasteInviteHelp(Player player){ //fix
		player.sendMessage(ChatColor.RED + "Help for " + ChatColor.LIGHT_PURPLE + "invite: " + ChatColor.YELLOW + " /clan");
		return true;
	}
	
	private boolean pastePromoteHelp(Player player){ //fix
		player.sendMessage(ChatColor.RED + "Help for " + ChatColor.LIGHT_PURPLE + "promote: " + ChatColor.YELLOW + " /clan");
		return true;
	}
	
	private boolean pasteEditrankHelp(Player player){ //fix
		player.sendMessage(ChatColor.RED + "Help for " + ChatColor.LIGHT_PURPLE + "editrank: " + ChatColor.YELLOW + " /clan");
		return true;
	}
	
	private boolean pasteMoneytakeHelp(Player player){ //fix
		player.sendMessage(ChatColor.RED + "Help for " + ChatColor.LIGHT_PURPLE + "moneytake: " + ChatColor.YELLOW + " /clan");
		return true;
	}
	
	private boolean pasteStdHelp(Player player){
		player.sendMessage(ChatColor.RED + "Usage:" + ChatColor.YELLOW + " /clan help [admin;info;kick;invite;promote;editrank;moneytake]");
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
