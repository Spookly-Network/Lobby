package de.zayon.lobbysystem.factory.data;

import de.zayon.lobbysystem.LobbySystem;
import lombok.Getter;

public class StringData {

    @Getter private static String prefix = LobbySystem.getLobbySystem().getGeneralConfig().getOrSetDefault("config.prefix", "§cLobby §8◆ §7");

    @Getter private static String noPerm = LobbySystem.getLobbySystem().getGeneralConfig().getOrSetDefault("config.noPerms", "Darauf hast du keine Rechte.");

    @Getter private static String highlightColor = LobbySystem.getLobbySystem().getGeneralConfig().getOrSetDefault("config.highlightColor", "§b");

    public static String combinate() { return prefix + noPerm; }
}
