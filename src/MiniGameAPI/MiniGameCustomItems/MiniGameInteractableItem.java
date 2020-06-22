package MiniGameAPI.MiniGameCustomItems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import PluginUtils.CustomItems.InteractableItem;

public abstract class MiniGameInteractableItem extends InteractableItem
{

	public MiniGameInteractableItem(String name, Material type, String displayName, String... lores)
	{
		super(name, type, displayName, lores);
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event)
	{
		if(instanceOfThisItem(event.getItemDrop().getItemStack()))
		{
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryMoveItem(InventoryMoveItemEvent event)
	{
		if(instanceOfThisItem(event.getItem()))
		{
			event.setCancelled(true);
		}
	}
}
