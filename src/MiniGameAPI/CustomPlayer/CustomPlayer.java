package MiniGameAPI.CustomPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import MiniGameAPI.MiniGame.MiniGame;

public abstract class CustomPlayer
{
	protected MiniGame<?> _miniGame;
	
	public MiniGame<?> getMiniGame()
	{
		return _miniGame;
	}
	
	public void playMiniGame(MiniGame<?> miniGame)
	{
		_miniGame = miniGame;
	}
	
	public void leaveMiniGame()
	{
		
	}
	
	public abstract String getName();
	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(getName());
	}
}
