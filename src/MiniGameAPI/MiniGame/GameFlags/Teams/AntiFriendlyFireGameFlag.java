package MiniGameAPI.MiniGame.GameFlags.Teams;

import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;
import MiniGameAPI.MiniGame.GameFlags.GameRules.AntiPVPGameFlag;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;

@GameFlagInfos
(
	name = "Interdire le PVP entre alli�s",
	description = { "Permet d'interdire � des joueurs", "d'une m�me �quipe de se taper" }
)
public class AntiFriendlyFireGameFlag<MG extends MiniGame<?>> extends AntiPVPGameFlag<MG>
{
	public AntiFriendlyFireGameFlag(MG miniGame) 
	{
		super(miniGame);
	}
	
	@Override
	public boolean check(MiniGamePlayer<?> damager, MiniGamePlayer<?> damaged)
	{
		return damager.getTeam() != null && damager.getTeam() == damaged.getTeam();
	}
}
