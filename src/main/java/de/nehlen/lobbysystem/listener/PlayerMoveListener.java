package de.nehlen.lobbysystem.listener;

import de.nehlen.lobbysystem.LobbySystem;
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

    private final LobbySystem lobbySystem;

    public PlayerMoveListener(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if(player.hasPermission("lobby.jump") || player.hasPermission("lobby.*")) {
            if(!player.isOnGround())
                return;
            player.setAllowFlight(true);
        }

        if(player.getLocation().getBlock().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE) {
            if(player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.EMERALD_BLOCK) {
                Vector vector = player.getLocation().getDirection().multiply(2D).setY(1D);
                player.setVelocity(vector);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 3);
            } else if(player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.DIAMOND_BLOCK) {
                Vector vector = player.getLocation().getDirection().multiply(4D).setY(1D);
                player.setVelocity(vector);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 3);
            }
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

            Vector vector = player.getLocation().getDirection().multiply(1.5D).setY(1.0F);

            event.setCancelled(true);
            player.setAllowFlight(false);
            player.setVelocity(vector);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 3);
            player.spawnParticle(Particle.END_ROD, player.getLocation(), 30);
            player.setFlying(false);

        }
    }
}
