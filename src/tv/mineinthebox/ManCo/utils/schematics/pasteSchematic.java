package tv.mineinthebox.ManCo.utils.schematics;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class pasteSchematic {
	
	/*
	 * 
	 * 
	 * this pastes the schematic however we may need to modify this and attach this to a scheduled task.
	 * 
	 * 
	 */
	
	@SuppressWarnings("deprecation")
	public static void pasteOldSchematic(World w, Location l, schematic schem) {
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
                    b.setTypeIdAndData(blocks[index], blockData[index], true);
                }
            }
        }
    }

}
