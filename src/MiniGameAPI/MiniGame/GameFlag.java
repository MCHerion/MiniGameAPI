package MiniGameAPI.MiniGame;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import MiniGameAPI.MainClass;
import MiniGameAPI.Utils.Actionable;

public abstract class GameFlag<MG extends MiniGame<?>> implements Listener, Actionable, MiniGameHandler<MG>
{
	protected MG _miniGame;
	protected String _name;
	protected String[] _description;
	
	public GameFlag(MG miniGame, String name, String... description)
	{
		_miniGame = miniGame;
		_name = name;
		_description = description;
		Bukkit.getPluginManager().registerEvents(this, MainClass.getInstance());
	}
	
	@Override
	public MG getMiniGame()
	{
		return _miniGame;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public String[] getDescription()
	{
		return _description;
	}
	
	public boolean isModifiable()
	{
		return false;
	}
}
