package ch.echo35.spigot.echoRPG.listeners;

import ch.echo35.spigot.echoRPG.Main;
import ch.echo35.spigot.echoRPG.handlers.GemHandler;
import ch.echo35.spigot.echoRPG.objects.Geomancer;
import ch.echo35.spigot.echoRPG.objects.Sigil;
import ch.echo35.spigot.echoRPG.utils.GeomancyItems;
import ch.echo35.spigot.echoRPG.utils.GeomancyMethods;
import ch.echo35.spigot.echoRPG.utils.MagusMethods;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

/**
 * Created by echo35 on 11/1/2016.
 */
public class GeomancyListener implements Listener {
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getBlockPlaced().getType() == Material.EMERALD_BLOCK && MagusMethods.isGeomancer(event.getPlayer())) {
			Block sigil = event.getBlockPlaced();
			if (GeomancyMethods.detectTotem(event.getPlayer(), sigil)) return;
			else if (GeomancyMethods.detectTeleportCircle(event.getPlayer(), sigil)) return;
			else if (GeomancyMethods.detectStorageCircle(event.getPlayer(), sigil)) return;
			else if (GeomancyMethods.detectTransmutationCircle(event.getPlayer(), sigil)) return;
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.EMERALD_BLOCK) {
			Block block = event.getBlock();
			Sigil sigil = null;
			for (Sigil sigil_ : Sigil.sigils) {
				if (sigil_.getBlock().getLocation().equals(block.getLocation())) {
					sigil = sigil_;
				}
			}
			if (sigil != null) {
				GeomancyMethods.breakSigil(sigil);
			}
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if (event.getPlayer().hasPermission("geomancy.user")) {
			if (GeomancyMethods.isValidTome(event.getPlayer().getInventory().getItemInMainHand())) {
				GeomancyMethods.castSpell(event.getPlayer(), event.getMessage());
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (MagusMethods.isGeomancer(event.getPlayer())) {
			if (GeomancyMethods.isValidTome(event.getPlayer().getInventory().getItemInMainHand())) {
				if (Sigil.isSigil(event.getClickedBlock())) {
					Geomancer.getGeomancer(event.getPlayer()).setSelectedSigil(Sigil.getSigil(event.getClickedBlock()));
				}
			}
		}
	}

	@EventHandler
	public void onEntityRightClick(PlayerInteractEntityEvent event) {
		if (MagusMethods.isGeomancer(event.getPlayer())) {
			if (GeomancyItems.matches(event.getPlayer().getInventory().getItemInMainHand(), GeomancyItems.getEmptySoulGem(1))
					&& !GemHandler.soulTheifCooldown.containsKey(event.getPlayer())) {
				if (event.getRightClicked() instanceof LivingEntity
						&& !(event.getRightClicked() instanceof Player)
						&& ((LivingEntity) event.getRightClicked()).hasAI()) {
					((LivingEntity) event.getRightClicked()).setAI(false);
					event.getRightClicked().setSilent(true);
					event.getRightClicked().setVelocity(new Vector(0, 0, 0));
					event.getPlayer().getInventory().removeItem(GeomancyItems.getEmptySoulGem(1));
					event.getPlayer().getInventory().addItem(GeomancyItems.getSoulGem(1));
					GemHandler.soulTheifCooldown.put(event.getPlayer(), System.currentTimeMillis()+1000);
				}
			}
			else if (GeomancyItems.matches(event.getPlayer().getInventory().getItemInMainHand(), GeomancyItems.getSoulGem(1))
					&& !GemHandler.soulTheifCooldown.containsKey(event.getPlayer())) {
				if (event.getRightClicked() instanceof LivingEntity
						&& !(event.getRightClicked() instanceof Player)
						&& !(((LivingEntity) event.getRightClicked()).hasAI())) {
					((LivingEntity) event.getRightClicked()).setAI(true);
					event.getRightClicked().setSilent(false);
					event.getPlayer().getInventory().removeItem(GeomancyItems.getSoulGem(1));
					event.getPlayer().getInventory().addItem(GeomancyItems.getEmptySoulGem(1));
					GemHandler.soulTheifCooldown.put(event.getPlayer(), System.currentTimeMillis()+1000);
				}
			}
		}
	}
}
