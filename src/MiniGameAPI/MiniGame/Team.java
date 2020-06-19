package MiniGameAPI.MiniGame;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;

import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import net.md_5.bungee.api.ChatColor;

public class Team implements MiniGameHandler<MiniGame<?>>
{
	protected MiniGame<?> _miniGame;
	protected TeamColor _teamColor;
	protected HashMap<MiniGamePlayer<?>, Boolean> _players = new HashMap<MiniGamePlayer<?>, Boolean>();
	
	public Team(MiniGame<?> miniGame, TeamColor teamColor)
	{
		_miniGame = miniGame;
		_teamColor = teamColor;
	}
	
	public String getTag()
	{
		return ChatColor.GRAY + "[" + _teamColor.getName() + ChatColor.GRAY + "]";
	}
	
	public TeamColor getTeamColor()
	{
		return _teamColor;
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
