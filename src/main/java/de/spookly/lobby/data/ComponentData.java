package de.spookly.lobby.data;

import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import de.spookly.lobby.LobbyPlugin;

public class ComponentData {

	static LobbyPlugin plugin = LobbyPlugin.getInstance();

	@Getter private final static String prefix_text = plugin.getGeneralConfig().getOrSetDefault("config.text.prefix.text", "Lobby");
	@Getter private final static String prefix_separator = plugin.getGeneralConfig().getOrSetDefault("config.text.prefix.separator", "◆");
	@Getter private final static TextColor prefix_color = TextColor.fromHexString(plugin.getGeneralConfig().getOrSetDefault("config.text.prefix.color", NamedTextColor.RED.asHexString()));
	@Getter private final static TextColor highlightColor = TextColor.fromHexString(plugin.getGeneralConfig().getOrSetDefault("config.text.highlightColor", NamedTextColor.RED.asHexString()));
	@Getter private final static String scoreboardTitle = plugin.getGeneralConfig().getOrSetDefault("config.scoreboard.title", String.valueOf('\uE003'));

	@Getter private final static String noPermKey = plugin.getGeneralConfig().getOrSetDefault("config.text.noPerms", "spookly.general.noPerms");
	@Getter private final static Component prefix = Component.text(prefix_text).color(prefix_color).append(Component.text(" "))
			.append(Component.text(prefix_separator).color(NamedTextColor.DARK_GRAY)).append(Component.text(" "));

	public static MenuBuilder menuBuilder() {
		return new MenuBuilder();
	}

	public static class MenuBuilder {
		Component background;
		Component title;

		public MenuBuilder setBackground(char background) {
			this.background = Component.text(background);
			return this;
		}

		public MenuBuilder setBackground(String background) {
			this.background = Component.text(background);
			return this;
		}

		public MenuBuilder setTitle(char title) {
			this.title = Component.text(title);
			return this;
		}

		public MenuBuilder setTitle(String title) {
			this.title = Component.text(title);
			return this;
		}

		public MenuBuilder setTitle(Component title) {
			this.title = title;
			return this;
		}

		public Component build() {
			Component menu = Component.empty()
					.font(Key.key("ui"))
					.color(NamedTextColor.WHITE);

			if (background != null)
				menu = menu
						.append(Component.text("\ue001"))
						.append(this.background);
			if (title != null)
				menu = menu
						.append(Component.text("\ue002"))
						.append(this.title);
			return menu;
		}
	}
}
