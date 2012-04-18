package de.tobiyas.clans.API;

import de.tobiyas.clans.Clans;

public class MoneyAPI {
	public static boolean giveMoneyToClan(String clanName, double amount){
		return Clans.getPlugin().getMoneyManager().transferToBank(clanName, amount);
	}
	
	public static boolean removeMoneyFromClan(String clanName, double amount){
		return Clans.getPlugin().getMoneyManager().withdrawFromBank(clanName, amount);
	}
}
