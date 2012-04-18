package de.tobiyas.clans.API;

import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;

import de.tobiyas.clans.Clans;
import de.tobiyas.clans.datacontainer.clan.Clan;
import de.tobiyas.clans.datacontainer.rank.Rank;
import de.tobiyas.clans.exceptions.ClanAlreadyExistsException;
import de.tobiyas.clans.exceptions.ClanNotFoundException;
import de.tobiyas.clans.exceptions.RankAlreadyExistsException;
import de.tobiyas.clans.exceptions.RankNotFoundException;

public class ClanAPI {
	public static void createNewClan(String clanName) throws ClanAlreadyExistsException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) throw new ClanAlreadyExistsException();
		plugin.getClanController().createNewClan(clanName);
	}
	
	public static Set<String> getPlayerOfClan(String clanName) throws ClanNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) throw new ClanNotFoundException(clanName);
		return clan.getAllMembers();
	}
	
	public static void addPlayerToClan(String clanName, String rankName, Player player) throws ClanNotFoundException, RankNotFoundException{
		
	}
	
	public static void addPlayerToClan(String clanName, String rankName, String player) throws ClanNotFoundException, RankNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) throw new ClanNotFoundException(clanName);
		Rank rank = clan.getRankByName(rankName);
		if(rank == null) throw new RankNotFoundException();
		clan.addMember(player, rankName);
	}
	
	public static void removeClan(String clanName) throws ClanNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) throw new ClanNotFoundException(clanName);
		plugin.getClanController().removeClan(clanName);
	}
	
	public static String getRankOfPlayer(Player player) throws ClanNotFoundException{
		return ClanAPI.getRankOfPlayer(player.getName());
	}
	
	public static String getRankOfPlayer(String player) throws ClanNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null) throw new ClanNotFoundException(player);
		return "";
	}
	
	public static List<String> getPermissionsOfRank(String clanName, String rankName) throws ClanNotFoundException, RankNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClanOfPlayer(clanName);
		if(clan == null) throw new ClanNotFoundException(clanName);
		Rank rank = clan.getRankByName(rankName);
		if(rank == null) throw new RankNotFoundException();
		return rank.getPermissionList();
	}
	
	public static List<String> getPermissionsOfPlayer(String player) throws ClanNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null) throw new ClanNotFoundException(player);
		Rank rank = clan.getRankOfPlayer(player);
		if(rank == null) throw new ClanNotFoundException(player);
		return rank.getPermissionList();
	}
	
	public static List<String> getPermissionsOfPlayer(Player player) throws ClanNotFoundException{
		return ClanAPI.getPermissionsOfPlayer(player.getName());
	}
	
	public static void changePlayerRank(Player player, String rankName) throws ClanNotFoundException, RankNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClanOfPlayer(player);
		if(clan == null) throw new ClanNotFoundException(player);
		Rank rank = clan.getRankByName(rankName);
		if(rank == null) throw new RankNotFoundException();
		clan.changeRank(player.getName(), rankName);
	}
	
	public static void addRankToClan(String clanName, String rankName, List<String> permissionList) throws ClanNotFoundException, RankAlreadyExistsException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) throw new ClanNotFoundException(clanName);
		Rank rank = clan.getRankByName(rankName);
		if(rank != null) throw new RankAlreadyExistsException();
		clan.addNewRank(rankName, permissionList);
	}
	
	public static void addPermissionToRank(String clanName, String rankName, String permission) throws ClanNotFoundException, RankNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) throw new ClanNotFoundException(clanName);
		Rank rank = clan.getRankByName(rankName);
		if(rank == null) throw new RankNotFoundException();
		rank.addPermission(permission);
	}
	
	public static void removePermissionFromRank(String clanName, String rankName, String permission) throws ClanNotFoundException, RankNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) throw new ClanNotFoundException(clanName);
		Rank rank = clan.getRankByName(rankName);
		if(rank == null) throw new RankNotFoundException();
		rank.removePermission(permission);
	}
	
	public static void setPermissions(String clanName, String rankName, List<String> permissions) throws ClanNotFoundException, RankNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) throw new ClanNotFoundException(clanName);
		Rank rank = clan.getRankByName(rankName);
		if(rank == null) throw new RankNotFoundException();
		rank.setPermissions(permissions);
	}
	
	public static void setRankTag(String clanName, String rankName, String tag) throws ClanNotFoundException, RankNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) throw new ClanNotFoundException(clanName);
		Rank rank = clan.getRankByName(rankName);
		if(rank == null) throw new RankNotFoundException();
		rank.setRankTag(tag);
	}
	
	public static String getRankTag(String clanName, String rankName) throws ClanNotFoundException, RankNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) throw new ClanNotFoundException(clanName);
		Rank rank = clan.getRankByName(rankName);
		if(rank == null) throw new RankNotFoundException();
		return rank.getTag();
	}
	
	public static void removeRankFromClan(String clanName, String rankName) throws ClanNotFoundException, RankNotFoundException{
		Clans plugin = Clans.getPlugin();
		Clan clan = plugin.getClanController().getClan(clanName);
		if(clan == null) throw new ClanNotFoundException(clanName);
		Rank rank = clan.getRankByName(rankName);
		if(rank == null) throw new RankNotFoundException();
		clan.removeRank(rankName);
	}
}
