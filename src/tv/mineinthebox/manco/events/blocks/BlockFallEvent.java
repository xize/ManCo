package tv.mineinthebox.manco.events.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import tv.mineinthebox.manco.ManCo;
import tv.mineinthebox.manco.api.customevents.CrateFallEvent;
import tv.mineinthebox.manco.instances.NormalCrate;

public class BlockFallEvent implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCall(EntityChangeBlockEvent e) {
		if(e.getEntity() instanceof FallingBlock) {
			FallingBlock fall = (FallingBlock) e.getEntity();
			
			if(fall.hasMetadata("crate_serie") && fall.hasMetadata("crate_owner")) {
				
				String player = (String)((FixedMetadataValue)fall.getMetadata("crate_owner").get(0)).asString();
				String serie = (String)((FixedMetadataValue)fall.getMetadata("crate_serie").get(0)).asString();
				
				if(!ManCo.getScheduler().canFall(fall.getLocation())) {
					e.setCancelled(true);
					return;
				}
				
				Player p = Bukkit.getPlayer(player);
				
				if(p instanceof Player) {
					Block block = fall.getLocation().getBlock();
					block.setType(Material.CHEST);
					Chest chest = (Chest) block.getState();
					chest.setMetadata("crate_owner", new FixedMetadataValue(ManCo.getPlugin(), player));
					chest.setMetadata("crate_serie", new FixedMetadataValue(ManCo.getPlugin(), serie));
					
					NormalCrate ncrate = new NormalCrate(serie);
					
					List<ItemStack> items = new ArrayList<ItemStack>();
					Iterator<ItemStack> it = ncrate.getRandomItems().iterator();
					
					while(it.hasNext()) {
						items.add(it.next());
					}
					
					for(int i = 0; i < ncrate.getMiniumSlots(); i++) {
						Collections.shuffle(items);
						chest.getInventory().addItem(items.get(0));
					}
					
					p.setMetadata("crate", new FixedMetadataValue(ManCo.getPlugin(), chest));
					
					Bukkit.getPluginManager().callEvent(new CrateFallEvent(ManCo.getApi().getCratePlayer(player), chest, ncrate));
					e.setCancelled(true);
				} else {
					e.setCancelled(true);
					return;
				}
			}
		}
	}

}
