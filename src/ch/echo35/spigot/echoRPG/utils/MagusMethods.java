package ch.echo35.spigot.echoRPG.utils;

import org.bukkit.entity.Player;

/**
 * Created by echo35 on 11/3/2016.
 */
public class MagusMethods {
	public static boolean isGeomancer(Player player) {
		return player.hasPermission("geomancy.user");
	}
}
