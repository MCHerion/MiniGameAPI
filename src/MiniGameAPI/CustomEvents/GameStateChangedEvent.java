package MiniGameAPI.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MiniGameAPI.MiniGame.GameStateType;
import MiniGameAPI.MiniGame.MiniGame;

public class GameStateChangedEvent extends Event
{
    private static final HandlerList HANDLERS = new HandlerList();
    
	protected MiniGame<?> _miniGame;
	protected GameStateType _newGameStateType;
	protected GameStateType _lastGameStateType;
    
	public GameStateChangedEvent(MiniGame<?> miniGame, GameStateType newGameStateType, GameStateType lastGameStateType) 
	{
		_miniGame = miniGame;
		_newGameStateType = newGameStateType;
		_lastGameStateType = lastGameStateType;
	}
	
	public GameStateType getNewGameState()
	{
		return _newGameStateType;
	}
	
	public GameStateType getLastGameState()
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
