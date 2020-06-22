package MiniGameAPI.MiniGame.GameStates;

import org.bukkit.event.EventHandler;
import MiniGameAPI.CustomEvents.PlayerJoinMiniGameEvent;
import MiniGameAPI.MiniGame.CreatorMode;
import MiniGameAPI.MiniGame.GameState;
import MiniGameAPI.MiniGame.MiniGame;
import net.md_5.bungee.api.ChatColor;

public abstract class WaitingGameState<MG extends MiniGame<?>> extends GameState<MG>
{
	public WaitingGameState(MG miniGame)
	{
		super(miniGame, "En attente de joueurs");
	}

	@Override
	public boolean canJoin() 
	{
		return true;
	}
	
	@Override
	public boolean isSkippable()
	{
		return true;
	}
	
	@Override
	public boolean isNextGameStateCountDownCancelled()
	{
		return _miniGame.getPlayersAmount() < _miniGame.getMinPlayers() || _miniGame.getPlayersAmount() == _miniGame.getMaxPlayers();
	}
	
	@Override
	public void onNextGameStateCountDownCancel()
	{
		if(_miniGame.getPlayersAmount() == _miniGame.getMaxPlayers())
		{
			skip();
		}
		else if(_miniGame.getPlayersAmount() < _miniGame.getMinPlayers())
		{
			_miniGame.dispatchTitle(ChatColor.DARK_RED + "Lancement annulé, des joueurs ont quitté");
		}
	}

	@EventHandler
	public void onPlayerJoinMiniGame(PlayerJoinMiniGameEvent event)
	{
		if(event.getMiniGame() == _miniGame)
		{
			_miniGame.dispatchMessage("Joueurs : " + _miniGame.getPlayers().size() + " / " + _miniGame.getMinPlayers());
			if(_miniGame.getCreatorMode() == CreatorMode.PARTIE_A_LA_DEMANDE)
			{
				if(_miniGame.getPlayers().size() >= _miniGame.getMinPlayers())
				{
					startNextGameStateCountDown();
				}
			}
		}
	}
}
