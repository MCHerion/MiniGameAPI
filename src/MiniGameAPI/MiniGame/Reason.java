package MiniGameAPI.MiniGame;

public class Reason 
{
	/**
	 * Variable that store the reason of this lose
	 */
	protected String _reason;
	
	public Reason(String reason)
	{
		_reason = reason;
	}
	
	/**
	 * Method used to get a shot description (String) to describe the reason of this lose
	 * 
	 * @return Reason of this lose
	 */
	public String getReason()
	{
		return _reason;
	}
}
