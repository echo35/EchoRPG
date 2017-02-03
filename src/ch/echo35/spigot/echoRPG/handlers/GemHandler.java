package ch.echo35.spigot.echoRPG.handlers;

import ch.echo35.spigot.echoRPG.Main;
import ch.echo35.spigot.echoRPG.utils.GeomancyItems;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by echo35 on 12/14/2016.
 */
public class GemHandler {
	public static ArrayList<LivingEntity> frozenEntities = new ArrayList<>();
	public static HashMap<Player, Long> soulTheifCooldown = new HashMap<>();

	public static void init(JavaPlugin plugin) {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers())
					if (soulTheifCooldown.containsKey(player) && soulTheifCooldown.get(player) >= System.currentTimeMillis())
						soulTheifCooldown.remove(player);
				for (World world : Bukkit.getWorlds())
					loop_a: for (Entity entity : world.getEntities()) {
						if (entity instanceof LivingEntity) {
							LivingEntity le = (LivingEntity) entity;
							if (frozenEntities.contains(le)) {
								for (Entity en : le.getWorld().getNearbyEntities(le.getLocation(), 5.0, 5.0, 5.0)) {
									if (en instanceof Item && GeomancyItems.matches(((Item) en).getItemStack(), GeomancyItems
											.getFearGem(1)))
										continue loop_a;
								}
								le.setAI(true);
								frozenEntities.remove(le);
							}
						}
						if (entity instanceof Item) {
							Item item = (Item) entity;
							ItemMeta meta = item.getItemStack().getItemMeta();
							if (meta == null || meta.getDisplayName() == null || !item.isOnGround())
								continue;
							if (GeomancyItems.matches(item.getItemStack(), GeomancyItems.getFireGem(1))) {
								for (Entity en : item.getWorld().getNearbyEntities(item.getLocation(), 5.0, 5.0, 5.0)) {
									if (en instanceof Player && GeomancyHandler.immuneCasters.contains((Player) en))
										continue;
									if (en instanceof LivingEntity) {
										LivingEntity le = (LivingEntity) en;
										le.setFireTicks(20);
									}
								}
							}
							else if (GeomancyItems.matches(item.getItemStack(), GeomancyItems.getGravityGem(1))) {
								for (Entity en : item.getWorld().getNearbyEntities(item.getLocation(), 5.0, 5.0, 5.0)) {
									if (en instanceof Player && GeomancyHandler.immuneCasters.contains(en))
										continue;
									if (en instanceof LivingEntity)
										((LivingEntity) en).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 4, true));
									en.setVelocity(en.getVelocity().add(new Vector(0, -0.5, 0)));
									break;
								}
							}
						}
					}
			}
		}, 1L, 1L);

		Main.logger.info("Geomancy:Gem Handler initialized!");

	}
}
