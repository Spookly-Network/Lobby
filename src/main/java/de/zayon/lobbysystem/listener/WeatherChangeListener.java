package de.zayon.lobbysystem.listener;

import de.zayon.lobbysystem.LobbySystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    private final LobbySystem lobbySystem;

    public WeatherChangeListener(LobbySystem lobbySystem) {
        this.lobbySystem = lobbySystem;
    }

    @EventHandler
    public void handleWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
