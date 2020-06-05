package MiniGameAPI.CustomEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import MiniGameAPI.MiniGame.MiniGame;

public class PlayerJoinMiniGameEvent extends Event
{
    private static final HandlerList HANDLERS = new HandlerList();
    
	protected MiniGame<?> _miniGame;
	protected Player _player;
    
	public PlayerJoinMiniGameEvent(MiniGame<?> miniGame, Player player) 
	{
		_miniGame = miniGame;
		_player = player;
	}
	
	public Player getPlayer()
	{
		return _player;
	}
	
	public MiniGame<?> getMiniGame()
	{
		return _miniGame;
	}
	
    @Override
	public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
