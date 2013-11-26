package tv.mineinthebox.ManCo.events;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import tv.mineinthebox.ManCo.api;
import tv.mineinthebox.ManCo.ManCo;
import tv.mineinthebox.ManCo.configuration.configuration;

public class handler {
	
	public static void launch() {
		setListener(new chestCheck());
		setListener(new api());
		setListener(new moneyCheck());
		setListener(new eggCheck());
		if(configuration.isChestProtectionDisabled()) {
			setListener(new chestOpenTimer_unprotected());
		} else {
			setListener(new chestOpenTimer_protected());
		}
	}
	
	public static void setListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, ManCo.getPlugin());
	}
	
	public static void restartListeners() {
		HandlerList.unregisterAll(ManCo.getPlugin());
		launch();
	}

}
