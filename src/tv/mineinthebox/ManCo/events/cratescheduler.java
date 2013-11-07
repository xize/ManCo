package tv.mineinthebox.ManCo.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import tv.mineinthebox.ManCo.ManCo;
import tv.mineinthebox.ManCo.logType;
import tv.mineinthebox.ManCo.configuration.configuration;
import tv.mineinthebox.ManCo.utils.normalCrate;
import tv.mineinthebox.ManCo.utils.normalCrateList;
import tv.mineinthebox.ManCo.utils.rareCrate;
import tv.mineinthebox.ManCo.utils.rareCrateList;
import tv.mineinthebox.ManCo.utils.util;
import tv.mineinthebox.ManCo.utils.vanish;
import tv.mineinthebox.ManCo.utils.worldguard;

public class cratescheduler {

	public static BukkitTask task;
	public static BukkitTask task2;

	public static void startScheduler() {
		BukkitTask taskID = Bukkit.getScheduler().runTaskTimer(ManCo.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if(Bukkit.getOnlinePlayers().length > configuration.roundsPerTime()) {
					for(int i = 0; i < configuration.roundsPerTime(); i++) {
						if(util.isWorldGuardEnabled()) {
							doCrateWithWG();
							if(configuration.isDebugMode()) {
								ManCo.log("Type: normal scheduler", logType.debug);
								ManCo.log("scheduler status both needs to be null here: \n" + "isNull("+configuration.isNull(cratescheduler.task) + "), isNull(" + configuration.isNull(cratescheduler.task2)+")", logType.debug);
								ManCo.log("=[normalCrateList memory]=", logType.debug);
								ManCo.log("ID: chestLocations: " + normalCrateList.chestLocations.size() + " memory entries", logType.debug);
								ManCo.log("ID: getCrateList: " + normalCrateList.getCrateList.size() + " memory entries", logType.debug);
								ManCo.log("ID: getCrateList2: " + normalCrateList.getCrateList2.size() + " memory entries", logType.debug);
								ManCo.log("ID: getFallingStateChest: " + normalCrateList.getFallingStateChest.size() + " memory entries", logType.debug);
								ManCo.log("ID: ItemsFromChest: " + normalCrateList.ItemsFromChest.size()+ " memory entries", logType.debug);
								ManCo.log("ID: ListDataValues should not be get higher: " + normalCrateList.ListDataValues.size() + " memory entries", logType.debug);
								ManCo.log("ID: schedulerTime: " + normalCrateList.schedulerTime.size() + " memory entries", logType.debug);
							}
						} else {
							doCrateWithoutWG();
							if(configuration.isDebugMode()) {
								ManCo.log("Type: normal scheduler", logType.debug);
								ManCo.log("scheduler status both needs to be null here: \n" + "isNull("+configuration.isNull(cratescheduler.task) + "), isNull(" + configuration.isNull(cratescheduler.task2)+")", logType.debug);
								ManCo.log("=[normalCrateList memory]=", logType.debug);
								ManCo.log("ID: chestLocations: " + normalCrateList.chestLocations.size() + " memory entries", logType.debug);
								ManCo.log("ID: getCrateList: " + normalCrateList.getCrateList.size() + " memory entries", logType.debug);
								ManCo.log("ID: getCrateList2: " + normalCrateList.getCrateList2.size() + " memory entries", logType.debug);
								ManCo.log("ID: getFallingStateChest: " + normalCrateList.getFallingStateChest.size() + " memory entries", logType.debug);
								ManCo.log("ID: ItemsFromChest: " + normalCrateList.ItemsFromChest.size()+ " memory entries", logType.debug);
								ManCo.log("ID: ListDataValues should not be get higher: " + normalCrateList.ListDataValues.size() + " memory entries", logType.debug);
								ManCo.log("ID: schedulerTime: " + normalCrateList.schedulerTime.size() + " memory entries", logType.debug);
							}
						}
					}
				}
			}

		}, 1000, configuration.getTime());
		task = taskID;
	}

	public static void startRareScheduler() {
		BukkitTask taskID = Bukkit.getScheduler().runTaskTimer(ManCo.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if(Bukkit.getOnlinePlayers().length > configuration.roundsPerTime()) {
					for(int i = 0; i < configuration.roundsPerTime(); i++) {
						if(util.isWorldGuardEnabled()) {
							doRareCrateWithWG();
							if(configuration.isDebugMode()) {
								ManCo.log("Type: rare scheduler", logType.debug);
								ManCo.log("=[rareCrateList memory]=", logType.debug);
								ManCo.log("ID: chestLocations: " + rareCrateList.chestLocations.size() + " memory entries", logType.debug);
								ManCo.log("ID: getCrateList: " + rareCrateList.getCrateList.size() + " memory entries", logType.debug);
								ManCo.log("ID: getCrateList2: " + rareCrateList.getCrateList2.size() + " memory entries", logType.debug);
								ManCo.log("ID: getFallingStateChest: " + rareCrateList.getFallingStateChest.size() + " memory entries", logType.debug);
								ManCo.log("ID: ItemsFromChest: " + rareCrateList.ItemsFromChest.size() + " memory entries", logType.debug);
								ManCo.log("ID: rareCrates: " + rareCrateList.rareCrates.size() + " memory entries", logType.debug);
								ManCo.log("ID: schedulerTime: " + rareCrateList.schedulerTime.size() + " memory entries", logType.debug);
								ManCo.log("=[rareCrate]=", logType.debug);
								ManCo.log("ID: rareCrates loaded from config: " + rareCrate.getRareCrateList().size(), logType.debug);
							}
						} else {
							doRareCrateWithoutWG();
							if(configuration.isDebugMode()) {
								ManCo.log("Type: rare scheduler", logType.debug);
								ManCo.log("=[rareCrateList memory]=", logType.debug);
								ManCo.log("ID: chestLocations: " + rareCrateList.chestLocations.size() + " memory entries", logType.debug);
								ManCo.log("ID: getCrateList: " + rareCrateList.getCrateList.size() + " memory entries", logType.debug);
								ManCo.log("ID: getCrateList2: " + rareCrateList.getCrateList2.size() + " memory entries", logType.debug);
								ManCo.log("ID: getFallingStateChest: " + rareCrateList.getFallingStateChest.size() + " memory entries", logType.debug);
								ManCo.log("ID: ItemsFromChest: " + rareCrateList.ItemsFromChest.size() + " memory entries", logType.debug);
								ManCo.log("ID: rareCrates: " + rareCrateList.rareCrates.size() + " memory entries", logType.debug);
								ManCo.log("ID: schedulerTime: " + rareCrateList.schedulerTime.size() + " memory entries", logType.debug);
								ManCo.log("=[rareCrate]=", logType.debug);
								ManCo.log("ID: rareCrates loaded from config: " + rareCrate.getRareCrateList().size(), logType.debug);
							}
						}
					}
				}
			}

		}, 1015, configuration.getTime()+15);
		task2 = taskID;
	}

	public static void doRareCrateWithoutWG() {
		//lets crawl through all avaible rare chests!
		int maxRareCrates = rareCrate.getRareCrateList().size();
		Random rand = new Random();
		int RandomCrate = rand.nextInt(maxRareCrates) + 0;
		if(rareCrate.getRareCrateList().get(RandomCrate) != null) {
			//now lets do the chance!
			int getCrateChance = rareCrate.getRareCrateChance(rareCrate.getRareCrateList().get(RandomCrate));
			Random rand2 = new Random();
			int crateChance = rand2.nextInt(100) + 1;
			if(crateChance <= getCrateChance) {
				//chance success
				Random rarerand = new Random();
				int i = Bukkit.getOnlinePlayers().length;
				if(i > 0) {
					int playerID = rarerand.nextInt(i) + 0;
					Player p = Bukkit.getOnlinePlayers()[playerID];
					if(rareCrateList.rareCrates.containsKey(p.getName()) || util.isSlab(p.getLocation().getBlock()) || normalCrateList.getFallingStateChest.containsValue(p.getName()) || rareCrateList.getCrateList.containsKey(p.getName()) || rareCrateList.getCrateList2.containsKey(p.getName()) || normalCrateList.getCrateList.containsKey(p.getName()) || normalCrateList.getCrateList2.containsKey(p.getName()) || vanish.isVanished(p) || configuration.isPluginDisabledForWorld(p.getWorld())) {

						//player already has a crate, loop through all possible online players to see if they can get a crate.
						//then use the native method.

						for(Player p2 : Bukkit.getOnlinePlayers()) {
							if(!(rareCrateList.rareCrates.containsKey(p2.getName()) || util.isSlab(p.getLocation().getBlock()) || normalCrateList.getFallingStateChest.containsValue(p2.getName()) ||rareCrateList.getCrateList.containsKey(p2.getName()) || rareCrateList.getCrateList2.containsKey(p2.getName()) || normalCrateList.getCrateList.containsKey(p2.getName()) || normalCrateList.getCrateList2.containsKey(p2.getName()) || configuration.isPluginDisabledForWorld(p2.getWorld()) || vanish.isVanished(p2))) {
								if(!p2.getName().equalsIgnoreCase(p.getName())) {
									doRareCrateNative(p2, RandomCrate);
									break;
								}
							}
						}
					} else {

						//Random player doesn't have a crate so we can proceed the code:)

						Location loc = p.getLocation();
						if(configuration.spawnCrateNearby()) {
							Random randx = new Random();
							Random randz = new Random();
							if(configuration.spawnCrateNearbyRange() > 100) {
								ManCo.log("your random range from configuration option spawnCrateNearby is to big, max range value is: " + 100, logType.severe);
							} else {
								loc.setX(loc.getX() + (randx.nextDouble() + configuration.spawnCrateNearbyRange()));
								loc.setZ(loc.getZ() + (randz.nextDouble() + configuration.spawnCrateNearbyRange()));
								loc.setY(normalCrate.getCrateSpawnHeight(p));
								if(configuration.isDebugMode()) {
									ManCo.log("Type: doRareCrateWithoutWG", logType.debug);
									ManCo.log("Rare crate spawns at: x:"+loc.getX() + " y:"+loc.getY()+" z:"+loc.getZ()+ " and world: " + loc.getWorld().getName(), logType.debug);
								}
							}
						} else {
							loc.setY(normalCrate.getCrateSpawnHeight(p));	
						}
						loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
						Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
						rareCrateList.getFallingStateChest.put(entity, p.getName()+","+rareCrate.getRareCrateList().get(RandomCrate));	
						rareCrateList.rareCrates.put(p.getName(), rareCrate.getRareCrateList().get(RandomCrate));
						if(configuration.isCrateDropMessageDisabled()) {
							//do nothing
						} else {
							Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + rareCrate.getCrateFoundMessage(rareCrate.getRareCrateList().get(RandomCrate)).replace("%p", p.getName()));	
						}	
					}
				}
			}
		}
	}

	public static void doRareCrateWithWG() {
		//lets crawl through all avaible rare chests!
		int maxRareCrates = rareCrate.getRareCrateList().size();
		Random rand = new Random();
		int RandomCrate = rand.nextInt(maxRareCrates) + 0;
		if(rareCrate.getRareCrateList().get(RandomCrate) != null) {
			//now lets do the chance!
			int getCrateChance = rareCrate.getRareCrateChance(rareCrate.getRareCrateList().get(RandomCrate));
			Random rand2 = new Random();
			int crateChance = rand2.nextInt(100) + 0;
			if(crateChance <= getCrateChance) {
				//chance success
				Random rarerand = new Random();
				int i = Bukkit.getOnlinePlayers().length;
				if(i > 0) {
					int playerID = rarerand.nextInt(i) + 0;
					Player p = Bukkit.getOnlinePlayers()[playerID];
					if(rareCrateList.rareCrates.containsKey(p.getName()) || util.isSlab(p.getLocation().getBlock()) || normalCrateList.getFallingStateChest.containsValue(p.getName()) || rareCrateList.getCrateList.containsKey(p.getName()) || rareCrateList.getCrateList2.containsKey(p.getName()) || normalCrateList.getCrateList.containsKey(p.getName()) || normalCrateList.getCrateList2.containsKey(p.getName()) || !worldguard.canPlayerBuild(p) || vanish.isVanished(p) || configuration.isPluginDisabledForWorld(p.getWorld())) {
						//player already has a crate, loop through all possible online players to see if they can get a crate.
						//then use the native method.
						for(Player p2 : Bukkit.getOnlinePlayers()) {
							if(!(rareCrateList.rareCrates.containsKey(p2.getName()) || util.isSlab(p.getLocation().getBlock()) || normalCrateList.getFallingStateChest.containsValue(p2.getName()) || configuration.isPluginDisabledForWorld(p2.getWorld()) || rareCrateList.getCrateList.containsKey(p2.getName()) || rareCrateList.getCrateList2.containsKey(p2.getName()) || normalCrateList.getCrateList.containsKey(p2.getName()) || normalCrateList.getCrateList2.containsKey(p2.getName()) || vanish.isVanished(p2) || !worldguard.canPlayerBuild(p2))) {
								if(!p2.getName().equalsIgnoreCase(p.getName())) {
									doRareCrateNative(p2, RandomCrate);
									break;
								}
							}
						}
					} else {

						//Random player doesn't have a crate so we can proceed the code:)

						Location loc = p.getLocation();
						if(configuration.spawnCrateNearby()) {
								Random randx = new Random();
								Random randz = new Random();
								if(configuration.spawnCrateNearbyRange() > 100) {
									ManCo.log("your random range from configuration option spawnCrateNearby is to big, max range value is: " + 100, logType.severe);
								} else {
									loc.setX(loc.getX() + (randx.nextDouble() + configuration.spawnCrateNearbyRange()));
									loc.setZ(loc.getZ() + (randz.nextDouble() + configuration.spawnCrateNearbyRange()));
									loc.setY(normalCrate.getCrateSpawnHeight(p));
									if(configuration.isDebugMode()) {
										ManCo.log("Type: doRareCrateWithWG", logType.debug);
										ManCo.log("Rare crate spawns at: x:"+loc.getX() + " y:"+loc.getY()+" z:"+loc.getZ()+ " and world: " + loc.getWorld().getName(), logType.debug);
									}
								}
							
						} else {
							loc.setY(normalCrate.getCrateSpawnHeight(p));	
						}
						loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
						Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
						rareCrateList.getFallingStateChest.put(entity, p.getName()+","+rareCrate.getRareCrateList().get(RandomCrate));
						rareCrateList.rareCrates.put(p.getName(), rareCrate.getRareCrateList().get(RandomCrate));
						if(configuration.isCrateDropMessageDisabled()) {

						} else {
							Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + rareCrate.getCrateFoundMessage(rareCrate.getRareCrateList().get(RandomCrate)).replace("%p", p.getName()));	
						}
					}
				}
			}
		}
	}

	public static void doCrateWithoutWG() {
		Random rand = new Random();
		int i = Bukkit.getOnlinePlayers().length;
		if(i > 0) {
			int playerID = rand.nextInt(i) + 0;
			Player p = Bukkit.getOnlinePlayers()[playerID];
			if(rareCrateList.rareCrates.containsKey(p.getName()) || util.isSlab(p.getLocation().getBlock()) || normalCrateList.getFallingStateChest.containsValue(p.getName()) || configuration.isPluginDisabledForWorld(p.getWorld()) || normalCrateList.getCrateList.containsKey(p.getName()) || normalCrateList.getCrateList2.containsKey(p.getName()) || rareCrateList.getCrateList.containsKey(p.getName()) || rareCrateList.getCrateList2.containsKey(p.getName()) || vanish.isVanished(p)) {

				//player already has a crate, loop through all possible online players to see if they can get a crate.
				//then use the native method.

				for(Player p2 : Bukkit.getOnlinePlayers()) {
					if(!(rareCrateList.rareCrates.containsKey(p2.getName()) || util.isSlab(p.getLocation().getBlock()) || configuration.isPluginDisabledForWorld(p2.getWorld()) || normalCrateList.getFallingStateChest.containsValue(p2.getName()) || normalCrateList.getCrateList.containsKey(p2.getName()) || normalCrateList.getCrateList2.containsKey(p2.getName()) || rareCrateList.getCrateList.containsKey(p2.getName()) || rareCrateList.getCrateList2.containsKey(p2.getName()) || vanish.isVanished(p2))) {
						if(!p2.getName().equalsIgnoreCase(p.getName())) {
							doCrateNative(p2);
							break;
						}
					}
				}
			} else {

				//Random player doesn't have a crate so we can proceed the code:)

				Location loc = p.getLocation();
				if(configuration.spawnCrateNearby()) {
					
						Random randx = new Random();
						Random randz = new Random();
						if(configuration.spawnCrateNearbyRange() > 100) {
							ManCo.log("your random range from configuration option spawnCrateNearby is to big, max range value is: " + 100, logType.severe);
						} else {
							loc.setX(loc.getX() + (randx.nextDouble() + configuration.spawnCrateNearbyRange()));
							loc.setZ(loc.getZ() + (randz.nextDouble() + configuration.spawnCrateNearbyRange()));
							loc.setY(normalCrate.getCrateSpawnHeight(p));
							if(configuration.isDebugMode()) {
								ManCo.log("Type: doCrateWithoutWG", logType.debug);
								ManCo.log("crate spawns at: x:"+loc.getX() + " y:"+loc.getY()+" z:"+loc.getZ()+ " and world: " + loc.getWorld().getName(), logType.debug);
							}
						}
					
				} else {
					loc.setY(normalCrate.getCrateSpawnHeight(p));	
				}
				loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
				Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
				normalCrateList.getFallingStateChest.put(entity, p.getName());
				if(configuration.isCrateDropMessageDisabled()) {

				} else {
					Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + normalCrate.getCrateFoundMessage().replace("%p", p.getName()));	
				}
			}
		}
	}

	public static void doCrateWithWG() {
		Random rand = new Random();
		int i = Bukkit.getOnlinePlayers().length;
		if(i > 0) {
			int playerID = rand.nextInt(i) + 0;
			Player p = Bukkit.getOnlinePlayers()[playerID];
			if(rareCrateList.rareCrates.containsKey(p.getName()) || util.isSlab(p.getLocation().getBlock()) || normalCrateList.getFallingStateChest.containsValue(p.getName()) || configuration.isPluginDisabledForWorld(p.getWorld()) || rareCrateList.getFallingStateChest.containsValue(p.getName()) || normalCrateList.getCrateList.containsKey(p.getName()) || normalCrateList.getCrateList2.containsKey(p.getName()) || rareCrateList.getCrateList.containsKey(p.getName()) || rareCrateList.getCrateList2.containsKey(p.getName()) || vanish.isVanished(p) || !worldguard.canPlayerBuild(p)) {
				//player already has a crate, loop through all possible online players to see if they can get a crate.
				//then use the native method.
				for(Player p2 : Bukkit.getOnlinePlayers()) {
					if(!(rareCrateList.rareCrates.containsKey(p2.getName()) || util.isSlab(p.getLocation().getBlock()) || normalCrateList.getFallingStateChest.containsValue(p2.getName()) || configuration.isPluginDisabledForWorld(p2.getWorld()) || normalCrateList.getCrateList.containsKey(p2.getName()) || normalCrateList.getCrateList2.containsKey(p2.getName()) || rareCrateList.getCrateList.containsKey(p2.getName()) || rareCrateList.getCrateList2.containsKey(p2.getName()) || vanish.isVanished(p2) || !worldguard.canPlayerBuild(p2))) {
						if(!p2.getName().equalsIgnoreCase(p.getName())) {
							doCrateNative(p2);
							break;
						}
					}
				}
			} else {

				//Random player doesn't have a crate so we can proceed the code:)

				Location loc = p.getLocation();
				if(configuration.spawnCrateNearby()) {
						Random randx = new Random();
						Random randz = new Random();
						if(configuration.spawnCrateNearbyRange() > 100) {
							ManCo.log("your random range from configuration option spawnCrateNearby is to big, max range value is: " + 100, logType.severe);
						} else {
							loc.setX(loc.getX() + (randx.nextDouble() + configuration.spawnCrateNearbyRange()));
							loc.setZ(loc.getZ() + (randz.nextDouble() + configuration.spawnCrateNearbyRange()));
							loc.setY(normalCrate.getCrateSpawnHeight(p));
							if(configuration.isDebugMode()) {
								ManCo.log("Type: doCrateWithWG", logType.debug);
								ManCo.log("crate spawns at: x:"+loc.getX() + " y:"+loc.getY()+" z:"+loc.getZ()+ " and world: " + loc.getWorld().getName(), logType.debug);
							}
						}
					
				} else {
					loc.setY(normalCrate.getCrateSpawnHeight(p));	
				}
				loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
				Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
				normalCrateList.getFallingStateChest.put(entity, p.getName());
				if(configuration.isCrateDropMessageDisabled()) {

				} else {
					Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + normalCrate.getCrateFoundMessage().replace("%p", p.getName()));	
				}
			}
		}
	}

	public static void doCrateNative(Player p) {
		Location loc = p.getLocation();
		if(configuration.spawnCrateNearby()) {
				Random randx = new Random();
				Random randz = new Random();
				if(configuration.spawnCrateNearbyRange() > 100) {
					ManCo.log("your random range from configuration option spawnCrateNearby is to big, max range value is: " + 100, logType.severe);
				} else {
					loc.setX(loc.getX() + (randx.nextDouble() + configuration.spawnCrateNearbyRange()));
					loc.setZ(loc.getZ() + (randz.nextDouble() + configuration.spawnCrateNearbyRange()));
					loc.setY(normalCrate.getCrateSpawnHeight(p));
					if(configuration.isDebugMode()) {
						ManCo.log("Type: doCrateNative", logType.debug);
						ManCo.log("crate spawns at: x:"+loc.getX() + " y:"+loc.getY()+" z:"+loc.getZ()+ " and world: " + loc.getWorld().getName(), logType.debug);
					}
				}
		} else {
			loc.setY(normalCrate.getCrateSpawnHeight(p));	
		}
		loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
		Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
		normalCrateList.getFallingStateChest.put(entity, p.getName());
		if(configuration.isCrateDropMessageDisabled()) {

		} else {
			Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + normalCrate.getCrateFoundMessage().replace("%p", p.getName()));
		}
	}

	public static void doRareCrateNative(Player p, int RandomCrate) {
		Location loc = p.getLocation();
		if(configuration.spawnCrateNearby()) {
				Random randx = new Random();
				Random randz = new Random();
				if(configuration.spawnCrateNearbyRange() > 100) {
					ManCo.log("your random range from configuration option spawnCrateNearby is to big, max range value is: " + 100, logType.severe);
				} else {
					loc.setX(loc.getX() + (randx.nextDouble() + configuration.spawnCrateNearbyRange()));
					loc.setZ(loc.getZ() + (randz.nextDouble() + configuration.spawnCrateNearbyRange()));
					loc.setY(normalCrate.getCrateSpawnHeight(p));
					if(configuration.isDebugMode()) {
						ManCo.log("Type: doRareCrateNative", logType.debug);
						ManCo.log("Rare crate spawns at: x:"+loc.getX() + " y:"+loc.getY()+" z:"+loc.getZ()+ " and world: " + loc.getWorld().getName(), logType.debug);
					}
				}
		} else {
			loc.setY(normalCrate.getCrateSpawnHeight(p));	
		}
		//first make sure the chunk is loaded and not empty!
		loc.getWorld().refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
		Entity entity = p.getWorld().spawnFallingBlock(loc, Material.CHEST, (byte) 1);
		rareCrateList.getFallingStateChest.put(entity, p.getName()+","+rareCrate.getRareCrateList().get(RandomCrate));
		rareCrateList.rareCrates.put(p.getName(), rareCrate.getRareCrateList().get(RandomCrate));
		if(configuration.isCrateDropMessageDisabled()) {

		} else {
			Bukkit.broadcastMessage(ChatColor.GREEN + configuration.getPrefix() + rareCrate.getCrateFoundMessage(rareCrate.getRareCrateList().get(RandomCrate)).replace("%p", p.getName()));
		}
	}

}
