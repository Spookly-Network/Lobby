package de.nehlen.lobbysystem.inventory;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.ext.bridge.player.CloudPlayer;
import de.dytanic.cloudnet.ext.cloudperms.CloudPermissionsHelper;
import de.dytanic.cloudnet.ext.cloudperms.CloudPermissionsPermissionManagement;
import de.dytanic.cloudnet.wrapper.Wrapper;
import de.exceptionflug.mccommons.config.shared.ConfigFactory;
import de.exceptionflug.mccommons.config.shared.ConfigWrapper;
import de.exceptionflug.mccommons.config.spigot.SpigotConfig;
import de.exceptionflug.mccommons.inventories.api.Arguments;
import de.exceptionflug.mccommons.inventories.api.CallResult;
import de.exceptionflug.mccommons.inventories.spigot.design.SpigotOnePageInventoryWrapper;
import de.nehlen.gameapi.Gameapi;
import de.nehlen.gameapi.ItemsAPI.Items;
import de.nehlen.gameapi.PointsAPI.PointsAPI;
import de.nehlen.lobbysystem.LobbySystem;
import de.nehlen.lobbysystem.factory.data.StringData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class BuyInventory extends SpigotOnePageInventoryWrapper {
    private static final ConfigWrapper CONFIG_WRAPPER = ConfigFactory.create(LobbySystem.getLobbySystem().getDescription().getName() + "/inventories", BuyInventory.class, SpigotConfig.class);

    public BuyInventory(Player player, String displayName, ItemStack icon, Integer price, String type, String value, Integer duration) {
        super(player, CONFIG_WRAPPER);
        setInventoryType(de.exceptionflug.mccommons.inventories.api.InventoryType.BREWING_STAND);

        ArrayList<Object> arguments0 = new ArrayList<>(Arrays.asList(displayName, price, icon, type, value, duration));
        set(0, Items.createItem(Material.LIME_DYE, "§aKaufen", 1), "buyKit", new Arguments(arguments0));
        set(1, Items.createItem(icon.getType(), StringData.getHighlightColor()+displayName.replace("oe", "ö").replace("ue", "ü").replace("ae", "ä")+" §7» "+price+" Punkte", 1), "buy");
        set(2, Items.createItem(Material.RED_DYE, "§cAbbrechen", 1), "deny");
        if(Gameapi.getGameapi().getPointsAPI().getPoints(player) < price) {
            set(4, Items.createItem(Material.RED_DYE, "§cDu hast leider nicht genug Punkte.", 1), "nothing");
        } else {
            set(4, Items.createItem(Material.LIME_DYE, "§aDu hast genug Punkte.", 1), "nothing");
        }
    }

    public void updateInventory() {
        super.updateInventory();
    }

    public void registerActionHandlers() {
        registerActionHandler("deny", click -> {
            ((Player)getPlayer()).openInventory((Inventory) new RangTraderInventory(((Player)getPlayer())));
            return CallResult.DENY_GRABBING;
        });
        registerActionHandler("nothing", click -> {
            return CallResult.DENY_GRABBING;
        });
        registerActionHandler("buy", click -> {
            String argument = (String) click.getArguments().get(0);
            Integer argument2 = (Integer) click.getArguments().get(1);
            buyKitHandler((Player) getPlayer(), click.getArguments());
            ((Player)getPlayer()).openInventory((Inventory) new RangTraderInventory(((Player)getPlayer())).build());
            return CallResult.DENY_GRABBING;
        });
    }

    public static void buyKitHandler(Player player, Arguments arguments) {
        PointsAPI pointsAPI = Gameapi.getGameapi().getPointsAPI();
        String name = arguments.get(0);
        Integer price = arguments.get(1);
        String type = arguments.get(2);
        String value = arguments.get(3);
        Integer duration = arguments.get(4);

        if(pointsAPI.getPoints(player) >= price) {
            pointsAPI.updatePoints(player, PointsAPI.UpdateType.REMOVE, price);
            IPermissionUser iPermissionUser = CloudNetDriver.getInstance().getPermissionManagement().getUser(player.getUniqueId());


            if(type.equalsIgnoreCase("rang")) {
                if(!LobbySystem.getLobbySystem().getGroupManager().getGroupName(player).equalsIgnoreCase(value)) {
                    iPermissionUser.addGroup(value, duration);
                } else {
                    player.sendMessage(StringData.getPrefix() + "§7Du hast diesen Rang bereits");
                }
            } else if (type.equalsIgnoreCase("permission")) {
                iPermissionUser.addPermission(value);
            }

            CloudNetDriver.getInstance().getPermissionManagement().updateUser(iPermissionUser);
            player.sendMessage(StringData.getPrefix() + "§7Du hast erfolgreich "+ StringData.getHighlightColor() + name + " §7gekauft.");
        }else{
            player.sendMessage(StringData.getPrefix() + "§7Du hast leider nicht genügend Coins.");
            player.closeInventory();
        }
    }
}
