package de.spookly.lobby.helper;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;

public class RNDHelper {

	private static final Random PRNG = new Random();

	public static Material getRndMaterial() {
		Material[] materials = Material.values();
		return materials[PRNG.nextInt(materials.length)];
	}

	public static EntityType getRndEntity() {
		EntityType[] entityTypes = EntityType.values();
		return entityTypes[PRNG.nextInt(entityTypes.length)];
	}

	public static Biome getRndBiome() {
		Biome[] entityTypes = Biome.values();
		return entityTypes[PRNG.nextInt(entityTypes.length)];
	}

	public static Integer getRndInt(Integer min, Integer max) {
		return PRNG.nextInt(max - min) + min;
	}

	public static Object getRndFromList(List list) {
		return list.get(PRNG.nextInt(list.size() - 1));
	}

	public static Location getRandomLocationInRadius(Location location, Integer radius, World world) {
		double diameter = radius * 2;
		Random rand = new Random();
		double x = location.getX() + (rand.nextDouble() * diameter - radius);
		double z = location.getZ() + (rand.nextDouble() * diameter - radius);
		int y = world.getHighestBlockYAt((int)Math.round(x), (int)Math.round(z));

		return new Location(world, x, y, z).add(0.5, 0, 0.5);
	}
}
