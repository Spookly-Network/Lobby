package de.zayon.lobbysystem.inventory;

import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.dytanic.cloudnet.wrapper.Wrapper;
import de.exceptionflug.mccommons.config.shared.ConfigFactory;
import de.exceptionflug.mccommons.config.shared.ConfigWrapper;
import de.exceptionflug.mccommons.config.spigot.SpigotConfig;
import de.exceptionflug.mccommons.inventories.api.Arguments;
import de.exceptionflug.mccommons.inventories.api.CallResult;
import de.exceptionflug.mccommons.inventories.api.design.MultiPageInventoryWrapper;
import de.zayon.lobbysystem.LobbySystem;
import de.zayon.lobbysystem.factory.util.Items;
import de.zayon.lobbysystem.manager.ServerManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SwitcherInventory extends MultiPageInventoryWrapper {

    private static final ConfigWrapper CONFIG_WRAPPER = ConfigFactory.create(LobbySystem.getLobbySystem().getDescription().getName() + "/inventories", SwitcherInventory.class, SpigotConfig.class);

    public SwitcherInventory(Player player) {
        super(player, CONFIG_WRAPPER);

        int i = 0;
        List<String> services = Wrapper.getInstance().getCloudServiceByGroup("Lobby").stream().map(it -> it.getServiceId().getName()).sorted().collect(Collectors.toList());

        for (String service : services) {
            ArrayList<Object> arguments = new ArrayList<Object>();
            arguments.add(service);

            if (BridgePlayerManager.getInstance().getOnlinePlayer(player.getUniqueId()).getConnectedService().getServerName().equals(service)) {
                add(Items.createItem(Material.LIME_DYE, "§6" + service, 1), "noAction", new Arguments(arguments));
            } else {
                add(Items.createItem(Material.GRAY_DYE, "§6" + service, 1), "switchToLobby", new Arguments(arguments));
            }
            i++;
        }
    }

    public void updateInventory() {
        super.updateInventory();
    }

    public void registerActionHandlers() {
        registerActionHandler("switchToLobby", click -> {
            String argument = (String) click.getArguments().get(0);
            switchServer((Player) getPlayer(), argument);
            ((Player) getPlayer()).closeInventory();
            return CallResult.DENY_GRABBING;
        });
    }

    public static void switchServer(Player player, String serviceName) {
        ServerManager serverManager = LobbySystem.getLobbySystem().getServerManager();
        serverManager.send(player, serviceName);
    }
}
