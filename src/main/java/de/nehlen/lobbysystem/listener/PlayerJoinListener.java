package de.nehlen.lobbysystem.listener;

import de.nehlen.gameapi.Gameapi;
import de.nehlen.gameapi.ItemsAPI.Items;
import de.nehlen.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerJoinListener implements Listener {

    private final LobbySystem lobbySystem;

    public PlayerJoinListener(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
    }

    @EventHandler
    public void handleJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("rang.Elite")) {
            event.setJoinMessage("§7➤ " + player.getDisplayName() + " §7hat die Lobby betreten");
        } else {
            event.setJoinMessage("");
        }

        player.setGameMode(GameMode.ADVENTURE);
        player.resetTitle();
        player.setLevel(Gameapi.getGameapi().getPointsAPI().getPoints(player));
        player.setFoodLevel(20);
        player.setHealth(6L);
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(6L);
        player.getInventory().clear();
        player.getInventory().setItem(0, Items.createItem(Material.COMPASS, "§cSchnellreise §7« Rechtsklick »", 1));
        player.getInventory().setItem(1, Items.createItem(Material.GLOWSTONE_DUST, "§cLobbys §7« Rechtsklick »", 1));
        player.getInventory().setItem(7, Items.createItem(Material.CHEST, "§cRucksack §7« Rechtsklick »", 1));
        player.getInventory().setItem(8, Items.createSkull("§cFreunde §7« Rechtsklick »", player.getUniqueId()));

        Bukkit.getOnlinePlayers().forEach(this.lobbySystem.getScoreboardManager()::setUserScoreboard);
    }

    @EventHandler
    public void handleSpawn(PlayerSpawnLocationEvent event) {
        Player player = event.getPlayer();
        Location location = this.lobbySystem.getLocationConfig().getLocation("config.location.Spawn");
        if (!player.hasPlayedBefore()) {
            event.setSpawnLocation(location);
        }
    }
}
