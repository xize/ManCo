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
		//important note about minecraft versions
		//slabs are changed from data value between 1.6.4 and 1.7.2
		//older versions not tested.
		String version = Bukkit.getVersion();
		if(version.contains("1.6.4")) {
			if(block.getType() == Material.getMaterial(126)) {
				return true;
			}	
		} else if(version.contains("1.7.2")) {
			if(block.getType() == Material.getMaterial(44)) {
				return true;
			}	
		}
		return false;
	}

}
