package de.tobiyas.clans.datacontainer.clan;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;

import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.datacontainer.InviteContainer;

public class ClanController {

	private LinkedList<Clan> clans;
	private Clans plugin;
	private InviteContainer invContainer;
	
	public ClanController(){
		plugin = Clans.getPlugin();
		invContainer = new InviteContainer();
		createClans();
	}
	
	private void createClans(){
		clans = new LinkedList<Clan>();
		
		File file = plugin.getDataFolder();
		if(!file.exists())
			file.mkdir();
		
		File clanDir = new File(file + File.separator + "clans");
		
		if(!clanDir.exists()){
			clanDir.mkdir();
			return;
		}
		
		//File filter for directories
		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File file) {
		        return file.isDirectory();
			}
		};
		
		File[] clanFiles = clanDir.listFiles(fileFilter);
				
		for(File clanName : clanFiles){
			clans.add(new Clan(clanName.getName()));
		}
	}
	
	public boolean createNewClan(Player player, String clanName){
		if(getClan(clanName) != null) return false;
		Clan newClan = new Clan(player, clanName);
		
		clans.add(newClan);
		return true;
	}
	
	public boolean createNewClan(String clanName) {
		return createNewClan(null, clanName);
	}
	
	public Clan getClanOfPlayer(Player player){
		return getClanOfPlayer(player.getName());
	}
	
	public Clan getClanOfPlayer(String player){
		for(Clan clan : clans){
			if(clan.hasMember(player)) return clan;
		}
		return null;
	}
	
	public Clan getClan(String clanName){
		for(Clan clan : clans){
			if(clan.getName().equalsIgnoreCase(clanName)) return clan;
		}
		return null;
	}
	
	public InviteContainer getInvContainer(){
		return invContainer;
	}
	
	public void removeClan(String clanName){
		Clan clan = getClan(clanName);
		if(clan == null) return;
		
		clan.delete();
		clans.remove(clan);
	}
}
