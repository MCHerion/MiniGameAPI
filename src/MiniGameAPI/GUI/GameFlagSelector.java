package MiniGameAPI.GUI;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import MiniGameAPI.MiniGame.GameFlag;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.GUI.Categorie;
import PluginUtils.Utils.ItemStackBuilder;
import net.md_5.bungee.api.ChatColor;

public class GameFlagSelector extends Categorie
{
	protected MiniGamePlayer<MiniGame<?>> _customPlayer;
	
	public GameFlagSelector(MiniGamePlayer<MiniGame<?>> MiniGamePlayer) 
	{
		super(54, null, MiniGamePlayer.getPlayer(), new ItemStackBuilder(Material.BEDROCK).setName("Selectionnez les règles de jeu").build(), new ItemStack(Material.STAINED_GLASS_PANE));
		_customPlayer = MiniGamePlayer;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void createIcones() 
	{
		int index = 0;
		for(Class<? extends GameFlag> gameFlagType : _customPlayer.getMiniGame().getAddableGameFlags())
		{
			GameFlagInfos gameFlagInfos = gameFlagType.getAnnotation(GameFlagInfos.class);
			setIconeAtSlot
			(
				new GameFlagBuilder
				(
					this, 
					_customPlayer,
					new ItemStackBuilder(Material.PAPER) 
					.setName(ChatColor.YELLOW + gameFlagInfos.name())
					.addLores(gameFlagInfos.description())
					.build(),
					gameFlagType
				), 
				index
			);	
			++index;
		}
	}
}
