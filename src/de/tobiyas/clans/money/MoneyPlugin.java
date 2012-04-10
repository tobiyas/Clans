package de.tobiyas.clans.money;

import org.bukkit.entity.Player;

public interface MoneyPlugin {

	public double getMoneyOfPlayer(Player player);
	
	public void addMoney(Player player, double amount);
	
	public void transferMoney(Player from, Player to, double amount);
	
	public double removeMoney(Player player, double amount);
}
