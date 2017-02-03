package ch.echo35.spigot.echoRPG.objects;

import ch.echo35.spigot.echoRPG.Main;
import ch.echo35.spigot.echoRPG.utils.GeomancyItems;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

/**
 * Created by echo35 on 12/15/2016.
 */
public class TransmutationIngredient {
	private String name;

	public TransmutationIngredient(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return getName();
	}

	public void yield(Location loc) {
		if (Material.getMaterial(name) != null) {
			Main.syncHack(() -> loc.getWorld().dropItem(loc, new ItemStack(Material.valueOf(name))));
		}
		else {
			if (name.equals("%EMPTYGEM%"))
				Main.syncHack(() -> loc.add(0.5, 0.5, 0.5).getWorld().dropItem(loc, GeomancyItems.getEmptyGem(1)));
			else if (name.equals("%FIREGEM%"))
				Main.syncHack(() -> loc.add(0.5, 0.5, 0.5).getWorld().dropItem(loc, GeomancyItems.getFireGem(1)));
			else if (name.equals("%GRAVITYGEM%"))
				Main.syncHack(() -> loc.add(0.5, 0.5, 0.5).getWorld().dropItem(loc, GeomancyItems.getGravityGem(1)));
			else if (name.equals("%SOULGEM%"))
				Main.syncHack(() -> loc.add(0.5, 0.5, 0.5).getWorld().dropItem(loc, GeomancyItems.getEmptySoulGem(1)));
			else if (name.equals("%LIVEGEM%"))
				Main.syncHack(() -> loc.add(0.5, 0.5, 0.5).getWorld().dropItem(loc, GeomancyItems.getSoulGem(1)));
			else if (name.equals("%ZOMBIE%"))
				Main.syncHack(() -> loc.add(0.5, 0.5, 0.5).getWorld().spawnEntity(loc, EntityType.ZOMBIE));
			else if (name.equals("%ZOMBIECORPSE%")) {
				Main.syncHack(new Runnable() {
					public void run() {
						LivingEntity e = (LivingEntity) loc.add(0.5, 0.5, 0.5).getWorld().spawnEntity(loc, EntityType.ZOMBIE);
						e.setAI(false);
						e.setSilent(true);
					}
				});
			}
			else if (name.equals("%PIG%")) {
				Main.syncHack(() -> loc.add(0.5, 0.5, 0.5).getWorld().spawnEntity(loc, EntityType.PIG));
			}
			else if (name.equals("%PIGCORPSE%")) {
				Main.syncHack(new Runnable() {
					public void run() {
						LivingEntity e = (LivingEntity) loc.add(0.5, 0.5, 0.5).getWorld().spawnEntity(loc, EntityType.PIG);
						e.setAI(false);
						e.setSilent(true);
					}
				});
			}
			else if (name.equals("%PIGZOMBIE%")) {
				Main.syncHack(() -> loc.add(0.5, 0.5, 0.5).getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE));
			}
			else if (name.equals("%PIGZOMBIECORPSE%")) {
				Main.syncHack(new Runnable() {
					public void run() {
						LivingEntity e = (LivingEntity) loc.add(0.5, 0.5, 0.5).getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
						e.setAI(false);
						e.setSilent(true);

					}
				});
			}
		}
	}

	public void consume(Location loc) {
		for (Entity en : loc.getWorld().getNearbyEntities(loc, 7, 1.5, 7)) {
			if (en instanceof Item) {
				Item item = (Item) en;
				if (Material.getMaterial(name) != null) {
					if (item.getItemStack().getType() == Material.valueOf(name)) {
						if (item.getItemStack().getAmount() <= 1)
							item.remove();
						else
							item.getItemStack().setAmount(item.getItemStack().getAmount() - 1);
						return;
					}
				}
				else {
					boolean decrease = false;
					if (name.equals("%EMPTYGEM%") && GeomancyItems.matches(item.getItemStack(), GeomancyItems.getEmptyGem(1)))
						decrease = true;
					else if (name.equals("%FIREGEM%") && GeomancyItems.matches(item.getItemStack(), GeomancyItems.getFireGem(1)))
						decrease = true;
					else if (name.equals("%GRAVITYGEM%") && GeomancyItems.matches(item.getItemStack(), GeomancyItems.getGravityGem(1)))
						decrease = true;
					else if (name.equals("%SOULGEM%") && GeomancyItems.matches(item.getItemStack(), GeomancyItems.getEmptySoulGem(1)))
						decrease = true;
					else if (name.equals("%LIVEGEM%") && GeomancyItems.matches(item.getItemStack(), GeomancyItems.getSoulGem(1)))
						decrease = true;
					if (decrease) {
						if (item.getItemStack().getAmount() <= 1)
							item.remove();
						else
							item.getItemStack().setAmount(item.getItemStack().getAmount() - 1);
						return;
					}
				}
			}
			if (en instanceof LivingEntity) {
				System.out.println("FOUND " + en.getType() + " : " +((((LivingEntity) en).hasAI()) ? "smart" : "dumb"));
				if (name.equals("%ZOMBIE%")) {
					if (en.getType() == EntityType.ZOMBIE && ((LivingEntity) en).hasAI()) {
						en.remove();
						return;
					}
				}
				else if (name.equals("%ZOMBIECORPSE%")) {
					if (en.getType() == EntityType.ZOMBIE && !(((LivingEntity) en).hasAI())) {
						en.remove();
						return;
					}
				}
				else if (name.equals("%PIG%")) {
					if (en.getType() == EntityType.PIG && ((LivingEntity) en).hasAI()) {
						en.remove();
						return;
					}
				}
				else if (name.equals("%PIGCORPSE%")) {
					if (en.getType() == EntityType.PIG && !(((LivingEntity) en).hasAI())) {
						en.remove();
						return;
					}
				}
				else if (name.equals("%PIGZOMBIE%")) {
					if (en.getType() == EntityType.PIG_ZOMBIE && ((LivingEntity) en).hasAI()) {
						en.remove();
						return;
					}
				}
				else if (name.equals("%PIGZOMBIECORPSE%")) {
					if (en.getType() == EntityType.PIG_ZOMBIE && !(((LivingEntity) en).hasAI())) {
						en.remove();
						return;
					}
				}
			}
		}
	}

	public static boolean match(Entity entity, String name) {
		Main.logger.info("match! called");
		Main.logger.info("name: " + name);
		if (entity instanceof Item) {
			Item item = (Item) entity;
			if (Material.getMaterial(name) != null) {
				if (item.getItemStack().getType() == Material.valueOf(name))
					return true;
			}
			else {
				Main.logger.info("not a normal item");
				if (name.equals("%EMPTYGEM%")) {
					Main.logger.info("EMPTY");
					if (GeomancyItems.matches(item.getItemStack(), GeomancyItems.getEmptyGem(1)))
						return true;
				}
				else if (name.equals("%FIREGEM%")) {
					if (GeomancyItems.matches(item.getItemStack(), GeomancyItems.getFireGem(1)))
						return true;
				}
				else if (name.equals("%GRAVITYGEM%")) {
					if (GeomancyItems.matches(item.getItemStack(), GeomancyItems.getGravityGem(1)))
						return true;
				}
				else if (name.equals("%SOULGEM%")) {
					Main.logger.info("SOUL");
					if (GeomancyItems.matches(item.getItemStack(), GeomancyItems.getEmptySoulGem(1)))
						return true;
				}
				else if (name.equals("%LIVEGEM%")) {
					Main.logger.info("LIVE");
					if (GeomancyItems.matches(item.getItemStack(), GeomancyItems.getSoulGem(1)))
						return true;
				}
			}
		}
		else {
			Main.logger.info("not an item : " + name);
			if (name.equals("%ZOMBIE%")) {
				System.out.println("ZOMBIE");
				if (entity.getType() == EntityType.ZOMBIE && ((LivingEntity) entity).hasAI())
					return true;
			}
			else if (name.equals("%ZOMBIECORPSE%")) {
				System.out.println("ZOMBIECORPSE");
				if (entity.getType() == EntityType.ZOMBIE && !(((LivingEntity) entity).hasAI()))
					return true;
			}
			else if (name.equals("%PIG%")) {
				if (entity.getType() == EntityType.PIG && ((LivingEntity) entity).hasAI())
					return true;
			}
			else if (name.equals("%PIGCORPSE%")) {
				Main.logger.info("pigcorpse?");
				if (entity.getType() == EntityType.PIG && !(((LivingEntity) entity).hasAI())) {
					Main.logger.info("no ai.");
					return true;
				}
			}
			else if (name.equals("%PIGZOMBIE%")) {
				if (entity.getType() == EntityType.PIG_ZOMBIE && ((LivingEntity) entity).hasAI())
					return true;
			}
			else if (name.equals("%PIGZOMBIECORPSE%")) {
				if (entity.getType() == EntityType.PIG_ZOMBIE && !(((LivingEntity) entity).hasAI()))
					return true;
			}
			Main.logger.info("fuck me.");
		}
		Main.logger.info("fuck me twice.");

		return false;
	}
}
