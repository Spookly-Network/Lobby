package de.zayon.lobbysystem.manager;

import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.zayon.lobbysystem.LobbySystem;
import de.zayon.lobbysystem.factory.data.ScoreboardData;
import de.zayon.lobbysystem.sidebar.Sidebar;
import de.zayon.lobbysystem.sidebar.SidebarCache;
import de.zayon.zayonapi.ZayonAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Locale;

public class ScoreboardManager {
    private final LobbySystem lobbySystem;

    private final HashMap<Player, BukkitTask> scoreboardTaskMap = new HashMap<>();

    private final int updateInterval = 1;

    public ScoreboardManager(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
    }

    public void setUserScoreboard(final Player player) {
        if (!this.scoreboardTaskMap.containsKey(player))
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
            }).runTaskTimer((Plugin) this.lobbySystem, 0L, 20L));
    }

    public void removeUserScoreboard(Player player) {
        if (this.scoreboardTaskMap.containsKey(player)) {
            ((BukkitTask) this.scoreboardTaskMap.get(player)).cancel();
            this.scoreboardTaskMap.remove(player);
        }
    }

    private void setScoreboardContent(Player player, int pageNumber) {
        ScoreboardData scoreboardData = ScoreboardData.values()[pageNumber];
        this.lobbySystem.getSidebarCache();
        Sidebar sidebar = SidebarCache.getUniqueCachedSidebar(player);
        sidebar.setDisplayName(scoreboardData.getDisplayName());
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        sidebar.setLines(scoreboardData.getLines(),
                "%points%", ZayonAPI.getZayonAPI().getPointsAPI().getPoints(player),
                "%rang%", this.lobbySystem.getGroupManager().getGroupName(player),
                "%server%", BridgePlayerManager.getInstance().getOnlinePlayer(player.getUniqueId()).getConnectedService().getServerName()
        );
    }
}
