package ch.echo35.spigot.echoRPG.handlers;

import ch.echo35.spigot.echoRPG.Main;
import ch.echo35.spigot.echoRPG.objects.Sigil;
import ch.echo35.spigot.echoRPG.utils.GeomancyMethods;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by echo35 on 12/14/2016.
 */
public class SigilHandler {
	public static void init(JavaPlugin plugin) {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for (Sigil sigil : Sigil.sigils) {
					for (Entity entity : sigil.getBlock().getWorld().getNearbyEntities(sigil.getBlock().getLocation(),
							GeomancyMethods.EFFECT_RANGE, GeomancyMethods.EFFECT_RANGE, GeomancyMethods.EFFECT_RANGE)) {
						if (!(entity instanceof Player && GeomancyHandler.immuneCasters.contains((Player) entity)))
							sigil.applyEffect(entity);
					}
				}
			}
		}, 1L, 1L);
		Main.logger.info("Geomancy:Sigil Handler initialized!");

	}


}
