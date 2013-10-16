package tv.mineinthebox.ManCo.utils;

import org.bukkit.Bukkit;

public class util {
	
	public static boolean isWorldGuardEnabled() {
		if(Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			return true;
		}
		return false;
	}

}
