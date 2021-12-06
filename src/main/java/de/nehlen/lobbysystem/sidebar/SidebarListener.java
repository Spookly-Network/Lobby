package de.nehlen.lobbysystem.sidebar;

import de.nehlen.lobbysystem.LobbySystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class SidebarListener implements Listener {
    private final LobbySystem lobbySystem;

    public SidebarListener(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
    }

    @EventHandler
    public void handleQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        SidebarCache.removeCachedSidebar(player);
        this.lobbySystem.getServer().getPluginManager().registerEvents(this, this.lobbySystem);
    }
}
