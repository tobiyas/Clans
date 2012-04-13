package de.tobiyas.clans.datacontainer.clan;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.configuration.YamlConfigExtended;
import de.tobiyas.clans.datacontainer.rank.Rank;
import de.tobiyas.clans.datacontainer.rank.RankContainer;

public class Clan {

	private RankContainer rankContainer;
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
	
	public Clan(Player player, String clanName){
		plugin = Clans.getPlugin();
		this.clanName = clanName;
		clanPath = plugin.getDataFolder() + File.separator + "clans" + File.separator + clanName + File.separator;
		
		createNewRankContainer(clanName);
		
		clanParser = new YamlConfigExtended(clanPath + "members.yml");
		clanParser.load();
		
		clanParser.createSection("members");
		clanParser.createSection("clanConfig");
		
		clanParser.set("members." + player.getName(), "Leader");
		clanParser.set("clanConfig.defaultRank", "Member");
		clanParser.save();
		
		plugin.getMoneyManager().createBank(clanName);
	}
	
	private void createNewRankContainer(String clanName){
		clanPath = plugin.getDataFolder() + File.separator + "clans" + File.separator + clanName + File.separator;
		File tempFile = new File(clanPath);
		tempFile.mkdirs();
		
		rankContainer = new RankContainer(this);
		rankContainer.initNewContainer();
	}
	
	public boolean hasMember(Player player){
		if(player == null) return false;
		String member = clanParser.getString("members." + player.getName());
		if(member == null || member == "")
			return false;
		return true;
	}
	
	public boolean hasMember(String name){
		String member = clanParser.getString("members." + name);
		if(member == null || member == "")
			return false;
		return true;
	}
	
	public HashMap<String, Set<String>> getMembersRanked(){
		HashMap<String, Set<String>> mem = new HashMap<String, Set<String>>();
		for(String name : clanParser.getYAMLChildren("members")){
			String playerRank = clanParser.getString("members." + name);
			if(!mem.containsKey(playerRank)){
				mem.put(playerRank, new HashSet<String>());
			}
			mem.get(playerRank).add(name);				
		}
		
		return mem;
	}
	
	
	private void loadRanks(){
		rankContainer = new RankContainer(this);
		rankContainer.loadAll();
	}
	
	private void loadMember(){
		clanParser = new YamlConfigExtended(clanPath + "members.yml");
		clanParser.load();
	}
	
	public Rank getRankOfPlayer(Player player){
		return getRankOfPlayer(player.getName());
	}
	
	public Rank getRankOfPlayer(String playerName){
		if(!hasMember(playerName)) return null;
		String rankName = clanParser.getString("members." + playerName);
		return rankContainer.getRank(rankName);
	}
	
	public Rank getRankByName(String rankName){
		return rankContainer.getRank(rankName);
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

	public String getDefaultRank() {
		return clanParser.getString("clanConfig.defaultRank");
	}

	public void addMember(Player player, String rankName) {
		String playerName = player.getName();
		Rank rank = getRankByName(rankName);
		if(rank == null){
			plugin.log("Something gone wrong in adding member: " + playerName + " to rank: " + rankName);
			return;
		}
		clanParser.set("members." + playerName, rank.getRankName());
		clanParser.save();
	}
	
	public void removeMember(Player player){
		clanParser.set("members." + player.getName(), null);
		clanParser.save();
	}
	
	public void removeMember(String playerName){
		clanParser.set("members." + playerName, null);
		clanParser.save();
	}
	
	public void changeRank(String playerName, String newRank){
		if(!hasMember(playerName)) return;
		clanParser.set("members." +playerName, newRank);
		clanParser.save();
	}
	
	public boolean hasPermission(Player player, String permission){
		return hasPermission(player.getName(), permission);
	}
	
	public boolean hasPermission(String playerName, String permission){
		Rank rank = getRankOfPlayer(playerName);
		if(rank == null) return false;
		
		return rank.hasPermission(permission);
	}
}
