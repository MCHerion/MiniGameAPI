package MiniGameAPI.MiniGame;

import java.util.ArrayList;

public class TeamManager implements MiniGameHandler<MiniGame<?>>
{
	protected MiniGame<?> _miniGame;
	protected ArrayList<Team> _teams = new ArrayList<Team>();
	
	public TeamManager(MiniGame<?> miniGame)
	{
		_miniGame = miniGame;
	}
	
	public void createTeams(int amount)
	{
		for(int i=0; i<amount; ++i)
		{
			addTeam(new Team());
		}
	}
	
	public void addTeam(Team team)
	{
		_teams.add(team);
	}
	
	public ArrayList<Team> getTeams()
	{
		return _teams;
	}
	
	public Team findTeam()
	{
		Team lessEmptyTeam = null;
		for(Team team : _teams)
		{
			if((lessEmptyTeam == null || team.getPlayerAmount() < lessEmptyTeam.getPlayerAmount()))
			{
				lessEmptyTeam = team;
			}
		}
		return lessEmptyTeam;
	}

	@Override
	public MiniGame<?> getMiniGame()
	{
		return _miniGame;
	}
}
 