package MiniGameAPI.CustomPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import MiniGameAPI.MainClass;
import MiniGameAPI.CustomPlayer.Compass.CompassManager;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.MiniGameHandler;
import MiniGameAPI.MiniGame.Team;

public abstract class CustomPlayer<MG extends MiniGame<?>> implements MiniGameHandler<MG>, Listener
{
	protected String _name;
	protected MG _miniGame;
	protected Team _team;
	protected CompassManager _compassManager;
	
	public CustomPlayer(MG miniGame, String name)
	{
		_name = name;
		_miniGame = miniGame;
		_compassManager = new CompassManager(getPlayer());
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
	
	public Team getTeam()
	{
		return _team;
	}
	
	public void setTeam(Team team)
	{
		_team = team;
	}
	
	public CompassManager getCompassManager()
	{
		return _compassManager;
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
		_team.removePlayer(this);
		_miniGame = null;
	}
}
