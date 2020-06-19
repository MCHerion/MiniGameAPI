package MiniGameAPI.MiniGame.GameFlags.Teams;

import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;
import MiniGameAPI.MiniGame.GameFlags.GameRules.AntiPVPGameFlag;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;

@GameFlagInfos
(
	name = "Interdire le PVP entre alliés",
	description = { "Permet d'interdire à des joueurs", "d'une même équipe de se taper" }
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
