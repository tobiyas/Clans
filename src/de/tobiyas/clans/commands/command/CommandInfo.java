package de.tobiyas.clans.commands.command;

import java.util.Observable;
import java.util.Observer;

import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.commands.CommandDelegator;
import de.tobiyas.clans.commands.CommandInterface;
import de.tobiyas.clans.commands.CommandParameter;

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
		
		String command = args[0];
		if(command.equalsIgnoreCase("money")) return moneyCommand(player);
		if(command.equalsIgnoreCase("online")) return onlineCommand(player);
		if(command.equalsIgnoreCase("members")) return membersCommand(player);
		if(command.equalsIgnoreCase("permissions")) return permissionsCommand(player); 
		
		return false;
	}
	
	private boolean moneyCommand(Player player){
		player.sendMessage("money: ");
		return false;
	}
	
	private boolean onlineCommand(Player player){
		player.sendMessage("online: ");
		return false;
	}
	
	private boolean membersCommand(Player player){
		player.sendMessage("members: ");
		return false;
	}
	
	private boolean permissionsCommand(Player player){
		player.sendMessage("permission: ");
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
