package de.spookly.lobby.listener;

import de.spookly.lobby.LobbyPlugin;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class PlayerMoveListener implements Listener {

	private final LobbyPlugin lobby;

	public PlayerMoveListener(LobbyPlugin lobby) {
		this.lobby = lobby;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();

//        lobby.getVolatileCodeHandler().getActiveNpcs()
//                .stream().filter(npc -> npc.getLocation().distance(player.getLocation()) < 20)
//                .forEach(npc -> {
//                    var location = npc.getLocation();
//                    location.setDirection(player.getLocation().subtract(npc.getLocation()).toVector());
//
//                    npc.setHeadRotationForPlayer(player, location.getPitch(), location.getYaw());
//                });

		if (player.hasPermission("lobby.jump") || player.hasPermission("lobby.*")) {
			if (!player.isOnGround())
				return;
			player.setAllowFlight(true);
		}

		if (player.getLocation().getBlock().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE) {
			if (player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.PISTON) {
				Vector vector = player.getLocation().getDirection().multiply(3D).setY(1.25D);
				player.setVelocity(vector);
				player.playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 1, 3);
			}
			return;
		} else if (player.getLocation().getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
			if (player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.PISTON) {
				Vector vector = player.getLocation().getDirection().multiply(1.25D).setY(0.75D);
				player.setVelocity(vector);
				player.playSound(player.getLocation(), Sound.BLOCK_PISTON_CONTRACT, 1, 3);
			}
			return;
		}

	}

	@EventHandler
	public void onJump(PlayerToggleFlightEvent event) {

		Player player = event.getPlayer();

		if (player.hasPermission("rang.vip") || player.hasPermission("lobby.*")) {
			if (!event.isFlying())
				return;
			if (player.getGameMode() == GameMode.CREATIVE)
				return;
			if (lobby.getFlyCommand().getFlyUserMap().contains(player))
				return;

			Vector vector = player.getLocation().getDirection().multiply(1.5D).setY(1.0F);

			event.setCancelled(true);
			player.setAllowFlight(false);
			player.setVelocity(vector);
			player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 3);
			player.spawnParticle(Particle.FIREWORK, player.getLocation(), 10, .5, .5, .5, 0.1);
			player.setFlying(false);
		}
	}

	private static void repeat(Particle effect, int data, Player player, int times) {
		int i = 1;
		while (i <= times) {

			player.getWorld().spawnParticle(effect, player.getLocation(), data);
			i++;
		}
	}
}
