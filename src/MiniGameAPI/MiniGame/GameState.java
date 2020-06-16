package MiniGameAPI.MiniGame;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import MiniGameAPI.MainClass;
import MiniGameAPI.Utils.Actionable;


public abstract class GameState<MG extends MiniGame<?>> implements Listener, Actionable, MiniGameHandler<MG>
{
	protected MG _miniGame;
	protected GameStateType _gameStateType;
	
	public GameState(MG miniGame, GameStateType gameStateType)
	{
		_miniGame = miniGame;
		_gameStateType = gameStateType;
		Bukkit.getPluginManager().registerEvents(this, MainClass.getInstance());
	}
	
	@Override
	public MG getMiniGame()
	{
		return _miniGame;
	}
	
	public GameStateType getGameStateType()
	{
		return _gameStateType;
	}
	
    @Override
    public boolean equals(Object object) 
    {
        return getGameStateType().equals(((GameState<?>)object).getGameStateType());
    }
    
    public void destroy()
    {
    	HandlerList.unregisterAll(this);
    }
}
