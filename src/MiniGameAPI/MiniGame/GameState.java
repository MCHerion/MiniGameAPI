package MiniGameAPI.MiniGame;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import MiniGameAPI.MainClass;
import MiniGameAPI.Utils.Actionable;

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
		// Registering events
		Bukkit.getPluginManager().registerEvents(this, MainClass.getInstance());
	}
	
	@Override
	public MG getMiniGame()
	{
		return _miniGame;
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
	 * Method used to store get the next GameState this GameState should change to if it needs to be generic
	 * Use this method only if you create a generic GameState
	 * 
	 * @return Next hypothetical GameState
	 */
	public abstract GameState<?> getNextGameState();
	
	/**
	 * Method used to know if this GameState accept new players to join the MiniGame
	 * 
	 * @return true if new players can join the MiniGame, else false
	 */
	public abstract boolean canJoin();
    
	/**
	 * Method used to destroy this GameState if it's not used anymore 
	 * Example: when {@link MiniGame#changeGameState(GameState)} is called, it'll destroy the last GameState
	 */
    public void destroy()
    {
    	HandlerList.unregisterAll(this);
    }
}
