package MiniGameAPI.MiniGame;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldBorder;
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
import MiniGameAPI.CustomPlayer.Compass.CompassSelector;
import MiniGameAPI.MiniGame.Reasons.LoseReason;
import MiniGameAPI.MiniGame.Reasons.WinReason;
import MiniGameAPI.MiniGame.Reasons.LoseReasons.LeftGameLoseReason;
import PluginUtils.Utils.Titles;

/**
 * 
 * Class that represents a MiniGame
 * 
 * @author Elytes
 *
 * @param <P> Type of CustomPlayer that'll be used for users.
 */
public abstract class MiniGame<P extends CustomPlayer<?>> implements Listener
{
	/**
	 * Variable that store the World owned by this MiniGame
	 */
	protected World _world;
	/**
	 * Variable that store the actual GameState of this MiniGame
	 */
	protected GameState<?> _gameState;
	/**
	 * Variable that store CustomPlayers associated by the name of the player that own it
	 */
	protected HashMap<String, P> _players = new HashMap<String, P>();
	/**
	 * Variable that store all spectators
	 */
	protected ArrayList<Player> _spectators = new ArrayList<Player>();
	/**
	 * Variable that store every GameFlag of this MiniGame
	 */
	protected ArrayList<GameFlag<?>> _gameFlags = new ArrayList<GameFlag<?>>();
	/**
	 * Variable that store the actual GameMode of playing players in this MiniGame
	 */
	protected TeamManager _teamManager;	
	/**
	 * Variable that store the TeamSelector that define how the player will be able to get his team
	 */
	protected TeamSelector _teamSelector = TeamSelector.NO_TEAM;
	/**
	 * Variable that store the default GameMode of this MiniGame
	 */
	protected GameMode _gameMode = GameMode.SURVIVAL;
	/**
	 * Variable that store the default CompassSelector for every players
	 */
	protected CompassSelector _compassSelector;
	/**
	 * Variable that store the maximum players in this MiniGame
	 * If TeamMode is {@link TeamSelector#NO_TEAM}, this number will result to max players for this whole MiniGame
	 * Else if TeamMode is {@link TeamSelector#FORCED} or {@link TeamSelector#CHOICE}, this number will result to max players per team
	 */
	protected int _maxPlayers;

	
	/**
	 * Constructor of the MiniGame
	 * 
	 * @param world World that'll be owned by this MiniGame
	 * @param gameState Starting GameState of this MiniGame
	 */
	public MiniGame(World world, GameState<?> gameState, int maxPlayers)
	{
		_world = world;
		generateWorld(_world);
		_gameState = gameState;
		_maxPlayers = maxPlayers;
		_teamManager = new TeamManager(this);
		// Registering events
		Bukkit.getPluginManager().registerEvents(this, MainClass.getInstance());
	}
	
	/**
	 * Method that'll get called when a Player leave the server
	 * 
	 * @param event Instance of the called event
	 */
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		// If this MiniGame contains the Player that fired this event as a playing player
		if(getPlayers().contains(event.getPlayer()))
		{
			// Force this player to lose and defining the reason to LeftGameLoseReason
			lose(event.getPlayer(), new LeftGameLoseReason());
		}
		// Else if this MiniGame contains the Player that fired this event as a Spectator
		else if(getSpectators().contains(event.getPlayer()))
		{
			// Removing this player from spectators
			removeSpectator(event.getPlayer());
		}
	}
	
	public WorldBorder getWorldBorder()
	{
		return getWorld().getWorldBorder();
	}
	
	public TeamManager getTeamManager()
	{
		return _teamManager;
	}
	
	public TeamSelector getTeamSelector()
	{
		return _teamSelector;
	}
	
	public void changeGameMode(GameMode gameMode)
	{
		_gameMode = gameMode;
		for(Player player : getPlayers())
		{
			player.setGameMode(_gameMode);
		}
	}
	
	public void setCompassSelector(CompassSelector compassSelector)
	{
		_compassSelector = compassSelector;
		for(P player : getCustomPlayers())
		{
			player.getCompassManager().setCompassSelector(_compassSelector);
		}
	}
	
	/**
	 * Method used to get a specific GameFlag by his type
	 * 
	 * @param <GF> Generic type of the GameFlag we want to get
	 * @param gameFlagType Class that represents the type of the GameFlag we want to get
	 * @return GameFlag associated to this Type, if can't get found, return null
	 */
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
	
	/**
	 * Method used to add a specific GameFlag to this MiniGame
	 * 
	 * @param gameFlag GameFlag we want to add 
	 */
	@SuppressWarnings("unchecked")
	public void addGameFlag(GameFlag<?> gameFlag)
	{
		if(!hasGameFlag((Class<? extends GameFlag<?>>) gameFlag.getClass()))
		{
			_gameFlags.add(gameFlag);
			gameFlag.activate();
		}
	}
	
	/**
	 * Method used to remove a GameFlag from this MiniGame
	 * 
	 * @param gameFlagType Class that represents the type of the GameFlag we want to remove
	 */
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
	
	/**
	 * Method used to check if this MiniGame contains a specific flag
	 * 
	 * @param gameFlagType Class that represents the type of the GameFlag we are searching for
	 * @return The GameFlag associated at the specified type
	 */
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
	
	public void win(WinReason winReason)
	{
		
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
		if(!isFull())
		{
			P customPlayer = createPlayer(player);
			_players.put(player.getName(), customPlayer);
			player.teleport(_world.getSpawnLocation());
			player.setGameMode(_gameMode);
			customPlayer.clearInventory();
			customPlayer.getCompassManager().setCompassSelector(_compassSelector);
			for(PotionEffect effect : player.getActivePotionEffects())
			{
				player.removePotionEffect(effect.getType());
			}
			Team team = _teamManager.findTeam();
			if(team != null)
			{
				team.addPlayer(customPlayer);
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
	
	/**
	 * Method used to get a CustomPlayer by a specific Player object
	 * @param player Player we want to get CustomPlayer of
	 * @return CustomPlayer associated to this Player
	 */
	public P getCustomPlayer(Player player)
	{
		return _players.get(player.getName());
	}
	
	public boolean isFull()
	{
		return getPlayersAmount() >= getMaxPlayers();
	}
	
	public int getPlayersAmount()
	{
		return getPlayers().size();
	}
	
	/**
	 * Method used to get max players of this MiniGame
	 * 
	 * @return Max players of this MiniGame
	 */
	public int getMaxPlayers()
	{
		if(_teamSelector == TeamSelector.NO_TEAM)
		{
			return _maxPlayers;
		}
		else if(_teamSelector == TeamSelector.CHOICE || _teamSelector == TeamSelector.FORCED)
		{
			return _teamManager.getTeams().size() * _maxPlayers;
		}
		else
		{
			return _maxPlayers;
		}
	}
	
	/**
	 * Method used to get this MiniGame tag which will be used to send messages for example
	 * 
	 * @return Tag of this MiniGame
	 */
	//ChatColor.GRAY + "[" + ChatColor.RED + "Loup" + ChatColor.GRAY + "-" + ChatColor.DARK_RED + "Garou" + ChatColor.GRAY + "] " 
	public abstract String getMiniGameTag();
	
	/**
	 * Method used to create a CustomPlayer object (P type)
	 * 
	 * @param player Player we want to create a CustomPlayer for
	 * @return Created CustomPlayer
	 */
	public abstract P createPlayer(Player player);
	
	public void generateWorld(World world)
	{
		
	}
	
	public void destroy()
	{
		HandlerList.unregisterAll(this);
		for(GameFlag<?> gameFlag : _gameFlags)
		{
			gameFlag.destroy();
		}
		for(P customPlayer : getCustomPlayers())
		{
			customPlayer.destroy();
		}
	}
}
