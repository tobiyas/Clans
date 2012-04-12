package de.tobiyas.clans.commands.command.commandpackage;

import java.util.Observable;
import java.util.Observer;

import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.commands.CommandDelegator;
import de.tobiyas.clans.commands.command.CommandInterface;
import de.tobiyas.clans.commands.command.CommandParameter;

public class CommandMember implements CommandInterface, Observer {

	private Clans plugin;
	private final String identString = "member";
	
	public CommandMember(CommandDelegator delegator){
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
		CommandDelegator delegator = (CommandDelegator) commandDelegator;
			
		Player player = parameter.getPlayer();
		String[] arg = parameter.getArgs();
		delegator.addUnknown(player, 10);

		if(!parameter.getCategory().equals(identString)) return;	
		if(run(player, arg)) delegator.addUnknown(player, 1);
	}

}
