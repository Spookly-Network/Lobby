package de.nehlen.lobbysystem.manager;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import de.nehlen.gameapi.Gameapi;
import de.nehlen.lobbysystem.LobbySystem;
import de.nehlen.lobbysystem.factory.data.ScoreboardData;
import de.nehlen.lobbysystem.sidebar.Sidebar;
import de.nehlen.lobbysystem.sidebar.SidebarCache;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ScoreboardManager {
    private final LobbySystem lobbySystem;

    private final HashMap<Player, BukkitTask> scoreboardTaskMap = new HashMap<>();

    private final int updateInterval = 1;

    private final IPlayerManager playerManager = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class);

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
                "%points%", Gameapi.getGameapi().getPointsAPI().getPoints(player),
                "%rang%", this.lobbySystem.getGroupManager().getGroupName(player),
                "%server%", playerManager.getOnlinePlayer(player.getUniqueId()).getConnectedService().getServerName()
        );
    }
}
