package MiniGameAPI.MiniGame;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.material.Wool;

/**
 * Enum that represents every available teams with their variables
 * 
 * @author Elytes
 *
 */
public enum TeamTemplate 
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
	
	/**
	 * Variable that store the name of this Team
	 */
	protected String _name;
	/**
	 * Variable that store the ChatColor of this Team
	 */
	protected ChatColor _chatColor;
	/**
	 * Variable that store the DyeColor of this Team
	 */
	protected DyeColor _dyeColor;
	
	/**
	 * Constructor of TeamTemplate
	 * 
	 * @param name Name of this Team
	 * @param chatColor ChatColor of this Team
	 * @param dyeColor DyeColor of this Team
	 */
	TeamTemplate(String name, ChatColor chatColor, DyeColor dyeColor)
	{
		_name = name;
		_chatColor = chatColor;
		_dyeColor = dyeColor;
	}
	
	/**
	 * Method used to get the ChatColor of this Team
	 * 
	 * @return ChatColor of this Team
	 */
	public ChatColor getChatColor()
	{
		return _chatColor;
	}
	
	/**
	 * Method used to get the name of this Team
	 * 
	 * @return Name of this Team
	 */
	public String getName()
	{
		return _name;
	}
	
	/**
	 * Method used to get the DyeColor of this team, this will be used to get the wool and the banner of this team
	 * 
	 * @return DyeColor of this team
	 */
	public DyeColor getDyeColor()
	{
		return _dyeColor;
	}
	
	/**
	 * Method used to get the Wool that represents this team
	 * 
	 * @return Wool of this team
	 */
	public ItemStack getWool()
	{
		return new Wool(_dyeColor).toItemStack();
	}
	
	/**
	 * Method used to get the Banner that represents this team
	 * 
	 * @return Banner of this team
	 */
	public ItemStack getBanner()
	{
		ItemStack banner = new ItemStack(Material.BANNER, 1);
		BannerMeta meta = (BannerMeta)banner.getItemMeta();
		meta.setBaseColor(_dyeColor);
		return banner;
	}
}
