package MiniGameAPI.MiniGame.GameStates;

import org.bukkit.event.EventHandler;

import MiniGameAPI.CustomEvents.GameStateActivatedEvent;
import MiniGameAPI.MiniGame.GameState;
import MiniGameAPI.MiniGame.MiniGame;

public abstract class InitializationGameState<MG extends MiniGame<?>> extends GameState<MG>
{
	public InitializationGameState(MG miniGame)
	{
		super(miniGame, "D�but");
	}

	@EventHandler
	public void onGameStateActivated(GameStateActivatedEvent event)
	{
		if(event.getGameState() == this)
		{
			getMiniGame().start();
			skip();
		}
	}
}
