package tv.mineinthebox.ManCo.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import tv.mineinthebox.ManCo.manCo;

public class handler {
	
	public static void launch() {
		setListener(new chestCheck());
	}
	
	public static void setListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, manCo.getPlugin());
	}

}
