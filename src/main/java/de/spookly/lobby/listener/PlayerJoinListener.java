package de.spookly.lobby.listener;


import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.player.PlayerRegisterEvent;
import de.nehlen.spookly.player.SpooklyPlayer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import de.spookly.lobby.LobbyPlugin;
import de.spookly.lobby.util.ItemBuilder;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

	private final LobbyPlugin lobby;

	public PlayerJoinListener(LobbyPlugin lobby) {
		this.lobby = lobby;

		Spookly.getServer().getEventExecuter().register(PlayerRegisterEvent.class, event -> {
			SpooklyPlayer player = event.getSpooklyPlayer();
			Player spigotPlayer = player.toPlayer();
			Component component = Component.text("« Rechtsklick »").color(NamedTextColor.GRAY);

			spigotPlayer.getInventory().setItem(0, lobbyItem(Material.COMPASS, "lobby.item.compass.name"));
			spigotPlayer.getInventory().setItem(1, lobbyItem(Material.GLOWSTONE_DUST, "lobby.item.lobbys.name"));
			spigotPlayer.getInventory().setItem(7, lobbyItem(Material.CHEST, "lobby.item.backpack.name"));
			spigotPlayer.getInventory().setItem(8, lobbySkull(lobbyItem(Material.PLAYER_HEAD, "lobby.item.friends.name"), player.textureUrl()));
			spigotPlayer.setLevel(player.points());

			this.lobby.getScoreboardManager().setUserScoreboard(spigotPlayer);
			if (spigotPlayer.hasPermission("spookly.lobby.join-message")) {
				Bukkit.broadcast(player.nameTag()
						.append(Component.text(" hat die Lobby betreten.").color(NamedTextColor.GRAY).font(Key.key("default"))));
			}
		});
	}

	@EventHandler
	public void handleJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		player.setGameMode(GameMode.ADVENTURE);
		player.resetTitle();
		player.setFoodLevel(20);
		player.setHealth(6L);
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(6L);
		player.getInventory().clear();

		event.joinMessage(Component.empty());
	}

	@EventHandler
	public void handleSpawn(PlayerSpawnLocationEvent event) {
		Player player = event.getPlayer();
		Location location = this.lobby.getLocationConfig().getLocation("spawn");
		if (!player.hasPlayedBefore()) {
			event.setSpawnLocation(location);
		}
	}

	private ItemStack lobbyItem(Material material, String translationKey) {
		return ItemBuilder.of(material)
				.displayName(Component.translatable(translationKey).color(NamedTextColor.RED)
						.append(Component.text(" "))
						.append(Component.translatable("lobby.item.action").color(NamedTextColor.GRAY))
						.decoration(TextDecoration.ITALIC, false))
				.build();
	}

	private ItemStack lobbySkull(ItemStack itemStack, String textureUrl) {
		return ItemBuilder.of(Material.PLAYER_HEAD)
				.skullTexture(textureUrl)
				.build();
	}
}
