package de.tobiyas.clans.datacontainer;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.configuration.YamlConfigExtended;

public class Clan {

	private RankContainer rankContainer;
	private HashMap<String, String> members;
	private Clans plugin;
	
	private String clanName;
	private String clanPath;
	
	private YamlConfigExtended clanParser;
	
	
	public Clan(String clanName){
		plugin = Clans.getPlugin();
		this.clanName = clanName;
		clanPath = plugin.getDataFolder() + File.separator + "clans" + File.separator + clanName + File.separator;
		
		loadRanks();
		loadMember();
	}
	
	public boolean hasMember(Player player){
		if(player == null) return false;
		return members.containsKey(player.getName());
	}
	
	
	private void loadRanks(){
		rankContainer = new RankContainer(this);
	}
	
	private void loadMember(){
		clanParser = new YamlConfigExtended(clanPath + "members.yml");
		Set<String> memberNames = clanParser.getYAMLChildren("members");
		members = new HashMap<String, String>();
		
		for(String name : memberNames){
			String rankName = clanParser.getString("members." + name);
			members.put(name, rankName);
		}
	}
	
	public Rank getRankOfPlayer(Player player){
		String playerName = player.getName();
		if(!members.containsKey(playerName)) return null;
		
		return rankContainer.getRank(members.get(playerName));
	}
	
	public String parseChatMessage(Player player, String message){
		Rank playerRank = getRankOfPlayer(player);
		if(!playerRank.showRankTag()) return message;
		return playerRank.getRankName() + message;
	}
	
	public String getName(){
		return clanName;
	}
	
	public String getClanPath(){
		return clanPath;
	}
}
