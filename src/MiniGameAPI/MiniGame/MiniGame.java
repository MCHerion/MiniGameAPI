package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import MiniGameAPI.CustomEvents.PlayerJoinMiniGameEvent;
import MiniGameAPI.CustomPlayer.CustomPlayer;

public abstract class MiniGame
{
	protected World _world;
	protected ArrayList<CustomPlayer<?>> _players = new ArrayList<CustomPlayer<?>>();
	protected ArrayList<CustomPlayer<?>> _spectators = new ArrayList<CustomPlayer<?>>();
	
	public MiniGame(World world)
	{
		_world = world;
	}
	
	public World getWorld()
	{
		return _world;
	}
	
	public ArrayList<CustomPlayer<?>> getCustomPlayers()
	{
		return _players;
	}
	
	public ArrayList<CustomPlayer<?>> getSpectators()
	{
		return _spectators;
	}
	
	public void joinAsPlayer(CustomPlayer<?> player)
	{
		_players.add(player);
		player.playMiniGame(this);
		player.getPlayer().teleport(_world.getSpawnLocation());
		Bukkit.getPluginManager().callEvent(new PlayerJoinMiniGameEvent(this, player.getPlayer()));
	}
	
	public void removePlayer(CustomPlayer<?> player)
	{
		_players.remove(player);
	}
	
	public void lose(CustomPlayer<?> player)
	{
		removePlayer(player);
		joinAsSpectator(player);
	}
	
	public void joinAsSpectator(CustomPlayer<?> player)
	{
		_spectators.add(player);
		player.getPlayer().setGameMode(GameMode.SPECTATOR);
	}
	
	public ArrayList<Player> getPlayers()
	{
		ArrayList<Player> players = new ArrayList<Player>();
		for(CustomPlayer<?> player : _players)
		{
			players.add(player.getPlayer());
		}
		return players;
	}
	
	public ArrayList<Player> getAllPlayers()
	{
		ArrayList<Player> allPlayers = new ArrayList<Player>();
		for(CustomPlayer<?> player : _spectators)
		{
			allPlayers.add(player.getPlayer());
		}
		for(CustomPlayer<?> player : _players)
		{
			allPlayers.add(player.getPlayer());
		}
		return allPlayers;
	}
	
	public abstract int getMaxPlayers();
}
