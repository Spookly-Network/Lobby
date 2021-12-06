package de.nehlen.lobbysystem.listener;

import de.nehlen.lobbysystem.LobbySystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemProtectListener implements Listener {

    private LobbySystem lobbySystem;

    public ItemProtectListener(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPick(EntityPickupItemEvent e) {
        e.setCancelled(true);
    }

}
