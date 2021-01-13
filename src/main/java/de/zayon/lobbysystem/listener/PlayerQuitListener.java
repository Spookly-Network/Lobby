package de.zayon.lobbysystem.listener;

import de.zayon.lobbysystem.LobbySystem;
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
        event.setQuitMessage(null);
    }
}
