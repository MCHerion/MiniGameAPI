package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import MiniGameAPI.MainClass;
import MiniGameAPI.CustomEvents.GameStateActivatedEvent;
import MiniGameAPI.CustomEvents.GameStateDeactivatedEvent;
import MiniGameAPI.MiniGameCustomItems.MiniGameCustomItem;
import MiniGameAPI.Utils.Actionable;
import PluginUtils.Utils.CountDown;
import net.md_5.bungee.api.ChatColor;

/**
 * Class that represents a GameState that is used to follow the MiniGame's progression
 * 
 * @author Elytes
 *
 * @param <MG> Generic type of the MiniGame where this GameState is used
 */
public abstract class GameState<MG extends MiniGame<?>> implements Listener, Actionable, MiniGameHandler<MG>
{
	/**
	 * Variable that store the MiniGame instance where this GameState is used
	 */
	protected MG _miniGame;
	/**
	 * Variable that store the name of this GameState
	 */
	protected String _name;
	protected CountDown _nextGameStateCountDown;
	protected RequestManager<Boolean> _skipRequestVoteManager;
	
	/**
	 * Constructor of the GameState
	 * 
	 * @param miniGame MiniGame instance where this GameState is used
	 * @param name Name of this GameState
	 */
	public GameState(MG miniGame, String name)
	{
		_miniGame = miniGame;
		_name = name;
		_skipRequestVoteManager = new RequestManager<Boolean>()
		{
			@Override
			public ArrayList<Player> getInvolvedPlayers()
			{
				return _miniGame.getPlayers();
			}

			@Override
			public void onAccept(Boolean request) 
			{
				if(request)
				{
					skip();
				}
			}
		};
		// Registering events
		Bukkit.getPluginManager().registerEvents(this, MainClass.getInstance());
	}
	
	@Override
	public void activate()
	{
		if(isSkippable())
		{
			_miniGame.giveItemToAllPlayers(MiniGameCustomItem.SkipItem);
		}
		Bukkit.getPluginManager().callEvent(new GameStateActivatedEvent(this));
	}
	
	@Override
	public void deactivate()
	{
		if(isSkippable())
		{
			_miniGame.removeItemToAllPlayers(MiniGameCustomItem.SkipItem);	
		}
		Bukkit.getPluginManager().callEvent(new GameStateDeactivatedEvent(this));
	}
	
	@Override
	public MG getMiniGame()
	{
		return _miniGame;
	}
	
	public RequestManager<Boolean> getSkipRequestVoteManager()
	{
		return _skipRequestVoteManager;
	}
	
	public boolean isNextGameStateCountDownStarted()
	{
		return _nextGameStateCountDown != null;
	}
	
	public CountDown getNextGameStateCountDown()
	{
		return _nextGameStateCountDown;
	}
	
	public void startNextGameStateCountDown()
	{
		if(_nextGameStateCountDown == null)
		{
			_nextGameStateCountDown = new CountDown(getNextGameStateCountDownTime() * 20, ChatColor.GREEN + (hasNextGameState() ? getNextGameState().getName() : getName()) + " dans " + ChatColor.DARK_GREEN + "%time%" + ChatColor.GREEN + " secondes !")
			{
				@Override
				public void onRun()
				{
					onNextGameStateCountDownRun();
				}
				
				@Override
				public void onCancel()
				{
					_nextGameStateCountDown = null;
					onNextGameStateCountDownCancel();
				}
				
				@Override
				public boolean isCancelled()
				{
					return _miniGame.getGameState() != GameState.this || isNextGameStateCountDownCancelled();
				}

				@Override
				public ArrayList<Player> getPlayers()
				{
					return _miniGame.getAllPlayers();
				}
			};
		}
	}
	
	public void onNextGameStateCountDownRun()
	{
		skip();
	}
	
	public void onNextGameStateCountDownCancel()
	{
		
	}
	
	public boolean isNextGameStateCountDownCancelled()
	{
		return false;
	}
	
	public int getNextGameStateCountDownTime()
	{
		return 30;
	}
	
	/**
	 * Method used to get the name of this GameState
	 * 
	 * @return Name of this GameState
	 */
	public String getName()
	{
		return _name;
	}
	
	/**
	 * Method used to check if this GameState is skippable by the Host or by Player's vote. 
	 * If this GameState is skippable, player's get an item to vote to skip this GameState if there is no Host.
	 *  Else the host will be able to type "/host skip" to skip this GameState
	 * 
	 * @return true if this GameState is skippable, false else
	 */
	public boolean isSkippable()
	{
		return false;
	}
	
	/**
	 * Method used to skip from this GameState to the {@link #getNextGameState()}
	 */
	public void skip()
	{
		_miniGame.changeGameState(getNextGameState());
	}
	
	/**
	 * Method used to store get the next GameState this GameState should change to if it needs to be generic
	 * Use this method only if you create a generic GameState
	 * 
	 * @return Next hypothetical GameState
	 */
	public abstract GameState<?> getNextGameState();
	
	public boolean hasNextGameState()
	{
		return true;
	}
	
	/**
	 * Method used to know if this GameState accept new players to join the MiniGame
	 * 
	 * @return true if new players can join the MiniGame, else false
	 */
	public boolean canJoin()
	{
		return false;
	}
    
	/**
	 * Method used to destroy this GameState if it's not used anymore 
	 * Example: when {@link MiniGame#changeGameState(GameState)} is called, it'll destroy the last GameState
	 */
    public void destroy()
    {
    	HandlerList.unregisterAll(this);
    }
}
