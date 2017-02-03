package ch.echo35.spigot.echoRPG.utils;

import ch.echo35.spigot.echoRPG.Main;
import ch.echo35.spigot.echoRPG.objects.MagusSchool;
import ch.echo35.spigot.echoRPG.objects.Sigil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

/**
 * Created by echo35 on 11/1/2016.
 */
public class ConfigManager {
	private static JavaPlugin plugin;
	private static FileConfiguration config;

	public ConfigManager(JavaPlugin plugin) {
		plugin.saveDefaultConfig();
		this.plugin = plugin;
		this.config = plugin.getConfig();
		generateDefaultConfig();
		plugin.saveConfig();
		Main.logger.info("Configuration Manager initialized!");
	}

	public static String getMagusSchools(UUID player) {
		String uuid = player.toString();
		if (!config.isSet(String.format("magicians.%s.schools", uuid)))
			return "";
		return config.getString("magicians.%s.schools");
	}

	public static void addMagusSchools(UUID player, MagusSchool... schools) {
		String currentSchools = getMagusSchools(player);
		String uuid = player.toString();
		for (MagusSchool school : schools) {
			currentSchools += school.toString() + ";";
		}
	}

	public static void addSigil(Sigil sigil) {
		Location loc = sigil.getBlock().getLocation();
		String location = String.format("%s/%d/%d/%d", loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		config.set(String.format("sigils.%s.%s.type", sigil.getOwner(), location), sigil.getType());
		plugin.saveConfig();
	}

	public static void removeSigil(Sigil sigil) {
		config.set(String.format("sigils.%s", sigil.getOwner()), null);
		plugin.saveConfig();
	}

	private void generateDefaultConfig() {
		config.addDefault("sigils.dummy", "");
		config.options().copyDefaults(true);
	}
}
