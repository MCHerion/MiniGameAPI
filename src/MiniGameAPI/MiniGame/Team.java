package MiniGameAPI.MiniGame;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;

import MiniGameAPI.CustomPlayer.CustomPlayer;

public class Team implements MiniGameHandler<MiniGame<?>>
{
	protected MiniGame<?> _miniGame;
	protected String _name;
	protected Material _icone;
	protected HashMap<CustomPlayer<?>, Boolean> _players = new HashMap<CustomPlayer<?>, Boolean>();
	
	public Team(MiniGame<?> miniGame, String name, Material icone)
	{
		_miniGame = miniGame;
		_name = name;
		_icone = icone;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void setName(String name)
	{
		_name = name;
	}
	
	public Material getIcone()
	{
		return _icone;
	}
	
	public void setIcone(Material icone)
	{
		_icone = icone;
	}
	
	public void addPlayer(CustomPlayer<?> player)
	{
		addPlayer(player, false);
	}
	
	public void addPlayer(CustomPlayer<?> player, boolean adminMode)
	{
		if(!_miniGame.isFull())
		{
			_players.put(player, adminMode);
			player.setTeam(this);
		}
	}
	
	public void removePlayer(CustomPlayer<?> player)
	{
		_players.remove(player);
	}
	
	public boolean hasPlayer(CustomPlayer<?> player)
	{
		return _players.containsKey(player);
	}
	
	public int getPlayerAmount()
	{
		return getPlayers().size();
	}
	
	public ArrayList<CustomPlayer<?>> getPlayers()
	{
		ArrayList<CustomPlayer<?>> players = new ArrayList<CustomPlayer<?>>();
		players.addAll(_players.keySet());
		return players;
	}

	@Override
	public MiniGame<?> getMiniGame() 
	{
		return _miniGame;
	}
}
