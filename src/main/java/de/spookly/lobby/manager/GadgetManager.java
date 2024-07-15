package de.spookly.lobby.manager;

import de.spookly.lobby.LobbyPlugin;

public class GadgetManager {
	private final LobbyPlugin lobbySystem;

	public GadgetManager(LobbyPlugin lobbySystem) {
		this.lobbySystem = lobbySystem;
	}

//    public void equipShoes(Player p, String name, int red, int green, int blue) {
//        p.sendMessage(StringData.getPrefix() + "§7Du hast die §a" + name + " Schuhe §7ausgerüstet.");
//        p.getInventory().setBoots(Items.createLeatherArmor(Material.LEATHER_BOOTS, "§a" + name + " Schuhe", Color.fromRGB(red, green, blue), false, 1));
//        p.closeInventory();
//    }
//
//    private static void repeat(Particle effect, int data, Player player, int times) {
//        player.getWorld().spawnParticle(effect, player.getLocation(), times, data);
//    }
//
//    public void killOverdoneItems() {
//        lobbySystem.getBukkitScheduler().scheduleSyncRepeatingTask(lobbySystem.getPlugin(), () -> {
//            for (Item item : Bukkit.getWorld("world").getEntitiesByClass(Item.class)) {
//                if (item.getTicksLived() >= 25) { // check if the item is at least 20 ticks old
//                    item.remove();
//                }
//            }
//        }, 0, 10);
//    }
//
//    public Integer startPlayBootEffects(Player player) {
//        return lobbySystem.getBukkitScheduler().scheduleSyncRepeatingTask(lobbySystem.getPlugin(), () -> {
//            if (player.getInventory().getBoots() != null) {
//                String itemDisplayName = player.getInventory().getBoots().getItemMeta().getDisplayName();
//                Vector vector = player.getLocation().getDirection().multiply(3D).setY(1D);
//
//                switch (itemDisplayName) {
//                    case "§aLiebes Schuhe":
//                        player.getWorld().spawnParticle(Particle.HEART, player.getLocation(), 1);
//                        break;
//                    case "§aFarben Schuhe":
//                        player.getWorld().spawnParticle(Particle.MOB_APPEARANCE, player.getLocation(), 3, 0);
//                        break;
//                    case "§aMusik Schuhe":
//                        player.getWorld().spawnParticle(Particle.NOTE, player.getLocation(), 3);
//                        break;
//                    case "§aEnder Schuhe":
//                        player.getWorld().spawnParticle(Particle.SPELL_WITCH, player.getLocation(), 1);
//                        break;
//                    case "§aSchleim Schuhe":
//                        if (player.isSneaking()) {
//                            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 5, true));
//                            player.getWorld().spawnParticle(Particle.BLOCK_CRACK, player.getLocation(), 12, Material.SLIME_BLOCK.createBlockData());
//                        } else {
//                            player.getWorld().spawnParticle(Particle.SLIME, player.getLocation(), 3);
//                        }
//                        break;
//                    case "§aPotion Schuhe":
//                        player.getWorld().spawnParticle(Particle.SLIME, player.getLocation(), 5);
//                        break;
//                    case "§aMagma Schuhe":
//                        player.getWorld().spawnParticle(Particle.LAVA, player.getLocation(), 1);
//                        break;
//                    case "§aRegen Schuhe":
//                        player.getWorld().spawnParticle(Particle.WATER_DROP, player.getLocation(), 7);
//                        break;
//                    case "§aMystische Schuhe":
//                        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation(), 3);
//                        break;
//                    case "§aSchnee Schuhe":
//                        player.getWorld().spawnParticle(Particle.SNOWBALL, player.getLocation(), 1);
//                        break;
//                    case "§aSchnelle Schuhe":
//                        if (player.isSneaking()) {
//                            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 75, true));
//                            player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 1);
//                        } else {
//                            player.getWorld().spawnParticle(Particle.SPELL_INSTANT, player.getLocation(), 1);
//                            player.getActivePotionEffects().remove(true);
//                        }
//                        break;
//                    case "§aWolken Schuhe":
//                        if (player.isSneaking()) {
//                            player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 0);
//                            player.setVelocity(vector);
//                            player.setAllowFlight(true);
//                            player.setFlying(true);
//                        } else {
//                            player.setAllowFlight(false);
//                            player.setFlying(false);
//                        }
//                        break;
//                    case "§aLehm Schuhe":
//                        player.getLocation().getWorld().dropItem(player.getLocation(), Items.createItem((Material)RNDHelper.getRndFromList(GadgetData.terracottaMaterialList), "", 1));
//                        break;
//                }
//            }
//        }, 0, 5);
//    }
//
//    public enum GadgetTypes {
//        BOOTS, HEADS;
//    }
}
