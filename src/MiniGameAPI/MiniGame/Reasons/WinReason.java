package MiniGameAPI.MiniGame.Reasons;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import MiniGameAPI.MiniGame.Reason;

public class WinReason extends Reason
{
	protected ArrayList<Player> _winners;
	
	public WinReason(String reason, ArrayList<Player> winners)
	{
		super(reason);
		_winners = winners;
	}
	
	public ArrayList<Player> getWinners()
	{
		return _winners;
	}
} 
