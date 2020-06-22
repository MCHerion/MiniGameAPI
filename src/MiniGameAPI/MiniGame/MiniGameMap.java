package MiniGameAPI.MiniGame;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import PluginUtils.Serializer.Serializer;
import PluginUtils.Serializer.Serializer.Serializable;

public class MiniGameMap implements ConfigurationSerializable
{
	@Serializable
	protected String _world;
	
	public String getMapName()
	{
		return _world;
	}
	
	public void setWorld(String world)
	{
		_world = world;
	}
	
	public World getWorld()
	{
		return Bukkit.getWorld(_world);
	}
	
	@Override
	public Map<String, Object> serialize()
	{
		return Serializer.serializeThis(this);
	}

	public static MiniGameMap deserialize(Map<String, Object> map)
	{
		return (MiniGameMap) Serializer.deserializeThis(new MiniGameMap(), map);
	}
}
