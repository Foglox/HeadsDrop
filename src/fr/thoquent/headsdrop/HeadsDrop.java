package fr.thoquent.headsdrop;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HeadsDrop extends JavaPlugin {

    public static FileConfiguration config;
    public static HashMap<String, CustomHead> headList = new HashMap<String, CustomHead>();

    @Override
    public void onEnable() {
	PluginManager pm = getServer().getPluginManager();
	pm.registerEvents(new EntityDeathListener(), this);

	reloadConfiguration();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (cmd.getName().equalsIgnoreCase("headsdrop")) {
	    if (args.length == 0 && sender.hasPermission("headsdrop.version")) {
		sender.sendMessage("§aHeadsDrop by §c§oThoquent");
		sender.sendMessage("§aversion: §c" + getDescription().getVersion());
		return true;
	    }
	    if (args.length >= 1) {
		if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("headsdrop.reload")) {
		    reloadConfiguration();
		    sender.sendMessage("§aHeadsDrop configuration has been reloaded");
		    return true;
		}
	    }
	}
	return true;
    }

    public void reloadConfiguration() {
	saveDefaultConfig();
	reloadConfig();
	config = getConfig();
	headList.clear();
	Iterator<String> entities = config.getConfigurationSection("entities").getKeys(false).iterator();
	while (entities.hasNext()) {
	    String name = entities.next();
	    headList.put(name, new CustomHead(config.getString("entities." + name + ".display-name"),
		    config.getDouble("entities." + name + ".chance"), config.getString("entities." + name + ".head")));
	}
    }
}
