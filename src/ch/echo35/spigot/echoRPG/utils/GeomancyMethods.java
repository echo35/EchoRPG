package ch.echo35.spigot.echoRPG.utils;

import ch.echo35.spigot.echoRPG.Main;
import ch.echo35.spigot.echoRPG.handlers.GeomancyHandler;
import ch.echo35.spigot.echoRPG.objects.Geomancer;
import ch.echo35.spigot.echoRPG.objects.Sigil;
import ch.echo35.spigot.echoRPG.objects.TransmutationIngredient;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

/**
 * Created by echo35 on 12/14/2016.
 */
public class GeomancyMethods {
	public static final double SPELL_RANGE = 10.2;
	public static final double EFFECT_RANGE = 10.0;
	private static final String SICKNESS_SPELL = "Toxis ur dux lehem naquis";
	private static final String GRAVITY_SPELL = "Lodas gravit fortim";
	private static final String RISING_SPELL = "Gravitad leher alta e";
	private static final String DECAY_SPELL = "Mater tomos nil morphit";
	private static final String IMMUNITY_SPELL = "Immuni suum boratae";
	private static final String IMMUNITY2_SPELL = "Immuni suum liberat";
	private static final String DEACTIVATION_SPELL = "Sigil auf net dormisit";

	private static final String TRANSMUTATION_SPELL = "Transmute";
	private static final String TELEPORTATION_SPELL = "Teleport";
	private static final String STORAGE_SPELL = "Store";
	private static final String FORGE_FIRE_SPELL= "Conflagrem lithos audelphis";
	private static final String FORGE_GRAVITY_SPELL= "Gravitad lithos audelphis";
	private static final String FORGE_EMPTY_SPELL = "Nullek lithi koohrem";

	public static void validateTransmutation(Player caster) {
		Sigil sigil = Geomancer.getGeomancer(caster).getSelectedSigil();
		String[] recipes = {
				"%ZOMBIECORPSE%,%PIGCORPSE%,%LIVEGEM%=%PIGZOMBIE%,%SOULGEM%",
				"%ZOMBIECORPSE%,%PIG%,%SOULGEM%=%PIGZOMBIECORPSE%,%LIVEGEM%",
				"%ZOMBIE%,%PIGCORPSE%,%SOULGEM%=%PIGZOMBIECORPSE%,%LIVEGEM%",
				"%ZOMBIE%,%PIG%,%SOULGEM%=%PIGZOMBIE%,%LIVEGEM%",

				"%ZOMBIECORPSE%,%PIG%=%PIGZOMBIE%",
				"%ZOMBIE%,%PIGCORPSE%=%PIGZOMBIE%",

				"%PIGZOMBIE%,%SOULGEM%=%ZOMBIECORPSE%,%PIGCORPSE%,%LIVEGEM%",
				"%PIGZOMBIECORPSE%,%LIVEGEM%=%ZOMBIECORPSE%,%PIG%,%SOULGEM%",
				"%PIGCORPSE%,%ZOMBIECORPSE%=%PIGZOMBIECORPSE%",

				"%PIGZOMBIE%=%ZOMBIECORPSE%,%PIG%",
				"%PIGZOMBIECORPSE%=%ZOMBIECORPSE%,%PIGCORPSE%",

				"BONE,BONE,BONE,BONE,ROTTEN_FLESH,ROTTEN_FLESH,ROTTEN_FLESH,ROTTEN_FLESH,IRON_INGOT,%LIVEGEM%=%ZOMBIE%,%SOULGEM%",
				"BONE,BONE,BONE,BONE,ROTTEN_FLESH,ROTTEN_FLESH,ROTTEN_FLESH,ROTTEN_FLESH,IRON_INGOT=%ZOMBIECORPSE%",
				"%ZOMBIE%,%SOULGEM%=BONE,BONE,BONE,BONE,ROTTEN_FLESH,ROTTEN_FLESH,ROTTEN_FLESH,ROTTEN_FLESH,IRON_INGOT,%LIVEGEM%",
				"%ZOMBIECORPSE%=BONE,BONE,BONE,BONE,ROTTEN_FLESH,ROTTEN_FLESH,ROTTEN_FLESH,ROTTEN_FLESH,IRON_INGOT",

				"%LIVEGEM%,REDSTONE,LAVA_BUCKET,BLAZE_POWDER,ENDER_PEARL=%FIREGEM%,BUCKET",
				"%LIVEGEM%,REDSTONE,LAVA_BUCKET,BEDROCK,ENDER_PEARL=%GRAVITYGEM%,BUCKET",
				"REDSTONE,LAVA_BUCKET,STONE,STONE,DIAMOND_BLOCK,OBSIDIAN=BEDROCK,BUCKET",

				"BONE,BONE,PORK,PORK,%LIVEGEM%=%PIG%,%SOULGEM%",
				"BONE,BONE,PORK,PORK=%PIGCORPSE%",
				"%PIG%,%SOULGEM%=BONE,BONE,PORK,PORK,%LIVEGEM%",
				"%PIGCORPSE%=BONE,BONE,PORK,PORK",

				"%LIVEGEM%=%SOULGEM%",

				"%GEMMAT%,REDSTONE,LAVA_BUCKET=%EMPTYGEM%,BUCKET",

				"COAL,COAL,COAL,COAL,COAL,COAL,COAL,COAL,COAL=DIAMOND",
				"%EMPTYGEM%,TORCH,RAW_FISH=%SOULGEM%"
		};
		if (sigil.getBlock().getLocation().distance(caster.getLocation()) <= SPELL_RANGE
				&& sigil.confirmPlayer(caster)
				&& sigil.getType().equalsIgnoreCase("transmutation")) {
			loop_a:
			for (String recipe : recipes) {
				if (recipe.length() < 1)
					continue;
				Main.logger.info("checking: " + recipe);
				int score = 0;
				TransmutationIngredient[][] ingredients = parseTransmutationRecipe(sigil.getBlock().getLocation().add(0, 1, 0), recipe);
				Main.logger.info("ingredients: " + Arrays.toString(ingredients[0]));
				for (TransmutationIngredient ingredient : ingredients[0]) {
					for (Entity en : sigil.getBlock().getWorld().getNearbyEntities(sigil.getBlock().getLocation(), 7, 1.5, 7)) {
						Main.logger.info("cmp " + en.getType() + " " + ingredient.getName() + "; score = " + score);
						if (TransmutationIngredient.match(en, ingredient.getName())) {
							Main.logger.info("match!");
							if (++score == ingredients[0].length) {
								Main.logger.info(String.format("calling doTransmutation(%s, %s)",
										Arrays.toString(ingredients[0]), Arrays.toString(ingredients[1])));
								doTransmutation(sigil.getBlock().getLocation().add(0, 1, 0), ingredients[0], ingredients[1]);
								return;
							}
						}
					}
				}
			}
		}
	}

	public static TransmutationIngredient[][] parseTransmutationRecipe(Location loc, String recipe) {
		recipe = recipe.replace("%GEMMAT%", GeomancyItems.GEM_MATERIAL.name());
		String[] input = recipe.split("=")[0].split(",");
		String[] output = recipe.split("=")[1].split(",");
		ArrayList<TransmutationIngredient> ingredients0 = new ArrayList<>();
		ArrayList<TransmutationIngredient> products0 = new ArrayList<>();
		for (String s : input)
			ingredients0.add(new TransmutationIngredient(s));
		for (String s : output)
			products0.add(new TransmutationIngredient(s));
		TransmutationIngredient[] ingredients = new TransmutationIngredient[ingredients0.size()];
		TransmutationIngredient[] products = new TransmutationIngredient[products0.size()];
		for (int i = 0; i < ingredients.length; i++)
			ingredients[i] = ingredients0.get(i);
		for (int i = 0; i < products.length; i++)
			products[i] = products0.get(i);
		return new TransmutationIngredient[][] {ingredients, products};
	}

	public static void doTransmutation(Location loc, TransmutationIngredient[] ingredients, TransmutationIngredient[] products) {
		for (TransmutationIngredient ingredient : ingredients)
			ingredient.consume(loc);
		loc.getWorld().strikeLightningEffect(loc);
		for (TransmutationIngredient product : products)
			product.yield(loc);
	}

	public static void addSigil(Sigil sigil) {
		addSigil(sigil, true);
	}

	public static void addSigil(Sigil sigil, boolean config) {
		Sigil.sigils.add(sigil);
		ConfigManager.addSigil(sigil);
	}

	public static void breakSigil(Sigil sigil) {
		sigil.remove();
		ConfigManager.removeSigil(sigil);
	}

	public static boolean isValidTome(ItemStack item) {
		if (!item.getType().equals(Material.BOOK))
			return false;
//
//		if (!item.getItemMeta().getDisplayName().equals("Geomancer's Forge"))
//			return false;

		return true;
	}

	public static boolean detectTotem(Player player, Block sigil) {
		Block down1 = sigil.getRelative(BlockFace.DOWN);
		Block down2 = down1.getRelative(BlockFace.DOWN);
		if (down1.getType() == Material.LOG_2 && down1.getData() == 0x01
				&& down2.getType() == Material.LOG_2 && down2.getData() == 0x01) {
			addSigil(new Sigil(player.getUniqueId(), sigil, "undefined"));
			return true;
		}

		return false;
	}

	public static boolean detectTeleportCircle(Player player, Block sigil) {
		String transmutationCirclePattern =
				"0000000\n" +
				"0001000\n" +
				"0111110\n" +
				"1102011\n" +
				"0111110\n" +
				"0001000\n" +
				"0000000\n";
		if (validatePattern(parsePattern(transmutationCirclePattern, new Material[] {null, Material.REDSTONE_WIRE, Material.EMERALD_BLOCK})
				, sigil.getLocation())) {
			addSigil(new Sigil(player.getUniqueId(), sigil, "teleport"));
			return true;
		}
		return false;
	}

	public static boolean detectStorageCircle(Player player, Block sigil) {
		String transmutationCirclePattern =
				"1111111\n" +
				"1000001\n" +
				"1011101\n" +
				"1012101\n" +
				"1011101\n" +
				"1000001\n" +
				"1111111\n";
		if (validatePattern(parsePattern(transmutationCirclePattern, new Material[] {null, Material.REDSTONE_WIRE, Material.EMERALD_BLOCK})
				, sigil.getLocation())) {
			addSigil(new Sigil(player.getUniqueId(), sigil, "teleport"));
			return true;
		}
		return false;
	}

	public static boolean detectTransmutationCircle(Player player, Block sigil) {
		String transmutationCirclePattern =
				"0111110\n" +
				"1001001\n" +
				"1011101\n" +
				"1112111\n" +
				"1011101\n" +
				"1001001\n" +
				"0111110\n";
		if (validatePattern(parsePattern(transmutationCirclePattern, new Material[] {null, Material.REDSTONE_WIRE, Material.EMERALD_BLOCK})
				, sigil.getLocation())) {
			addSigil(new Sigil(player.getUniqueId(), sigil, "transmutation"));
			return true;
		}
		return false;
	}

	public static Material[][] parsePattern(String pattern, Material[] legend) {
		Material[][] circle = new Material[7][];
		byte[] data = {-1, -1};
		for (int line_index = 0; line_index < circle.length; line_index++) {
			String line = pattern.split("\n")[line_index];
			Material[] row = new Material[7];
			for (int block_index = 0; block_index < line.length(); block_index++) {
				String c = line.charAt(block_index) + "";
				int material_id = Integer.parseInt(c);
				row[block_index] = legend[material_id];
			}
			circle[line_index] = row;
		}

		return circle;
	}

	public static boolean validatePattern(Material[][] circle, Location center) {
		Block sigil = center.getBlock();
		Block east1 = sigil.getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 1);
		Block east2 = sigil.getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 2);
		Block east3 = sigil.getRelative(BlockFace.UP).getRelative(BlockFace.EAST, 3);
		Block east1a = east1.getRelative(BlockFace.SOUTH, 1);
		Block east1b = east1.getRelative(BlockFace.SOUTH, 2);
		Block east1c = east1.getRelative(BlockFace.SOUTH, 3);
		Block east2a = east2.getRelative(BlockFace.SOUTH, 1);
		Block east2b = east2.getRelative(BlockFace.SOUTH, 2);
		Block east2c = east2.getRelative(BlockFace.SOUTH, 3);
		Block east3a = east3.getRelative(BlockFace.SOUTH, 1);
		Block east3b = east3.getRelative(BlockFace.SOUTH, 2);
		Block east3c = east3.getRelative(BlockFace.SOUTH, 3);

		Block north1 = sigil.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 1);
		Block north2 = sigil.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 2);
		Block north3 = sigil.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH, 3);
		Block north1a = north1.getRelative(BlockFace.EAST, 1);
		Block north1b = north1.getRelative(BlockFace.EAST, 2);
		Block north1c = north1.getRelative(BlockFace.EAST, 3);
		Block north2a = north2.getRelative(BlockFace.EAST, 1);
		Block north2b = north2.getRelative(BlockFace.EAST, 2);
		Block north2c = north2.getRelative(BlockFace.EAST, 3);
		Block north3a = north3.getRelative(BlockFace.EAST, 1);
		Block north3b = north3.getRelative(BlockFace.EAST, 2);
		Block north3c = north3.getRelative(BlockFace.EAST, 3);

		Block west1 = sigil.getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 1);
		Block west2 = sigil.getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 2);
		Block west3 = sigil.getRelative(BlockFace.UP).getRelative(BlockFace.WEST, 3);
		Block west1a = west1.getRelative(BlockFace.NORTH, 1);
		Block west1b = west1.getRelative(BlockFace.NORTH, 2);
		Block west1c = west1.getRelative(BlockFace.NORTH, 3);
		Block west2a = west2.getRelative(BlockFace.NORTH, 1);
		Block west2b = west2.getRelative(BlockFace.NORTH, 2);
		Block west2c = west2.getRelative(BlockFace.NORTH, 3);
		Block west3a = west3.getRelative(BlockFace.NORTH, 1);
		Block west3b = west3.getRelative(BlockFace.NORTH, 2);
		Block west3c = west3.getRelative(BlockFace.NORTH, 3);

		Block south1 = sigil.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 1);
		Block south2 = sigil.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 2);
		Block south3 = sigil.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH, 3);
		Block south1a = south1.getRelative(BlockFace.WEST, 1);
		Block south1b = south1.getRelative(BlockFace.WEST, 2);
		Block south1c = south1.getRelative(BlockFace.WEST, 3);
		Block south2a = south2.getRelative(BlockFace.WEST, 1);
		Block south2b = south2.getRelative(BlockFace.WEST, 2);
		Block south2c = south2.getRelative(BlockFace.WEST, 3);
		Block south3a = south3.getRelative(BlockFace.WEST, 1);
		Block south3b = south3.getRelative(BlockFace.WEST, 2);
		Block south3c = south3.getRelative(BlockFace.WEST, 3);

		Material[][] structure = new Material[][] {
				new Material[] { west3c.getType(), west2c.getType(), west1c.getType(), north3.getType(), north3a.getType(), north3b
						.getType(), north3c.getType() },

				new Material[] { west3b.getType(), west2b.getType(), west1b.getType(), north2.getType(), north2a.getType(), north2b
						.getType(), north2c.getType() },

				new Material[] { west3a.getType(), west2a.getType(), west1a.getType(), north1.getType(), north1a.getType(), north1b
						.getType(), north1c.getType() },

				new Material[] { west3.getType(), west2.getType(), west1.getType(), sigil.getType(), east1.getType(), east2.getType(),
						east3.getType() },

				new Material[] { south1c.getType(), south1b.getType(), south1a.getType(), south1.getType(), east1a.getType(), east1b
						.getType(), east1c.getType() },

				new Material[] { south2c.getType(), south2b.getType(), south2a.getType(), south2.getType(), east2a.getType(), east2b
						.getType(), east2c.getType() },

				new Material[] { south3c.getType(), south3b.getType(), south3a.getType(), south3.getType(), east3a.getType(), east3b
						.getType(), east3c.getType() },
		};


		Main.logger.info("below is circle pattern");
		for (Material[] mats : circle) {
			String str = "";
			for (Material mat : mats)
				str += (mat == null) ? "ANY, " : mat.name() + ", ";
			System.out.println(str);
		}
		Main.logger.info("below is real structure");
		for (Material[] mats : structure) {
			String str = "";
			for (Material mat : mats)
				str += mat.name() + ", ";
			System.out.println(str);
		}

		if (structure.length != circle.length)
			return false;
		for (int row = 0; row < structure.length; row++) {
			Material[] structure_row = structure[row];
			Material[] circle_row = circle[row];
			if (structure_row.length != circle_row.length)
				return false;
			for (int col = 0; col < structure.length; col++) {
				System.out.println(String.format("cmp %s, %s", structure_row[col].name(), (circle_row[col] == null) ?
						"NULL" : circle_row[col].name()));
				if (circle_row[col] == null)
					continue;
				if (structure_row[col] != circle_row[col])
					return false;
			}
		}

		return true;
	}

	public static void changeSigilType(Player caster, String new_type) {
		Sigil sigil = Geomancer.getGeomancer(caster).getSelectedSigil();
		if (sigil.getBlock().getLocation().distance(caster.getLocation()) <= SPELL_RANGE
				&& sigil.confirmPlayer(caster))
			sigil.setType(new_type);
	}

	public static void infuseGems(Player caster, String type) {
		Sigil sigil = Geomancer.getGeomancer(caster).getSelectedSigil();
		if (sigil.getBlock().getLocation().distance(caster.getLocation()) <= SPELL_RANGE
				&& sigil.confirmPlayer(caster)
				&& sigil.getType().equalsIgnoreCase("forge")) {
			for (Entity entity : sigil.getBlock().getWorld().getNearbyEntities(sigil.getBlock().getLocation(), 1, 2, 1)) {
				if (entity.getType() == EntityType.DROPPED_ITEM) {
					Item item = (Item) entity;
					ItemMeta meta = item.getItemStack().getItemMeta();
					if (meta.getLore().get(0).equals("Geomancy Artifact")) {
						switch (type) {
							case "fire":
								item.setItemStack(GeomancyItems.getFireGem(item.getItemStack().getAmount()));
							case "gravity":
								item.setItemStack(GeomancyItems.getGravityGem(item.getItemStack().getAmount()));
							case "empty":
								item.setItemStack(GeomancyItems.getEmptyGem(item.getItemStack().getAmount()));
						}
						item.getWorld().playSound(item.getLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
					}
				}
			}
		}
	}

	public static void castSpell(Player caster, String message) {
		if (message.equalsIgnoreCase(SICKNESS_SPELL)) {
			changeSigilType(caster, "poison");
		} else if (message.equalsIgnoreCase(DECAY_SPELL)) {
			changeSigilType(caster, "decay");
		} else if (message.equalsIgnoreCase(DEACTIVATION_SPELL)) {
			changeSigilType(caster, "undefined");
		} else if (message.equalsIgnoreCase(GRAVITY_SPELL)) {
			changeSigilType(caster, "gravity");
		} else if (message.equalsIgnoreCase(RISING_SPELL)) {
			changeSigilType(caster, "antigravity");
		} else if (message.equalsIgnoreCase(FORGE_FIRE_SPELL)) {
			infuseGems(caster, "fire");
		} else if (message.equalsIgnoreCase(FORGE_GRAVITY_SPELL)) {
			infuseGems(caster, "gravity");
		} else if (message.equalsIgnoreCase(FORGE_EMPTY_SPELL)) {
			infuseGems(caster, "empty");
		} else if (message.equalsIgnoreCase(TRANSMUTATION_SPELL)) {
//			transmuteItems(caster);
			validateTransmutation(caster);
		} else if (message.equalsIgnoreCase(TELEPORTATION_SPELL)) {
			
		} else if (message.equalsIgnoreCase(IMMUNITY_SPELL)) {
			for (int i = 0; i < GeomancyHandler.immuneCasters.size(); i++) {
				if (GeomancyHandler.immuneCasters.get(i).getUniqueId().equals(caster.getUniqueId())) {
					GeomancyHandler.immuneCasters.remove(i);
					return;
				}
			}
			GeomancyHandler.immuneCasters.add(caster);
		}
	}
}
