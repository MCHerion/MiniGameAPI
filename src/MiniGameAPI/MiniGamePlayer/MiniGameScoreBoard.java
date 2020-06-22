package MiniGameAPI.MiniGamePlayer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import MiniGameAPI.MiniGame.MiniGame;
import PluginUtils.ScoreBoard.BasicScoreBoard;
import net.md_5.bungee.api.ChatColor;

public class MiniGameScoreBoard extends BasicScoreBoard
{
	public MiniGameScoreBoard(String nom, Player player)
	{
		super(nom, player);
	}

	@Override
	public List<String> getText() 
	{
		ArrayList<String> returnValue = new ArrayList<String>();
		MiniGamePlayer<?> miniGamePlayer = MiniGamePlayer.getMiniGamePlayer(_player);
		if(miniGamePlayer.hasMiniGame())
		{
			MiniGame<?> miniGame = miniGamePlayer.getMiniGame();
			returnValue.add(ChatColor.GREEN + "Joueurs : " + ChatColor.DARK_GREEN + miniGame.getPlayersAmount() + ChatColor.GREEN + " / " + ChatColor.DARK_GREEN + miniGame.getPlayedPlayersAmount());
			returnValue.add(ChatColor.GREEN + "État : " + ChatColor.DARK_GREEN + miniGame.getGameState().getName());
			if(miniGame.getGameState().hasNextGameState() && miniGame.getGameState().isNextGameStateCountDownStarted())
			{
				returnValue.add
				(
					ChatColor.GREEN + "Temps avant " + ChatColor.DARK_GREEN + miniGame.getGameState().getNextGameState().getName() + ChatColor.GREEN + " : " + 
					ChatColor.DARK_GREEN + miniGame.getGameState().getNextGameStateCountDown().getRemainingTime() + ChatColor.GREEN + " secondes"
				);
			}
			if(miniGame.hasWorldBorder())
			{
				returnValue.add
				(
					ChatColor.GREEN + "Bordure : " + 
					ChatColor.DARK_GREEN + miniGame.getXWorldBorder() + ChatColor.GREEN + "X " + 
					ChatColor.DARK_GREEN + miniGame.getZWorldBorder() + ChatColor.GREEN + "Z"
				);
			}
		}
		else
		{
			returnValue.add(ChatColor.RED + "Vous n'êtes pas dans un mini-jeu");
		}
		return returnValue;
	}
}
