package ch.echo35.spigot.echoRPG;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Arrays;

/**
 * Created by echo35 on 12/17/2016.
 */
public class Tests {
	public static void main(String[] args) {
		String pattern =
				"1121211\n" +
				"1122211\n" +
				"2212122\n" +
				"1222221\n" +
				"2212122\n" +
				"1122211\n" +
				"0021211\n";
		Material[][] circle = parsePattern(pattern);

		for (Material[] materials : circle)
			System.out.println(Arrays.toString(materials));
	}

	public static Material[][] parsePattern(String pattern) {
		Material[] legend = {null, Material.AIR, Material.REDSTONE};
		Material[][] circle = new Material[7][];
		byte[] data = {-1, -1};
		for (int line_index = 0; line_index < circle.length; line_index++) {
			String line = pattern.split("\n")[line_index];
			Material[] row = new Material[7];
			for (int block_index = 0; block_index < line.length(); block_index++) {
				String c = line.charAt(block_index) + "";
				int material_id = Integer.parseInt(c);
				System.out.println(material_id);
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
						.getType(), east2b.getType() },

				new Material[] { south3c.getType(), south3b.getType(), south3a.getType(), south3.getType(), east3a.getType(), east3b
						.getType(), east3c.getType() },
		};

		if (structure.length != circle.length)
			return false;
		for (int row = 0; row < structure.length; row++) {
			Material[] structure_row = structure[row];
			Material[] circle_row = circle[row];
			if (structure_row.length != circle_row.length)
				return false;
			for (int col = 0; col < structure.length; col++) {
				if ((circle_row[col] != null) && (structure_row[col] != circle_row[col]))
					return false;
			}
		}

		return true;
	}
}
