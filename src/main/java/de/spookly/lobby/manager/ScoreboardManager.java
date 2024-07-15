package de.spookly.lobby.manager;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.stream.Collectors;

import de.nehlen.spookly.Spookly;
import de.nehlen.spooklycloudnetutils.helper.CloudPlayerHelper;
import de.nehlen.spooklycloudnetutils.manager.GroupManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;

import de.spookly.lobby.LobbyPlugin;
import de.spookly.lobby.factory.data.ScoreboardData;
import de.spookly.lobby.sidebar.Sidebar;
import de.spookly.lobby.sidebar.SidebarCache;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ScoreboardManager {
	private final LobbyPlugin lobby;

	private final HashMap<Player, BukkitTask> scoreboardTaskMap = new HashMap<>();

	public ScoreboardManager(LobbyPlugin lobby) {
		this.lobby = lobby;
	}

	public void setUserScoreboard(final Player player) {
		if (!this.scoreboardTaskMap.containsKey(player)) {
			this.scoreboardTaskMap.put(player, (new BukkitRunnable() {
				int counter = 0;
				int sec = 0;

				public void run() {
					ScoreboardManager.this.setScoreboardContent(player, this.counter);
					if (sec >= 7) {
						this.counter = ++this.counter % (ScoreboardData.values()).length;
						sec = 0;
					}
					sec++;
				}
			}).runTaskTimer(this.lobby, 0L, 20L));
		}
	}

	public void removeUserScoreboard(Player player) {
		if (this.scoreboardTaskMap.containsKey(player)) {
			((BukkitTask) this.scoreboardTaskMap.get(player)).cancel();
			this.scoreboardTaskMap.remove(player);
		}
	}

	private void setScoreboardContent(Player player, int pageNumber) {
		ScoreboardData scoreboardData = ScoreboardData.values()[pageNumber];
		DecimalFormat decimalFormat = new DecimalFormat("#,###,###,###", new DecimalFormatSymbols(Locale.GERMANY));
		this.lobby.getSidebarCache();
		Sidebar sidebar = SidebarCache.getUniqueCachedSidebar(player);
		sidebar.setDisplayName(scoreboardData.getDisplayName());
		sidebar.setLines(scoreboardData.getLines().stream().map(line -> {
			return line.replaceText(replace("%points%", Component.text(decimalFormat.format(Spookly.getPlayer(player).points()))))
					.replaceText(replace("%rang%", Component.translatable(GroupManager.getGroupPrefix(player)).font(Key.key("rangs"))))
					.replaceText(replace("%server%", Component.text(CloudPlayerHelper.playerConnectedService(player).serverName())));
		}).collect(Collectors.toList()));
	}

	private TextReplacementConfig replace(String literal, Component component) {
		return TextReplacementConfig.builder()
				.matchLiteral(literal)
				.replacement(component)
				.build();
	}
}
