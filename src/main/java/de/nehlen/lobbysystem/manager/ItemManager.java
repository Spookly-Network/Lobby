package de.nehlen.lobbysystem.manager;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemManager {

    @Getter @Setter private Material material;
    @Getter @Setter private Integer amount;
    @Getter @Setter private String displayName;
    @Getter @Setter private ArrayList<String> lore;
    @Getter @Setter private ArrayList<Enchantment> enchantments;

    public ItemManager(Material material) {
        this.material = material;
        this.amount = 1;
        this.displayName = "";
    }
    public ItemManager(Material material, String displayName) {
        this.material = material;
        this.amount = 1;
        this.displayName = displayName;
    }
    public ItemManager(Material material, String displayName, Integer amount) {
        this.material = material;
        this.amount = amount;
        this.displayName = displayName;
    }
    public ItemManager(Material material, Integer amount) {
        this.material = material;
        this.amount = amount;
        this.displayName = "";
    }

    public ItemManager addLoreLine(String line) {
        this.lore.add(line);
        return this;
    }

    public ItemManager removeLoreLine(Integer index) {
        this.lore.remove(index);
        return this;
    }

    public ItemManager addEnchantment(Enchantment enchantment) {
        this.enchantments.add(enchantment);
        return this;
    }

    public ItemManager removeEnchantment(Integer index) {
        this.enchantments.remove(index);
        return this;
    }

    public ItemStack create() {

        ItemStack item = new ItemStack(this.material, this.amount);
        ItemMeta mitem = item.getItemMeta();
        mitem.setDisplayName(this.displayName);

        if(this.enchantments.size() > 0) {
            for (Enchantment enchantment: this.enchantments) {
                item.addEnchantment(enchantment, 0);
            }
        }
        item.setItemMeta(mitem);

        return item;
    }
}
