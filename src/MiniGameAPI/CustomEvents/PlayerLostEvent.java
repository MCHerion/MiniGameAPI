package MiniGameAPI.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.Reasons.LoseReason;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;

public class PlayerLostEvent extends Event
{
    protected static final HandlerList HANDLERS = new HandlerList();
    
    protected MiniGame<?> _miniGame;
    protected MiniGamePlayer<?> _loupGarouPlayer;
	protected LoseReason _loseReason;
    
	public PlayerLostEvent(MiniGame<?> miniGame, MiniGamePlayer<?> loupGarouPlayer, LoseReason loseReason) 
	{
		_miniGame = miniGame;
		_loupGarouPlayer = loupGarouPlayer;
		_loseReason = loseReason;
	}
	
	public MiniGame<?> getMiniGame()
	{
		return _miniGame;
	}
	
	public MiniGamePlayer<?> getPlayer()
	{
		return _loupGarouPlayer;
	}
	
	public LoseReason getLoseReason()
	{
		return _loseReason;
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
