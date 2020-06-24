package MiniGameAPI.GUI;


import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.GUI.Case;
import PluginUtils.GUI.CaseClickedEvent;
import PluginUtils.GUI.Categorie;
import PluginUtils.Utils.ItemStackBuilder;
import net.md_5.bungee.api.ChatColor;

public abstract class ListBuilder extends BuilderCategorie
{
	protected MiniGamePlayer<?> _miniGamePlayer;
	protected Class<?> _toBuild;
	protected ArrayList<Object> _buildedObjects = new ArrayList<Object>();
	protected int _actualIndex = 0;

	public ListBuilder(Categorie parent, MiniGamePlayer<?> miniGamePlayer, ItemStack icone, Class<?> toBuild) 
	{
		super(54, parent, miniGamePlayer.getPlayer(), icone);
		_miniGamePlayer = miniGamePlayer;
		_toBuild = toBuild;
	}

	@Override
	public void createIcones() 
	{
		int index = 0;
		for(String tag : _values.keySet())
		{
			createField(_miniGamePlayer, _toBuild, false, index, tag, "Élément n°" + index);
			++index;
		}
		setIconeAtSlot
		(
			new Case
			(
				this, 
				_player, 
				new ItemStackBuilder(Material.BARRIER)
				.setName(ChatColor.RED + "Annuler")
				.build(), 
				new OnClickHandler()
				{
					@Override
					public void onClick(CaseClickedEvent event)
					{
						_miniGamePlayer.getGUI().changeCategorie(_pere);
					}
				}
			),
			44
		);
		setIconeAtSlot
		(
			new Case
			(
				this, 
				_player, 
				new ItemStackBuilder(Material.MOB_SPAWNER)
				.setName(ChatColor.GOLD + "Ajouter un élément (" + ChatColor.YELLOW + (_actualIndex + 1) + ChatColor.GOLD + ")")
				.build(), 
				new OnClickHandler()
				{
					@Override
					public void onClick(CaseClickedEvent event)
					{
						if(_values.keySet().size() < 35)
						{
							try
							{
								_values.put("tag" + _actualIndex, _toBuild.getConstructor().newInstance());
								_actualIndex += 1;
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							_miniGamePlayer.getGUI().refresh();
						}
					}
				}
			),
			52
		);
		setIconeAtSlot
		(
			new Case
			(
				this, 
				_player, 
				new ItemStackBuilder(Material.ANVIL)
				.setName(ChatColor.GREEN + "Enregistrer")
				.build(), 
				new OnClickHandler()
				{
					@Override
					public void onClick(CaseClickedEvent arg0)
					{
						build();
					}
				}
			),
			53
		);
	}
	
	public abstract void onBuild(ArrayList<Object> objects);
	
	public void build()
	{
		_miniGamePlayer.getGUI().changeCategorie(_pere);
		onBuild(_buildedObjects);
	}

	@Override
	public void onSubTypeBuild(String tag, Object object)
	{
		_buildedObjects.add(object);
	}
}
