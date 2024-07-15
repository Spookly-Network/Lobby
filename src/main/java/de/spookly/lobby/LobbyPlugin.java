package de.spookly.lobby;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.configuration.ConfigurationWrapper;
import de.nehlen.spookly.plugin.SpooklyPlugin;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.ipvp.canvas.MenuFunctionListener;

import de.spookly.lobby.commands.BuildCommand;
import de.spookly.lobby.commands.FlyCommand;
import de.spookly.lobby.commands.SetspawnCommand;
import de.spookly.lobby.listener.*;
import de.spookly.lobby.manager.ScoreboardManager;
import de.spookly.lobby.sidebar.SidebarCache;

import org.bukkit.Bukkit;

@Getter
public class LobbyPlugin extends SpooklyPlugin {

	@Getter
	private static LobbyPlugin instance;

	@Deprecated(forRemoval = true)
	public static LobbyPlugin getLobby() {
		return instance;
	}

	private ConfigurationWrapper generalConfig;
	private ConfigurationWrapper npcConfig;
	private ConfigurationWrapper locationConfig;


	private SidebarCache sidebarCache;
	private ScoreboardManager scoreboardManager;

	private InteractListener interactListener;
	private PlayerJoinListener playerJoinListener;
	private PlayerMoveListener playerMoveListener;
	private PlayerQuitListener playerQuitListener;
	private AsyncChatListener asyncChatListener;
	private Listener listener;

	private SetspawnCommand setspawnCommand;
	private BuildCommand buildCommand;
	private FlyCommand flyCommand;

	@Override
	protected void load() {
		instance = this;
	}

	@Override
	protected void enable() {
		this.generalConfig = Spookly.getServer().createConfiguration(new File(getDataFolder(), "general_settings.yml"));
		this.locationConfig = Spookly.getServer().createConfiguration(new File(getDataFolder(), "location_settings.yml"));
		this.npcConfig = Spookly.getServer().createConfiguration(new File(getDataFolder(), "npc_settings.yml"));
		getLogger().info("Loaded configuration files");

//        this.databaseLib = Spookly.getServer().getConnection();
//        this.cosmeticProfileSql = new CosmeticProfileSql(databaseLib);
		getLogger().info("Loaded Database");

		this.sidebarCache = new SidebarCache();
		this.scoreboardManager = new ScoreboardManager(this);
//        this.factoryUserCosmetics = new FactoryUserCosmetics(this);
		getLogger().info("Loaded managers");

		this.interactListener = new InteractListener(this);
		this.playerJoinListener = new PlayerJoinListener(this);
		this.playerMoveListener = new PlayerMoveListener(this);
		this.playerQuitListener = new PlayerQuitListener(this);
		this.asyncChatListener = new AsyncChatListener();
		this.listener = new Listener(this);
		this.setspawnCommand = new SetspawnCommand(this);
		this.buildCommand = new BuildCommand(this);
		this.flyCommand = new FlyCommand();
		getLogger().info("Loaded listener / commands");

		registerEvent(listener);
		registerEvent(interactListener);
		registerEvent(playerJoinListener);
		registerEvent(playerQuitListener);
		registerEvent(playerMoveListener);
		registerEvent(asyncChatListener);
		registerEvent(new MenuFunctionListener());

		registerCommandOnly("build", buildCommand);
		registerCommandOnly("fly", flyCommand);
		registerCommandOnly("setspawn", setspawnCommand);

		Bukkit.getScheduler().runTaskLater(this, this::postStartup, 1L);
	}

	@Override
	protected void disable() {

	}

	@Override
	protected void postStartup() {
//        this.chestTraderHandler = new ChestTraderHandler(this);
		registerTranslatables();
	}

	private void registerTranslatables() {
		TranslationRegistry registry = TranslationRegistry.create(Key.key("spookly_lobby:value"));
		ResourceBundle bundleUS = ResourceBundle.getBundle("SpooklyLobby.Translations", Locale.US, UTF8ResourceBundleControl.get());
		ResourceBundle bundleDE = ResourceBundle.getBundle("SpooklyLobby.Translations", Locale.GERMANY, UTF8ResourceBundleControl.get());

		registry.registerAll(Locale.US, bundleUS, true);
		registry.registerAll(Locale.GERMAN, bundleDE, true);

		GlobalTranslator.translator().addSource(registry);
	}
}
