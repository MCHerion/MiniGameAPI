package MiniGameAPI.MiniGame;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.material.Wool;

public enum TeamColor 
{
	RED("Rouge", ChatColor.RED, DyeColor.RED),
	ORANGE("Orange", ChatColor.GOLD, DyeColor.ORANGE),
	YELLOW("Jaune", ChatColor.YELLOW, DyeColor.YELLOW),
	GREEN("Vert", ChatColor.DARK_GREEN, DyeColor.GREEN),
	LIGHT_GREEN("Vert clair", ChatColor.GREEN, DyeColor.LIME),
	BLUE("Bleu", ChatColor.BLUE, DyeColor.BLUE),
	LIGHT_BLUE("Bleu clair", ChatColor.AQUA, DyeColor.CYAN),
	MAGENTA("Magenta", ChatColor.DARK_PURPLE, DyeColor.MAGENTA),
	PINK("Rose", ChatColor.LIGHT_PURPLE, DyeColor.PINK),
	WHITE("Blanc", ChatColor.WHITE, DyeColor.WHITE),
	LIGHT_GRAY("Gris clair", ChatColor.GRAY, DyeColor.GRAY),
	BLACK("Noir", ChatColor.BLACK, DyeColor.BLACK);
	
	protected String _name;
	protected ChatColor _chatColor;
	protected DyeColor _dyeColor;
	
	TeamColor(String name, ChatColor chatColor, DyeColor dyeColor)
	{
		_name = name;
		_chatColor = chatColor;
		_dyeColor = dyeColor;
	}
	
	public ChatColor getChatColor()
	{
		return _chatColor;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public DyeColor getDyeColor()
	{
		return _dyeColor;
	}
	
	public ItemStack getWool()
	{
		return new Wool(_dyeColor).toItemStack();
	}
	
	public ItemStack getBanner()
	{
		ItemStack banner = new ItemStack(Material.BANNER, 1);
		BannerMeta meta = (BannerMeta)banner.getItemMeta();
		meta.setBaseColor(_dyeColor);
		return banner;
	}
}
