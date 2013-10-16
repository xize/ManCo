package tv.mineinthebox.ManCo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import tv.mineinthebox.ManCo.events.cratescheduler;
import tv.mineinthebox.ManCo.events.handler;
import tv.mineinthebox.ManCo.utils.vanish;

public class command implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("manco")) {
			if(sender.hasPermission("manco.command")) {
				if(args.length == 0) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						if(chestList.getCrateList.containsKey(p.getName()) || chestList.getCrateList2.containsKey(p.getName())) {
							sender.sendMessage(ChatColor.RED + "could not create a crate because you allready have a non used crate!");
							return false;
						}
						if(vanish.isVanished(p)) {
							sender.sendMessage(ChatColor.RED + "you cannot spawn a crate for yourself when you are vanished!");
							return false;
						}
						Location loc = p.getLocation();
						loc.setY(120);
						Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
						chestList.getFallingStateChest.put(entity, p.getName());
						Bukkit.broadcastMessage(ChatColor.GREEN + "[ManCo] " + ChatColor.GRAY + p.getName() + " found a ManCo crate!");
						sender.sendMessage(ChatColor.GREEN + "[ManCo] " + ChatColor.GRAY + "you've successfully spawned a crate for yourself!");
					} else {
						sender.sendMessage("a console cannot have a location !");
					}
				} else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("reload")) {
						cratescheduler.task.cancel();
						cratescheduler.task = null;
						cratescheduler.startScheduler();
						handler.restartListeners();
						sender.sendMessage(ChatColor.GREEN + "[ManCo] " + ChatColor.GRAY + "reload successfully");
					} else if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + ".oO___[ManCo supplycrates]___Oo.");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/manco " + ChatColor.WHITE + ": spawn a supplycrate for yourself");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/manco <player> " + ChatColor.WHITE + ": spawn a supplycrate for a player");
						sender.sendMessage(ChatColor.RED + "Admin: " + ChatColor.GRAY + "/manco reload " + ChatColor.WHITE + ": reloads the plugin");
					} else {
						Player p = Bukkit.getPlayer(args[0]);
						if(p instanceof Player) {
							if(chestList.getCrateList.containsKey(p.getName())) {
								sender.sendMessage(ChatColor.RED + "could not create a crate because this player has allready a non used crate!");
								return false;
							} else if(chestList.getCrateList2.containsKey(p.getName())) {
								sender.sendMessage(ChatColor.RED + "could not create a crate because this player has allready a non used crate!");
								return false;
							}
							if(vanish.isVanished(p)) {
								sender.sendMessage(ChatColor.RED + "you cannot spawn a crate for yourself when you are vanished!");
								return false;
							}
							Location loc = p.getLocation();
							loc.setY(120);
							Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
							chestList.getFallingStateChest.put(entity, p.getName());
							Bukkit.broadcastMessage(ChatColor.GREEN + "[ManCo] " + ChatColor.GRAY + p.getName() + " found a ManCo crate!");	
							sender.sendMessage(ChatColor.GREEN + "[ManCo] " + ChatColor.GRAY + "you've successfully spawned a ManCo crate for " + p.getName() + "!");
						} else {
							sender.sendMessage(ChatColor.RED + "this player is not online!");
						}	
					}
				}
			} else {
				sender.sendMessage(ChatColor.RED + "you don't have permission to use this command!");
			}
		}
		return false;
	}

}
