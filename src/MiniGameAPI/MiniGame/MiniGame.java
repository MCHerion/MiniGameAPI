package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import MiniGameAPI.CustomPlayer.CustomPlayer;

public class MiniGame<P extends CustomPlayer>
{
	protected ArrayList<Team<P>> _teams;
	
	public ArrayList<P> getCustomPlayers()
	{
		return null;
	}
	
	public ArrayList<Player> getPlayers()
	{
		return null;
	}
}
