package de.spookly.lobby.nms;

import java.util.UUID;

import de.spookly.lobby.npc.NpcSkin;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public interface Npc {
	int getEntityId();

	UUID getUniqueId();

	Location getLocation();

	float getRotation();

	void despawn();
	void sendToPlayer(Player player);

	void equip(EquipmentSlot slot, ItemStack item);
	void equipBackItem(org.bukkit.inventory.ItemStack item);
	void setHeadRotationForPlayer(Player player, float pitch, float yaw);

	public interface NpcBuilder {
		NpcBuilder setName(String name);
		NpcBuilder setLocation(World world, double x, double y, double z);
		NpcBuilder setLocation(World world, double x, double y, double z, float yaw, float pitch);
		NpcBuilder setLocation(Location location);
		NpcBuilder setHead(ItemStack item);
		NpcBuilder setChestplate(ItemStack chestplate);
		NpcBuilder setLeggings(ItemStack leggings);
		NpcBuilder setBoots(ItemStack boots);
		NpcBuilder setItemInHand(ItemStack itemInHand);
		NpcBuilder setItemInOffHand(ItemStack itemInHand);
		NpcBuilder setTexture(NpcSkin texture);
		NpcBuilder setInteractHandler(NpcInteractHandler interactHandler);
		Npc build();

		public interface NpcInteractHandler {
			void handle(Player player, Npc npc);
		}
	}
}
