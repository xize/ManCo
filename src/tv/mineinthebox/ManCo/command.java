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
						Location loc = p.getLocation();
						loc.setY(120);
						Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
						chestList.getFallingStateChest.put(entity, p.getName());
						Bukkit.broadcastMessage(ChatColor.GREEN + "[ManCo] " + ChatColor.GRAY + p.getName() + " found a ManCo crate!");
					} else {
						sender.sendMessage("a console cannot have a location !");
					}
				} else if(args.length == 1) {
					Player p = Bukkit.getPlayer(args[0]);
					if(p instanceof Player) {
						if(chestList.getCrateList.containsKey(p.getName())) {
							sender.sendMessage(ChatColor.RED + "could not create a crate because this player has allready a non used crate!");
							System.out.print("this is crate 1");
							return false;
						} else if(chestList.getCrateList2.containsKey(p.getName())) {
							sender.sendMessage(ChatColor.RED + "could not create a crate because this player has allready a non used crate!");
							System.out.print("this is crate 2");
							return false;
						}
						Location loc = p.getLocation();
						loc.setY(120);
						Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
						chestList.getFallingStateChest.put(entity, p.getName());
						Bukkit.broadcastMessage(ChatColor.GREEN + "[ManCo] " + ChatColor.GRAY + p.getName() + " found a ManCo crate!");	
					} else {
						sender.sendMessage(ChatColor.RED + "this player is not online!");
					}
				}
			} else {
				sender.sendMessage(ChatColor.RED + "you don't have permission to use this command!");
			}
		}
		return false;
	}

}
