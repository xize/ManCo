package tv.mineinthebox.ManCo.utils;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

import de.diddiz.LogBlock.Consumer;

public class logblock {
	
	public static void log(String player, Block block) {
		Consumer lbConsumer = (Consumer) Bukkit.getPluginManager().getPlugin("LogBlock");
		lbConsumer.queueBlockPlace(player, block.getState());
	}

}
