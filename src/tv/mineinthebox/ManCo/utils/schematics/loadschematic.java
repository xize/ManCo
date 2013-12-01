package tv.mineinthebox.ManCo.utils.schematics;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.minecraft.server.v1_7_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_7_R1.NBTTagCompound;

public class loadschematic {
	
	/*
	 * 
	 * this returns the schematic Type
	 * 
	 */
	
	public static schematic loadSchematic(File f) throws IOException {
        FileInputStream stream = new FileInputStream(f);
        NBTTagCompound nbtData = NBTCompressedStreamTools.a(stream);
     
        short width = nbtData.getShort("Width");
        short height = nbtData.getShort("Height");
        short length = nbtData.getShort("Length");
     
        byte[] blocks = nbtData.getByteArray("Blocks");
        byte[] data = nbtData.getByteArray("Data");
     
        return new schematic(blocks, data, width, length, height);
    }

}
