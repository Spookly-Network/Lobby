package de.spookly.lobby.listener;

import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.placeholder.PlaceholderContext;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.punishments.PunishmentType;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import de.spookly.lobby.data.ComponentData;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncChatListener implements Listener {


	@EventHandler
	public void onChat(AsyncChatEvent event) {
		SpooklyPlayer spooklyPlayer = Spookly.getPlayer(event.getPlayer().getUniqueId());
		PlaceholderContext context = new PlaceholderContext(spooklyPlayer.toPlayer(), PlaceholderContext.PlaceholderType.CHAT);

		//Check if player has active punishment from type mute
		var punishment = spooklyPlayer.activePunishments().stream().filter(pnshmt -> pnshmt.getType().equals(PunishmentType.MUTE)).findFirst();
		if (punishment.isPresent()) {
			spooklyPlayer.toPlayer().sendMessage(ComponentData.getPrefix().append(Component.translatable("gamemode.general.message.muted").color(NamedTextColor.RED)));
			return;
		}

		event.renderer(spooklyPlayer.getChatRenderer());
//		event.setCancelled(true);
//		SpooklyPlayer sPlayer = Spookly.getPlayer(event.getPlayer().getUniqueId());
//
//		//Check if player has active punishment from type mute
//		var punishment = sPlayer.activePunishments().stream().filter(pnshmt -> pnshmt.getType().equals(PunishmentType.MUTE)).findFirst();
//
//		//If punishment is active do not send message
//
//
//		//Send message to players that are online
//		sPlayer.nameColor();
//		event.set
//		Bukkit.getOnlinePlayers().forEach(player -> {
//			ChatType
//			player.sendMessage(event.signedMessage(), ChatType.CHAT.bind(Component.text("123")));
//		});

//		event.signedMessage();
//		event.renderer(ChatRenderer.);
//		Bukkit.broadcast(sPlayer.nameTag()
//				.append(Component.text(": ").color(NamedTextColor.GRAY))
//				.append(Spookly.getPlaceholderManager().replacePlaceholder(event.message(), event.getPlayer())));
	}
}
