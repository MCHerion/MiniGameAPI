package MiniGameAPI.GUI;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.GUI.Case;
import PluginUtils.GUI.CaseClickedEvent;
import PluginUtils.GUI.Categorie;
import PluginUtils.Utils.ItemStackBuilder;
import net.md_5.bungee.api.ChatColor;

public abstract class EnumBuilder extends Categorie
{
	protected MiniGamePlayer<?> _customPlayer;
	protected int _page = 0;
	protected Class<Enum<?>> _toBuild;
	
	public EnumBuilder(Categorie parent, MiniGamePlayer<?> miniGamePlayer, ItemStack icone, Class<Enum<?>> toBuild) 
	{
		super(54, parent, miniGamePlayer.getPlayer(), icone, new ItemStack(Material.STAINED_GLASS_PANE));
		_customPlayer = miniGamePlayer;
		_toBuild = toBuild;
	}
	
	@Override
	public void createIcones() 
	{
		Class<?> superClass = _toBuild.getClass();
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
		while(superClass.getSuperclass() != null)
		{
			superClass = superClass.getSuperclass();
			fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
		}
		int index = 0;
		for(int i=_page*35; i<(_page+1)*35; ++i)
		{
			if(_toBuild.getEnumConstants().length < i)
			{
				break;
			}
			Enum<?> enumValue = (Enum<?>) _toBuild.getEnumConstants()[i];
			setIconeAtSlot
			(
				new Case
				(
					this, 
					_customPlayer.getPlayer(),
					new ItemStackBuilder(Material.class.isAssignableFrom(_toBuild) ? (Material) enumValue : Material.PAPER)
					.setName(enumValue.name().replaceAll("_", " "))
					.build(),
					new OnClickHandler()
					{
						@Override
						public void onClick(CaseClickedEvent arg0)
						{
							onBuild(enumValue);
							_customPlayer.getGUI().changeCategorie(_pere);
						}
					}
				),
				index
			);
			++index;
		}
		setIconeAtSlot
		(
			new Case
			(
				this, 
				_player, 
				new ItemStackBuilder(Material.BARRIER)
				.setName(ChatColor.RED + "Retour à la page " + (_page - 1))
				.build(), 
				new OnClickHandler()
				{
					@Override
					public void onClick(CaseClickedEvent arg0)
					{
						previousPage();
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
				new ItemStackBuilder(Material.ANVIL)
				.setName(ChatColor.GREEN + "Aller à la page " + (_page + 1))
				.build(), 
				new OnClickHandler()
				{
					@Override
					public void onClick(CaseClickedEvent arg0)
					{
						nextPage();
					}
				}
			),
			53
		);
	}
	
	public void nextPage()
	{
		if(_toBuild.getEnumConstants().length > _page*35)
		{
			_page += 1;
			createIcones();
		}
	}
	
	public void previousPage()
	{
		if(_page > 0)
		{
			_page -= 1;
			createIcones();
		}
	}
	
	public abstract void onBuild(Object object);
}
