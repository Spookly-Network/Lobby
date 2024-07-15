package de.spookly.lobby.inventory;

//public class LootChestInventory extends AbstractSinglePageInventory {
//    private CosmeticsChestType cosmeticsType;
//    private Integer counter = 20;
//    private BukkitTask task;
//    private List<Cosmetics> cosmeticsList;
//
//    public LootChestInventory(Player player, CosmeticsChestType chestType) {
//        super(4, Component.text("Kiste öffnen").color(NamedTextColor.RED), player);
//        this.cosmeticsType = chestType;
//        init();
//    }
//
//    private void init() {
//        setItemsInList();
//        mask(Material.BLACK_STAINED_GLASS_PANE).apply(menu());
//        set(Items.item(Material.RED_DYE, Component.text("Stoppen").color(NamedTextColor.RED), 1), 31, HandleResult.DENY_GRABBING, this::stopAnimation);
//        for (int i = 9; i <= 17; i++) {
//            menu().getSlot(i).setItem(((Cosmetics) RNDHelper.getRndFromList(cosmeticsList)).getItem());
//        }
//
//        menu().setCloseHandler((player1, menu) -> {
//            menu.open(player1);
//        });
//    }
//
//    private void startAnimation() {
//        task = Bukkit.getScheduler().runTaskTimerAsynchronously(Lobby.getLobby(), () -> {
//            shiftItems();
//            player().playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
//        }, 0, 4);
//    }
//
//    private void stopAnimation(Player player, ClickInformation clickInformation) {
//        task.cancel();
//        set(Items.item(Material.BLACK_STAINED_GLASS_PANE, Component.text(" "), 1), 31, HandleResult.DENY_GRABBING);
//        Bukkit.getScheduler().runTaskTimerAsynchronously(Lobby.getLobby(), task -> {
//            counter--;
//            shiftItems();
//            player().playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
//            if (counter == 0) {
//                Cosmetics finalCosmetic = cosmeticsList.stream().filter(cosmetics -> cosmetics.getItem().equals(menu().getSlot(13).getItem(player))).toList().get(0);
//                //TODO add permission
//                //Lobby.getLobby().getPermissionManagement().user(player().getUniqueId()).addPermission(finalCosmetic.getPermission());
//                //Lobby.getLobby().getPermissionManagement().updateUser(Lobby.getLobby().getPermissionManagement().user(player().getUniqueId()));
//
//                if(finalCosmetic.getRarity() == CosmeticRarity.LEGENDARY) {
//                    Bukkit.broadcast(StringData.getPrefix()
//                            .append(Spookly.getPlayer(player).nameTag())
//                            .append(Component.text(" hat ").color(NamedTextColor.GRAY))
//                            .append(finalCosmetic.getItem().displayName())
//                            .append(Component.text(" aus einer Kiste gezogen!").color(NamedTextColor.GRAY))
//                    );
//                }
//
//                player.sendMessage(StringData.getPrefix()
//                        .append(Component.text("Du hast ").color(NamedTextColor.GRAY))
//                        .append(finalCosmetic.getItem().displayName())
//                        .append(Component.text(" aus der Kiste gezogen!").color(NamedTextColor.GRAY))
//                );
//                mask(Material.GREEN_STAINED_GLASS_PANE).apply(menu());
//                set(Items.item(Material.GREEN_STAINED_GLASS_PANE, Component.text(" "), 1), 31, HandleResult.DENY_GRABBING);
//                player().playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
//                menu().setCloseHandler((player1, menu) -> {});
//
//                task.cancel();
//            }
//        }, 0, 10);
//    }
//
//    private void shiftItems() {
//        for (int i = 9; i <= 17; i++) {
//            if (i != 17) {
//                menu().getSlot(i).setItem(menu().getSlot(i + 1).getItem(player()));
//            } else {
//                menu().getSlot(i).setItem(((Cosmetics) RNDHelper.getRndFromList(cosmeticsList)).getItem());
//            }
//        }
//    }
//
//    private void setItemsInList() {
//        List<Cosmetics> list = new ArrayList(List.of());
//
//        list.addAll(Arrays.stream(CosmeticsHead.values()).toList());
//        list.addAll(Arrays.stream(CosmeticsChest.values()).toList());
//        list.addAll(Arrays.stream(CosmeticsHand.values()).toList());
//
//        list.remove(CosmeticsHead.NONE);
//        if (cosmeticsType == CosmeticsChestType.COMMON) {
//            cosmeticsList = list.stream().filter(cosmetic -> cosmetic.getRarity().getSortValue() <= 2).toList();
//            return;
//        } else if (cosmeticsType == CosmeticsChestType.RARE) {
//            cosmeticsList = list.stream().filter(cosmetic -> cosmetic.getRarity().getSortValue() <= 3 && cosmetic.getRarity().getSortValue() >= 1).toList();
//            return;
//        } else if (cosmeticsType == CosmeticsChestType.LEGENDARY) {
//            cosmeticsList = list.stream().filter(cosmetic -> cosmetic.getRarity().getSortValue() >= 2).toList();
//            return;
//        }
//        cosmeticsList = List.of();
//    }
//
//    private Mask mask(Material material) {
//        Mask mask = BinaryMask.builder(menu())
//                .item(Items.item(material, Component.text(" "), 1))
//                .pattern("111111111")
//                .pattern("111101111")
//                .pattern("111111111")
//                .pattern("111101111")
//                .build();
//        return mask;
//    }
//
//    @Override
//    public void open() {
//        super.open();
//        startAnimation();
//    }
//}
