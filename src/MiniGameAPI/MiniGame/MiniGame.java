package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import MiniGameAPI.CustomPlayer.CustomPlayer;

public class MiniGame<P extends CustomPlayer>
{
	protected GameState<P, MiniGame<P>> _gameState;
	protected ArrayList<Team<P>> _teams;
	
	public MiniGame(GameState<P, MiniGame<P>> gameState)
	{
		setGameState(gameState);
	}
	
	public ArrayList<P> getCustomPlayers()
	{
		return null;
	}
	
	public ArrayList<Player> getPlayers()
	{
		return null;
	}
	
	public void setGameState(GameState<P, MiniGame<P>> gameState)
	{
		_gameState.unsubscribe(this);
		_gameState = gameState;
		_gameState.subscribe(this);
	}
	
	public GameState<P, MiniGame<P>> getGameState()
	{
		return _gameState;
	}
}
