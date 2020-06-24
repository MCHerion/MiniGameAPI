package MiniGameAPI.GUI;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import MiniGameAPI.GUI.FieldCases.DoubleFieldCase;
import MiniGameAPI.GUI.FieldCases.IntegerFieldCase;
import MiniGameAPI.GUI.FieldCases.StringFieldCase;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.GUI.Categorie;
import PluginUtils.Utils.ItemStackBuilder;
import net.md_5.bungee.api.ChatColor;

public abstract class BuilderCategorie extends Categorie
{	
	protected LinkedHashMap<String, Object> _values = new LinkedHashMap<String, Object>();
	
	public BuilderCategorie(int size, Categorie pere, Player player, ItemStack icone)
	{
		super(size, pere, player, icone, new ItemStack(Material.STAINED_GLASS_PANE));
	}
	
	public abstract void onSubTypeBuild(String tag, Object object);
	
	@SuppressWarnings("unchecked")
	public void createField(MiniGamePlayer<?> miniGamePlayer, Class<?> type, boolean isList, int index, String tag, String name, String... description)
	{
		try
		{
			if(isList)
			{
				setIconeAtSlot
				(
					new ListBuilder
					(
						this, 
						miniGamePlayer,
						new ItemStackBuilder(Material.LADDER)
						.setName(name)
						.addLores(description)
						.build(),
						type
					)
					{
						@Override
						public void onBuild(ArrayList<Object> objects)
						{
							_values.put(tag, objects);		
							onSubTypeBuild(tag, objects);	
						}
					}, 
					index
				);	
			}
			else if(type == Integer.class || type == int.class)
			{
				setIconeAtSlot
				(
					new IntegerFieldCase
					(
						this, 
						miniGamePlayer,
						new ItemStackBuilder(Material.IRON_INGOT)
						.setName(name)
						.addLores(description)
						.addLores
						(
							"",
							ChatColor.GOLD + "Valeur : " + ChatColor.YELLOW + _values.get(tag)
						)
						.build()
					)
					{
						@Override
						public void onBuild(Object object)
						{
							_values.put(tag, object);
							onSubTypeBuild(tag, object);	
						}
					}, 
					index
				);	
			}
			else if(type == Double.class || type == double.class)
			{
				setIconeAtSlot
				(
					new DoubleFieldCase
					(
						this, 
						miniGamePlayer,
						new ItemStackBuilder(Material.GOLD_INGOT)
						.setName(name)
						.addLores(description)
						.addLores
						(
							"",
							ChatColor.GOLD + "Valeur : " + ChatColor.YELLOW + _values.get(tag)
						)
						.build()
					)
					{
						@Override
						public void onBuild(Object object)
						{
							_values.put(tag, object);
							onSubTypeBuild(tag, object);	
						}
					}, 
					index
				);	
			}
			else if(type == String.class)
			{
				setIconeAtSlot
				(
					new StringFieldCase
					(
						this, 
						miniGamePlayer,
						new ItemStackBuilder(Material.STRING)
						.setName(name)
						.addLores(description)
						.addLores
						(
							"",
							ChatColor.GOLD + "Valeur : " + ChatColor.YELLOW + _values.get(tag)
						)
						.build()
					)
					{
						@Override
						public void onBuild(Object object)
						{
							_values.put(tag, object);
							onSubTypeBuild(tag, object);	
						}
					},
					index
				);
			}
			else if(type.isEnum())
			{
				setIconeAtSlot
				(
					new EnumBuilder
					(								
						this, 
						miniGamePlayer,
						new ItemStackBuilder(Material.ITEM_FRAME)
						.setName(name)
						.addLores(description)
						.addLores
						(
							"",
							ChatColor.GOLD + "Valeur : " + ChatColor.YELLOW + ((Enum<?>)_values.get(tag)).name().replaceAll("_", " ")
						)
						.build(),
						(Class<Enum<?>>) type
					)
					{
						@Override
						public void onBuild(Object object)
						{
							_values.put(tag, object);
							onSubTypeBuild(tag, object);							
						}
					}, 
					index
				);
			}
			else
			{
				setIconeAtSlot
				(
					new ObjectBuilder
					(
						this, 
						miniGamePlayer,
						new ItemStackBuilder(Material.BOOK)
						.setName(name)
						.addLores
						(
							description
						)
						.build(),
						type
					)
					{
						@Override
						public void onBuild(Object object)
						{
							_values.put(tag, object);
							onSubTypeBuild(tag, object);
						}
					}, 
					index
				);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
}
