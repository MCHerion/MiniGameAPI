package MiniGameAPI.CustomEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import MiniGameAPI.CustomPlayer.CustomPlayer;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.Reasons.LoseReason;

public class PlayerLostEvent extends Event
{
    protected static final HandlerList HANDLERS = new HandlerList();
    
    protected MiniGame<?> _miniGame;
    protected CustomPlayer<?> _loupGarouPlayer;
	protected LoseReason _loseReason;
    
	public PlayerLostEvent(MiniGame<?> miniGame, CustomPlayer<?> loupGarouPlayer, LoseReason loseReason) 
	{
		_miniGame = miniGame;
		_loupGarouPlayer = loupGarouPlayer;
		_loseReason = loseReason;
	}
	
	public MiniGame<?> getMiniGame()
	{
		return _miniGame;
	}
	
	public CustomPlayer<?> getPlayer()
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
