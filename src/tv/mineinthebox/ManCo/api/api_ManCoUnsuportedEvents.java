package tv.mineinthebox.ManCo.api;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.ManCo.exceptions.InvalidChestStorageException;

public class api_ManCoUnsuportedEvents implements Listener {

	private static HashMap<Entity, ItemStack[]> entityChest = new HashMap<Entity, ItemStack[]>();

	public static void add(Entity entity, ItemStack[] items) {
		entityChest.put(entity, items);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void fillChestApi(EntityChangeBlockEvent e) throws InvalidChestStorageException {
		if(entityChest.containsKey(e.getEntity())) {
			e.setCancelled(true);
			e.getBlock().setTypeIdAndData(Material.CHEST.getId(), (byte) 1, true);
			Chest chest = (Chest) e.getBlock().getState();
			if(entityChest.get(e.getEntity()).length < 27) {
				for(ItemStack item : entityChest.get(e.getEntity())) {
					chest.getInventory().addItem(item);
				}
				entityChest.remove(e.getEntity());
			} else {
				//memory safety in case it throws allready.
				entityChest.remove(e.getEntity());
				throw new InvalidChestStorageException("[ManCo-API]InvalidChestStorage: the ItemStack[] amount is more than the single chest could obtain!");
			}
		}
	}

}
