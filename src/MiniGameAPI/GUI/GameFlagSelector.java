package MiniGameAPI.GUI;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import MiniGameAPI.MiniGame.CreatorMode;
import MiniGameAPI.MiniGame.GameFlag;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;
import MiniGameAPI.MiniGame.GameStates.ConfigurationGameState;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.GUI.Case;
import PluginUtils.GUI.CaseClickedEvent;
import PluginUtils.GUI.Categorie;
import PluginUtils.Utils.ItemStackBuilder;
import net.md_5.bungee.api.ChatColor;

public class GameFlagSelector extends Categorie
{
	protected MiniGamePlayer<?> _customPlayer;
	
	public GameFlagSelector(MiniGamePlayer<?> MiniGamePlayer) 
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
			if(_customPlayer.getMiniGame().getCreatorMode() == CreatorMode.HOST && _customPlayer.getAdminMode())
			{
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
			}
			else if(_customPlayer.getMiniGame().getCreatorMode() == CreatorMode.PARTIE_A_LA_DEMANDE && _customPlayer.getMiniGame().getGameState() instanceof ConfigurationGameState)
			{
				setIconeAtSlot
				(
					new Case
					(
						this, 
						_customPlayer.getPlayer(),
						new ItemStackBuilder(Material.PAPER) 
						.setName(ChatColor.YELLOW + gameFlagInfos.name())
						.addLores(gameFlagInfos.description())
						.addLore("")
						.addLore(ChatColor.AQUA + "Votes : " + ChatColor.DARK_AQUA + _customPlayer.getMiniGame().getGameFlagRequestManager().getAcceptedAmount(gameFlagType) + ChatColor.AQUA + " / " + ChatColor.DARK_AQUA + _customPlayer.getMiniGame().getGameFlagRequestManager().getNeededVotes())
						.build(),
						new OnClickHandler()
						{
							@Override
							public void onClick(CaseClickedEvent event) 
							{
								_customPlayer.getMiniGame().getGameFlagRequestManager().request(gameFlagType, _customPlayer.getPlayer());
							}
						}
					), 
					index
				);	
			}
			++index;
		}
	}
}
