package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import MiniGameAPI.CustomPlayer.CustomPlayer;

public class MiniGame<P extends CustomPlayer, G extends GameState<P, MiniGame<P, ? extends GameState<?, ?>>>>
{
	protected G _gameState;
	protected ArrayList<Team<P>> _teams;
	
	public MiniGame(G gameState)
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
	
	public void setGameState(G gameState)
	{
		_gameState.unsubscribe(this);
		_gameState = gameState;
		_gameState.subscribe(this);
	}
	
	public G getGameState()
	{
		return _gameState;
	}
}
