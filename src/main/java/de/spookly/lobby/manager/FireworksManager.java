package de.spookly.lobby.manager;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworksManager {

	private static final Random PRNG = new Random();

	public static Color getRandomColor() {
		Color c = null;
		int next = PRNG.nextInt(6);
		c = switch (next) {
			case 0 -> Color.AQUA;
			case 1 -> Color.RED;
			case 2 -> Color.LIME;
			case 3 -> Color.ORANGE;
			case 4 -> Color.BLUE;
			case 5 -> Color.YELLOW;
			default -> c;
		};
		return c;
	}

	public static Type getRandomType() {
		Type[] type = Type.values();
		return type[PRNG.nextInt(type.length)];
	}

	public static Boolean getRandomBoolean() {
		return PRNG.nextBoolean();
	}

	public static void playRandomFirework(Player lv) {
		World w = lv.getWorld();
		Location loc = lv.getLocation();
		Firework f = w.spawn(loc, Firework.class);
		FireworkMeta fm = f.getFireworkMeta();
		fm.addEffect(FireworkEffect.builder()
				.with(getRandomType())
				.withColor(getRandomColor())
				.withFade(getRandomColor())
				.flicker(getRandomBoolean())
				.build());
		fm.setPower(2);
		f.setFireworkMeta(fm);
	}

}
