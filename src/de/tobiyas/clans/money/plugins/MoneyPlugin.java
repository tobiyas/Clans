package de.tobiyas.clans.money.plugins;

import org.bukkit.entity.Player;

public interface MoneyPlugin {
	
	public boolean isActive();

	public double getMoneyOfPlayer(Player player);
	
	public boolean addMoney(Player player, double amount);
	
	public boolean transferMoney(Player from, Player to, double amount);
	
	public double removeMoney(Player player, double amount);
	
	public String getName();
}
