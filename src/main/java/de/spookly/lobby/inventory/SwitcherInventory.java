package de.spookly.lobby.inventory;

import java.util.Collection;

import de.nehlen.spookly.inventory.AbstractMultiPageInventory;
import de.nehlen.spookly.inventory.HandleResult;
import de.nehlen.spooklycloudnetutils.helper.CloudPlayerHelper;
import de.nehlen.spooklycloudnetutils.helper.CloudServiceHelper;
import eu.cloudnetservice.driver.service.ServiceInfoSnapshot;
import eu.cloudnetservice.modules.bridge.BridgeDocProperties;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.ipvp.canvas.ClickInformation;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.mask.RecipeMask;
import org.ipvp.canvas.slot.SlotSettings;

import de.spookly.lobby.LobbyPlugin;
import de.spookly.lobby.data.ComponentData;
import de.spookly.lobby.util.ItemBuilder;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SwitcherInventory extends AbstractMultiPageInventory {

	private static final String CLOUD_LOBBY_GROUP = LobbyPlugin.getInstance().getGeneralConfig().getOrSetDefault("lobbyGroup", "lobby");

	public SwitcherInventory(Player player) {
		super(2, ComponentData.menuBuilder()
						.setBackground('\uE201')
						.setTitle(Component.translatable("gamemode.lobby.gui.switcher"))
						.build(),
				dimension -> {
					return BinaryMask.builder(dimension)
							.pattern("000000000")
							.pattern("111111111")
							.build();
				}, player);
		addItems();
	}

	private void addItems() {
		newMenuModifier(menu -> {
			Mask mask = RecipeMask.builder(menu)
					.item('a', new ItemStack(Material.AIR))
					.item('c', SlotSettings.builder()
							.item(ItemBuilder.of(Material.PAPER)
									.customModelData(20001)
									.displayName(Component.translatable("gamemode.general.gui.close")
											.color(NamedTextColor.RED).
											decoration(TextDecoration.ITALIC, false))
									.build())
							.clickHandler(this::handleClose)
							.build())
					.item('r', SlotSettings.builder()
							.item(ItemBuilder.of(Material.PAPER)
									.customModelData(20002)
									.displayName(Component.translatable("gamemode.general.gui.reload")
											.color(NamedTextColor.GRAY).
											decoration(TextDecoration.ITALIC, false))
									.build())
							.clickHandler(this::handleClose)
							.build())
					.row(1)
					.pattern("aaaaaaarc")
					.build();
			mask.apply(menu);
		});

		Collection<ServiceInfoSnapshot> serviceInfoSnapshots = CloudServiceHelper.servicesByGroup(CLOUD_LOBBY_GROUP);
		serviceInfoSnapshots.stream().sorted().forEach(serviceSnapshot -> {
			String service = serviceSnapshot.serviceId().name();
			Integer onlineCount = serviceSnapshot.readProperty(BridgeDocProperties.ONLINE_COUNT);
			Integer maxPlayers = serviceSnapshot.readProperty(BridgeDocProperties.MAX_PLAYERS);
			String lore = "§7" + onlineCount + "/" + maxPlayers;
			serviceSnapshot.readProperty(BridgeDocProperties.ONLINE_COUNT);
			if (CloudPlayerHelper.playerConnectedService(player).serverName().equals(service)) {
				add(ItemBuilder.of(Material.LIME_DYE)
						.addLoreLine(Component.text(service).color(NamedTextColor.GOLD))
						.build(), HandleResult.DENY_GRABBING);
			} else {
				add(ItemBuilder.of(Material.GRAY_DYE)
						.addLoreLine(Component.text(service).color(NamedTextColor.GOLD))
						.build(), HandleResult.DENY_GRABBING, this::switchService, serviceSnapshot.serviceId().name());
			}
		});
	}

	public void switchService(Player player1, ClickInformation clickInformation) {
		String serviceName = clickInformation.getClickedSlot().getSettings().getItemArguments().getFirst();

		player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 0);
		player.closeInventory();

		CloudPlayerHelper.sendPlayerToService(player1, serviceName);
	}

	private void handleClose(Player player, ClickInformation clickInformation) {
		player.closeInventory();
	}
}
