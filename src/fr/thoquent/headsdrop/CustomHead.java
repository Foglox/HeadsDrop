package fr.thoquent.headsdrop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class CustomHead {

    private String displayName;
    private String head;
    private double chance;

    public CustomHead(String displayName, double chance, String head) {
	this.displayName = (displayName != null) ? displayName : "Alex";
	this.head = (head != null) ? head : "MHF_Alex";
	this.chance = (chance > 0.0D) ? chance : 0.0D;
    }

    public ItemStack getItemStack() {
	ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
	SkullMeta meta = ((SkullMeta) skull.getItemMeta());
	meta.setOwner(head);
	meta.setDisplayName(ChatColor.RESET + displayName);
	skull.setItemMeta(meta);
	return skull;
    }

    public void setHead(String head) {
	this.head = head;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }
    
    public void setChance(double chance) {
	this.chance = chance;
    }
    
    public String getDisplayName() {
	return this.displayName;
    }
    
    public double getChance() {
	return this.chance;
    }
    
}
