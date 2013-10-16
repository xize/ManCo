package tv.mineinthebox.ManCo.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class worldguard {
	
	public static boolean canPlayerBuild(Player p) {
		if(pl().canBuild(p, p.getLocation())) {
			return true;
		}
		return false;
	}
	
	public static WorldGuardPlugin pl() {
		if(util.isWorldGuardEnabled()) {
			WorldGuardPlugin getWg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
			return getWg;
		}
		return null;
	}

}
