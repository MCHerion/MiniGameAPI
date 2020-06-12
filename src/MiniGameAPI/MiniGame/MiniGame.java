package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

import MiniGameAPI.MainClass;
import MiniGameAPI.CustomEvents.PlayerJoinMiniGameEvent;
import MiniGameAPI.CustomPlayer.CustomPlayer;

public abstract class MiniGame<P extends CustomPlayer<?>> implements Listener
{
	protected World _world;
	protected ArrayList<P> _players = new ArrayList<P>();
	protected ArrayList<Player> _spectators = new ArrayList<Player>();
	
	public MiniGame(World world)
	{
		_world = world;
		Bukkit.getPluginManager().registerEvents(this, MainClass.getInstance());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if(getSpectators().contains(event.getPlayer()))
		{
			removeSpectator(event.getPlayer());
		}
	}
	
	public World getWorld()
	{
		return _world;
	}
	
	public ArrayList<P> getCustomPlayers()
	{
		return _players;
	}
	
	public ArrayList<Player> getSpectators()
	{
		return _spectators;
	}
	
	public void joinAsPlayer(P player)
	{
		_players.add(player);
		player.playMiniGame(this);
		player.getPlayer().teleport(_world.getSpawnLocation());
		player.getPlayer().setGameMode(GameMode.SURVIVAL);
		player.getPlayer().getInventory().clear();
		for(PotionEffect effect : player.getPlayer().getActivePotionEffects())
		{
			player.getPlayer().removePotionEffect(effect.getType());
		}
		Bukkit.getPluginManager().callEvent(new PlayerJoinMiniGameEvent(this, player.getPlayer()));
	}
	
	public void removePlayer(P player)
	{
		_players.remove(player);
		_spectators.remove(player.getPlayer());
	}
	
	public void removeSpectator(Player player)
	{
		_spectators.remove(player);
	}
	
	public void lose(P player)
	{
		removePlayer(player);
	}
	
	public void joinAsSpectator(Player player)
	{
		_spectators.add(player);
		player.setGameMode(GameMode.SPECTATOR);
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
		allPlayers.addAll(_spectators);
		for(P player : _players)
		{
			allPlayers.add(player.getPlayer());
		}
		return allPlayers;
	}
	
	public abstract int getMaxPlayers();
}
