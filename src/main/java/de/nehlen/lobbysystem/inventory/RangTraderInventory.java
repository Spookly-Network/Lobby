package de.nehlen.lobbysystem.inventory;

import de.exceptionflug.mccommons.config.shared.ConfigFactory;
import de.exceptionflug.mccommons.config.shared.ConfigWrapper;
import de.exceptionflug.mccommons.config.spigot.SpigotConfig;
import de.exceptionflug.mccommons.inventories.spigot.design.SpigotOnePageInventoryWrapper;
import de.nehlen.lobbysystem.LobbySystem;
import org.bukkit.entity.Player;

public class RangTraderInventory extends SpigotOnePageInventoryWrapper {

    private static final ConfigWrapper CONFIG_WRAPPER = ConfigFactory.create(LobbySystem.getLobbySystem().getDescription().getName() + "/inventories", RangTraderInventory.class, SpigotConfig.class);

    public RangTraderInventory(Player player) {
        super(player, CONFIG_WRAPPER);
    }

    public void updateInventory() {
        super.updateInventory();
    }
}
