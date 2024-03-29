package de.tobiyas.clans.datacontainer.clan;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
		
		if(player != null) clanParser.set("members." + player.getName(), "Leader");
		
		clanParser.set("clanConfig.defaultRank", "Member");
		clanParser.set("clanConfig.leaderRank", "Leader");
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
	
	public Set<Player> getOnlineMembers(){
		Set<Player> online = new HashSet<Player>();
		for(String player : clanParser.getYAMLChildren("members")){
			Player onPlayer = Bukkit.getPlayer(player);
			if(onPlayer != null) online.add(onPlayer);
		}
		
		return online;
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
		if(playerRank == null) return message;
		message = " " + player.getName() + ChatColor.GREEN + ": " + message;
		if(!playerRank.showRankTag()) return ChatColor.GREEN + "[CLAN]" + ChatColor.WHITE + message;
		return ChatColor.GREEN + "[CLAN] " + ChatColor.WHITE + playerRank.getRankTag() + message;
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
	
	public String getLeaderRank() {
		return clanParser.getString("clanConfig.leaderRank");
	}

	public void addMember(Player player, String rankName) {
		addMember(player.getName(), rankName);
	}
	
	public void addMember(String playerName, String rankName){
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
	
	public Set<String> getAllMembers(){
		return clanParser.getYAMLChildren("members");
	}

	public void delete() {
		File folder = new File(clanPath);
		File members = new File(clanPath + "members.yml");
		File ranks = new File(clanPath + "ranks.yml");
		
		ranks.delete();
		members.delete();
		folder.delete();
		plugin.getMoneyManager().deleteBank(clanName);
	}

	public void addNewRank(String rankName, List<String> permissionList) {
		rankContainer.addNewRank(rankName, permissionList);
	}

	public boolean removeRank(String rankName) {
		if(!rankContainer.removeRank(rankName)) return false;
		for(String member : clanParser.getYAMLChildren("members")){
			String rank = clanParser.getString("members." + member);
			if(rank.equalsIgnoreCase(rankName))
				clanParser.set("members." + member, null);
		}
		return true;
	}
}
