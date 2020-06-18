package MiniGameAPI.MiniGame.GameFlags.GameRules;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import MiniGameAPI.MiniGame.GameFlag;
import MiniGameAPI.MiniGame.MiniGame;

public class AntiPVPGameFlag<MG extends MiniGame<?>> extends GameFlag<MG>
{
	public AntiPVPGameFlag(MG miniGame) 
	{
		super(miniGame, "Interdire le PVP", "Permet d'interdire le PVP à tous les joueurs du mini-jeu");
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
	{
		if(event.getEntity().getWorld() == getMiniGame().getWorld())
		{
			if(event.getDamager() instanceof LivingEntity && event.getEntity() instanceof Player)
			{
				Player damager = null;
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
					event.setCancelled(true);
				}
			}
		}
	}
}