package de.tobiyas.clans.datacontainer.rank;

import java.util.List;

import de.tobiyas.clans.configuration.YamlConfigExtended;

public class Rank {
	
	private String rankName;
	private YamlConfigExtended parser;

	public Rank(YamlConfigExtended config, String rankName){
		this.rankName = rankName;
		this.parser = config;
	}
	
	public void createNew(List<String> permissions){
		parser.createSection("rank." + rankName);
		setPermissions(permissions);
		setRankTag("[" + rankName + "]");
		setShowRankTag(false);
	}
	
	private List<String> getPermissions(){
		return parser.getStringList("rank." + rankName + ".permissions");
	}
	
	public void setPermissions(List<String> permissions){
		parser.set("rank." + rankName + ".permissions", permissions);
		parser.save();
	}
	
	
	private String getRankTag(){
		return parser.getString("rank." + rankName + ".tag", "[" + rankName + "]");
	}
	
	public void setRankTag(String tag){
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
	
	public List<String> getPermissionList(){
		return parser.getStringList("rank." + rankName + ".permissions");
	}
	
	public String getPermissionString(){
		List<String> perms = getPermissionList();
		String perm = "";
		for(String permission : perms){
			perm += permission + ", ";
		}
		if(perm == "") return "";
		
		perm = perm.substring(0, perm.length() - 2);
		return perm;
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

	public void remove() {
		parser.set("ranks." + rankName + ".permissions", null);
		parser.set("ranks." + rankName + ".tag", null);
		parser.set("ranks." + rankName + ".showtag", null);
		parser.set("ranks." + rankName, null);
		
		parser.save();
	}
}
