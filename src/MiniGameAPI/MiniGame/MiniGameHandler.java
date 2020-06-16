package MiniGameAPI.MiniGame;

public interface MiniGameHandler<MG extends MiniGame<?>>
{
	public MG getMiniGame();
	
	public default boolean hasMiniGame()
	{
		return getMiniGame() != null;
	}
}

