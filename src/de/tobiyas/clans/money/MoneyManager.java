package de.tobiyas.clans.money;

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
		
		tempPlugin = new EssentialsEcoMoney();
		if(tempPlugin.isActive()){
			moneyPlugin = tempPlugin;
			return true;
		}
		
		return false;
	}
	
	
	//needed stuff here.
	
	
	public String getActiveEcoName(){
		if(!isActive) return "NONE";
		return moneyPlugin.getName();
	}

}
