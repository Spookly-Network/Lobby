package de.nehlen.lobbysystem.listener;

import de.nehlen.gameapi.Gameapi;
import de.nehlen.gameapi.ItemsAPI.Items;
import de.nehlen.lobbysystem.LobbySystem;
import de.nehlen.lobbysystem.inventory.BackpackInventroy;
import de.nehlen.lobbysystem.inventory.CompassInventory;
import de.nehlen.lobbysystem.inventory.SwitcherInventory;
import de.nehlen.lobbysystem.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InteractListener implements Listener {

    private LobbySystem lobbySystem;
    public static HashMap<Player, Player> lastClickedCache = new HashMap<Player, Player>();

    public InteractListener(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
    }

    @EventHandler
    public void handleInteractWithEntity(PlayerInteractEntityEvent event) {

        Player player = event.getPlayer();
        Player target = (Player) event.getRightClicked();

        Inventory inventory = Bukkit.createInventory(player, 27, "§cSpieler Informationen");
        inventory.setItem(0, Items.createSkull("§c" + target.getName() + " Profil", target.getUniqueId()));
        inventory.setItem(1, new ItemManager(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "§c").create());
        inventory.setItem(9, new ItemManager(Material.NETHER_STAR, "§cStats").create());
        inventory.setItem(10, new ItemManager(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "§c").create());
        inventory.setItem(18, new ItemManager(Material.EMERALD, "§c???").create());
        inventory.setItem(19, new ItemManager(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "§c").create());
        inventory.setItem(12, Items.createSkull(target.getDisplayName(), target.getUniqueId()));
        inventory.setItem(14, new ItemManager(Material.PAPER, "§Status:").addLoreLine("STATUS").create());
        inventory.setItem(16, new ItemManager(Material.GOLD_NUGGET, "§7Punkte:").addLoreLine("§7➥§c " + Gameapi.getGameapi().getPointsAPI().getPoints(target)).create());

        player.openInventory(inventory);
        lastClickedCache.put(player, target);
    }

    @EventHandler
    public void handleInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if ((event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !this.lobbySystem.getBuildCommand().getEditUserMap().contains(player)))
            if (player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS)) {
                player.openInventory((Inventory)(new CompassInventory(player)).build());
            } else if (player.getInventory().getItemInMainHand().getType().equals(Material.CHEST)) {
                player.openInventory((Inventory)(new BackpackInventroy(player).build()));
            } else if (player.getInventory().getItemInMainHand().getType().equals(Material.GLOWSTONE_DUST)) {
                player.openInventory((Inventory)(new SwitcherInventory(player).buildFirstPage()));
            } else if (player.getInventory().getItemInMainHand().getType().equals(Material.PLAYER_HEAD)) {
                player.performCommand("friendsgui");
            }
    }
}
