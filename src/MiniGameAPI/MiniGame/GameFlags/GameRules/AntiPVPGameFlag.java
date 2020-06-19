package MiniGameAPI.MiniGame.GameFlags.GameRules;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import MiniGameAPI.MiniGame.GameFlag;
import MiniGameAPI.MiniGame.MiniGame;
import MiniGameAPI.MiniGame.GameFlags.GameFlagInfos;
import MiniGameAPI.MiniGamePlayer.MiniGamePlayer;

@GameFlagInfos
(
	name = "Interdire le PVP",
	description = { "Permet d'interdire le PVP à tous les joueurs du mini-jeu" }
)
public class AntiPVPGameFlag<MG extends MiniGame<?>> extends GameFlag<MG>
{
	public AntiPVPGameFlag(MG miniGame) 
	{
		super(miniGame);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if(event.getEntity().getWorld() == getMiniGame().getWorld())
		{
			if(event.getDamager() instanceof LivingEntity && event.getEntity() instanceof Player)
			{
				Player damager = null;
				Player damaged = (Player) event.getEntity();
				if(damager instanceof Player)
				{
					damager = (Player) event.getDamager();
				}
				else if(damager instanceof Projectile)
				{
					if(((Projectile) event.getDamager()).getShooter() instanceof Player)
					{
						damager = (Player) ((Projectile) event.getDamager()).getShooter();
					}
				}
				if(damager != null)
				{
					if(check(_miniGame.getCustomPlayer(damager), _miniGame.getCustomPlayer(damaged)))
					{
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	public boolean check(MiniGamePlayer<?> damagerPlayer, MiniGamePlayer<?> damagedPlayer)
	{
		return true;
	}
}