package MiniGameAPI.MiniGame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;

public abstract class MatchMaking<MG extends MiniGame<?>>
{
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
	
	public void createGame(MiniGamePlayer<?> player)
	{
		if(hasCreatorMode(CreatorMode.HOST))
		{
			// Create MiniGame and set this player as admin of this MiniGame
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
					returnValue = createMiniGame((MiniGameMap) YamlConfiguration.loadConfiguration(new File(_mapsPath + mapName)).get("MiniGameMap"));
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
	
	public abstract MG createMiniGame(MiniGameMap mapName);
	
	public void removeCreatorMode(CreatorMode creatorMode)
	{
		_creatorModes.remove(creatorMode);
	}
	
	public boolean hasCreatorMode(CreatorMode creatorMode)
	{
		return _creatorModes.contains(creatorMode);
	}
}
