package MiniGameAPI.GUI;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import MiniGameAPI.GUI.FieldCases.FieldInfos;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.GUI.Case;
import PluginUtils.GUI.CaseClickedEvent;
import PluginUtils.GUI.Categorie;
import PluginUtils.Utils.ItemStackBuilder;
import net.md_5.bungee.api.ChatColor;

public abstract class ObjectBuilder extends BuilderCategorie
{
	protected MiniGamePlayer<?> _miniGamePlayer;
	protected Object _toBuild;

	public ObjectBuilder(Categorie parent, MiniGamePlayer<?> miniGamePlayer, ItemStack icone, Class<?> toBuild) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException 
	{
		this(parent, miniGamePlayer, icone, toBuild.getConstructor().newInstance());
	}
	
	public ObjectBuilder(Categorie parent, MiniGamePlayer<?> miniGamePlayer, ItemStack icone, Object toBuild) 
	{
		super(54, parent, miniGamePlayer.getPlayer(), icone);
		_miniGamePlayer = miniGamePlayer;
		_toBuild = toBuild;
	}
	
	public void setValue(String fieldName, Object value)
	{
		_values.put(fieldName, value);
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
		for(Field field : fields)
		{
			if(index > 35)
			{
				break;
			}
			field.setAccessible(true);
			if(field.isAnnotationPresent(FieldInfos.class))
			{
				FieldInfos fieldInfos = field.getAnnotation(FieldInfos.class);
				boolean isList = _toBuild.getClass().isAssignableFrom(List.class);
				Class<?> type = field.getType();
				if(isList)
				{
			        ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
			        type = (Class<?>) stringListType.getActualTypeArguments()[0];
				}
				createField(_miniGamePlayer, type, isList, index, field.getName(), fieldInfos.name(), fieldInfos.description());
			}
			field.setAccessible(false);
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
					public void onClick(CaseClickedEvent arg0)
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
	
	@Override
	public void onSubTypeBuild(String tag, Object object)
	{
		_values.put(tag, object);
	}
	
	public abstract void onBuild(Object object);
	
	public void build()
	{
		for(String key : _values.keySet())
		{
			try 
			{
				_toBuild.getClass().getField(key).set(_toBuild, _values.get(key));
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		_miniGamePlayer.getGUI().changeCategorie(_pere);
		onBuild(_toBuild);
	}
}
