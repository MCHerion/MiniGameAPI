package MiniGameAPI.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MiniGameAPI.MiniGame.GameState;
import MiniGameAPI.MiniGame.MiniGame;

public class MiniGameStartedEvent extends Event
{
    private static final HandlerList HANDLERS = new HandlerList();
    
	protected MiniGame<?> _miniGame;
    
	public MiniGameStartedEvent(MiniGame<?> miniGame) 
	{
		_miniGame = miniGame;
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
