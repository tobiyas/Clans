package de.tobiyas.clans.commands;

import java.util.Observable;
import java.util.Observer;

import org.bukkit.entity.Player;

public interface CommandInterface extends Observer{

	public boolean run(Player player, String[] args);
	
	@Override
	void update(Observable commandDelegator, Object args);
}
