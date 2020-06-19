package MiniGameAPI.GUI;

import java.lang.reflect.Field;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.GUI.Case;
import PluginUtils.GUI.CaseClickedEvent;
import PluginUtils.GUI.Categorie;
import PluginUtils.Main.MainClass;
import PluginUtils.Utils.ItemStackBuilder;

public abstract class FieldCase extends Case
{
	protected MiniGamePlayer<?> _customPlayer;
	protected Field _field;
	protected Object _object;
	
	public FieldCase(GameFlagBuilder pere, MiniGamePlayer<?> MiniGamePlayer, Field field, Material icone, GameFlagInfos gameFlagInfos)
	{
		super
		(
			pere, 
			MiniGamePlayer.getPlayer(),
			new ItemStackBuilder(icone)
			.setName(gameFlagInfos.name())
			.addLores(gameFlagInfos.description())
			.build(), 
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
		_field = field;
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
				((GameFlagBuilder) _pere).setValue(_field.getName(), getObject());
				destroy();
			}
		}
	}
	
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
