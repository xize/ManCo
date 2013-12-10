package tv.mineinthebox.ManCo.utils;

import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import tv.mineinthebox.ManCo.ManCo;
import uk.co.oliwali.HawkEye.DataType;
import uk.co.oliwali.HawkEye.util.HawkEyeAPI;

public class hawkeye {
	
	public static void log(String p, Block block) {
		HawkEyeAPI.addCustomEntry((JavaPlugin)ManCo.getPlugin(), "Used ManCo Monument Egg", p, block.getLocation(), DataType.BLOCK_PLACE.name());
	}

}
