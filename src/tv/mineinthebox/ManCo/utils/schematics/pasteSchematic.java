package tv.mineinthebox.ManCo.utils.schematics;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.ManCo.ManCo;
import tv.mineinthebox.ManCo.utils.coreprotect;
import tv.mineinthebox.ManCo.utils.hawkeye;
import tv.mineinthebox.ManCo.utils.logblock;
import tv.mineinthebox.ManCo.utils.prism;
import tv.mineinthebox.ManCo.utils.util;

public class pasteSchematic {

	/*
	 * 
	 * 
	 * this pastes the schematic however we may need to modify this and attach this to a scheduled task.
	 * 
	 * 
	 */

	public static HashMap<Location, String> locations = new HashMap<Location, String>();
	public static BukkitTask task;

	public static void pasteOldSchematic(World w, Location l, schematic schem, Player p) {
		try {
			byte[] blocks = schem.getBlocks();
			byte[] blockData = schem.getData();

			short length = schem.getLength();
			short width = schem.getWidth();
			short height = schem.getHeight();

			for(int y = 0; y < height;y++) {
				for(int x = 0; x < width; x++) {
					for(int z = 0; z < length; ++z) {
						//here under working version
						//int index = x + (y * length + z) * width;
						int index = y*width*length+z*width+x;
						Block b = new Location(w, x + l.getX(), y + l.getY(), z + l.getZ()).getBlock();
						if(b.getType() == Material.AIR) {
							//b.setTypeIdAndData(blocks[index], blockData[index], true);
							String dataTypeSerialized = Math.abs(blocks[index])+":"+Math.abs(blockData[index])+":"+p.getName();
							if(!dataTypeSerialized.equalsIgnoreCase(0+":"+0+":"+p.getName()) || !dataTypeSerialized.equalsIgnoreCase("?:?:"+p.getName())) {
								locations.put(b.getLocation(), dataTypeSerialized);	
							}	
						}
					}
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			//ignore
		}
	}

	public static void scheduleBuild() {
		BukkitTask taskID = Bukkit.getScheduler().runTaskTimer(ManCo.getPlugin(), new Runnable() {

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				Iterator<Location> it = locations.keySet().iterator();
				if(it.hasNext()) {
					Location loc = it.next();
					if(loc != null) {
						String[] args = locations.get(loc).split(":");
						Integer DataValue = Integer.parseInt(args[0]);
						Byte subValue = Byte.parseByte(args[1]);
						String playerName = args[2];
						Block block = loc.getBlock();
						block.setTypeIdAndData(DataValue, subValue, false);
						block.getWorld().playEffect(loc, Effect.STEP_SOUND, block.getTypeId());
						//we now log on the players name!
						if(util.isCoreProtectEnabled()) {
							coreprotect.log(playerName, block);
						} else if(util.isLogBlockEnabled()) {
							logblock.log(playerName, block);
						} else if(util.isPrismEnabled()) {
							prism.log(playerName, block);
						} else if(util.isHawkEyeEnabled()) {
							hawkeye.log(playerName, block);
						}
					}
					it.remove();
					locations.remove(loc);
				}
			}
	}, 0, 1);
		task = taskID;
}

public static void saveHashMap() { 
	ArrayList<String> array = new ArrayList<String>();
	try {
		File f = new File(ManCo.getPlugin().getDataFolder() + File.separator + "schematicTask_data.db");
		FileConfiguration con = YamlConfiguration.loadConfiguration(f);
		Iterator<Location> it = locations.keySet().iterator();
		while(it.hasNext()) {
			Location loc = it.next();
			array.add(loc.getWorld().getName()+","+loc.getBlock().getX()+","+loc.getBlock().getY()+","+loc.getBlock().getZ()+","+locations.get(loc));

		}
		con.set("locations", array.toArray());
		con.save(f);
	} catch(Exception e) {
		e.printStackTrace();
	}
}

public static void loadSavedHashMap() {
	try {
		File f = new File(ManCo.getPlugin().getDataFolder() + File.separator + "schematicTask_data.db");
		if(f.exists()) {
			FileConfiguration con = YamlConfiguration.loadConfiguration(f);
			for(String serialized : con.getStringList("locations")) {
				String[] args = serialized.split(",");
				String worldName = args[0];
				Integer x = Integer.parseInt(args[1]);
				Integer y = Integer.parseInt(args[2]);
				Integer z = Integer.parseInt(args[3]);
				String DataValues = args[4];
				Location loc = new Location(Bukkit.getWorld(worldName), x, y , z);
				locations.put(loc, DataValues);
			}
			f.delete();
		}
	} catch(Exception e) {
		e.printStackTrace();
	}
}


}
