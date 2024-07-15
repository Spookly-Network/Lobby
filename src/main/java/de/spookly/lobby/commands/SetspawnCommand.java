package de.spookly.lobby.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import de.spookly.lobby.LobbyPlugin;
import de.spookly.lobby.data.ComponentData;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetspawnCommand implements CommandExecutor {
	private final LobbyPlugin lobbySystem;

	public SetspawnCommand(LobbyPlugin lobbySystem) {
		this.lobbySystem = lobbySystem;
	}

	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (!(commandSender instanceof Player))
			return true;
		Player player = (Player) commandSender;
		if (!player.hasPermission("spookly.command.setup")) {
			player.sendMessage(ComponentData.getPrefix().append(Component.translatable(ComponentData.getNoPermKey()).color(NamedTextColor.GRAY)));
			return true;
		}
		if (args.length == 1) {
			Location location = player.getLocation();
			String name = args[0];
			Bukkit.getConsoleSender().sendMessage(name);
			this.lobbySystem.getLocationConfig().getOrSetDefault("config.location." + name, location);
			player.sendMessage(ComponentData.getPrefix().append(Component.text(name + " gesetzt.").color(NamedTextColor.GRAY)));
		}

		return false;
	}
}
