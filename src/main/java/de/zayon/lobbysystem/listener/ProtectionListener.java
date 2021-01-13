package de.zayon.lobbysystem.listener;

import de.zayon.lobbysystem.LobbySystem;
import de.zayon.lobbysystem.factory.util.Items;
import de.zayon.lobbysystem.manager.FireworksManager;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.PacketPlayOutBlockBreakAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public class ProtectionListener implements Listener {

    private final LobbySystem lobbySystem;

    public ProtectionListener(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
    }

    @EventHandler
    public void handlePlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType().equals(Material.SPONGE)) {
            Bukkit.getScheduler().runTaskLater((Plugin) this.lobbySystem, () -> {
                PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(new Random().nextInt(2000), new BlockPosition(block.getX(), block.getY(), block.getZ()), 3);
                Bukkit.getScheduler().runTaskLater((Plugin) this.lobbySystem, () -> {
                    new PacketPlayOutBlockBreakAnimation(new Random().nextInt(2000), new BlockPosition(block.getX(), block.getY(), block.getZ()), 3);
                    Bukkit.getScheduler().runTaskLater((Plugin) this.lobbySystem, () -> {
                        new PacketPlayOutBlockBreakAnimation(new Random().nextInt(2000), new BlockPosition(block.getX(), block.getY(), block.getZ()), 9);
                        Bukkit.getScheduler().runTaskLater((Plugin) this.lobbySystem, () -> {

                            FireworksManager rf = new FireworksManager();
                            rf.playRandomFirework(player);
                            rf.playRandomFirework(player);
                            rf.playRandomFirework(player);
                            rf.playRandomFirework(player);

                            Item item = block.getLocation().getWorld().dropItem(player.getLocation(), Items.createItem(Material.DIAMOND, "", 5));
                            item.setPickupDelay(999999);
                            item.setTicksLived(5700);

                            Item item2 = block.getLocation().getWorld().dropItem(player.getLocation(), Items.createItem(Material.EMERALD, "", 5));
                            item2.setPickupDelay(999999);
                            item2.setTicksLived(5700);

                            Item item3 = block.getLocation().getWorld().dropItem(player.getLocation(), Items.createItem(Material.GOLD_NUGGET, "", 5));
                            item3.setPickupDelay(999999);
                            item3.setTicksLived(5700);

                            Item item4 = block.getLocation().getWorld().dropItem(player.getLocation(), Items.createItem(Material.IRON_INGOT, "", 5));
                            item4.setPickupDelay(999999);
                            item4.setTicksLived(5700);

                        }, 10L);
                    }, 10L);
                }, 20L);
            }, 10L);

        } else if (!this.lobbySystem.getBuildCommand().getEditUserMap().contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!this.lobbySystem.getBuildCommand().getEditUserMap().contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageE(EntityDamageEvent event) {
        event.setCancelled(true);
    }
}
