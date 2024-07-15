package de.spookly.lobby.listener;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import de.spookly.lobby.LobbyPlugin;
import de.spookly.lobby.data.ComponentData;
import de.spookly.lobby.inventory.NavigationInventory;
import de.spookly.lobby.inventory.SwitcherInventory;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

	private LobbyPlugin lobby;
	public static HashMap<Player, Player> lastClickedCache = new HashMap<Player, Player>();
	public static Map<Player, Instant> lastCosmeticAction = new HashMap<>();

	public InteractListener(LobbyPlugin lobby) {
		this.lobby = lobby;
	}

//    @EventHandler
//    public void handleInteractWithEntity(PlayerInteractEntityEvent event) {
//        Player player = event.getPlayer();
//        Player target = (Player) event.getRightClicked();
//        Inventory inventory = Bukkit.createInventory(player, 27, "§cSpieler Informationen");
//        inventory.setItem(0, Items.createSkull("§c" + target.getName() + " Profil", target.getUniqueId()));
//        inventory.setItem(1, new ItemManager(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "§c").create());
//        inventory.setItem(9, new ItemManager(Material.NETHER_STAR, "§cStats").create());
//        inventory.setItem(10, new ItemManager(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "§c").create());
//        inventory.setItem(18, new ItemManager(Material.EMERALD, "§c???").create());
//        inventory.setItem(19, new ItemManager(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "§c").create());
//        inventory.setItem(12, Items.createSkull(target.getDisplayName(), target.getUniqueId()));
//        inventory.setItem(14, new ItemManager(Material.PAPER, "§7Status:").addLoreLine("STATUS").create());
//        inventory.setItem(16, new ItemManager(Material.GOLD_NUGGET, "§7Punkte:").addLoreLine("§7➥§c " + Gameapi.getGameapi().getPointsAPI().points(target)).create());
////        player.openInventory(inventory);
//        lastClickedCache.put(player, target);
//    }

	@EventHandler
	public void handleInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			if (event.getClickedBlock().getType().equals(Material.BARRIER)) {
				Collection<ItemFrame> itemFrames = event.getClickedBlock().getLocation().add(0, 1, 0).getNearbyEntitiesByType(ItemFrame.class, 1);
				ItemStack itemStack = itemFrames.stream().toList().get(0).getItem();
				if (itemStack.getType().equals(Material.STICK) && itemStack.getItemMeta().getCustomModelData() == 3) {
					//TODO open overview
					player.playSound(player, Sound.BLOCK_CHEST_OPEN, 1, 1);
					player.sendMessage(ComponentData.getPrefix().append(Component.translatable("gamemode.general.message.development").color(NamedTextColor.GRAY)));
				}
			}

		}

		if ((event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !this.lobby.getBuildCommand().getEditUserMap().contains(player)))
			if (player.getInventory().getItemInMainHand().getType().equals(Material.COMPASS)) {
				new NavigationInventory(player).open();
			} else if (player.getInventory().getItemInMainHand().getType().equals(Material.CHEST)) {
//				new BackpackInventory(player, null).open();
			} else if (player.getInventory().getItemInMainHand().getType().equals(Material.GLOWSTONE_DUST)) {
				new SwitcherInventory(player).open();
			} else if (player.getInventory().getItemInMainHand().getType().equals(Material.PLAYER_HEAD)) {
				player.performCommand("friendsgui");
			}

//        if (player.getInventory().getItemInOffHand().equals(CosmeticsHand.FIREWORK.getItem())) {
//            if (lastCosmeticAction.containsKey(player)) {
//                Instant lastAction = lastCosmeticAction.get(player);
//                if (Instant.now().isBefore(lastAction.plusSeconds(2))) {
//                    player.sendActionBar(Component.text("Bitte warte einen kurzen moment.").color(NamedTextColor.RED));
//                    return;
//                }
//            }
//
//            FireworksManager.playRandomFirework(player);
//            lastCosmeticAction.put(player, Instant.now());
//        }

		if (!lobby.getBuildCommand().getEditUserMap().contains(player))
			event.setCancelled(true);
	}

	@EventHandler
	public void entityInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if (lobby.getBuildCommand().getEditUserMap().contains(player))
			return;
		if (event.getRightClicked().getType().equals(EntityType.ITEM_FRAME)) {
			ItemStack itemStack = ((ItemFrame) event.getRightClicked()).getItem();
			if (itemStack.getType().equals(Material.STICK) && itemStack.getItemMeta().getCustomModelData() == 3) {
//                new BuyChestInventory(player).open();
				player.playSound(player, Sound.BLOCK_CHEST_OPEN, 1, 1);
			}
			event.setCancelled(true);
		}
		return;
	}
}
