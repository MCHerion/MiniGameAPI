package MiniGameAPI.CustomPlayer.Compass;

import org.bukkit.Location;

/**
 * Class that represents a CompassTarget defined by a Location and a String as name of this target
 * 
 * @author Elytes
 */
public class CompassTarget 
{
	/**
	 * Variable that store the location of this target
	 */
	protected Location _location;
	/**
	 * Variable that store the name of this target
	 */
	protected String _name;
	
	/**
	 * Constructor of CompassTarget that has no name, {@link #_name} will be equals to null
	 * 
	 * @param location Location of this target
	 */
	public CompassTarget(Location location)
	{
		this(location, null);
	}
	
	/**
	 * Constructor of CompassTarget
	 * 
	 * @param location Location of this target
	 * @param name Name of this target
	 */
	public CompassTarget(Location location, String name)
	{
		_location = location;
		_name = name;
	}
	
	/**
	 * Method used to get the location of this target
	 * 
	 * @return Location of this target
	 */
	public Location getLocation()
	{
		return _location;
	}
	
	/**
	 * Method used to check if this target has a name
	 * 
	 * @return true if this target has a name, false else
	 */
	public boolean hasName()
	{
		return _name != null;
	}
	
	/**
	 * Method used to get the name of this target
	 * 
	 * @return Name of this target
	 */
	public String getName()
	{
		return _name;
	}
}
