package tv.mineinthebox.ManCo.events;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.ManCo.ManCo;
import tv.mineinthebox.ManCo.configuration.configuration;
import tv.mineinthebox.ManCo.utils.util;
import tv.mineinthebox.ManCo.utils.worldguard;
import tv.mineinthebox.ManCo.utils.schematics.loadschematic;
import tv.mineinthebox.ManCo.utils.schematics.pasteSchematic;
import tv.mineinthebox.ManCo.utils.schematics.schematic;
import tv.mineinthebox.ManCo.utils.schematics.schematicApi;

public class eggCheck implements Listener {

	String prefix = ChatColor.GREEN + "[ManCo]" + ChatColor.GOLD + ":monument";

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEggSchematicCheck(PlayerInteractEvent e) throws IOException {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getTypeId() == 383) {
				ItemStack item = e.getPlayer().getItemInHand();
				if(item.hasItemMeta()) {
					if(item.containsEnchantment(Enchantment.DURABILITY)) {
						if(item.getDurability() == (short)120) {
							String[] args = item.getItemMeta().getDisplayName().split(" ");
							if(args[0].equals(prefix)) {
								if(schematicApi.isSchematic(ChatColor.stripColor(args[1]))) {
									e.setCancelled(true);
									//we need first to check whenever the player is able to build !
									if(util.isWorldGuardEnabled()) {
										if(!worldguard.canPlayerBuild(e.getPlayer())) {
											e.getPlayer().sendMessage(ChatColor.RED + "you are not allowed to place these kind of ManCo eggs in a protected region!");
											return;
										}
									}
									ItemStack items = new ItemStack(e.getItem());
									items.setAmount(items.getAmount() - 1);
									e.getPlayer().getInventory().setItemInHand(items);
									schematic schem = loadschematic.loadSchematic(new File(ManCo.getPlugin().getDataFolder() + File.separator + "schematics" + File.separator + ChatColor.stripColor(args[1]) + ".schematic"));
									pasteSchematic.pasteOldSchematic(e.getPlayer().getWorld(), e.getClickedBlock().getRelative(BlockFace.UP).getLocation(), schem);
									e.getPlayer().sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you successfully spawned a " + args[1] + " house!");
								} else {
									e.getPlayer().sendMessage(ChatColor.RED + "this schematic monument does not exists!");
								}
							}
						}
					}
				}
			}
		}
	}

}
