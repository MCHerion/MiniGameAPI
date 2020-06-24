package MiniGameAPI.MiniGamePlayer;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import MiniGameAPI.MainClass;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.MiniGameHandler;
import MiniGameAPI.MiniGame.Team;
import MiniGameAPI.MiniGame.GameFlags.Teams.UnknownTeammatesGameFlag;
import MiniGameAPI.MiniGamePlayer.Compass.CompassManager;
import PluginUtils.CustomItems.CustomItemHandler;
import PluginUtils.GUI.GUI;
import PluginUtils.ScoreBoard.BasicScoreBoard;
import PluginUtils.ScoreBoard.ScoreBoardManager;

/**
 * Class that represents a MiniGamePlayer where data will be stored for a specific player
 * 
 * @author Elytes
 *
 * @param <MG> Generic type of the MiniGame where this MiniGamePlayer is playing
 */
public abstract class MiniGamePlayer<MG extends MiniGame<?>> implements MiniGameHandler<MG>, Listener
{
	protected static HashMap<String, MiniGamePlayer<?>> _miniGamePlayers = new HashMap<String, MiniGamePlayer<?>>();
	
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
	protected ScoreBoardManager _scoreBoardManager;
	
	public MiniGamePlayer(MG miniGame, String name)
	{
		_name = name;
		_miniGamePlayers.put(_name, this);
		setDisplayName(_name);
		_miniGame = miniGame;
		_compassManager = new CompassManager(getPlayer());
		_scoreBoardManager = new ScoreBoardManager(getPlayer(), createBasicScoreBoard());
		resetStats();
		Bukkit.getPluginManager().registerEvents(this, MainClass.getInstance());
	}
	
	public abstract BasicScoreBoard createBasicScoreBoard();
	
	public static boolean isInMiniGame(Player player)
	{
		return isInMiniGame(player.getName());
	}
	
	public static boolean isInMiniGame(String name)
	{
		return getMiniGamePlayer(name) != null;
	}
	
	public static MiniGamePlayer<?> getMiniGamePlayer(Player player)
	{
		return getMiniGamePlayer(player.getName());
	}
	
	public static MiniGamePlayer<?> getMiniGamePlayer(String name)
	{
		return _miniGamePlayers.get(name);
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
	
    public void resetStats()
    {
    	getPlayer().setHealth(20);
        getPlayer().setFoodLevel(20);
        getPlayer().setTotalExperience(0);
        getPlayer().setExp(0);
        getPlayer().setFlying(false);
        for (PotionEffect effect : getPlayer().getActivePotionEffects())
        {
            getPlayer().removePotionEffect(effect.getType());
        }
    }
	
	public void clearInventory()
	{
		getPlayer().getInventory().setHelmet(null);
		getPlayer().getInventory().setChestplate(null);
		getPlayer().getInventory().setLeggings(null);
		getPlayer().getInventory().setBoots(null);
		getPlayer().getInventory().clear();
		for(CustomItemHandler customItem : _miniGame.getDefaultItems())
		{
			giveCustomItem(customItem);
		}
	}
	
	public boolean hasCustomItem(CustomItemHandler customItemHandler)
	{
		for(ItemStack item : getPlayer().getInventory().all(customItemHandler.getCustomItem().getType()).values())
		{
			if(customItemHandler.getCustomItem().instanceOfThisItem(item))
			{
				return true;
			}
		}
		return false;
	}
	
	public void giveCustomItem(CustomItemHandler customItemHandler)
	{
		if(!hasCustomItem(customItemHandler))
		{
			getPlayer().getInventory().addItem(customItemHandler.getCustomItem().getItem());
		}
	}
	
	public void removeCustomItem(CustomItemHandler customItemHandler)
	{
		for(ItemStack item : getPlayer().getInventory().all(customItemHandler.getCustomItem().getType()).values())
		{
			if(customItemHandler.getCustomItem().instanceOfThisItem(item))
			{
				getPlayer().getInventory().remove(item);
			}
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
		_miniGamePlayers.put(_name, null);
	}
}
