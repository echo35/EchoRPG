package ch.echo35.spigot.echoRPG.listeners;

import ch.echo35.spigot.echoRPG.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by echo35 on 11/2/2016.
 */
public class AngelListener implements Listener {

	private static double HB_RADIUS = 4.0;
	private static double HB_DAMAGE = 8.5;
	private static double HB_BOOST = 2.0;
	private static long HB_BOOST_CD = 2000;
	private static HashMap<Player, Long> HB_BOOST_LIST = new HashMap<>();

	public static void init(JavaPlugin plugin) {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for (Player p : plugin.getServer().getOnlinePlayers()) {
					if (p.hasPermission("echoRPG.fly") && p.isGliding()) {
						double pitch = p.getLocation().getPitch();
						if (pitch < 0) {
							p.setVelocity(p.getVelocity().add(new Vector(0., pitch * -.0015, 0.)));
						}
					}

					if (p.hasPermission("echoRPG.fireimmune") && p.getFireTicks() > 0) {
						p.setFireTicks(0);
					}
				}

				for (Player p : HB_BOOST_LIST.keySet()) {
					if (System.currentTimeMillis() > HB_BOOST_LIST.get(p)) {
						HB_BOOST_LIST.remove(p);
					}
				}
			}
		}, 1L, 1L);

		Main.logger.info("Angel handler initialized!");
	}

	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		if (e.getPlayer().hasPermission("echoRPG.vanish") && e.getPlayer().isOnGround()) {
			if (e.getPlayer().getGameMode() == GameMode.ADVENTURE || e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
				if (e.isSneaking())
					e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
				else
					e.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
			}
		}
	}

	@EventHandler
	public void onFly(PlayerToggleFlightEvent e) {
		if (e.getPlayer().hasPermission("echoRPG.fly")) {
			if (e.getPlayer().getGameMode() == GameMode.ADVENTURE || e.getPlayer().getGameMode() == GameMode.SURVIVAL) {
				if (!e.getPlayer().isGliding()) {
					Vector v = e.getPlayer().getVelocity().multiply(3).setY(e.getPlayer().getVelocity().getY() + 0.3);//new Vector(0, 0.3, 0)));
					e.getPlayer().setGliding(true);
					e.getPlayer().setFlying(false);
					e.getPlayer().setVelocity(v);
					e.setCancelled(true);
				} else {
					e.getPlayer().setGliding(false);
					e.getPlayer().setFlying(true);
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onGlide(EntityToggleGlideEvent e) {
		if (e.getEntity().hasPermission("echoRPG.fly") && !e.getEntity().isOnGround()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onCollide(EntityDamageEvent e) {
		if (e.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
			if (e.getEntity() instanceof Player) {
				if (((Player) e.getEntity()).hasPermission("echoRPG.fly")) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (e.getPlayer().hasPermission("echoRPG.fly"))
			e.getPlayer().setAllowFlight(true);
	}

	@EventHandler
	public void onSwing(PlayerAnimationEvent e) {
		if (e.getPlayer().hasPermission("echoRPG.heavenblade")
				&& e.getPlayer().isGliding()
				&& e.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLD_SWORD
				&& e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()
				&& e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains("A Legendary Blade from the Heavens")) {
			for (LivingEntity en : e.getPlayer().getWorld().getLivingEntities()) {
				if (en.getLocation().distance(e.getPlayer().getLocation()) < HB_RADIUS
						&& en.getEntityId() != e.getPlayer().getEntityId()) {
					en.damage(HB_DAMAGE, e.getPlayer());
					e.getPlayer().getWorld().playSound(en.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);
				}
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getPlayer().hasPermission("echoRPG.heavenblade")
				&& !HB_BOOST_LIST.containsKey(e.getPlayer())) {
			if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLD_SWORD
					&& e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()
					&& e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains("A Legendary Blade from the Heavens")
					&& e.getAction() == Action.RIGHT_CLICK_AIR) {
//				e.getPlayer().setVelocity(e.getPlayer().getVelocity().add(e.getPlayer().getVelocity().multiply(HB_BOOST)));
				e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().normalize().multiply(HB_BOOST)); //add(e.getPlayer().getVelocity().multiply(HB_BOOST)));
				HB_BOOST_LIST.put(e.getPlayer(), System.currentTimeMillis() + HB_BOOST_CD);
				e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ITEM_ELYTRA_FLYING, 1, 1);
			}
		}

		if (e.getPlayer().hasPermission("echoRPG.heavenblade")) {
			if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.IRON_SWORD
					&& (!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()
					|| !e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore().contains("A Legendary Blade from the Heavens"))) {
				if (e.getAction() == Action.LEFT_CLICK_BLOCK
						&& e.getClickedBlock().getType() == Material.GOLD_BLOCK
						&& e.getClickedBlock().getLocation().getBlockY() >= 200) {
					e.getPlayer().sendMessage(ChatColor.GRAY + "Heaven grants you a holy blade");
					ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
					ItemMeta meta = item.getItemMeta();
					meta.setLore(
							Arrays.asList(new String[]{"A Legendary Blade from the Heavens"})
					);
					meta.spigot().setUnbreakable(true);
					meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					item.setItemMeta(meta);
					item.setType(Material.GOLD_SWORD);
					item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 8);
					e.getPlayer().getInventory().setItemInMainHand(item);
				}
			}
		}
	}
}
