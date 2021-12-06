package de.nehlen.lobbysystem;

import de.exceptionflug.mccommons.config.shared.ConfigFactory;
import de.exceptionflug.mccommons.config.spigot.SpigotConfig;
import de.nehlen.lobbysystem.commands.BuildCommand;
import de.nehlen.lobbysystem.commands.SetspawnCommand;
import de.nehlen.lobbysystem.factory.UserFactoryGadgetSave;
import de.nehlen.lobbysystem.factory.util.DatabaseLib;
import de.nehlen.lobbysystem.listener.*;
import de.nehlen.lobbysystem.manager.GroupManager;
import de.nehlen.lobbysystem.manager.ScoreboardManager;
import de.nehlen.lobbysystem.manager.ServerManager;
import de.nehlen.lobbysystem.sidebar.SidebarCache;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class LobbySystem extends JavaPlugin {

    @Getter private static LobbySystem lobbySystem;


    @Getter private SpigotConfig generalConfig;
    @Getter private SpigotConfig locationConfig;
    @Getter private SidebarCache sidebarCache;
    @Getter private ScoreboardManager scoreboardManager;
    @Getter private GroupManager groupManager;
    @Getter private ServerManager serverManager;
    @Getter private DatabaseLib databaseLib;
    @Getter private UserFactoryGadgetSave userFactoryGadgetSave;

    @Getter private FoodLevelChangeListener foodLevelChangeListener;
    @Getter private InteractListener interactListener;
    @Getter private ItemProtectListener itemProtectListener;
    @Getter private PlayerJoinListener playerJoinListener;
    @Getter private ProtectionListener protectionListener;
    @Getter private WeatherChangeListener weatherChangeListener;
    @Getter private PlayerMoveListener playerMoveListener;
    @Getter private PlayerQuitListener playerQuitListener;
    @Getter private SetspawnCommand setspawnCommand;
    @Getter private BuildCommand buildCommand;


    @Override
    public void onEnable() {
        lobbySystem = this;

        this.generalConfig = ConfigFactory.create(new File(getDataFolder(), "general_settings.yml"), SpigotConfig.class);
        this.locationConfig = ConfigFactory.create(new File(getDataFolder(), "location_settings.yml"), SpigotConfig.class);

        this.sidebarCache = new SidebarCache();
        this.scoreboardManager = new ScoreboardManager(this);
        this.groupManager = new GroupManager();
        this.serverManager = new ServerManager(this);
        this.databaseLib = new DatabaseLib(this);
        this.userFactoryGadgetSave = new UserFactoryGadgetSave(this);

        this.foodLevelChangeListener = new FoodLevelChangeListener(this);
        this.interactListener = new InteractListener(this);
        this.itemProtectListener = new ItemProtectListener(this);
        this.playerJoinListener = new PlayerJoinListener(this);
        this.protectionListener = new ProtectionListener(this);
        this.weatherChangeListener = new WeatherChangeListener(this);
        this.playerMoveListener = new PlayerMoveListener(this);
        this.playerQuitListener = new PlayerQuitListener(this);
        this.setspawnCommand = new SetspawnCommand(this);
        this.buildCommand = new BuildCommand(this);

        this.userFactoryGadgetSave.createTable();

        Bukkit.getServer().getPluginManager().registerEvents(foodLevelChangeListener, this);
        Bukkit.getServer().getPluginManager().registerEvents(interactListener, this);
        Bukkit.getServer().getPluginManager().registerEvents(itemProtectListener, this);
        Bukkit.getServer().getPluginManager().registerEvents(playerJoinListener, this);
        Bukkit.getServer().getPluginManager().registerEvents(protectionListener, this);
        Bukkit.getServer().getPluginManager().registerEvents(weatherChangeListener, this);
        Bukkit.getServer().getPluginManager().registerEvents(playerMoveListener, this);

        getCommand("build").setExecutor(buildCommand);
        getCommand("setspawn").setExecutor(setspawnCommand);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

    }
}
