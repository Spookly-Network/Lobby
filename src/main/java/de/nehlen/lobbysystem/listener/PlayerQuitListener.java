package de.nehlen.lobbysystem.listener;

import de.nehlen.lobbysystem.LobbySystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final LobbySystem lobbySystem;

    public PlayerQuitListener(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
    }

    @EventHandler
    public void handleLeave(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }
}
