package MiniGameAPI.MiniGameCustomItems;

import PluginUtils.CustomItems.CustomItemHandler;

public enum MiniGameCustomItem implements CustomItemHandler
{
	GameFlagSelectorItem,
	SkipItem;

	@Override
	public String getCustomItemName() 
	{
		return name();
	}
}
