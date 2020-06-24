package MiniGameAPI.GUI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.GUI.Case;
import PluginUtils.GUI.CaseClickedEvent;
import PluginUtils.Main.MainClass;

public abstract class FieldCase extends Case
{
	protected MiniGamePlayer<?> _customPlayer;
	protected Object _object;
	
	public FieldCase(BuilderCategorie pere, MiniGamePlayer<?> MiniGamePlayer, ItemStack item)
	{
		super
		(
			pere, 
			MiniGamePlayer.getPlayer(),
			item,
			new OnClickHandler()
			{
				@Override
				public void onClick(CaseClickedEvent event) 
				{				
					MiniGamePlayer.getPlayer().closeInventory();
				}
			}
		);
		_customPlayer = MiniGamePlayer;
		new BukkitRunnable()
		{
			@Override
			public void run() 
			{
				destroy();
			}
		}.runTaskLater(MainClass.getInstance(), 600);
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
	{
		if(event.getPlayer() == _player)
		{
			setObject(_player, event.getMessage());
			if(getObject() != null)
			{
				onBuild(getObject());
				destroy();
			}
		}
	}
	
	public abstract void onBuild(Object object);
	
	public abstract void setObject(Player player, String message);
	
	public Object getObject()
	{
		return _object;
	}
	
	public void destroy()
	{
		HandlerList.unregisterAll(this);
		if(_player.isOnline())
		{
			_customPlayer.getGUI().open(_pere);
		}
	}
}
