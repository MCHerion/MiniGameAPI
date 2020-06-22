package MiniGameAPI.MiniGame.WorldGenerator;

import org.bukkit.Material;

public class OreInfo
{
	protected Material _type;
	protected int _minHeight;
	protected int _maxHeight;
	protected int _veinsPerChunk;
	protected int _amountPerVein;
	
	public OreInfo(Material type, int minHeight, int maxHeight, int veinsPerChunk, int amountPerVein)
	{
		_type = type;
		_minHeight = minHeight;
		_maxHeight = maxHeight;
		_veinsPerChunk = veinsPerChunk;
		_amountPerVein = amountPerVein;
	}
	
	public Material getType()
	{
		return _type;
	}
	
	public int getMinHeight()
	{
		return _minHeight;
	}
	
	public int getMaxHeight()
	{
		return _maxHeight;
	}
	
	public int getVeinsPerChunk()
	{
		return _veinsPerChunk;
	}
	
	public int getAmountPerVein()
	{
		return _amountPerVein;
	}
}
