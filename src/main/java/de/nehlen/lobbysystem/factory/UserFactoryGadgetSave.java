package de.nehlen.lobbysystem.factory;

import de.nehlen.lobbysystem.LobbySystem;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.concurrent.CompletableFuture;

public class UserFactoryGadgetSave {
    private final LobbySystem lobbySystem;

    public UserFactoryGadgetSave(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
    }

    public void createTable() {
        StringBuilder table = new StringBuilder();
        table.append("id INT(11) NOT NULL AUTO_INCREMENT, ");
        table.append("`uuid` VARCHAR(64) NOT NULL UNIQUE, ");
        table.append("`head` TEXT NOT NULL, ");
        table.append("`boot` TEXT NOT NULL, ");
        table.append("`gadget` TEXT NOT NULL, ");
        table.append("PRIMARY KEY (`id`)");
        this.lobbySystem.getDatabaseLib().executeUpdateAsync("CREATE TABLE IF NOT EXISTS lobby_gadgets (" + table.toString() + ")", resultSet -> {});
    }

    public CompletableFuture<Boolean> userExists(Player player) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        this.lobbySystem.getDatabaseLib().executeQueryAsync("SELECT id FROM lobby_gadgets WHERE uuid = ?", resultSet -> {
            try {
                completableFuture.complete(Boolean.valueOf(resultSet.next()));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }, player.getUniqueId().toString());
        return completableFuture;
    }

    public void createUser(Player player) {
        userExists(player).whenCompleteAsync((exist, throwable) -> {
            if (throwable == null && !exist.booleanValue())
                this.lobbySystem.getDatabaseLib().executeUpdateAsync("INSERT INTO lobby_gadgets (uuid, head, boot, gadget) VALUES (?, ?, ?, ?, ?)", resultSet -> {}, player.getUniqueId().toString(), 0, 0, 0, 0);
        });
    }

//    public int getKills(Player player) {
//        return (Integer) this.lobbySystem.getDatabaseLib().get("SELECT kills FROM lobby_gadgets WHERE uuid = ?", player.getUniqueId().toString(), "kills");
//    }

//    public void updateKills(Player player, UpdateType updateType, int kills) {
//        int newKills = 0;
//        if (updateType == UpdateType.ADD) {
//            newKills = getKills(player) + kills;
//        } else if (updateType == UpdateType.REMOVE) {
//            newKills = getKills(player) - kills;
//        }
//        this.lobbySystem.getDatabaseLib().executeUpdateAsync("UPDATE lobby_gadgets SET kills = ? WHERE uuid = ?", resultSet -> {},newKills, player.getUniqueId().toString() );
//    }

    public enum UpdateType {
        ADD, REMOVE;
    }
}
