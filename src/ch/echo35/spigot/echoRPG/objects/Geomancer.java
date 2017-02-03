package ch.echo35.spigot.echoRPG.objects;

import ch.echo35.spigot.echoRPG.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by echo35 on 11/3/2016.
 */
public class Geomancer extends MagicUser {
	public static ArrayList<Geomancer> onlineGeomancers = new ArrayList<>();
	private Sigil selectedSigil = null;

	public Geomancer(Player player) {
		super(player);
		onlineGeomancers.add(this);
		Main.logger.info("New geomancer " + player.getName());
	}

	public Sigil getSelectedSigil() {
		return selectedSigil;
	}

	public void setSelectedSigil(Sigil sigil) {
		this.selectedSigil = sigil;
		sigil.getBlock().getWorld().playSound(sigil.getBlock().getLocation(), Sound.ENTITY_IRONGOLEM_STEP, 1, 1);
	}

	public static Geomancer getGeomancer(Player player) {
		for (Geomancer geomancer : onlineGeomancers)
			if (geomancer.getPlayer().getEntityId() == player.getEntityId())
				return geomancer;
		return null;
	}

	public static void remove(Player player) {
		onlineGeomancers.remove(player);
	}
}