package de.spookly.lobby.listener;

import de.nehlen.spookly.Spookly;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import de.spookly.lobby.LobbyPlugin;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

	private final LobbyPlugin lobby;

	public PlayerQuitListener(LobbyPlugin lobby) {
		this.lobby = lobby;
	}

	@EventHandler
	public void handleLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("spookly.lobby.leave-message")) {
			event.quitMessage(Spookly.getPlayer(player).nameTag().append(Component.text(" hat die Lobby verlassen.").color(NamedTextColor.GRAY)));
		} else {
			event.quitMessage(Component.empty());
		}
		lobby.getScoreboardManager().removeUserScoreboard(player);
	}
}
