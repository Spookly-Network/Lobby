package de.nehlen.lobbysystem.commands;

import de.nehlen.lobbysystem.LobbySystem;
import de.nehlen.lobbysystem.factory.data.StringData;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildCommand implements CommandExecutor {
    private final LobbySystem lobbySystem;

    @Getter private final ArrayList<Player> editUserMap = new ArrayList<>();

    @Getter private final HashMap<Player, ItemStack[]> editItemMap = (HashMap)new HashMap<>();

    public BuildCommand(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player))
            return true;
        Player player = (Player)commandSender;
        if (!player.hasPermission("lobby.build")) {
            player.sendMessage(StringData.combinate());
            return true;
        }
        if (args.length == 0) {
            if (this.editUserMap.contains(player)) {
                this.editUserMap.remove(player);
                player.setGameMode(GameMode.SURVIVAL);
                ItemStack[] saveItems = this.editItemMap.get(player);
                player.getInventory().setContents(saveItems);
                player.sendMessage(StringData.getPrefix() + "§7Dein Editmodus wurde §cdeaktiviert§7.");
            } else {
                this.editUserMap.add(player);
                player.setGameMode(GameMode.CREATIVE);
                this.editItemMap.put(player, player.getInventory().getContents());
                player.getInventory().clear();
                player.sendMessage(StringData.getPrefix() + "§7Dein Editmodus wurde §aaktiviert§7.");
            }
        } else {
            player.sendMessage(StringData.getPrefix() + "§7Synatx §8- §7/edit");
        }
        return false;
    }
}
