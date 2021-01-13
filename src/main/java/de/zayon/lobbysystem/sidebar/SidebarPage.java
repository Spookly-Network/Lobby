package de.zayon.lobbysystem.sidebar;

import java.util.List;

public interface SidebarPage {
    String getDisplayName();

    List<String> getLines();
}
