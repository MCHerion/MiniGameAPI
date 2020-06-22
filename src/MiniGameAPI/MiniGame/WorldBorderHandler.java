package MiniGameAPI.MiniGame;

import org.bukkit.WorldBorder;

public interface WorldBorderHandler 
{
	/**
	 * Method used to get the WorldBorder of the World owned by this MiniGame
	 * 
	 * @return WorldBorder of the World owned by this MiniGame
	 */
	public WorldBorder getWorldBorder();
	
	public boolean hasWorldBorder();
	
	public void setHasWorldBorder(boolean hasWorldBorder);
	
	public default int getXWorldBorder()
	{
		return getWorldBorder().getCenter().getBlockX() + (int) getWorldBorder().getSize();
	}
	
	public default int getZWorldBorder()
	{
		return getWorldBorder().getCenter().getBlockZ() + (int) getWorldBorder().getSize();
	}
	
	public default void setWorldBorderSize(int size)
	{
		setWorldBorderSize(size, 0);
	}
	
	public default void setWorldBorderSize(int size, int seconds)
	{
		getWorldBorder().setSize(size, seconds);
		setHasWorldBorder(true);
	}
	
	/**
	 * Method used to reset the WorldBorder of the World owned by this MiniGame
	 * 
	 * @see {@link #getWorldBorder()}
	 * @see {@link WorldBorder#reset()}
	 */
	public default void resetWorldBorder()
	{
		getWorldBorder().reset();
		setHasWorldBorder(false);
	}
}
