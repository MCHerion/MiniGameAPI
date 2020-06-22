package MiniGameAPI.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MiniGameAPI.MiniGame.GameState;
import MiniGameAPI.MiniGame.MiniGame;

public class GameStateActivatedEvent extends Event
{
    private static final HandlerList HANDLERS = new HandlerList();
    
	protected GameState<?> _gameState;
    
	public GameStateActivatedEvent(GameState<?> gameState) 
	{
		_gameState = gameState;
	}
	
	public GameState<?> getGameState()
	{
		return _gameState;
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
