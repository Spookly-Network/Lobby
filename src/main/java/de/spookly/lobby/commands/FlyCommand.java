package de.spookly.lobby.commands;

import java.util.ArrayList;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import de.spookly.lobby.data.ComponentData;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

	@Getter
	private final ArrayList<Player> flyUserMap = new ArrayList<>();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;

		if (!player.hasPermission("lobby.build")) {
			player.sendMessage(ComponentData.getPrefix().append(Component.translatable(ComponentData.getNoPermKey()).color(NamedTextColor.GRAY)));
			return true;
		}

		if (args.length == 0) {
			this.setUserFly(player);
		} else if (args.length == 1) {
			Player otherPlayer = Bukkit.getPlayer(args[0]);
			this.setUserFly(otherPlayer);
		} else {
			player.sendMessage(ComponentData.getPrefix() + "§7Synatx §8- §7/fly (player)");
		}

		return false;
	}

	private void setUserFly(Player player) {
		if (this.flyUserMap.contains(player)) {
			player.setAllowFlight(false);
			player.setFlying(true);
			player.sendMessage(ComponentData.getPrefix() + "§7Dein Flugmodus wurde §cdeaktiviert§7.");
			flyUserMap.remove(player);
		} else {
			player.setAllowFlight(true);
			player.setFlying(true);
			player.sendMessage(ComponentData.getPrefix() + "§7Dein Flugmodus wurde §caktiviert§7.");
			flyUserMap.add(player);
		}
	}
}
