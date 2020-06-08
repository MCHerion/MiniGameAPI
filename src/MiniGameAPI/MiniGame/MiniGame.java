package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import MiniGameAPI.CustomEvents.PlayerJoinMiniGameEvent;
import MiniGameAPI.CustomPlayer.CustomPlayer;

public abstract class MiniGame<P extends CustomPlayer<?>>
{
	protected World _world;
	protected ArrayList<P> _players = new ArrayList<P>();
	protected ArrayList<P> _spectators = new ArrayList<P>();
	
	public MiniGame(World world)
	{
		_world = world;
	}
	
	public World getWorld()
	{
		return _world;
	}
	
	public ArrayList<P> getCustomPlayers()
	{
		return _players;
	}
	
	public ArrayList<P> getSpectators()
	{
		return _spectators;
	}
	
	public void joinAsPlayer(P player)
	{
		_players.add(player);
		player.playMiniGame(this);
		player.getPlayer().teleport(_world.getSpawnLocation());
		Bukkit.getPluginManager().callEvent(new PlayerJoinMiniGameEvent(this, player.getPlayer()));
	}
	
	public void removePlayer(P player)
	{
		_players.remove(player);
	}
	
	public void lose(P player)
	{
		removePlayer(player);
		joinAsSpectator(player);
	}
	
	public void joinAsSpectator(P player)
	{
		_spectators.add(player);
		player.getPlayer().setGameMode(GameMode.SPECTATOR);
	}
	
	public ArrayList<Player> getPlayers()
	{
		ArrayList<Player> players = new ArrayList<Player>();
		for(P player : _players)
		{
			players.add(player.getPlayer());
		}
		return players;
	}
	
	public ArrayList<Player> getAllPlayers()
	{
		ArrayList<Player> allPlayers = new ArrayList<Player>();
		for(P player : _spectators)
		{
			allPlayers.add(player.getPlayer());
		}
		for(P player : _players)
		{
			allPlayers.add(player.getPlayer());
		}
		return allPlayers;
	}
	
	public abstract int getMaxPlayers();
}
