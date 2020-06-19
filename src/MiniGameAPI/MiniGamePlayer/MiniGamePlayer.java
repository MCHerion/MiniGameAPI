package MiniGameAPI.MiniGamePlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import MiniGameAPI.MainClass;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.MiniGameHandler;
import MiniGameAPI.MiniGame.Team;
import MiniGameAPI.MiniGame.GameFlags.Teams.UnknownTeammatesGameFlag;
import MiniGameAPI.MiniGamePlayer.Compass.CompassManager;
import PluginUtils.CustomItems.CustomItem;
import PluginUtils.GUI.GUI;

/**
 * Class that represents a MiniGamePlayer where data will be stored for a specific player
 * 
 * @author Elytes
 *
 * @param <MG> Generic type of the MiniGame where this MiniGamePlayer is playing
 */
public abstract class MiniGamePlayer<MG extends MiniGame<?>> implements MiniGameHandler<MG>, Listener
{
	/**
	 * Variable that store the name of this Player
	 */
	protected String _name;
	/**
	 * Variable that store the MiniGame where this Player is playing
	 */
	protected MG _miniGame;
	/**
	 * Variable that store the Team of this Player
	 */
	protected Team _team;
	/**
	 * Variable that store the CompassManager of this Player
	 */
	protected CompassManager _compassManager;
	protected GUI _gui;
	protected boolean _adminMode = false;
	protected String _displayName;
	
	public MiniGamePlayer(MG miniGame, String name)
	{
		_name = name;
		setDisplayName(_name);
		_miniGame = miniGame;
		_compassManager = new CompassManager(getPlayer());
		Bukkit.getPluginManager().registerEvents(this, MainClass.getInstance());
	}
	
	public void setDisplayName(String displayName)
	{
		_displayName = displayName;
		refreshDisplayName();
	}
	
	protected void refreshDisplayName()
	{
		getPlayer().setPlayerListName(getDisplayName());
	}
	
	public String getDisplayName()
	{
		if(!_miniGame.hasGameFlag(UnknownTeammatesGameFlag.class))
		{
			return _team.getTag() + " " + _displayName;
		}
		else
		{
			return _displayName;
		}
	}
	
	public GUI getGUI()
	{
		if(_gui == null)
		{
			_gui = new GUI(getPlayer());
		}
		return _gui;
	}
	
	public void clearInventory()
	{
		getPlayer().getInventory().setHelmet(null);
		getPlayer().getInventory().setChestplate(null);
		getPlayer().getInventory().setLeggings(null);
		getPlayer().getInventory().setBoots(null);
		getPlayer().getInventory().clear();
		for(CustomItem customItem : _miniGame.getDefaultItems())
		{
			getPlayer().getInventory().addItem(customItem.getItem());
		}
	}
	
	@Override
	public MG getMiniGame()
	{
		return _miniGame;
	}
	
	public boolean getAdminMode()
	{
		return _adminMode;
	}
	
	public void setAdminMode(boolean adminMode)
	{
		_adminMode = adminMode;
	}
	
	/**
	 * Method used to get the Team of this Player
	 * 
	 * @return Team of this Player
	 */
	public Team getTeam()
	{
		return _team;
	}
	
	/**
	 * Method used to define the Team of this Player
	 * 
	 * @param team New team of this Player
	 */
	public void setTeam(Team team)
	{
		_team = team;
	}
	
	/**
	 * Method used to get the CompassManager of this Player
	 * 
	 * @return CompassManager instance associated to this Player
	 */
	public CompassManager getCompassManager()
	{
		return _compassManager;
	}
	
	/**
	 * Method used to get the name of the Player associated to this MiniGamePlayer
	 * 
	 * @return Name of this Player
	 */
	public String getName()
	{
		return _name;
	}
	
	/**
	 * Method used to get the Player object associated to this MiniGamePlayer
	 * 
	 * @return Player object associated to this MiniGamePlayer
	 */
	public Player getPlayer()
	{
		return Bukkit.getPlayer(getName());
	}
	
	/**
	 * Method used to destroy this MiniGamePlayer instance when it's not used anymore
	 */
	public void destroy()
	{
		// Unregistering all events which are listened by this object
		HandlerList.unregisterAll(this);
		_team.removePlayer(this);
		_miniGame = null;
	}
}
