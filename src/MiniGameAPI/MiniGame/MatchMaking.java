package MiniGameAPI.MiniGame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;

public abstract class MatchMaking<MG extends MiniGame<?>>
{
	protected static MatchMaking<?> _instance;
	
	protected ArrayList<CreatorMode> _creatorModes = new ArrayList<CreatorMode>();
	protected HashMap<String, MG> _miniGames = new HashMap<String, MG>();
	protected String _mapsPath;
	
	public MatchMaking(String mapPath)
	{
		_mapsPath = mapPath;
		for(CreatorMode creatorMode : CreatorMode.values())
		{
			_creatorModes.add(creatorMode);
		}
		loadMaps();		
	}
	
	public void hub(Player player)
	{
		// Add game selector items
	}
	
	public void loadMaps()
	{
		File mapsFolder = new File(_mapsPath);
		if(mapsFolder.listFiles() != null)
		{
			for(File file : mapsFolder.listFiles())
			{
				_miniGames.put(file.getName(), null);
			}
		}
	}
	
	public boolean mapExists(String mapName)
	{
		return _miniGames.containsKey(mapName);
	}
	
	public boolean mapUsed(String mapName)
	{
		return _miniGames.get(mapName) != null;
	}
	
	public void createHostGame(Player player, String mapName)
	{
		if(hasCreatorMode(CreatorMode.HOST))
		{
			if(mapExists(mapName) && !mapUsed(mapName))
			{
				MG miniGame = createMiniGame((MiniGameMap) YamlConfiguration.loadConfiguration(new File(_mapsPath + mapName)).get("MiniGameMap"), CreatorMode.HOST);
				_miniGames.put(mapName, miniGame);
				miniGame.joinAsPlayer(player);
				MiniGamePlayer.getMiniGamePlayer(player).setAdminMode(true);
			}
		}
	}
	
	public MG findGame()
	{
		if(hasCreatorMode(CreatorMode.PARTIE_A_LA_DEMANDE))
		{
			MG returnValue = null;
			for(String mapName : _miniGames.keySet())
			{
				MG miniGame = _miniGames.get(mapName);
				if(miniGame != null && miniGame.getGameState().canJoin() && !miniGame.isFull())
				{
					returnValue = miniGame;
					break;
				}
				else if(miniGame == null)
				{
					returnValue = createMiniGame((MiniGameMap) YamlConfiguration.loadConfiguration(new File(_mapsPath + mapName)).get("MiniGameMap"), CreatorMode.PARTIE_A_LA_DEMANDE);
					_miniGames.put(mapName, returnValue);
					break;
				}
			}
			return returnValue;
		}
		else
		{
			return null;
		}
	}
	
	public abstract MG createMiniGame(MiniGameMap mapName, CreatorMode creatorMode);
	
	public ArrayList<CreatorMode> getCreatorModes()
	{
		return _creatorModes;
	}
	
	public void removeCreatorMode(CreatorMode creatorMode)
	{
		_creatorModes.remove(creatorMode);
	}
	
	public boolean hasCreatorMode(CreatorMode creatorMode)
	{
		return _creatorModes.contains(creatorMode);
	}
	
	public static MatchMaking<?> getInstance()
	{
		return _instance;
	}
}
