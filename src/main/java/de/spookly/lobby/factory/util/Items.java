package de.spookly.lobby.factory.util;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Items {

	@Deprecated
	public static ItemStack createItem(Material material, String displayName, int amount) {
		return item(material, LegacyComponentSerializer.legacySection().deserialize(displayName), amount);
	}

	public static ItemStack item(Material material, Component displayName, Integer amount) {
		ItemStack itemStack = new ItemStack(material, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.displayName(displayName.decoration(TextDecoration.ITALIC, false));
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack itemLore(Material material, Component displayName, Integer amount, Component... lore ) {
		ItemStack itemStack = item(material, displayName, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.lore(Arrays.stream(lore).map(item -> item.decoration(TextDecoration.ITALIC, false)).collect(Collectors.toList()));
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack customItem(Material material, Component displayName, Integer customModelData, Integer amount) {
		ItemStack itemStack = item(material, displayName, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setCustomModelData(customModelData);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack customItem(Material material, Component displayName, Integer customModelData, Color color, Integer amount) {
		ItemStack itemStack = item(material, displayName, amount);
		LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
		itemMeta.setColor(color);
		itemMeta.setCustomModelData(customModelData);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack customItemLore(Material material, Component displayName, Integer customModelData, Integer amount, Component... lore ) {
		ItemStack itemStack = customItem(material, displayName, customModelData, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.lore(Arrays.stream(lore).collect(Collectors.toList()));
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack customSkullItem(String url, Component displayName, Integer amount) {
		ItemStack itemStack = item(Material.PLAYER_HEAD, displayName, amount);
		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

		if (url.isEmpty()) return itemStack;
		PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
		profile.getProperties().add(new ProfileProperty("textures", url));
		skullMeta.setPlayerProfile(profile);

		itemStack.setItemMeta(skullMeta);
		return itemStack;
	}

	public static ItemStack color(ItemStack itemStack, Color color) {
		ItemMeta meta = itemStack.getItemMeta();
		if (meta instanceof PotionMeta pMetax) {
			pMetax.setColor(color);
		} else if (meta instanceof LeatherArmorMeta pMeta) {
			pMeta.setColor(color);
		}
		meta.addItemFlags(ItemFlag.HIDE_DYE);
		itemStack.setItemMeta(meta);
		return itemStack;
	}

	public static ItemStack customItem(ItemStack itemStack, Integer model) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setCustomModelData(model);
		itemStack.setItemMeta(meta);
		return itemStack;
	}

	public static ItemStack hideAttributes(ItemStack itemStack) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemStack.setItemMeta(meta);
		return itemStack;
	}

	@Deprecated
	public static ItemStack createItemLore(Material material, String displayName, int amount, String... lore) {
		ItemStack itemStack = new ItemStack(material, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setLore(Arrays.stream(lore).collect(Collectors.toList()));
		itemMeta.setDisplayName(displayName);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	@Deprecated
	public static ItemStack getCustomSkullItem(String url, String name, Integer amount) {
		return customSkullItem(url, LegacyComponentSerializer.legacySection().deserialize(name), amount);
	}
}
