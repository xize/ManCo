package tv.mineinthebox.ManCo.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import tv.mineinthebox.ManCo.configuration.configuration;
import tv.mineinthebox.ManCo.utils.iconomy;
import tv.mineinthebox.ManCo.utils.util;

public class moneyCheck implements Listener {

	public static String prefix = ChatColor.GREEN + "[ManCo]" + ChatColor.GOLD + ":money";

	@EventHandler
	public void onMoneyCheck(PlayerInteractEvent e) {
		if(configuration.isPluginDisabledForWorld(e.getPlayer().getWorld())) {
			e.getPlayer().sendMessage(ChatColor.RED + "you cannot use ManCo money in a disabled world!");
			return;
		}
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getItemInHand().getType() == Material.PAPER && e.getPlayer().getItemInHand() != null) {
				if(e.getItem().hasItemMeta()) {
					if(e.getItem().getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
						String[] args = e.getItem().getItemMeta().getDisplayName().split(" ");
						if(args[0].equals(prefix)) {
							if(util.isIconomyEnabled()) {
								try {
									Double money = Double.parseDouble(ChatColor.stripColor(args[1].replace("$", "")));
									if(iconomy.addMoney(e.getPlayer(), money)) {
										ItemStack item = new ItemStack(e.getItem());
										item.setAmount(item.getAmount() - 1);
										e.getPlayer().getInventory().setItemInHand(item);
										e.getPlayer().sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + "you successfully got " + money + "$ added to your economy bank!");
									}
								} catch(Exception r) {
									r.printStackTrace();
								}
							} else {
								e.getPlayer().sendMessage(ChatColor.GREEN + configuration.getPrefix() + ChatColor.GRAY + " iConomy or Vault is not installed!");
							}
						}
					}
				}
			}
		}
	}

}
