package MiniGameAPI.MiniGame;

public abstract class LoseReason 
{
	protected String _reason;
	
	public LoseReason(String reason)
	{
		_reason = reason;
	}
	
	public String getReason()
	{
		return _reason;
	}
}