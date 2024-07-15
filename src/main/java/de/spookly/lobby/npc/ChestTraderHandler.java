package de.spookly.lobby.npc;

public class ChestTraderHandler {
//
//    private final LobbyPlugin plugin;
//    private final Npc npc;
//
//    private final String texSignature;
//    private final String texValue;
//
//    public ChestTraderHandler(LobbyPlugin plugin) {
//        this.plugin = plugin;
//        this.texSignature = plugin.getNpcConfig().getOrSetDefault("npc.adventurer.texture.signature", "texture-signature");
//        this.texValue = plugin.getNpcConfig().getOrSetDefault("npc.adventurer.texture.value", "texture-value");
//
//        var location = plugin.getNpcConfig().getLocation("npc.adventurer.location");
//        npc = Lobby.getLobby().getVolatileCodeHandler().createNpc()
//                .setLocation(location)
//                .setHead(Items.customItem(Material.LEATHER_HORSE_ARMOR, Component.text(" "), 46, 1))
//                .setItemInHand(Items.customItem(Material.BONE, Component.empty(), 17, 1))
//                .setName("§cChests kaufen")
//                .setTexture(new NpcSkinImpl(texValue, texSignature, "textures"))
//                .setInteractHandler(this::handleInteract)
//                .build();
//
//        npc.equipBackItem(Items.customItem(Material.LEATHER_HORSE_ARMOR, Component.empty(), 17, Color.fromRGB(189,24,24),1));
//
//        Spookly.getServer().getEventExecuter().register(PlayerRegisterEvent.class, event -> sendToPlayer(event.getSpooklyPlayer().toPlayer()));
//    }
//
//    public void handleInteract(Player player, Npc npc) {
//        Bukkit.getScheduler().runTask(plugin, () -> new SelectBuyChestInventory(player).open());
//    }
//
//    public void sendToPlayer(Player player) {
//        npc.sendToPlayer(player);
//    }
}
