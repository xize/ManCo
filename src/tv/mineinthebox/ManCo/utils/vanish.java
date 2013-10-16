package tv.mineinthebox.ManCo.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class vanish {
	
	public static boolean isVanished(Player p) {
		for(Player p2 : Bukkit.getOnlinePlayers()) {
			if(!p2.getName().equalsIgnoreCase(p.getName())) {
				if(!(p2.canSee(p))) {
					return true;
				}
			}
		}
		return false;
	}

}
