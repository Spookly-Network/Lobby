package de.spookly.lobby.commands;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import de.spookly.lobby.LobbyPlugin;
import de.spookly.lobby.data.ComponentData;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuildCommand implements CommandExecutor {
	private final LobbyPlugin lobbySystem;

	@Getter private final ArrayList<Player> editUserMap = new ArrayList<>();

	@Getter private final HashMap<Player, ItemStack[]> editItemMap = new HashMap<>();

	public BuildCommand(LobbyPlugin lobbySystem) {
		this.lobbySystem = lobbySystem;
	}

	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (!(commandSender instanceof Player))
			return true;
		Player player = (Player)commandSender;
		if (!player.hasPermission("lobby.build")) {
			player.sendMessage(ComponentData.getPrefix().append(Component.translatable(ComponentData.getNoPermKey()).color(NamedTextColor.GRAY)));
			return true;
		}
		if (args.length == 0) {
			if (this.editUserMap.contains(player)) {
				this.editUserMap.remove(player);
				player.setGameMode(GameMode.SURVIVAL);
				ItemStack[] saveItems = this.editItemMap.get(player);
				player.getInventory().setContents(saveItems);
				player.sendMessage(ComponentData.getPrefix()
						.append(Component.text("Dein Editmodus wurde").color(NamedTextColor.GRAY))
						.append(Component.text(" deaktiviert").color(NamedTextColor.RED))
						.append(Component.text(".").color(NamedTextColor.GRAY)));
			} else {
				this.editUserMap.add(player);
				player.setGameMode(GameMode.CREATIVE);
				this.editItemMap.put(player, player.getInventory().getContents());
				player.getInventory().clear();
				player.sendMessage(ComponentData.getPrefix()
						.append(Component.text("Dein Editmodus wurde").color(NamedTextColor.GRAY))
						.append(Component.text(" aktiviert").color(NamedTextColor.GREEN))
						.append(Component.text(".").color(NamedTextColor.GRAY)));
			}
		} else {
			player.sendMessage(ComponentData.getPrefix().append(Component.text("Syntax - /edit")));
			return false;
		}
		return true;
	}
}