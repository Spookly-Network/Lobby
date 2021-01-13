package de.zayon.lobbysystem.factory.data;



import de.zayon.lobbysystem.sidebar.SidebarPage;

import java.util.Arrays;
import java.util.List;

public enum ScoreboardData implements SidebarPage {
    PAGE_1("§7» §c§lZayon §8| 1/2", Arrays.asList("§r", "§7➤ §ePunkte", "%points%", "§r", "§7➤ §aRang", "%rang%", "§r", "§7➤ §bServer", "%server%", "§r", "§7Zayon.de")),
    PAGE_2("§7» §c§lZayon §8| 2/2",Arrays.asList("§r", "§7➤ §eDiscord", "discord.zayon.de", "§r", "§7➤ §aWebseite", "https://zayon.de", "§r", "§7➤ §bTwitter", "@Zayon.de", "§r", "§7Zayon.de"));

    private final List<String> lines;

    private final String displayName;


    ScoreboardData(String displayName, List<String> lines) {
        this.displayName = displayName;
        this.lines = lines;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public List<String> getLines() {
        return this.lines;
    }
}
