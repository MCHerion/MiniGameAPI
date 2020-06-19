package MiniGameAPI.GUI.FieldCases;

import java.lang.reflect.Field;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import MiniGameAPI.GUI.FieldCase;
import MiniGameAPI.GUI.GameFlagBuilder;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;
import net.md_5.bungee.api.ChatColor;

public class StringFieldCase extends FieldCase
{
	public StringFieldCase(GameFlagBuilder pere, MiniGamePlayer<?> MiniGamePlayer, Field field, GameFlagInfos gameFlagInfos) 
	{
		super(pere, MiniGamePlayer, field, Material.STRING, gameFlagInfos);
		_player.sendMessage(ChatColor.YELLOW + "Veuillez saisir du texte");
	}

	@Override
	public void setObject(Player player, String message)
	{
		_object = message;
	}
}
