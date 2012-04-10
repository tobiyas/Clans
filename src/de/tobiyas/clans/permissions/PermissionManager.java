/**
 * @author Toby
 *
 */
package de.tobiyas.clans.permissions;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.permissions.plugins.BukkitPermissionsPermissions;
import de.tobiyas.clans.permissions.plugins.GroupManagerPermissions;
import de.tobiyas.clans.permissions.plugins.PEXPermissions;
import de.tobiyas.clans.permissions.plugins.PermissionPlugin;
import de.tobiyas.clans.permissions.plugins.VaultPermissions;

public class PermissionManager{

	private PermissionPlugin permPlugin;
	private Clans plugin;
	
	private boolean noPermissionsFound;
	
	
	public PermissionManager(){
		plugin = Clans.getPlugin();
		noPermissionsFound = false;
		checkForPermissionsPlugin();
	}
	
	private void checkForPermissionsPlugin(){
		PermissionPlugin tempPlugin;
		try{
			tempPlugin = new VaultPermissions();
			if(tempPlugin.isActive()){
				permPlugin = tempPlugin;
				return;
			}
		}catch(NoClassDefFoundError e){}
		
		try{
			tempPlugin = new PEXPermissions();
			if(tempPlugin.isActive()){
				permPlugin = tempPlugin;
				return;
			}
		}catch(NoClassDefFoundError e){}
		
		try{
			tempPlugin = new GroupManagerPermissions();
			if(tempPlugin.isActive()){
				permPlugin = tempPlugin;
				return;
			}
		}catch(NoClassDefFoundError e){}
		
		try{
			tempPlugin = new BukkitPermissionsPermissions();
			if(tempPlugin.isActive()){
				permPlugin = tempPlugin;
				return;
			}
		}catch(NoClassDefFoundError e){}
		
		plugin.log("CRITICAL: No Permission-System hooked. Plugin will not work properly. Use one of the following Systems: Vault, PermissionsEx, GroupManager, BukkitPermissions.");
		noPermissionsFound = true;
	}

	
	/**
	 * The Check of Permissions on the inited Permission-System
	 * 
	 * @param player the Player to check
	 * @param permissionNode the String to check
	 * @return if the Player has Permissions
	 */
	private boolean checkPermissionsIntern(CommandSender sender, String permissionNode){
		if(noPermissionsFound) return false;
		if(sender == null) return false;
		return permPlugin.getPermissions(sender, permissionNode);
	}
	
	public boolean checkPermissions(CommandSender sender, String permissionNode){
		boolean perm = checkPermissionsIntern(sender, permissionNode);
		if(!perm)
			sender.sendMessage(ChatColor.RED + "You don't have Permissions!");
		
		return perm;
	}
	
	public boolean checkPermissionsSilent(CommandSender sender, String permissionNode){
		return checkPermissionsIntern(sender, permissionNode);
	}
	
	public ArrayList<String> getAllGroups(){
		if(noPermissionsFound) return new ArrayList<String>();
		return permPlugin.getGroups();
	}
	
	public String getGroupOfPlayer(Player player){
		if(noPermissionsFound) return "";
		return permPlugin.getGroupOfPlayer(player);
	}
	
	public String getPermissionsName(){
		if(noPermissionsFound) return "NONE";
		return permPlugin.getName();
	}
	
}
