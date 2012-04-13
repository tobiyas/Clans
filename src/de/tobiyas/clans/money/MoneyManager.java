package de.tobiyas.clans.money;

import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.money.plugins.EssentialsEcoMoney;
import de.tobiyas.clans.money.plugins.IconomyMoney;
import de.tobiyas.clans.money.plugins.MoneyPlugin;
import de.tobiyas.clans.money.plugins.VaultMoney;

public class MoneyManager {
	
	private MoneyPlugin moneyPlugin;
	private Clans plugin;
	
	private boolean isActive;
	
	
	public MoneyManager(){
		plugin = Clans.getPlugin();
		isActive = initMoneyPlugin();
		
		if(!isActive)
			plugin.log("No Economy System found.");
	}
	
	private boolean initMoneyPlugin(){
		MoneyPlugin tempPlugin;
		
		tempPlugin = new VaultMoney();
		if(tempPlugin.isActive()){
			moneyPlugin = tempPlugin;
			return true;
		}
		
		tempPlugin = new IconomyMoney();
		if(tempPlugin.isActive()){
			moneyPlugin = tempPlugin;
			return true;
		}
		
		try{
			tempPlugin = new EssentialsEcoMoney();
			if(tempPlugin.isActive()){
				moneyPlugin = tempPlugin;
				return true;
			}
		}catch(NoClassDefFoundError e)
		{}
		
		return false;
	}
	
	public boolean transferPlayerToBank(Player player, String bankName, double amount){
		if(!isActive) return false;
		
		double playerAmount = moneyPlugin.getMoneyOfPlayer(player);
		if(playerAmount < amount) return false;
		
		moneyPlugin.addToBankAccount(bankName, amount);
		return true;
	}
	
	public boolean transferBankToPlayer(Player player, String bankName, double amount){
		if(!isActive) return false;
		
		double bankAmount = moneyPlugin.getBankBalance(bankName);
		if(bankAmount < amount) return false;
		
		moneyPlugin.addMoney(player, amount);
		return true;
	}
	
	public String getActiveEcoName(){
		if(!isActive) return "NONE";
		return moneyPlugin.getName();
	}

	public void createBank(String clanName){
		if(!isActive) return;
		moneyPlugin.createBankAccount(clanName);
	}
	
	public double getBankBalance(String clanName){
		if(!isActive) return 0;
		return moneyPlugin.getBankBalance(clanName);
	}
	
	public void deleteBank(String clanName){
		if(!isActive) return;
		moneyPlugin.removeBankAccount(clanName);
	}

}
