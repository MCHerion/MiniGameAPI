package MiniGameAPI.MiniGame.GameStates;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import MiniGameAPI.CustomEvents.PlayerJoinMiniGameEvent;
import MiniGameAPI.MiniGame.GameState;
import MiniGameAPI.MiniGame.MiniGame;
import PluginUtils.Utils.CountDown;
import net.md_5.bungee.api.ChatColor;

public abstract class WaitingGameState<MG extends MiniGame<?>> extends GameState<MG>
{
	protected boolean _isStarting = false;
	
	protected int _startingTime;

	public WaitingGameState(MG miniGame)
	{
		this(miniGame, 30);
	}
	
	public WaitingGameState(MG miniGame, int startingTime)
	{
		super(miniGame, "En attente de joueurs");
		_startingTime = startingTime;
	}

	@Override
	public boolean canJoin() 
	{
		return true;
	}

	@EventHandler
	public void onPlayerJoinMiniGame(PlayerJoinMiniGameEvent event)
	{
		MiniGame<?> miniGame = event.getMiniGame();
		miniGame.dispatchMessage("Joueurs : " + miniGame.getPlayers().size() + " / " + miniGame.getMinPlayers());
		if(miniGame.getPlayers().size() >= miniGame.getMinPlayers())
		{
			if(!_isStarting)
			{
				_isStarting = true;
				new CountDown(_startingTime * 20, ChatColor.GREEN + "Configuration dans " + ChatColor.DARK_GREEN + "%time%" + ChatColor.GREEN + " secondes !")
				{
					@Override
					public void onRun()
					{
						miniGame.changeGameState(getNextGameState());
					}
					
					@Override
					public void onCancel()
					{
						_isStarting = false;
						_miniGame.dispatchTitle(ChatColor.DARK_RED + "Lancement annulé, des joueurs ont quitté");
					}
					
					@Override
					public boolean isCancelled()
					{
						return event.getMiniGame().getPlayers().size() >= miniGame.getMinPlayers();
					}

					@Override
					public ArrayList<Player> getPlayers()
					{
						return miniGame.getAllPlayers();
					}
				};
			}
		}
	}
}
