package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import MiniGameAPI.MainClass;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;
import MiniGameAPI.Utils.Actionable;

public abstract class GameFlag<MG extends MiniGame<?>> implements Listener, Actionable, MiniGameHandler<MG>
{
	protected MG _miniGame;
	
	public GameFlag(MG miniGame)
	{
		_miniGame = miniGame;
		Bukkit.getPluginManager().registerEvents(this, MainClass.getInstance());
	}
	
	@Override
	public MG getMiniGame()
	{
		return _miniGame;
	}
	
	public GameFlagInfos getGameFlagInfos()
	{
		return getClass().getAnnotation(GameFlagInfos.class);
	}
	
	public String getName()
	{
		return getGameFlagInfos().name();
	}
	
	public String[] getDescription()
	{
		return getGameFlagInfos().description();
	}
	
	public void destroy()
	{
		HandlerList.unregisterAll(this);
	}
}
