package MiniGameAPI.CustomPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import LoupGarou.CustomItems.CustomItem;
import MiniGameAPI.MainClass;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.MiniGameHandler;

public abstract class CustomPlayer<MG extends MiniGame<?>> implements MiniGameHandler<MG>, Listener
{
	protected String _name;
	protected MG _miniGame;
	
	public CustomPlayer(MG miniGame, String name)
	{
		_name = name;
		_miniGame = miniGame;
		Bukkit.getPluginManager().registerEvents(this, MainClass.getInstance());
	}
	
	public void clearInventory()
	{
		getPlayer().getInventory().setHelmet(null);
		getPlayer().getInventory().setChestplate(null);
		getPlayer().getInventory().setLeggings(null);
		getPlayer().getInventory().setBoots(null);
		getPlayer().getInventory().clear();
		for(CustomItem customItem : _customItems)
		{
			getPlayer().getInventory().addItem(customItem.getItem());
		}
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
	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(getName());
	}
	
	public void destroy()
	{
		HandlerList.unregisterAll(this);
		_miniGame = null;
	}
}
