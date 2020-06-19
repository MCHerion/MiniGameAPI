package MiniGameAPI.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MiniGameAPI.MiniGame.GameState;
import MiniGameAPI.MiniGame.MiniGame;

public class GameStateChangedEvent extends Event
{
    private static final HandlerList HANDLERS = new HandlerList();
    
	protected MiniGame<?> _miniGame;
	protected GameState<?> _newGameStateType;
	protected GameState<?> _lastGameStateType;
    
	public GameStateChangedEvent(MiniGame<?> miniGame, GameState<?> newGameStateType, GameState<?> lastGameStateType) 
	{
		_miniGame = miniGame;
		_newGameStateType = newGameStateType;
		_lastGameStateType = lastGameStateType;
	}
	
	public GameState<?> getNewGameState()
	{
		return _newGameStateType;
	}
	
	public GameState<?> getLastGameState()
	{
		return _lastGameStateType;
	}
	
	public MiniGame<?> getMiniGame()
	{
		return _miniGame;
	}
	
    @Override
	public HandlerList getHandlers() 
    {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() 
    {
        return HANDLERS;
    }
}
