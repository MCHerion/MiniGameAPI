package MiniGameAPI.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MiniGameAPI.MiniGame.GameState;

public class GameStateChangedEvent extends Event
{
    protected static final HandlerList HANDLERS = new HandlerList();
    protected GameState _newGameState;
    
	public GameStateChangedEvent(GameState newGameState) 
	{
		_newGameState = newGameState;
	}
	
	public GameState getNewGameState()
	{
		return _newGameState;
	}
	
    @Override
	public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
