package MiniGameAPI.CustomPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.MiniGameHandler;

public abstract class CustomPlayer<MG extends MiniGame> implements MiniGameHandler<MG>
{
	protected MG _miniGame;
	
	@Override
	public MG getMiniGame()
	{
		return _miniGame;
	}
	
	@SuppressWarnings("unchecked")
	public void playMiniGame(MiniGame miniGame)
	{
		_miniGame = (MG) miniGame;
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
