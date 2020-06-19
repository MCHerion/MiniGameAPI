package MiniGameAPI.MiniGamePlayer.Compass;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import MiniGameAPI.MainClass;
import net.md_5.bungee.api.ChatColor;

/**
 * Class that represents a CompassManager, which is used to manage compass pointed at location
 * 
 * @author Elytes
 */
public class CompassManager 
{
	/**
	 * Constant that store the delay (in 1/20 second) between each refresh
	 */
	public static final int REFRESHER_DELAY = 100;
	
	/**
	 * Variable that store the owner of this CompassManager
	 */
	protected Player _player;
	/**
	 * Variable that store the Runnable that'll actualize the compass location
	 */
	protected BukkitRunnable _refresher;
	/**
	 * Variable that store the CompassSelector of this CompassManager
	 */
	protected CompassSelector _compassSelector;
	
	/**
	 * Constructor of CompassManager
	 * 
	 * @param player Player that own this CompassManager
	 */
	public CompassManager(Player player)
	{
		_player = player;
	}
	
	/**
	 * Method used to get the CompassSelector of this CompassManager
	 * 
	 * @return CompassSelector of this CompassManager
	 */
	public CompassSelector getCompassSelector()
	{
		return _compassSelector;
	}
	
	/**
	 * Method used to define the CompassSelector of this CompassManager
	 * 
	 * @param compassSelector New CompassSelector of this CompassManager
	 */
	public void setCompassSelector(CompassSelector compassSelector)
	{
		// If refreshing task isn't null
		if(_refresher != null)
		{
			// Cancel refreshing task
			_refresher.cancel();
		}
		_compassSelector = compassSelector;
		if(_compassSelector != null)
		{
			_refresher = new BukkitRunnable()
			{
				@Override
				public void run()
				{
					// If player is online
					if(_player.isOnline())
					{
						// Getting target of the CompassSelector
						final CompassTarget compassTarget = _compassSelector.getTarget(_player);
						// Scheduling a task synchronously
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask
				        (
				        	MainClass.getInstance(),
			        		new Runnable()
			        		{
								@Override
								public void run()
								{
									// Getting Location of the CompassTarget
									Location location = compassTarget.getLocation();
									// Setting compass target for this player
									_player.setCompassTarget(location);
									if(compassTarget != null)
									{
										// For each compass in player's inventory
										for(ItemStack compass : _player.getInventory().all(Material.COMPASS).values())
										{
											// Getting ItemMeta of this Compass
											ItemMeta meta = compass.getItemMeta();
											String position = ChatColor.GRAY + "Position : " + 
													ChatColor.YELLOW + location.getBlockX() + ChatColor.GOLD + "X " +
													ChatColor.YELLOW + location.getBlockY() + ChatColor.GOLD + "Y " +
													ChatColor.YELLOW + location.getBlockZ() + ChatColor.GOLD + "Z";
											// If the CompassTarget has a name
											if(compassTarget.hasName())
											{
												// Setting display name
												meta.setDisplayName(ChatColor.GRAY + "Cible : " + compassTarget.getName());
												ArrayList<String> lores = new ArrayList<String>();
												lores.add(position);
												lores.add(ChatColor.GRAY + "Distance : " + ChatColor.YELLOW + _player.getLocation().distance(location) + ChatColor.GOLD + " blocs");
												// Setting lores
												meta.setLore(lores);
											}
											// Else if CompassTarget hasn't a name
											else
											{
												// Setting display name
												meta.setDisplayName(position);
												ArrayList<String> lores = new ArrayList<String>();
												lores.add(ChatColor.GRAY + "Distance : " + ChatColor.YELLOW + _player.getLocation().distance(location) + ChatColor.GOLD + " blocs");
												// Setting lores
												meta.setLore(lores);
											}
											compass.setItemMeta(meta);
										}
									}
								}
			        		}
						);
					}
					// Else if player isn't online
					else
					{
						// Canceling this Task
						cancel();
					}
				}
			};
			// Running task Asynchronously
			_refresher.runTaskTimerAsynchronously(MainClass.getInstance(), REFRESHER_DELAY, REFRESHER_DELAY);
		}
	}
	
	public void destroy()
	{
		_refresher.cancel();
		_refresher = null;
	}
}
