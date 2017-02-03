package ch.echo35.spigot.echoRPG.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.LingeringPotionSplashEvent;

/**
 * Created by echo35 on 11/2/2016.
 */
public class AlchemyListener implements Listener {
	@EventHandler
	public void onLinger(LingeringPotionSplashEvent e) {
		e.getAreaEffectCloud().setDuration(500);
	}
}
