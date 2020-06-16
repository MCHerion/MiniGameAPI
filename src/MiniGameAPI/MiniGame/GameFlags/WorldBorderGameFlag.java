package MiniGameAPI.MiniGame.GameFlags;

import MiniGameAPI.MiniGame.GameFlag;
import MiniGameAPI.MiniGame.MiniGame;

public class WorldBorderGameFlag<MG extends MiniGame<?>> extends GameFlag<MG>
{
	public WorldBorderGameFlag(MG miniGame, String name, String[] description)
	{
		super(miniGame, name, description);
	}

	@Override
	public void activate() 
	{

	}

	@Override
	public void deactivate()
	{
		
	}
}
