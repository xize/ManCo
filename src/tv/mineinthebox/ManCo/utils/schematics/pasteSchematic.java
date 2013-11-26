package tv.mineinthebox.ManCo.utils.schematics;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.ManCo.ManCo;

public class pasteSchematic {

	/*
	 * 
	 * 
	 * this pastes the schematic however we may need to modify this and attach this to a scheduled task.
	 * 
	 * 
	 */

	//old source.

	public static HashMap<Location, String> locations = new HashMap<Location, String>();
	public static BukkitTask task;

	public static void pasteOldSchematic(World w, Location l, schematic schem) {
		try {
			byte[] blocks = schem.getBlocks();
			byte[] blockData = schem.getData();

			short length = schem.getLength();
			short width = schem.getWidth();
			short height = schem.getHeight();

			for(int x = 0; x < width; ++x) {
				for(int y = 0; y < height; ++y) {
					for(int z = 0; z < length; ++z) {
						//int index = y * width * length + z * width + x;
						int index = x + (y * length + z) * width;

						Block b = new Location(w, x + l.getX(), y + l.getY(), z + l.getZ()).getBlock();
						if(b.getType() == Material.AIR) {
							//b.setTypeIdAndData(blocks[index], blockData[index], true);
							locations.put(b.getLocation(), blocks[index]+":"+blockData[index]);	
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
				if(!locations.isEmpty()) {
					Iterator<Location> it = locations.keySet().iterator();
					if(it.hasNext()) {
						Location loc = it.next();
						String[] args = locations.get(loc).split(":");
						int dataValue = Integer.parseInt(args[0]);
						byte subValue = Byte.parseByte(args[1]);
						Block block = loc.getBlock();
						block.setTypeIdAndData(dataValue, subValue, true);
						block.getWorld().playEffect(loc, Effect.STEP_SOUND, dataValue);
						it.remove();
						locations.remove(loc);
					}
				}
			}
			
		}, 0, 1);
		task = taskID;
	}


}
