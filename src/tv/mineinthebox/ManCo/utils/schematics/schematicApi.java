package tv.mineinthebox.ManCo.utils.schematics;

import java.io.File;

import tv.mineinthebox.ManCo.ManCo;

public class schematicApi {
	
	public static boolean isSchematic(String name) {
		try {
			File f = new File(ManCo.getPlugin().getDataFolder() + File.separator + "schematics" + File.separator + name+".schematic");
			if(f.exists()) {
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
