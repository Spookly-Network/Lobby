package de.nehlen.lobbysystem.inventory;

import de.exceptionflug.mccommons.config.shared.ConfigFactory;
import de.exceptionflug.mccommons.config.shared.ConfigWrapper;
import de.exceptionflug.mccommons.config.spigot.SpigotConfig;
import de.exceptionflug.mccommons.inventories.api.CallResult;
import de.exceptionflug.mccommons.inventories.spigot.design.SpigotOnePageInventoryWrapper;
import de.nehlen.gameapi.Gameapi;
import de.nehlen.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class CompassInventory extends SpigotOnePageInventoryWrapper {

    private static final ConfigWrapper CONFIG_WRAPPER = ConfigFactory.create(LobbySystem.getLobbySystem().getDescription().getName() + "/inventories", CompassInventory.class, SpigotConfig.class);

    public CompassInventory(Player player) {
        super(player, CONFIG_WRAPPER);
    }

    public void updateInventory() {
        super.updateInventory();
    }

    public void registerActionHandlers() {
        registerActionHandler("teleportToPoint", click -> {
            String argument = (String) click.getArguments().get(0);
            selectPointHandler((Player) getPlayer(), argument);
            ((Player) getPlayer()).closeInventory();
            return CallResult.DENY_GRABBING;
        });
    }

    public static void selectPointHandler(Player player, String kitName) {
        Location location = null;
        location = LobbySystem.getLobbySystem().getLocationConfig().getLocation("config.location." + kitName);
        Location finalLocation = location;
        player.teleport(finalLocation);
        Bukkit.getScheduler().runTaskLater(Gameapi.getGameapi(), () -> {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 3.0F);
        }, 5);
    }
}
