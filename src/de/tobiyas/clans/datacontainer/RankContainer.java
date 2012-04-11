package de.tobiyas.clans.datacontainer;

import java.util.HashSet;
import java.util.Set;

import de.tobiyas.clans.configuration.YamlConfigExtended;

public class RankContainer {

	private Clan clan;
	private Set<Rank> ranks;
	
	private YamlConfigExtended rankParser;
	
	public RankContainer(Clan clan){
		this.clan = clan;
		this.ranks = new HashSet<Rank>();
		
		rankParser = new YamlConfigExtended(clan.getClanPath() + "ranks.yml");
		rankParser.load();
		
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
