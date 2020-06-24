package MiniGameAPI.Commands;

import org.bukkit.entity.Player;

import MiniGameAPI.MiniGame.CreatorMode;
import MiniGameAPI.MiniGame.MatchMaking;
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
	
	@HelpInfos(_description = "Créez une partie")
	@Permission(_permission = "minigame.host")
	public void create(Player player, String mapName)
	{
		if(!MiniGamePlayer.isInMiniGame(player))
		{
			if(MatchMaking.getInstance().mapExists(mapName))
			{
				if(MatchMaking.getInstance().mapUsed(mapName))
				{
					MatchMaking.getInstance().createHostGame(player, mapName);
				}
				else
				{
					player.sendMessage(getTag() + " " + ChatColor.RED + "Une partie est déjà en cours sur cette map !");
				}
			}
			else
			{
				player.sendMessage(getTag() + " " + ChatColor.RED + "Cette map n'existe pas !");
			}
		}
		else
		{
			player.sendMessage(getTag() + " " + ChatColor.RED + "Vous êtes déjà dans un mini-jeu !");
		}
	}
	
	@HelpInfos(_description = "Forcer à changer de période de jeu")
	@Permission(_permission = "minigame.host")
	public void skip(Player player)
	{
		MiniGamePlayer<?> miniGamePlayer = MiniGamePlayer.getMiniGamePlayer(player);
		if(checkHost(miniGamePlayer))
		{
			if(miniGamePlayer.getMiniGame().getGameState().isSkippable())
			{
				miniGamePlayer.getMiniGame().getGameState().skip();
				miniGamePlayer.getPlayer().sendMessage(getTag() + " " + ChatColor.AQUA + "Vous avez passé cette période !");
			}
			else
			{
				miniGamePlayer.getPlayer().sendMessage(getTag() + " " + ChatColor.RED + "Impossible de passer cette période ...");
			}
		}
	}
	
	@HelpInfos(_description = "Permet de démarrer une partie que vous avez créé")
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
				miniGamePlayer.getPlayer().sendMessage(getTag() + " " + ChatColor.RED + "Le Mini-Jeu est déjà lancé !");
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
			miniGamePlayer.getPlayer().sendMessage(getTag() + " " + ChatColor.RED + "Vous ne pouvez pas faire ça quand vous n'êtes pas dans un Mini-Jeu !");
			return false;
		}
	}
	
	public String getTag()
	{
		return ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Host" + ChatColor.DARK_GRAY + "]";
	}
}
