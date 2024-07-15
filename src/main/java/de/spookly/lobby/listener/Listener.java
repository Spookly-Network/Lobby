package de.spookly.lobby.listener;

import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.events.EventExecuter;
import de.nehlen.spookly.player.PlayerPointsChangeEvent;

import de.spookly.lobby.LobbyPlugin;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class Listener implements org.bukkit.event.Listener {

	private LobbyPlugin plugin;

	public Listener(LobbyPlugin plugin) {
		EventExecuter eventExecuter = Spookly.getServer().getEventExecuter();
		this.plugin = plugin;

		eventExecuter.register(PlayerPointsChangeEvent.class, event -> {
			Player player = event.getSpooklyPlayer().toPlayer();
			player.setLevel(event.getPoints());
		});
		eventExecuter.register(BlockPlaceEvent.class, event -> {
			if (!plugin.getBuildCommand().getEditUserMap().contains(event.getPlayer()))
				event.setCancelled(true);
		});
		eventExecuter.register(BlockBreakEvent.class, event -> {
			if (!plugin.getBuildCommand().getEditUserMap().contains(event.getPlayer()))
				event.setCancelled(true);
		});
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			if (plugin.getBuildCommand().getEditUserMap().contains((Player) event.getDamager()))
				return;
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityPickupItem(EntityPickupItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteract(EntityDamageEvent event) {
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void handleInventoryClick(InventoryClickEvent event) {
		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void handeHandSwitch(PlayerSwapHandItemsEvent event) {
		event.setCancelled(true);
	}

}
