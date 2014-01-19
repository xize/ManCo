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
	
	/*public static schematic loadSchematic(File f) throws IOException, InvalidFormatException {
		FileInputStream stream = new FileInputStream(f);
		NBTInputStream nbtData = new NBTInputStream(new GZIPInputStream(stream));
		CompoundTag schematicTagList = (CompoundTag) nbtData.readTag();
		
		if(!schematicTagList.getName().equals("Schematic")) {
			nbtData.close();
			throw new IllegalArgumentException("[ManCo] The disered schematic is invalid!");
		}
		
		Map<String, Tag> schematic = schematicTagList.getValue();
		short width = NBTUtils.getChildTag(schematic, "Width", ShortTag.class).getValue();
		short length = NBTUtils.getChildTag(schematic, "Length", ShortTag.class).getValue();
		short height = NBTUtils.getChildTag(schematic, "Height", ShortTag.class).getValue();
		String materials = NBTUtils.getChildTag(schematic, "Materials", StringTag.class).getValue();
		
		if(!materials.equals("Alpha")) {
			nbtData.close();
			throw new IllegalArgumentException("[ManCo] this schematic is very outdated!");
		}
		
		byte[] blocks = NBTUtils.getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
		byte[] blockData = NBTUtils.getChildTag(schematic, "Data", ByteArrayTag.class).getValue();
		
		nbtData.close();
		
		return new schematic(blocks, blockData, width, length, height);
	}
	*/

}
