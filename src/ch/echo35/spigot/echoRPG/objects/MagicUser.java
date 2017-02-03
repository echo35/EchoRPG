package ch.echo35.spigot.echoRPG.objects;

import org.bukkit.entity.Player;

/**
 * Created by echo35 on 11/3/2016.
 */
public abstract class MagicUser {
	public static final int DEFAULT_MAX_MANA = 80;

	protected Player player;
	private int mana;
	private int maxMana;

	public MagicUser(Player player) {
		this.player = player;
		this.maxMana = DEFAULT_MAX_MANA;
	}

	public Player getPlayer() {
		return this.player;
	}

	public int getMana() {
		return this.mana;
	}

	public int getMaxMana() {
		return this.maxMana;
	}

	public boolean subtractMana(int amount) {
		if (this.mana - amount < 0)
			return false;
		this.mana -= amount;
		return true;
	}

	public boolean addMana(int amount) {
		if (this.mana + amount > getMaxMana())
			return false;
		this.mana += amount;
		return true;
	}
}
