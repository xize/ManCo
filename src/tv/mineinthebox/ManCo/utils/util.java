package tv.mineinthebox.ManCo.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import tv.mineinthebox.ManCo.logType;
import tv.mineinthebox.ManCo.manCo;

public class util {

	public static boolean isWorldGuardEnabled() {
		if(Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			return true;
		}
		return false;
	}

	public static boolean isIconomyEnabled() {
		if(Bukkit.getPluginManager().isPluginEnabled("iConomy")) {
			if(Bukkit.getPluginManager().isPluginEnabled("Vault")) {
				return true;
			} else {
				manCo.log("in order to let the iConomy part work, please download vault at http://dev.bukkit.org/server-mods/Vault", logType.severe);
				return false;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean isSlab(Block block) {
		Block highest = block.getLocation().getWorld().getHighestBlockAt(block.getLocation());
		if(highest.getType() == Material.getMaterial(126) || block.getType() == Material.getMaterial(44)) {
			return true;
		}
		return false;
	}

}
