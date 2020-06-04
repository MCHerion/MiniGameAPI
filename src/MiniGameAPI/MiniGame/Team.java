package MiniGameAPI.MiniGame;

import java.util.ArrayList;

import MiniGameAPI.Player.CustomPlayer;

public interface Team
{
	public ArrayList<CustomPlayer> getPlayers();
	
	public default void addPlayer(CustomPlayer customPlayer)
	{
		getPlayers().add(customPlayer);
	}
}
