package de.spookly.lobby.inventory;

import de.nehlen.spookly.inventory.AbstractSinglePageInventory;
import de.nehlen.spookly.inventory.HandleResult;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.ipvp.canvas.ClickInformation;

import de.spookly.lobby.LobbyPlugin;
import de.spookly.lobby.data.ComponentData;
import de.spookly.lobby.util.ItemBuilder;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class NavigationInventory extends AbstractSinglePageInventory {

	public NavigationInventory(Player player) {
		super(5, ComponentData.menuBuilder()
				.setBackground('\uE301')
				.setTitle(Component.translatable("gamemode.lobby.gui.navigation"))
				.build(), player);
		addItems();
	}

	private void addItems() {
		//Realms item
		//set(ItemBuilder.of(Material.PLAYER_HEAD)
		//		.displayName(Component.translatable("lobby.compass.realms.name").color(TextColor.fromHexString("#b454ff")))
		//		.skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFkYmNmMjNlNzI2ZjE5OTMzOWIyNTNhNTIxNGY4ODMxZDYyYjk3YTUwYjBlZDkzMmM1MzFmNjJjMzIyMyJ9fX0=")
		//		.build(), 10, HandleResult.DENY_GRABBING, this::teleportPlayerToPoint, "realms")

		//Close item
		set(ItemBuilder.of(Material.PAPER)
				.customModelData(20001)
				.displayName(Component.translatable("gamemode.general.gui.close")
						.color(NamedTextColor.RED)
						.decoration(TextDecoration.ITALIC, false))
				.build(), 8, HandleResult.DENY_GRABBING, this::handleClose);

		//Bingo item
		set(ItemBuilder.of(Material.CRAFTING_TABLE)
				.displayName(Component.text("Bingo / Bingo-Quick"))
				.addLoreLine(Component.text("§7Beweise dein Minecraft wissen"))
				.addLoreLine(Component.text("§7und Gewinne gegen deine"))
				.addLoreLine(Component.text("§7gegner nur mit Gehirnschmalz"))
				.addLoreLine(Component.text("§7und ein bisschen Glück."))
				.addLoreLine(Component.text("§c1h - 2h §7§l| §c20min."))
				.build(), 9, HandleResult.DENY_GRABBING, this::teleportPlayerToPoint, "bingo");

		//Jumpwars item
		set(ItemBuilder.of(Material.DIAMOND_BOOTS)
				.displayName(Component.translatable("lobby.compass.jumpwars.name").color(TextColor.fromHexString("#75ae8c")))
				.addLoreLine(Component.translatable("lobby.compass.jumpwars.line1").color(NamedTextColor.GRAY))
				.addLoreLine(Component.translatable("lobby.compass.jumpwars.line2").color(NamedTextColor.GRAY))
				.addLoreLine(Component.translatable("lobby.compass.jumpwars.line3").color(NamedTextColor.RED))
				.build(), 10, HandleResult.DENY_GRABBING, this::teleportPlayerToPoint, "jumpwars");

		//Skyfighters item
		set(ItemBuilder.of(Material.VAULT)
				.displayName(Component.translatable("lobby.compass.skyfighters.name").color(TextColor.fromHexString("#75ae8c")))
				.addLoreLine(Component.translatable("lobby.compass.skyfighters.line1").color(NamedTextColor.GRAY))
				.addLoreLine(Component.translatable("lobby.compass.skyfighters.line2").color(NamedTextColor.GRAY))
				.addLoreLine(Component.translatable("lobby.compass.skyfighters.line3").color(NamedTextColor.RED))
				.build(), 11, HandleResult.DENY_GRABBING, this::teleportPlayerToPoint, "skyfighters");

		//Spawn item
		set(ItemBuilder.of(Material.FIRE_CHARGE)
				.displayName(Component.translatable("lobby.compass.spawn.name").color(NamedTextColor.RED))
				.addLoreLine(Component.translatable("lobby.compass.spawn.line1").color(NamedTextColor.GRAY))
				.build(), 36, HandleResult.DENY_GRABBING, this::teleportPlayerToPoint, "spawn");
	}

	private void teleportPlayerToPoint(Player player, ClickInformation clickInformation) {
		String argument = clickInformation.getClickedSlot().getSettings().getItemArguments().get(0);
		selectPointHandler(player(), argument);
		player().closeInventory();
	}

	private void selectPointHandler(Player player, String spawnPoint) {
		Location location = LobbyPlugin.getInstance().getLocationConfig().getLocation(spawnPoint);
		player.teleport(location);
		player.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 3.0F);
	}

	private void handleClose(Player player, ClickInformation clickInformation) {
		player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 0);
		player.closeInventory();
	}
}