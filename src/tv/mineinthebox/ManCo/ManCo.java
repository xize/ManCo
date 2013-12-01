package tv.mineinthebox.ManCo;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

import tv.mineinthebox.ManCo.api.api;
import tv.mineinthebox.ManCo.configuration.configuration;
import tv.mineinthebox.ManCo.events.chestCheck;
import tv.mineinthebox.ManCo.events.cratescheduler;
import tv.mineinthebox.ManCo.events.handler;
import tv.mineinthebox.ManCo.utils.normalCrateList;
import tv.mineinthebox.ManCo.utils.rareCrate;
import tv.mineinthebox.ManCo.utils.rareCrateList;
import tv.mineinthebox.ManCo.utils.schematics.pasteSchematic;

public class ManCo extends JavaPlugin {
	
	private static ManCo plugin;
	
	public void onEnable() {
		plugin = this;
		log("has been enabled!", logType.info);
		configuration.createConfigs();
		handler.launch();
		cratescheduler.startScheduler();
		getCommand("manco").setExecutor(new command());
		pasteSchematic.loadSavedHashMap();
		pasteSchematic.scheduleBuild();
		if(!rareCrate.getRareCrateList().isEmpty()) {
			cratescheduler.startRareScheduler();
		}
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block	
		}
		if(configuration.isDebugMode()) {
			ManCo.log("Type: onEnable check", logType.debug);
			ManCo.log("scheduler status both needs to be not null here: \n" + "isNull("+configuration.isNull(cratescheduler.task) + "), isNull(" + configuration.isNull(cratescheduler.task2)+")", logType.debug);
			ManCo.log("=[normalCrateList memory]=", logType.debug);
			ManCo.log("ID: chestLocations: " + normalCrateList.chestLocations.size() + " memory entries", logType.debug);
			ManCo.log("ID: getCrateList: " + normalCrateList.getCrateList.size() + " memory entries", logType.debug);
			ManCo.log("ID: getCrateList2: " + normalCrateList.getCrateList2.size() + " memory entries", logType.debug);
			ManCo.log("ID: getFallingStateChest: " + normalCrateList.getFallingStateChest.size() + " memory entries", logType.debug);
			ManCo.log("ID: ItemsFromChest: " + normalCrateList.ItemsFromChest.size()+ " memory entries", logType.debug);
			ManCo.log("ID: ListDataValues should not be get higher: " + normalCrateList.ListDataValues.size() + " memory entries", logType.debug);
			ManCo.log("ID: schedulerTime needs to be 0: " + normalCrateList.schedulerTime.size() + " memory entries", logType.debug);
			ManCo.log("=[rareCrateList memory]=", logType.debug);
			ManCo.log("ID: chestLocations: " + rareCrateList.chestLocations.size() + " memory entries", logType.debug);
			ManCo.log("ID: getCrateList: " + rareCrateList.getCrateList.size() + " memory entries", logType.debug);
			ManCo.log("ID: getCrateList2: " + rareCrateList.getCrateList2.size() + " memory entries", logType.debug);
			ManCo.log("ID: getFallingStateChest: " + rareCrateList.getFallingStateChest.size() + " memory entries", logType.debug);
			ManCo.log("ID: ItemsFromChest: " + rareCrateList.ItemsFromChest.size() + " memory entries", logType.debug);
			ManCo.log("ID: rareCrates: " + rareCrateList.rareCrates.size() + " memory entries", logType.debug);
			ManCo.log("ID: schedulerTime: " + rareCrateList.schedulerTime.size() + " memory entries", logType.debug);
			ManCo.log("=[rareCrate]=", logType.debug);
			ManCo.log("ID: rareCrates loaded from config: " + rareCrate.getRareCrateList().size(), logType.debug);
		}
	}
	
	public void onDisable() {
		log("has been disabled!", logType.info);
		chestCheck.destroyChestOnDisable();
		if(!pasteSchematic.locations.isEmpty()) {
			pasteSchematic.saveHashMap();
		}
		if(configuration.isDebugMode()) {
			ManCo.log("Type: on shutdown/reload, check if everything is empty!", logType.debug);
			ManCo.log("scheduler status both needs to be null here: \n" + "isNull("+configuration.isNull(cratescheduler.task) + "), isNull(" + configuration.isNull(cratescheduler.task2)+")", logType.debug);
			ManCo.log("=[normalCrateList memory]=", logType.debug);
			ManCo.log("ID: chestLocations: " + normalCrateList.chestLocations.size() + " memory entries", logType.debug);
			ManCo.log("ID: getCrateList: " + normalCrateList.getCrateList.size() + " memory entries", logType.debug);
			ManCo.log("ID: getCrateList2: " + normalCrateList.getCrateList2.size() + " memory entries", logType.debug);
			ManCo.log("ID: getFallingStateChest: " + normalCrateList.getFallingStateChest.size() + " memory entries", logType.debug);
			ManCo.log("ID: ItemsFromChest: " + normalCrateList.ItemsFromChest.size()+ " memory entries", logType.debug);
			ManCo.log("ID: ListDataValues should not be get higher: " + normalCrateList.ListDataValues.size() + " memory entries", logType.debug);
			ManCo.log("ID: schedulerTime needs to be 0: " + normalCrateList.schedulerTime.size() + " memory entries", logType.debug);
			ManCo.log("=[rareCrateList memory]=", logType.debug);
			ManCo.log("ID: chestLocations: " + rareCrateList.chestLocations.size() + " memory entries", logType.debug);
			ManCo.log("ID: getCrateList: " + rareCrateList.getCrateList.size() + " memory entries", logType.debug);
			ManCo.log("ID: getCrateList2: " + rareCrateList.getCrateList2.size() + " memory entries", logType.debug);
			ManCo.log("ID: getFallingStateChest: " + rareCrateList.getFallingStateChest.size() + " memory entries", logType.debug);
			ManCo.log("ID: ItemsFromChest: " + rareCrateList.ItemsFromChest.size() + " memory entries", logType.debug);
			ManCo.log("ID: rareCrates: " + rareCrateList.rareCrates.size() + " memory entries", logType.debug);
			ManCo.log("ID: schedulerTime: " + rareCrateList.schedulerTime.size() + " memory entries", logType.debug);
			ManCo.log("=[rareCrate]=", logType.debug);
			ManCo.log("ID: rareCrates loaded from config: " + rareCrate.getRareCrateList().size(), logType.debug);
		}
	}
	
	public static ManCo getPlugin() {
		return plugin;
	}
	
	public static void log(String message, logType type) {
		if(type == logType.info) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&2["+getPlugin().getName()+"]&f" + " " + message));
		} else if(type == logType.severe) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[Error]&2["+getPlugin().getName()+"]&f" + " " + message));
		} else if(type == logType.debug) {
			Bukkit.getConsoleSender().sendMessage((ChatColor.translateAlternateColorCodes('&', "&c[Debug]&2["+getPlugin().getName()+"]&3" + " " + message)));
		}
	}
	
	public api getApi() {
		api app = new api();
		return app;
	}
	
}
