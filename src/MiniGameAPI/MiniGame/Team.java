package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import MiniGameAPI.CustomPlayer.CustomPlayer;

public interface Team<T extends CustomPlayer<?>>
{
	public ArrayList<T> getPlayers();
	
	public default void addPlayer(T customPlayer)
	{
		getPlayers().add(customPlayer);
	}
}
