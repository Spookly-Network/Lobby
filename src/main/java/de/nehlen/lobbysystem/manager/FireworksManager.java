package de.nehlen.lobbysystem.manager;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class FireworksManager {
	
	public Color getRandomColor() {
		Color c = null;
		Random rm = new Random();
		int next = rm.nextInt(6);
		
		if(next == 0){ 
			c = Color.AQUA;
		}
		if(next == 1){ 
			c = Color.RED;
		}
		if(next == 2){ 
			c = Color.LIME;
		}
		if(next == 3){ 
			c = Color.ORANGE;
		}	
		if(next == 4){ 
			c = Color.BLUE;
		}
		if(next == 5){ 
			c = Color.YELLOW;
		}
			return c;	
		
	}
	
	public Type getRandomType() {
		Type t = null;
		Random rm = new Random();
		int next = rm.nextInt(6);
		
		if(next == 0) {
			t = Type.BALL;
		}
		if(next == 2) {
			t = Type.BALL_LARGE;
		}
		if(next == 3) {
			t = Type.BURST;
		}
		if(next == 4) {
			t = Type.CREEPER;
		}
		if(next == 5) {
			t = Type.STAR;
		}
		return t;
	}
	
	public Boolean getRandomBoolean() {
		Random rm = new Random();
		return rm.nextBoolean();
		
	}
	
	 public void playRandomFirework(Player lv) {
		 World w = lv.getWorld();
		 Location loc = lv.getLocation();
		 Firework f = w.spawn(loc, Firework.class);
		 FireworkMeta fm = f.getFireworkMeta();
		 fm.addEffect(FireworkEffect.builder().with(getRandomType()).withColor(getRandomColor()).withFade(getRandomColor()).flicker(getRandomBoolean()).build());
		 fm.setPower(2);
		 f.setFireworkMeta(fm);
	 }

}
