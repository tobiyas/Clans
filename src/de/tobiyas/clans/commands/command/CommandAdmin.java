package de.tobiyas.clans.commands.command;

import java.util.Observable;

import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.commands.CommandDelegator;
import de.tobiyas.clans.commands.CommandInterface;
import de.tobiyas.clans.commands.CommandParameter;

public class CommandAdmin implements CommandInterface{

	private Clans plugin;
	private final String identString = "admin";
	
	public CommandAdmin(CommandDelegator delegator){
		plugin = Clans.getPlugin();
		delegator.addObserver(this);
	}
	
	@Override
	public boolean run(Player player, String[] args) {
		// TODO Auto-generated method stub
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
