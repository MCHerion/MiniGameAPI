package MiniGameAPI.MiniGame.GameStates;

import org.bukkit.event.EventHandler;

import MiniGameAPI.CustomEvents.GameStateActivatedEvent;
import MiniGameAPI.MiniGame.GameState;
import MiniGameAPI.MiniGame.MiniGame;

public class EndGameState<MG extends MiniGame<?>> extends GameState<MG>
{
	public EndGameState(MG miniGame)
	{
		super(miniGame, "Fin");
	}
	
	@Override
	public int getNextGameStateCountDownTime()
	{
		return 10;
	}
	
	@EventHandler
	public void onGameStateActivated(GameStateActivatedEvent event)
	{
		if(event.getGameState() == this)
		{
			startNextGameStateCountDown();
		}
	}
	
	@Override
	public void onNextGameStateCountDownRun()
	{
		_miniGame.destroy();
	}
	
	@Override
	public boolean hasNextGameState()
	{
		return false;
	}

	@Override
	public GameState<?> getNextGameState()
	{
		return null;
	}
}
