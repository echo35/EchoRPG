package ch.echo35.spigot.echoRPG.utils;

import ch.echo35.spigot.echoRPG.Main;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by echo35 on 12/15/2016.
 */
public class GeomancyItems {
	public static Material GEM_MATERIAL = Material.EMERALD;

	public static ItemStack getFireGem(int quantity) {
		ItemStack itemStack = new ItemStack(GEM_MATERIAL);
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName("Conflagration Gem");
		meta.setLore(Arrays.asList("Geomancy Artifact", "A shining green gem storing a fire curse."));
		itemStack.setItemMeta(meta);
		itemStack.setAmount(quantity);
		return itemStack;
	}

	public static ItemStack getGravityGem(int quantity) {
		ItemStack itemStack = new ItemStack(GEM_MATERIAL);
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName("Gravity Gem");
		meta.setLore(Arrays.asList("Geomancy Artifact", "A shining green gem that distorts space."));
		itemStack.setItemMeta(meta);
		itemStack.setAmount(quantity);
		return itemStack;
	}

	public static ItemStack getFearGem(int quantity) {
		ItemStack itemStack = new ItemStack(GEM_MATERIAL);
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName("Fear Gem");
		meta.setLore(Arrays.asList("Geomancy Artifact", "A shining green gem that paralyzes enemies out of fear."));
		itemStack.setItemMeta(meta);
		itemStack.setAmount(quantity);
		return itemStack;
	}

	public static ItemStack getEmptySoulGem(int quantity) {
		ItemStack itemStack = new ItemStack(GEM_MATERIAL);
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName("Soul Gem");
		meta.setLore(Arrays.asList("Geomancy Artifact", "A shining green gem that can contain a soul."));
		itemStack.setItemMeta(meta);
		itemStack.setAmount(quantity);
		return itemStack;
	}

	public static ItemStack getSoulGem(int quantity) {
		ItemStack itemStack = new ItemStack(GEM_MATERIAL);
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName("Soul Gem");
		meta.setLore(Arrays.asList("Geomancy Artifact", "A shining green gem that contains a soul."));
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemStack.setItemMeta(meta);
		itemStack.setAmount(quantity);
		return itemStack;
	}

	public static ItemStack getEmptyGem(int quantity) {
		ItemStack itemStack = new ItemStack(GEM_MATERIAL);
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName("Empty Gem");
		meta.setLore(Arrays.asList("Geomancy Artifact", "A shining green gem capable of storing magic."));
		itemStack.setItemMeta(meta);
		itemStack.setAmount(quantity);
		return itemStack;
	}

	public static boolean matches(ItemStack a, ItemStack b) {
		if (a.hasItemMeta() != b.hasItemMeta())
			return false;
		if (a.getItemMeta().hasDisplayName() != b.getItemMeta().hasDisplayName())
			return false;
		if (a.getItemMeta().hasLore() != b.getItemMeta().hasLore())
			return false;
		if (!(a.getItemMeta().getDisplayName().equals(b.getItemMeta().getDisplayName())))
			return false;
		if (!(a.getItemMeta().getLore().toString().equals(b.getItemMeta().getLore().toString())))
			return false;
		return true;
	}
}
