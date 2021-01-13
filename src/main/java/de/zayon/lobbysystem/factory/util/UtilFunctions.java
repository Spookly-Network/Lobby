package de.zayon.lobbysystem.factory.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class UtilFunctions {

    public static void ActionBar(Player p, String message) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(message).create());
    }

    public static String getRandomStringOutList(List<String> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
