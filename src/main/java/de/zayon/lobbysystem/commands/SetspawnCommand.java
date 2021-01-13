package de.zayon.lobbysystem.commands;

import de.zayon.lobbysystem.LobbySystem;
import de.zayon.lobbysystem.factory.data.StringData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetspawnCommand implements CommandExecutor {
    private final LobbySystem lobbySystem;

    public SetspawnCommand(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Bukkit.getConsoleSender().sendMessage("1");
        if (!(commandSender instanceof Player))
            return true;
        Bukkit.getConsoleSender().sendMessage("2");
        Player player = (Player) commandSender;
        if (!player.hasPermission("zayon.admin")) {
            player.sendMessage(StringData.combinate());
            return true;
        }
        Bukkit.getConsoleSender().sendMessage("3");
        if (args.length == 1) {
            Location location = player.getLocation();
            String name = args[0];
            Bukkit.getConsoleSender().sendMessage(name);
            this.lobbySystem.getLocationConfig().getOrSetDefault("config.location." + name, location);
            player.sendMessage(StringData.getPrefix() + "§7 " + name + " gesetzt§8.");
        }
        Bukkit.getConsoleSender().sendMessage("4");

        return false;
    }
}
