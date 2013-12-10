package tv.mineinthebox.ManCo.utils;

import net.coreprotect.CoreProtect;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class coreprotect {
	
	@SuppressWarnings("deprecation")
	public static void log(String player, Block block) {
		CoreProtect core = (CoreProtect) Bukkit.getPluginManager().getPlugin("CoreProtect");
		core.getAPI().logPlacement(player, block.getLocation(), block.getTypeId(), block.getData());
	}

}
