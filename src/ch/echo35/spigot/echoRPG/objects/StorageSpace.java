package ch.echo35.spigot.echoRPG.objects;

import ch.echo35.spigot.echoRPG.utils.MagusMethods;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by echo35 on 12/17/2016.
 */
public class StorageSpace {
	public static HashMap<Player, StorageSpace> instances = new HashMap<>();

	private ArrayList<Entity> entities = new ArrayList<>();

	public StorageSpace(Player player) {
		if (!MagusMethods.isGeomancer(player))
			return;
		if (Geomancer.getGeomancer(player).getSelectedSigil() == null)
			return;
		Sigil sigil = Geomancer.getGeomancer(player).getSelectedSigil();
		if (!sigil.getType().equals("storage"))
			return;
		store(sigil);
	}

	public void store(Sigil sigil) {
		for (Entity entity : sigil.getBlock().getWorld().getNearbyEntities(sigil.getBlock().getLocation(), 7, 1.5, 7)) {
			entities.add(entity);
		}
	}
}
