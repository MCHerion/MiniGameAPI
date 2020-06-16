package MiniGameAPI.MiniGame;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import MiniGameAPI.MainClass;
import MiniGameAPI.CustomEvents.GameStateChangedEvent;
import MiniGameAPI.CustomEvents.PlayerJoinMiniGameEvent;
import MiniGameAPI.CustomEvents.PlayerLosingEvent;
import MiniGameAPI.CustomEvents.PlayerLostEvent;
import MiniGameAPI.CustomPlayer.CustomPlayer;
import MiniGameAPI.MiniGame.LoseReasons.LeftGameLoseReason;
import PluginUtils.Utils.Titles;

public abstract class MiniGame<P extends CustomPlayer<?>> implements Listener
{
	protected World _world;
	protected GameState<?> _gameState;
	protected HashMap<String, P> _players = new HashMap<String, P>();
	protected ArrayList<Player> _spectators = new ArrayList<Player>();
	protected ArrayList<GameFlag<?>> _gameFlags = new ArrayList<GameFlag<?>>();
	
	public MiniGame(World world, GameState<?> gameState)
	{
		_world = world;
		_gameState = gameState;
		Bukkit.getPluginManager().registerEvents(this, MainClass.getInstance());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		if(getSpectators().contains(event.getPlayer()))
		{
			lose(event.getPlayer(), new LeftGameLoseReason());
			removeSpectator(event.getPlayer());
		}
	}
	
	@SuppressWarnings("unchecked")
	public <GF extends GameFlag<?>> GF getGameFlag(Class<GF> gameFlagType)
	{
		GF returnValue = null;
		for(GameFlag<?> gameFlag : _gameFlags)
		{
			if(gameFlag.getClass() == gameFlagType)
			{
				returnValue = (GF) gameFlag;
				break;
			}
		}
		return returnValue;
	}
	
	@SuppressWarnings("unchecked")
	public void addGameFlag(GameFlag<?> gameFlag)
	{
		if(!hasGameFlag((Class<? extends GameFlag<?>>) gameFlag.getClass()))
		{
			_gameFlags.add(gameFlag);
			gameFlag.activate();
		}
	}
	
	public void removeGameFlag(Class<? extends GameFlag<?>> gameFlagType)
	{
		GameFlag<?> toRemove = null;
		for(GameFlag<?> gameFlag : _gameFlags)
		{
			if(gameFlag.getClass() == gameFlagType)
			{
				toRemove = gameFlag;
				break;
			}
		}
		if(toRemove != null)
		{
			toRemove.deactivate();
			_gameFlags.remove(toRemove);
		}
	}
	
	public boolean hasGameFlag(Class<? extends GameFlag<?>> gameFlagType)
	{
		boolean hasGameFlag = false;
		for(GameFlag<?> gameFlag : _gameFlags)
		{
			if(gameFlag.getClass() == gameFlagType)
			{
				hasGameFlag = true;
				break;
			}
		}
		return hasGameFlag;
	}
	
	/**
	 * Method used to dispatch a message to every players in this MiniGame (spectators included)
	 * 
	 * @param message Message to send to these players
	 * @see {@link #dispatchMessage(ArrayList, String)}
	 */
	public void dispatchMessage(String message)
	{
		dispatchMessage(getAllPlayers(), message);
	}
	
	/**
	 * Method used to dispatch a message to a list of specific players in this MiniGame
	 * 
	 * @param players Players to send message to
	 * @param message Message to send to these players
	 * @see {@link #sendMessage(Player, String)}
	 */
	public void dispatchMessage(ArrayList<Player> players, String message)
	{
		for(Player player : players)
		{
			sendMessage(player, message);
		}
	}
	
	/**
	 * Method used to send a message to a specific player
	 * 
	 * @param player Player to send message to
	 * @param message Message to send to this player
	 */
	public void sendMessage(Player player, String message)
	{
		player.sendMessage(getMiniGameTag() + message);
	}
	
	/**
	 * Method used to dispatch a title to all players in this MiniGame (spectators included)
	 * 
	 * @param title Title to dispatch
	 * @see {@link #dispatchTitle(String, String)}
	 */
	public void dispatchTitle(String title)
	{
		dispatchTitle(title, "");
	}

	/**
	 * Method used to dispatch a title and a subtitle to all players in this MiniGame (spectators included)
	 * 
	 * @param title Title to dispatch
	 * @param subtitle Subtitle to dispatch
	 */
	public void dispatchTitle(String title, String subtitle)
	{
		Titles.dispatchTitle(getAllPlayers(), title, subtitle);
	}
	
	/**
	 * Method used to get in which world this MiniGame is affected to
	 * 
	 * @return World this MiniGame is affected to
	 */
	public World getWorld()
	{
		return _world;
	}
	
	/**
	 * Method used to get actual GameState of this MiniGame
	 * 
	 * @return Actual GameState of this MiniGame
	 */
	public GameState<?> getGameState()
	{
		return _gameState;
	}
	
	/**
	 * Method used to change the GameState of this MiniGame
	 * 
	 * @param gameState New GameState to change to
	 */
	public void changeGameState(GameState<?> gameState)
	{
		// Store lastGameState
		GameState<?> lastGameState = _gameState;
		// If _gameState isn't null
		if(_gameState != null)
		{
			// Deactivating the last GameState
			_gameState.deactivate();
			// Destroying the last GameState
			_gameState.destroy();
		}
		// Defining the new GameState
		_gameState = gameState;
		// Activating the new GameState
		_gameState.activate();
		// Calling GameStateChangedEvent
		Bukkit.getPluginManager().callEvent(new GameStateChangedEvent(this, _gameState.getGameStateType(), lastGameState.getGameStateType()));
	}
	
	/**
	 * Method used to get all playing CustomPlayers of this MiniGame
	 * 
	 * @return All playing CustomPlayers of this MiniGame
	 */
	public ArrayList<P> getCustomPlayers()
	{
		ArrayList<P> players = new ArrayList<P>();
		players.addAll(_players.values());
		return players;
	}
	
	/**
	 * Method used to get all spectators of this MiniGame
	 * 
	 * @return All spectators of this MiniGame
	 */
	public ArrayList<Player> getSpectators()
	{
		return _spectators;
	}
	
	/**
	 * Method used to remove a player from this MiniGame
	 * 
	 * @param player Player to remove from this MiniGame
	 */
	public void removePlayer(Player player)
	{
		_players.remove(player.getName());
		_spectators.remove(player);
	}
	
	/**
	 * Method used to remove a spectator from this MiniGame
	 * 
	 * @param player Player to remove from this MiniGame
	 */
	public void removeSpectator(Player player)
	{
		_spectators.remove(player);
	}
	
	/**
	 * Method used to lose a specific player 
	 * 
	 * @param player Player to lose
	 * @param loseReason Lose reason
	 */
	public void lose(Player player, LoseReason loseReason)
	{
		if(getPlayers().contains(player))
		{
			PlayerLosingEvent event = new PlayerLosingEvent(this, getCustomPlayer(player), loseReason);
			Bukkit.getPluginManager().callEvent(event);
			if(!event.isCancelled())
			{
				removePlayer(player.getPlayer());
				joinAsSpectator(player.getPlayer());
				Bukkit.getPluginManager().callEvent(new PlayerLostEvent(this, getCustomPlayer(player), loseReason));
			}
		}
	}
	
	/**
	 * Method used to force a player to join as spectator this MiniGame
	 * 
	 * @param player Player to force to join 
	 */
	public void joinAsSpectator(Player player)
	{
		_spectators.add(player);
		player.setGameMode(GameMode.SPECTATOR);
	}
	/**
	 * Method used to force a player to join as playing player this MiniGame
	 * 
	 * @param player Player to force to join
	 */
	public void joinAsPlayer(Player player)
	{
		if(getPlayers().size() < getMaxPlayers())
		{
			P customPlayer = createPlayer(player);
			_players.put(player.getName(), customPlayer);
			player.teleport(_world.getSpawnLocation());
			player.setGameMode(GameMode.SURVIVAL);
			customPlayer.clearInventory();
			for(PotionEffect effect : player.getActivePotionEffects())
			{
				player.removePotionEffect(effect.getType());
			}
			Bukkit.getPluginManager().callEvent(new PlayerJoinMiniGameEvent(this, player));
		}
		else
		{
			sendMessage(player, "Impossible de rejoindre ce MiniJeu car il est complet");
		}
	}
	
	/**
	 * Method used to get all playing players in this MiniGame
	 * 
	 * @return All playing players 
	 */
	public ArrayList<Player> getPlayers()
	{
		ArrayList<Player> players = new ArrayList<Player>();
		for(P player : getCustomPlayers())
		{
			players.add(player.getPlayer());
		}
		return players;
	}
	
	/**
	 * Method used to get every players (spectators and playing players) in this MiniGame
	 * 
	 * @return Every players (spectators and playing players)
	 */
	public ArrayList<Player> getAllPlayers()
	{
		ArrayList<Player> allPlayers = new ArrayList<Player>();
		allPlayers.addAll(_spectators);
		allPlayers.addAll(getPlayers());
		return allPlayers;
	}
	
	public P getCustomPlayer(Player player)
	{
		return _players.get(player.getName());
	}
	
	/**
	 * Method used to get max players of this MiniGame
	 * 
	 * @return Max players of this MiniGame
	 */
	public abstract int getMaxPlayers();
	
	//ChatColor.GRAY + "[" + ChatColor.RED + "Loup" + ChatColor.GRAY + "-" + ChatColor.DARK_RED + "Garou" + ChatColor.GRAY + "] " 
	public abstract String getMiniGameTag();
	
	public abstract P createPlayer(Player player);
	
	public void destroy()
	{
		HandlerList.unregisterAll(this);
		for(P customPlayer : getCustomPlayers())
		{
			customPlayer.destroy();
		}
	}
}
