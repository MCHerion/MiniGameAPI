package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.entity.Player;

import MiniGameAPI.CustomPlayer.CustomPlayer;

public class MiniGame<P extends CustomPlayer>
{
	protected World _world;
	protected ArrayList<Team<P>> _teams;
	
	public MiniGame(World world)
	{
		_world = world;
	}
	
	public World getWorld()
	{
		return _world;
	}
	
	public ArrayList<P> getCustomPlayers()
	{
		return null;
	}
	
	public ArrayList<Player> getPlayers()
	{
		return null;
	}
}
