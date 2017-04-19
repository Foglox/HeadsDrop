package fr.thoquent.headsdrop;

import java.util.Random;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDeathListener implements Listener {

    // Monsters and animals heads
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
	if (!e.getEntityType().isAlive() || e.getEntityType().equals(EntityType.PLAYER))
	    return;
	// Check if there was a killer
	if (e.getEntity().getKiller() != null || HeadsDrop.config.getBoolean("ignore-killer")) {
	    // Check if the entity head is enable
	    if (HeadsDrop.headList.containsKey(e.getEntityType().toString())) {
		CustomHead customHead = HeadsDrop.headList.get(e.getEntityType().toString());
		ItemStack headItem = customHead.getItemStack();
		// Calculating percentage chance
		double chance = customHead.getChance();
		double looting = HeadsDrop.config.getDouble("looting");
		if (looting > 0) {
		    int lootingLevel = e.getEntity().getKiller().getInventory().getItemInMainHand()
			    .getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
		    looting *= lootingLevel;
		    if (looting > 0) {
			chance += chance * looting / 100;
		    }
		}
		// Chance of drop
		if (drop(chance)) {
		    e.getDrops().add(headItem);
		}
	    }
	}
    }

    // Players heads
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
	Player player = e.getEntity();
	// Check if there was a killer
	if (player.getKiller() != null) {
	    // Check if the player head is enable
	    if (HeadsDrop.headList.containsKey("PLAYER")) {
		CustomHead customHead = HeadsDrop.headList.get("PLAYER");
		customHead.setDisplayName(player.getName());
		customHead.setHead(player.getName());
		ItemStack headItem = customHead.getItemStack();
		// Calculating chance of drop
		double chance = customHead.getChance();
		double looting = HeadsDrop.config.getDouble("looting");
		if (looting > 0) {
		    int lootingLevel = e.getEntity().getKiller().getInventory().getItemInMainHand()
			    .getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
		    looting *= lootingLevel;
		    if (looting > 0) {
			chance += chance * looting / 100;
		    }
		}
		// Chance of drop
		if (drop(chance)) {
		    e.getDrops().add(headItem);
		}
	    }
	}
    }

    public boolean drop(double chance) {
	return chance >= 100 * new Random().nextDouble();
    }

}
