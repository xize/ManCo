package tv.mineinthebox.ManCo.api;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;

public class api_ManCoSupportedEvents implements Listener {

	public static HashMap<Entity, String> fallingRareState = new HashMap<Entity, String>();
	public static HashMap<String, ItemStack[]> items = new HashMap<String, ItemStack[]>();
	public static HashMap<Chest, String> getRareCrateList1 = new HashMap<Chest, String>();


	@SuppressWarnings("deprecation")
	@EventHandler
	public void setChestState(EntityChangeBlockEvent e) {
		if(fallingRareState.containsKey(e.getEntity())) {
			e.getBlock().setType(Material.CHEST);
			if(e.getBlock().getType() == Material.CHEST) {
				String[] args = fallingRareState.get(e.getEntity()).split(":");
				if(args.length == 5) {
					//(entity, p.getName()+":"+YourCrateName+":"+useKey+":"+useEffects+":"+price)
					String playerName = args[0];
					String crateName = args[1];
					boolean useKey = Boolean.parseBoolean(args[2]);
					boolean useEffects = Boolean.parseBoolean(args[3]);
					double price = Double.parseDouble(args[4]);
					
					//creating the chest with contents
					e.getBlock().setData((byte)1);
					Chest chest = (Chest) e.getBlock().getState();
					chest.getInventory().setContents(items.get(playerName));
					
					//cleaning up old data or we have a memory leak.
					fallingRareState.remove(e.getEntity());
					items.remove(playerName);
					
					//now lets use the serialized data again in our getRareCrateList1, the chest will be the key.
					getRareCrateList1.put(chest, playerName+":"+crateName+":"+useKey+":"+useEffects+":"+price);
				} else if(args.length == 4) {

				}
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
