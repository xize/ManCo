package tv.mineinthebox.ManCo.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

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
		if(highest.getRelative(BlockFace.DOWN).getType() == Material.getMaterial(126) || highest.getRelative(BlockFace.DOWN).getType() == Material.getMaterial(44) || highest.getType() == Material.RAILS || highest.getType() == Material.TORCH || highest.getType() == Material.REDSTONE_WIRE || highest.getType() == Material.REDSTONE_TORCH_ON || highest.getType() == Material.REDSTONE_TORCH_OFF || highest.getType() == Material.ACTIVATOR_RAIL || highest.getType() == Material.ANVIL || highest.getType() == Material.CHEST || highest.getType() == Material.FENCE || highest.getType() == Material.STONE_PLATE || highest.getType() == Material.WOOD_PLATE || highest.getType() == Material.WHEAT || highest.getType() == Material.MELON_STEM || highest.getType() == Material.PUMPKIN_STEM || highest.getType() == Material.RAILS || highest.getType() == Material.TORCH || highest.getType() == Material.REDSTONE_WIRE || highest.getType() == Material.REDSTONE_TORCH_ON || highest.getType() == Material.REDSTONE_TORCH_OFF || highest.getType() == Material.ACTIVATOR_RAIL || highest.getType() == Material.ANVIL || highest.getType() == Material.CHEST || highest.getType() == Material.FENCE || highest.getType() == Material.STONE_PLATE || highest.getType() == Material.TRIPWIRE || highest.getType() == Material.NETHER_FENCE || highest.getType() == Material.RAILS || highest.getType() == Material.TORCH || highest.getType() == Material.REDSTONE_WIRE || highest.getType() == Material.REDSTONE_TORCH_ON || highest.getType() == Material.REDSTONE_TORCH_OFF || highest.getType() == Material.ACTIVATOR_RAIL || highest.getType() == Material.ANVIL || highest.getType() == Material.CHEST || highest.getType() == Material.FENCE || highest.getType() == Material.CROPS || highest.getType() == Material.SIGN_POST || highest.getType() == Material.SUGAR_CANE_BLOCK || highest.getType() == Material.STONE_PLATE) {
			return true;
		}
		return false;
	}

}
