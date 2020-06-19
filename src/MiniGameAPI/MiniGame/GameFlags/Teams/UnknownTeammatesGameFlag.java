package MiniGameAPI.MiniGame.GameFlags.Teams;

import MiniGameAPI.MiniGame.GameFlag;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;

@GameFlagInfos
(
	name = "Masquer l'�quipe des joueurs",
	description = { "Les joueurs ne pourront pas savoir", "quel joueur est dans leur �quipe" }
)
public class UnknownTeammatesGameFlag<MG extends MiniGame<?>> extends GameFlag<MG>
{
	public UnknownTeammatesGameFlag(MG miniGame) 
	{
		super(miniGame);
	}
}
