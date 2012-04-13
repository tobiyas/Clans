package de.tobiyas.clans.datacontainer.rank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import de.tobiyas.clans.configuration.YamlConfigExtended;
import de.tobiyas.clans.datacontainer.clan.Clan;

public class RankContainer {

	private Clan clan;
	private Set<Rank> ranks;
	
	private YamlConfigExtended rankParser;
	
	public RankContainer(Clan clan){
		this.clan = clan;
		this.ranks = new HashSet<Rank>();
		
		rankParser = new YamlConfigExtended(clan.getClanPath() + "ranks.yml");
		rankParser.load();
	}
	
	public void initNewContainer(){
		ArrayList<String> memberList = new ArrayList<String>();
		memberList.add("chat");
		memberList.add("moneygive");
		memberList.add("info");
		
		Rank member = new Rank(rankParser, "Member");
		member.createNew(memberList);
		
		ArrayList<String> leaderList = new ArrayList<String>();
		leaderList.add("chat");
		leaderList.add("moneygive");
		leaderList.add("info");
		leaderList.add("invite");
		leaderList.add("kick");
		leaderList.add("moneytake");
		leaderList.add("editrank");
		Rank leader = new Rank(rankParser, "Leader");
		leader.createNew(leaderList);
		
		ranks.add(leader);
		ranks.add(member);
	}
	
	public void loadAll(){
		loadRanks();
	}	
	
	private void loadRanks(){
		Set<String> rankStrings = rankParser.getYAMLChildren("rank");
		
		for(String rank : rankStrings){
			ranks.add(new Rank(rankParser, rank));
		}
	}
	
	public Rank getRank(String rankName){
		for(Rank rank : ranks)
			if(rank.getRankName().equalsIgnoreCase(rankName)) return rank;
		
		return null;
	}
	
}
