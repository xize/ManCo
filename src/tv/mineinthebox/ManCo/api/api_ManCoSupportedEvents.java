package tv.mineinthebox.ManCo.api;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;

public class api_ManCoSupportedEvents implements Listener {
	
	public static HashMap<Entity, String> fallingRareState = new HashMap<Entity, String>();
	public static HashMap<String, ItemStack[]> items = new HashMap<String, ItemStack[]>();
	public static HashMap<String, Chest> getRareCrateList1 = new HashMap<String, Chest>();
	
	
	@SuppressWarnings("deprecation")
	public void setChestState(EntityChangeBlockEvent e) {
		if(fallingRareState.containsKey(e.getEntity())) {
			e.getBlock().setType(Material.CHEST);
			if(e.getBlock().getType() == Material.CHEST) {
				String[] args = fallingRareState.get(e.getEntity()).split(":");
				String playerName = args[0];
				String crateName = args[1];
				e.getBlock().setData((byte)1);
				Chest chest = (Chest) e.getBlock().getState();
				chest.getInventory().setContents(items.get(playerName));
				
			}
		}
	}
	
	public static void addFallState(Entity entity, String playerName) {
		fallingRareState.put(entity, playerName);
	}
	
	public static void addItemsForChestPreparation(Player p, ItemStack[] itemss) {
		items.put(p.getName(), itemss);
	}

}
