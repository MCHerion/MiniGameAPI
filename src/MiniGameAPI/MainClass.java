package MiniGameAPI;

import org.bukkit.plugin.java.JavaPlugin;
import MiniGameAPI.Commands.HostCommand;
import MiniGameAPI.MiniGameCustomItems.GameFlagSelectorItem;
import MiniGameAPI.MiniGameCustomItems.MiniGameCustomItem;
import MiniGameAPI.MiniGameCustomItems.SkipItem;
import PluginUtils.Commands.CustomCommand;
import PluginUtils.CustomItems.CustomItemManager;

public class MainClass extends JavaPlugin
{
	protected static MainClass _instance;
		
	@Override
	public void onEnable()
	{
		_instance = this;
		CustomCommand.register(new HostCommand());
		CustomItemManager.getInstance().registerItem(MiniGameCustomItem.GameFlagSelectorItem.name(), new GameFlagSelectorItem());
		CustomItemManager.getInstance().registerItem(MiniGameCustomItem.SkipItem.name(), new SkipItem());
	}
	
	public static MainClass getInstance()
	{
		return _instance;
	}
}
