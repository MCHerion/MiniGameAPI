package MiniGameAPI.CustomEvents;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.Reasons.LoseReason;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;

public class PlayerLosingEvent extends Event implements Cancellable
{
    protected static final HandlerList HANDLERS = new HandlerList();
    
    protected MiniGame<?> _miniGame;
    protected MiniGamePlayer<?> _loupGarouPlayer;
	protected LoseReason _loseReason;
	protected boolean _cancelled;
    
	public PlayerLosingEvent(MiniGame<?> miniGame, MiniGamePlayer<?> loupGarouPlayer, LoseReason loseReason) 
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

	@Override
	public boolean isCancelled()
	{
		return _cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled)
	{
		_cancelled = cancelled;
	}
}
