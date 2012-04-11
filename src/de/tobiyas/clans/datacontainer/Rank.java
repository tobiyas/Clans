package de.tobiyas.clans.datacontainer;

import java.util.List;

import de.tobiyas.clans.configuration.YamlConfigExtended;

public class Rank {
	
	private String rankName;
	private YamlConfigExtended parser;

	public Rank(YamlConfigExtended config, String rankName){
		this.rankName = rankName;
		this.parser = config;
	}
	
	private List<String> getPermissions(){
		return parser.getStringList("rank." + rankName + ".permissions");
	}
	
	private void setPermissions(List<String> permissions){
		parser.set("rank." + rankName + ".permissions", permissions);
		parser.save();
	}
	
	
	private String getRankTag(){
		return parser.getString("rank." + rankName + ".tag", "[" + rankName + "]");
	}
	
	private void setRankTag(String tag){
		parser.set("rank." + rankName + ".tag", tag);
		parser.save();
	}
	
	public boolean showRankTag(){
		return parser.getBoolean("rank." + rankName + ".showtag", false);
	}
	
	private void setShowRankTag(boolean set){
		parser.set("rank." + rankName + ".showtag", set);
		parser.save();
	}
	
	public boolean addPermission(String perm){
		if(hasPermission(perm)) return false;
		List<String> list = getPermissions();
		list.add(perm);
		setPermissions(list);
		return true;
	}
	
	public boolean removePermission(String perm){
		if(!hasPermission(perm)) return false;
		List<String> list = getPermissions();
		list.remove(perm);
		setPermissions(list);
		return true;
	}
	
	public boolean hasPermission(String perm){
		return getPermissions().contains(perm);
	}
	
	public void changeTag(String newTag){
		setRankTag(newTag);
	}
	
	public String getTag(){
		return getRankTag();
	}
	
	public void changeTagVisible(boolean set){
		setShowRankTag(set);
	}
	
	public String getRankName(){
		return rankName;
	}
}
