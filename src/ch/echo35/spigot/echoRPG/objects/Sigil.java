package ch.echo35.spigot.echoRPG.objects;

import ch.echo35.spigot.echoRPG.utils.ConfigManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by echo35 on 11/1/2016.
 */
public class Sigil {
	private Block block;
	private UUID uuid;
	private String type;

	public static ArrayList<Sigil> sigils = new ArrayList<>();
	public HashMap<Entity, Long> cooldowns = new HashMap<>();

	public Sigil(UUID uuid, Block block, String type) {
		this.block = block;
		this.uuid = uuid;
		setType(type);
		sigils.add(this);
	}

	public Block getBlock() {
		return this.block;
	}

	public String getType() {
		return this.type;
	}

	public UUID getOwner() {
		return this.uuid;
	}

	public boolean confirmPlayer(Player player) {
		return player.getUniqueId().equals(this.uuid);
	}

	public void setType(String newtype) {
		this.type = newtype;
		getBlock().getWorld().playSound(getBlock().getLocation(), Sound.ENTITY_IRONGOLEM_DEATH, 2.f, 2.f);
		ConfigManager.addSigil(this);
	}

	public void applyEffect(Entity entity) {
		if (cooldowns.containsKey(entity)) {
			if (System.currentTimeMillis() < cooldowns.get(entity))
				return;
			else
				cooldowns.remove(entity);
		}
		switch (type) {
			case "poison":
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 2, true, true));
				break;
			case "decay":
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 3, true, true));
				break;
			case "gravity":
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 4, true));
				entity.setVelocity(entity.getVelocity().add(new Vector(0, -0.5, 0)));
				cooldowns.put(entity, System.currentTimeMillis() + 50);
				break;
			case "antigravity":
				entity.setVelocity(entity.getVelocity().add(new Vector(0, 2.0, 0)));
				cooldowns.put(entity, System.currentTimeMillis() + 1000);
				break;
			case "forge":
				if (entity.getType() == EntityType.DROPPED_ITEM && entity.getLocation().distance(this.getBlock().getLocation()) <= 1.5) {
					Item item = (Item) entity;
					if (item.getItemStack().getType() == Material.EMERALD) {
						ItemMeta meta = item.getItemStack().getItemMeta();
						if (item.getItemStack().getItemMeta().hasLore() && meta.getLore().get(0).equals("Geomancy Artifact"))
							break;
						meta.setDisplayName("Empty Gem");
						meta.setLore(Arrays.asList("Geomancy Artifact", "A shining green gem capable of storing magic."));
						meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						item.getItemStack().setItemMeta(meta);
						item.getWorld().strikeLightningEffect(item.getLocation());
					}
				}
				break;
			default:
				break;
		}
	}

	public void remove() {
		sigils.remove(this);
	}

	public static boolean isSigil(Block block) {
		return getSigil(block) != null;
	}

	public static Sigil getSigil(Block block) {
		for (Sigil sigil : sigils)
			if (sigil.getBlock().equals(block))
				return sigil;
		return null;
	}
}
