package ch.echo35.spigot.echoRPG;

/**
 * Created by echo35 on 9/7/16.
 */

import ch.echo35.spigot.echoRPG.handlers.GeomancyHandler;
import ch.echo35.spigot.echoRPG.listeners.AlchemyListener;
import ch.echo35.spigot.echoRPG.listeners.AngelListener;
import ch.echo35.spigot.echoRPG.listeners.GeneralListener;
import ch.echo35.spigot.echoRPG.listeners.GeomancyListener;
import ch.echo35.spigot.echoRPG.objects.Geomancer;
import ch.echo35.spigot.echoRPG.utils.ConfigManager;
import ch.echo35.spigot.echoRPG.utils.MagusMethods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener {
	public static Logger logger;

	public void onEnable() {
		logger = getLogger();
		new ConfigManager(this);
		PluginManager pm = this.getServer().getPluginManager();
		GeomancyHandler.init(this);
		AngelListener.init(this);
		pm.registerEvents(new GeneralListener(), this);
		pm.registerEvents(new AngelListener(), this);
		pm.registerEvents(new AlchemyListener(), this);
		pm.registerEvents(new GeomancyListener(), this);
		reInit();
		this.getLogger().info(String.format("%s version %s loaded!", getDescription().getName(), getDescription().getVersion()));
	}

	public static void reInit() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (MagusMethods.isGeomancer(player))
				new Geomancer(player);
		}
	}

	public static void syncHack(Runnable runnable) {
		org.spigotmc.AsyncCatcher.enabled = false;
		runnable.run();
		org.spigotmc.AsyncCatcher.enabled = true;
	}
}