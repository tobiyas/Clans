package de.tobiyas.clans.datacontainer;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;

import de.tobiyas.clans.Clans;

public class ClanController {

	private LinkedList<Clan> clans;
	private Clans plugin;
	
	public ClanController(){
		plugin = Clans.getPlugin();
		createClans();
	}
	
	private void createClans(){
		clans = new LinkedList<Clan>();
		
		File file = plugin.getDataFolder();
		if(!file.exists())
			file.mkdir();
		
		File clanDir = new File(file + "clans");
		
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
	
	public Clan getClan(String clanName){
		for(Clan clan : clans){
			if(clan.getName().equalsIgnoreCase(clanName)) return clan;
		}
		return null;
	}
}
