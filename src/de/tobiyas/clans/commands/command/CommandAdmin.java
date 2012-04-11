package de.tobiyas.clans.commands.command;

import java.util.Observable;
import java.util.Observer;

import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.commands.CommandDelegator;
import de.tobiyas.clans.commands.CommandInterface;
import de.tobiyas.clans.commands.CommandParameter;

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
		
		if(command.equalsIgnoreCase("takemoney")) return takeMoneyCommand(player, newArgs);
		
		if(command.equalsIgnoreCase("invite")) return inviteCommand(player, newArgs);
		
		if(command.equalsIgnoreCase("kick")) return kickCommand(player, newArgs);
		
		return false;		
	}
	
	private boolean promoteCommand(Player player, String[] args){
		
		return false;
	}
	
	private boolean editRankCommand(Player player, String[] args){
		
		return false;
	}
	
	private boolean takeMoneyCommand(Player player, String[] args){
		
		return false;
	}
	
	private boolean inviteCommand(Player player, String[] args){
		
		return false;
	}
	
	private boolean kickCommand(Player player, String[] args){
		
		return false;
	}
	
	

	@Override
	public void update(Observable commandDelegator, Object args) {
		CommandParameter parameter = (CommandParameter) args;
		if(!parameter.getCategory().equals(identString)) return;
		
		Player player = parameter.getPlayer();
		String[] arg = parameter.getArgs();
		
		run(player, arg);
	}

}
