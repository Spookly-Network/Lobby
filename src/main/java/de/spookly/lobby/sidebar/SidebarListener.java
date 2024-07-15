package de.spookly.lobby.sidebar;

import de.spookly.lobby.LobbyPlugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class SidebarListener implements Listener {
	private final LobbyPlugin lobbySystem;

	public SidebarListener(LobbyPlugin lobbySystem) {
		this.lobbySystem = lobbySystem;
	}

	@EventHandler
	public void handleQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		SidebarCache.removeCachedSidebar(player);
		lobbySystem.registerEvent(this);
	}
}
