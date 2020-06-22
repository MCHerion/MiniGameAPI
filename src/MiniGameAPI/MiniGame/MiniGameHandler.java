package MiniGameAPI.MiniGame;

/**
 * Interface that is used to make an object handle a MiniGame
 * 
 * @author Elytes
 *
 * @param <MG> Generic MiniGame type 
 */
public interface MiniGameHandler<MG extends MiniGame<?>>
{
	/**
	 * Method used to get the MiniGame instance associated to this object
	 * 
	 * @return MiniGame instance associated to this object
	 */
	public MG getMiniGame();
	
	/**
	 * Method used to check is this object is associated to a MiniGame
	 * 
	 * @return true if this object is associated to a MiniGame, false else
	 */
	public default boolean hasMiniGame()
	{
		return getMiniGame() != null;
	}
}

