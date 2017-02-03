package ch.echo35.spigot.echoRPG.handlers;

import ch.echo35.spigot.echoRPG.Main;
import ch.echo35.spigot.echoRPG.objects.Sigil;
import ch.echo35.spigot.echoRPG.utils.GeomancyMethods;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by echo35 on 11/1/2016.
 */
public class GeomancyHandler {
	public static ArrayList<Player> immuneCasters = new ArrayList<>();


	public static void init(JavaPlugin plugin) {
		SigilHandler.init(plugin);
		GemHandler.init(plugin);
		FileConfiguration config = plugin.getConfig();
		for (String uuid : config.getConfigurationSection("sigils").getKeys(false)) {
			if (uuid.equals("dummy"))
				continue;
			Main.logger.info("Creating sigil at uuid " + uuid);
			for (String location : config.getConfigurationSection("sigils." + uuid).getKeys(false)) {
				Main.logger.info("Creating sigil at loc " + location);
				String[] coords = location.split("/");
				Location loc = new Location(
						plugin.getServer().getWorld(coords[0]),
						Integer.parseInt(coords[1]),
						Integer.parseInt(coords[2]),
						Integer.parseInt(coords[3]));
				GeomancyMethods.addSigil(new Sigil(UUID.fromString(uuid), loc.getBlock(), config.getString(String.format("sigils.%s.%s" +
								".type", uuid,
						location))), false);
			}
		}

		Main.logger.info("All Geomancy Handlers initialized!");
	}




}
