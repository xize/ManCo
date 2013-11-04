package tv.mineinthebox.ManCo;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

import tv.mineinthebox.ManCo.configuration.configuration;
import tv.mineinthebox.ManCo.events.chestCheck;
import tv.mineinthebox.ManCo.events.cratescheduler;
import tv.mineinthebox.ManCo.events.handler;
import tv.mineinthebox.ManCo.utils.rareCrate;

public class ManCo extends JavaPlugin {
	
	private static Logger log = Logger.getLogger("Minecraft");
	private static ManCo plugin;
	
	public void onEnable() {
		plugin = this;
		log("has been enabled!", logType.info);
		configuration.createConfigs();
		handler.launch();
		cratescheduler.startScheduler();
		getCommand("manco").setExecutor(new command());
		if(!rareCrate.getRareCrateList().isEmpty()) {
			cratescheduler.startRareScheduler();
		}
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}
	
	public void onDisable() {
		log("has been disabled!", logType.info);
		chestCheck.destroyChestOnDisable();
	}
	
	public static ManCo getPlugin() {
		return plugin;
	}
	
	public static void log(String message, logType type) {
		if(type == logType.info) {
			log.info("["+getPlugin().getName()+"]" + " " + message);
		} else if(type == logType.severe) {
			log.severe("["+getPlugin().getName()+"]" + " " + message);
		}
	}
	
	public api getApi() {
		api app = new api();
		return app;
	}
	
}
