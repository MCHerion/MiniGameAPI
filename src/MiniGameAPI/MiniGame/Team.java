package MiniGameAPI.MiniGame;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;

import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import net.md_5.bungee.api.ChatColor;

public class Team implements MiniGameHandler<MiniGame<?>>
{
	protected MiniGame<?> _miniGame;
	protected TeamTemplate _teamTemplate;
	protected HashMap<MiniGamePlayer<?>, Boolean> _players = new HashMap<MiniGamePlayer<?>, Boolean>();
	
	public Team(MiniGame<?> miniGame, TeamTemplate teamTemplate)
	{
		_miniGame = miniGame;
		_teamTemplate = teamTemplate;
	}
	
	public String getTag()
	{
		return ChatColor.GRAY + "[" + _teamTemplate.getName() + ChatColor.GRAY + "]";
	}
	
	public TeamTemplate getTeamColor()
	{
		return _teamTemplate;
	}
	
	public void addPlayer(MiniGamePlayer<?> player)
	{
		addPlayer(player, false);
	}
	
	public void addPlayer(MiniGamePlayer<?> player, boolean adminMode)
	{
		if(!_miniGame.isFull())
		{
			_players.put(player, adminMode);
			player.setTeam(this);
			
		}
	}
	
	public void removePlayer(MiniGamePlayer<?> player)
	{
		_players.remove(player);
	}
	
	public boolean hasPlayer(MiniGamePlayer<?> player)
	{
		return _players.containsKey(player);
	}
	
	public int getPlayerAmount()
	{
		return getPlayers().size();
	}
	
	public ArrayList<MiniGamePlayer<?>> getPlayers()
	{
		ArrayList<MiniGamePlayer<?>> players = new ArrayList<MiniGamePlayer<?>>();
		players.addAll(_players.keySet());
		return players;
	}

	@Override
	public MiniGame<?> getMiniGame() 
	{
		return _miniGame;
	}
}
