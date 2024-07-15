package de.spookly.lobby.inventory;





/*
public class BackpackInventory extends AbstractMultiPageInventory {

	CosmeticType currentType;

	public BackpackInventory(Player player, CosmeticType currentType) {
		super(5, Component.text("\uE001\uE001\uE012\uE002\uE002\uE010").font(Key.key("ui")).color(NamedTextColor.WHITE), dimension -> {
			return BinaryMask.builder(dimension)
					.pattern("011111110")
					.pattern("011111110")
					.pattern("011111110")
					.pattern("011111110")
					.pattern("000000000")
					.build();
		}, player);


		if (currentType != null)
			this.currentType = currentType;
		addItems();
	}

	public void addItems() {
		Player player = player();

		newMenuModifier(menu -> {
			Mask mask = RecipeMask.builder(menu)
					.item('a', new ItemStack(Material.AIR))
					.item('h', SlotSettings.builder()
							.clickHandler(this::changeMenuPage)
							.arguments(CosmeticType.HEAD.toString())
							.item(Items.itemLore(Material.LEATHER_HELMET, Component.translatable("gamemode.lobby.item.cosmetic_hat").color(NamedTextColor.GRAY), 1,
									leftClickAction("gamemode.lobby.item.cosmetic_select"),
									rightClickAction("gamemode.lobby.item.cosmetic_uneqiup")
							)).build())
					.item('b', SlotSettings.builder()
							.clickHandler(this::changeMenuPage)
							.arguments(CosmeticType.BACK.toString())
							.item(Items.itemLore(Material.LEATHER_CHESTPLATE, Component.translatable("gamemode.lobby.item.cosmetic_back").color(NamedTextColor.GRAY), 1,
									leftClickAction("gamemode.lobby.item.cosmetic_select"),
									rightClickAction("gamemode.lobby.item.cosmetic_uneqiup")
							)).build())
					.item('g', SlotSettings.builder()
							.clickHandler(this::changeMenuPage)
							.arguments(CosmeticType.HAND.toString())
							.item(Items.itemLore(Material.FIREWORK_ROCKET, Component.translatable("gamemode.lobby.item.cosmetic_offhand").color(NamedTextColor.GRAY), 1,
									leftClickAction("gamemode.lobby.item.cosmetic_select"),
									rightClickAction("gamemode.lobby.item.cosmetic_uneqiup")
							)).build())
					.item('l', SlotSettings.builder()
							.clickHandler(this::changeMenuPage)
							.arguments(CosmeticType.LEGS.toString())
							.item(Items.item(Material.LEATHER_LEGGINGS, Component.text("Hosen"), 1)).build())
					.item('s', SlotSettings.builder()
							.clickHandler(this::changeMenuPage)
							.arguments(CosmeticType.BOOTS.toString())
							.item(Items.item(Material.LEATHER_BOOTS, Component.text("Schuhe"), 1)).build())
					.row(5)
					.pattern("aaahbgaaa")
					.build();
			mask.apply(menu);
		});

		if (currentType == null) return;
		switch (currentType) {
			case HEAD -> CosmeticsPlugin.getInstance().getHatManager().getAllCosmetics().stream()
					.sorted(Comparator.comparing(HatAccessory::getDisplay))
					.sorted(Comparator.comparingInt(o -> o.getRarity().getSortValue()))
					.forEach(accessory -> {
						if (player.hasPermission(accessory.getPermission()) || player.hasPermission("spookly.cosmetics.*")) {
							add(accessory.getItem(), HandleResult.DENY_GRABBING, this::selectCosmetic, accessory.getKey(), "HAT");
						}
					});
			case BACK -> CosmeticsPlugin.getInstance().getBackManager().getAllCosmetics().stream()
					.sorted(Comparator.comparing(BackAccessory::getDisplay))
					.sorted(Comparator.comparingInt(o -> o.getRarity().getSortValue()))
					.forEach(accessory -> {
						if (player.hasPermission(accessory.getPermission()) || player.hasPermission("spookly.cosmetics.*")) {
							add(accessory.getItem(), HandleResult.DENY_GRABBING, this::selectCosmetic, accessory.getKey(), "BACK");
						}
					});
			case HAND -> CosmeticsPlugin.getInstance().getOffhandManager().getAllCosmetics().stream()
					.sorted(Comparator.comparing(OffhandAccessory::getDisplay))
					.sorted(Comparator.comparingInt(o -> o.getRarity().getSortValue()))
					.forEach(accessory -> {
						if (player.hasPermission(accessory.getPermission()) || player.hasPermission("spookly.cosmetics.*")) {
							add(accessory.getItem(), HandleResult.DENY_GRABBING, this::selectCosmetic, accessory.getKey(), "OFFHAND");
						}
					});
		}

		nextButtonSlot(44);
		nextButtonEmpty(new ItemStack(Material.AIR));
		nextButton(Items.customItem(Material.STICK, Component.translatable("cosmetics.inventory.item.next").color(NamedTextColor.GRAY), 2, 1));
		previousButtonSlot(36);
		previousButtonEmpty(new ItemStack(Material.AIR));
		previousButton(Items.customItem(Material.STICK, Component.translatable("cosmetics.inventory.item.previous").color(NamedTextColor.GRAY), 1, 1));
//        add(craftBoots(player, 5, 145, 33, "Schleim", "slime"), HandleResult.DENY_GRABBING, this::selectShoes, "slime");
//        add(craftBoots(player, 18, 234, 208, "Regen", "regen"), HandleResult.DENY_GRABBING, this::selectShoes, "regen");
//        add(craftBoots(player, 255, 225, 0, "Musik", "musik"), HandleResult.DENY_GRABBING, this::selectShoes, "musik");
//        add(craftBoots(player, 216, 10, 45, "Liebes", "liebe"), HandleResult.DENY_GRABBING, this::selectShoes, "liebe");
//        add(craftBoots(player, 145, 5, 136, "Ender", "ender"), HandleResult.DENY_GRABBING, this::selectShoes, "ender");
//        add(craftBoots(player, 137, 2, 2, "Magma", "magma"), HandleResult.DENY_GRABBING, this::selectShoes, "magma");
//        add(craftBoots(player, 68, 67, 67, "Mystische", "mystisch"), HandleResult.DENY_GRABBING, this::selectShoes, "mystisch");
//        add(craftSpecialBoots(player, Material.IRON_BOOTS, "Schnelle", "schnell"), HandleResult.DENY_GRABBING, this::selectShoes, "schnell");
//        add(craftSpecialBoots(player, Material.GOLDEN_BOOTS, "Wolken", "wolken"), HandleResult.DENY_GRABBING, this::selectShoes, "wolken");
//        add(craftBoots(player, 65, 72, 178, "Lehm", "clay"), HandleResult.DENY_GRABBING, this::selectShoes, "clay");
	}

	private void selectCosmetic(Player player, ClickInformation clickInformation) {
		String name = clickInformation.getClickedSlot().getSettings().getItemArguments().get(0);
		String type = clickInformation.getClickedSlot().getSettings().getItemArguments().get(1);
		CosmeticProfile profile = CosmeticsPlugin.getInstance().getProfileManager().getProfile(player);

		var maybeManager = CosmeticsPlugin.getInstance().getCosmetics().getManager(type);
		if (maybeManager.isEmpty()) {
			return;
		}
		var manager = maybeManager.get();
		var maybeCosmetic = manager.getCosmetic(name);
		if (maybeCosmetic.isEmpty()) {
			player.sendMessage(ComponentData.getPrefix()
					.append(Component.text("Leider ist ein Fehler aufgetreten.").color(NamedTextColor.GRAY)));
			return;
		}
		var cosmetic = (Cosmetic) maybeCosmetic.get();

		if (!profile.has(cosmetic)) {
			player.sendMessage(ComponentData.getPrefix()
					.append(Component.text("Leider ist ein Fehler aufgetreten.").color(NamedTextColor.GRAY)));
		}
		profile.equip(cosmetic);

		player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1);
		player.closeInventory();
	}

	private void changeMenuPage(Player player, ClickInformation clickInformation) {
		var cosmeticType = CosmeticType.valueOf(clickInformation.getClickedSlot().getSettings().getItemArguments().get(0));

		player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1);
		if (clickInformation.getClickType().isLeftClick()) {
			new BackpackInventory(player, cosmeticType)
					.open();
			return;
		}

		CosmeticProfile profile = CosmeticsPlugin.getInstance().getProfileManager().getProfile(player);
		switch (cosmeticType) {
			case HEAD -> profile.unequip(HatAccessory.class);
			case BACK -> profile.unequip(BackAccessory.class);
			case HAND -> profile.unequip(OffhandAccessory.class);
		}
		player.closeInventory();
	}

	private Component rightClickAction(String key) {
		return Component.empty().decoration(TextDecoration.ITALIC, false)
				.append(Component.text("» ").color(NamedTextColor.GRAY))
				.append(Component.translatable("gamemode.general.item.right_click").color(NamedTextColor.RED))
				.append(Component.text(" "))
				.append(Component.translatable(key).color(NamedTextColor.GRAY));
	}

	private Component leftClickAction(String key) {
		return Component.empty().decoration(TextDecoration.ITALIC, false)
				.append(Component.text("» ").color(NamedTextColor.GRAY))
				.append(Component.translatable("gamemode.general.item.left_click").color(NamedTextColor.RED))
				.append(Component.text(" "))
				.append(Component.translatable(key).color(NamedTextColor.GRAY));
	}
}*/