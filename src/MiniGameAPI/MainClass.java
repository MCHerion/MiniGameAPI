package MiniGameAPI;

import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin
{
	protected static MainClass _instance;
	@Override
	public void onEnable()
	{
		_instance = this;
	}
	
	public static MainClass getInstance()
	{
		return _instance;
	}
}
