package ch.echo35.spigot.echoRPG.listeners;

import ch.echo35.spigot.echoRPG.objects.Geomancer;
import ch.echo35.spigot.echoRPG.utils.MagusMethods;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by echo35 on 11/3/2016.
 */
public class GeneralListener implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if (MagusMethods.isGeomancer(event.getPlayer()))
			new Geomancer(event.getPlayer());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if (MagusMethods.isGeomancer(event.getPlayer()))
			Geomancer.remove(event.getPlayer());
	}
}
