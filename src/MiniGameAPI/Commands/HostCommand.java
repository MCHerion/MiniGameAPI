package MiniGameAPI.Commands;

import org.bukkit.entity.Player;
import MiniGameAPI.MiniGame.CreatorMode;
import MiniGameAPI.MiniGame.GameStates.WaitingGameState;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.Commands.CustomCommand;
import net.md_5.bungee.api.ChatColor;

public class HostCommand extends CustomCommand
{
	public HostCommand()
	{
		super("host");
	}
	
	@HelpInfos(_description = "Permet de passer une p�riode de jeu")
	@Permission(_permission = "minigame.host")
	public void skip(Player player)
	{
		MiniGamePlayer<?> miniGamePlayer = MiniGamePlayer.getMiniGamePlayer(player);
		if(checkHost(miniGamePlayer))
		{
			if(miniGamePlayer.getMiniGame().getGameState().isSkippable())
			{
				miniGamePlayer.getMiniGame().getGameState().skip();
				miniGamePlayer.getPlayer().sendMessage(getTag() + " " + ChatColor.AQUA + "Vous avez pass� cette p�riode !");
			}
			else
			{
				miniGamePlayer.getPlayer().sendMessage(getTag() + " " + ChatColor.RED + "Impossible de passer cette p�riode ...");
			}
		}
	}
	
	@HelpInfos(_description = "Permet de d�marrer une partie que vous avez cr��")
	@Permission(_permission = "minigame.host")
	public void start(Player player)
	{
		MiniGamePlayer<?> miniGamePlayer = MiniGamePlayer.getMiniGamePlayer(player);
		if(checkHost(miniGamePlayer))
		{
			if(miniGamePlayer.getMiniGame().getGameState() instanceof WaitingGameState)
			{
				((WaitingGameState<?>) miniGamePlayer.getMiniGame().getGameState()).skip();
			}
			else
			{
				miniGamePlayer.getPlayer().sendMessage(getTag() + " " + ChatColor.RED + "Le Mini-Jeu est d�j� lanc� !");
			}
		}
	}
	
	public boolean checkHost(MiniGamePlayer<?> miniGamePlayer)
	{
		if(miniGamePlayer != null && miniGamePlayer.hasMiniGame())
		{
			if(miniGamePlayer.getMiniGame().getCreatorMode() == CreatorMode.HOST && miniGamePlayer.getAdminMode())
			{
				return true;
			}
			else
			{
				miniGamePlayer.getPlayer().sendMessage(getTag() + " " + ChatColor.RED + "Vous ne disposez pas des droits sur ce Mini-Jeu !");
				return false;
			}
		}
		else
		{
			miniGamePlayer.getPlayer().sendMessage(getTag() + " " + ChatColor.RED + "Vous ne pouvez pas faire �a quand vous n'�tes pas dans un Mini-Jeu !");
			return false;
		}
	}
	
	public String getTag()
	{
		return ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Host" + ChatColor.DARK_GRAY + "]";
	}
}
