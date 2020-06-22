package MiniGameAPI.GUI;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import MiniGameAPI.GUI.FieldCases.DoubleFieldCase;
import MiniGameAPI.GUI.FieldCases.FieldInfos;
import MiniGameAPI.GUI.FieldCases.IntegerFieldCase;
import MiniGameAPI.GUI.FieldCases.StringFieldCase;
import MiniGameAPI.MiniGame.GameFlag;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import PluginUtils.GUI.Categorie;

public class GameFlagBuilder extends Categorie
{
	protected MiniGamePlayer<?> _customPlayer;
	protected HashMap<String, Object> _values = new HashMap<String, Object>();
	protected GameFlag<?> _toBuild;

	@SuppressWarnings("rawtypes")
	public GameFlagBuilder(Categorie parent, MiniGamePlayer<?> MiniGamePlayer, ItemStack icone, Class<? extends GameFlag> toBuild) 
	{
		super(54, parent, MiniGamePlayer.getPlayer(), icone, new ItemStack(Material.STAINED_GLASS_PANE));
		_customPlayer = MiniGamePlayer;
		if(_customPlayer.getMiniGame().hasGameFlag(toBuild))
		try 
		{
			_toBuild = _customPlayer.getMiniGame().hasGameFlag(toBuild) ?
				_customPlayer.getMiniGame().getGameFlag(toBuild) : 
				toBuild.getConstructor(MiniGame.class).newInstance(_customPlayer.getMiniGame());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
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
			field.setAccessible(true);
			if(field.isAnnotationPresent(FieldInfos.class))
			{
				FieldInfos fieldInfos = field.getAnnotation(FieldInfos.class);
				if(field.getType() == Integer.class || field.getType() == int.class)
				{
					setIconeAtSlot
					(
						new IntegerFieldCase
						(
							this, 
							_customPlayer,
							field,
							fieldInfos
						), 
						index
					);	
				}
				else if(field.getType() == Double.class || field.getType() == double.class)
				{
					setIconeAtSlot
					(
						new DoubleFieldCase
						(
							this, 
							_customPlayer,
							field,
							fieldInfos
						), 
						index
					);	
				}
				else if(field.getType() == String.class)
				{
					setIconeAtSlot
					(
						new StringFieldCase
						(
							this, 
							_customPlayer,
							field,
							fieldInfos
						), 
						index
					);	
				}
			}
			field.setAccessible(false);
			++index;
		}
	}
	
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
		if(!_customPlayer.getMiniGame().hasGameFlag(_toBuild))
		{
			_customPlayer.getMiniGame().addGameFlag(_toBuild);
		}
		_customPlayer.getGUI().changeCategorie(_pere);
	}
}
