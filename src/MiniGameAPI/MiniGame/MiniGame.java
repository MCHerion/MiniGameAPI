package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

import MiniGameAPI.CustomPlayer.CustomPlayer;

public abstract class MiniGame<P extends CustomPlayer<?>>
{
	protected World _world;
	protected ArrayList<P> _players;
	protected ArrayList<P> _spectators;
	
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
		player.getPlayer().teleport(_world.getSpawnLocation());
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
		return null;
	}
	
	public abstract int getMaxPlayers();
}
