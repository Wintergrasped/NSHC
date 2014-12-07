package com.xmailrage.procedures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.xmailrage.net.MainClass;

public class RandomEvents {
	
	public static Timer gametime;
	
	private MainClass main;
	
	public int EventID;
	
	


    Zombie zombie;
	public void starts () {
		start();
	}
	public void start () {
		
		EventID = getRandom(0,100);
		
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.main, new Runnable() {
            public void run() {
            		main.logger.info("Event ID = "+EventID);
            		Bukkit.broadcastMessage("EventID = "+EventID);
            	
            	if (EventID == 25) {
            		ZombieApoc();
            	}
            	if (EventID == 75) {
            		ZombieApoc();
            	}
            	if (EventID == 51) {
            		ZombieApoc();
            	}
            	if (EventID == 35) {
            		SkeletalArmy();
            	}
            	if (EventID == 38) {
            		SkeletalArmy();
            	}
            	if (EventID == 41) {
            		SkeletalArmy();
            	}
            	if (EventID == 90) {
            		SkeletalArmy();
            	}
            	if (EventID == 20) {
            		SkeletalArmy();
            	}
            	if (EventID == 15) {
            		wwa();
            	}
            	if (EventID == 27) {
            		wwa();
            	}
            	if (EventID == 75) {
            		wwa();
            	}
            	if (EventID == 95) {
            		wwa();
            	}
            	if (EventID == 77) {
            		wwa();
            	}
            	if (EventID == 99) {
            		wwa();
            	}
            	if (EventID == 82) {
            		wwa();
            	}
            	if (EventID == 62) {
            		SkeletalArmy();
            	}
            	if (EventID == 65) {
            		SkeletalArmy();
            	}
            	if (EventID == 53) {
            		SkeletalArmy();
            	}
            }
        }, 20L, 20L);
    }
    public int getRandom(int lower, int upper) {
        Random random = new Random();
        return random.nextInt((upper - lower) + 1) + lower;
    }
    
    public void manualtrigger (String s) {

    	if (s == "Zombie") {
    		this.ZombieApoc();
    	}
    	if (s == "Skeletal") {
    			this.SkeletalArmy();
    		}
    	if (s == "WWA") {
    		this.wwa();
    		}
    }

	
    
	
    
    
    
    
    public void ZombieApoc() {
    	for (Player pl : Bukkit.getOnlinePlayers()) {
    		Bukkit.broadcastMessage(ChatColor.BOLD+""+ChatColor.RED+"The Zombie Appocolypse Has Begun!");
    		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time night");
    		Location loc = pl.getLocation();
    		Zombie zombie = (Zombie) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.ZOMBIE);
    		Zombie zombie1 = (Zombie) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.ZOMBIE);
    		Zombie zombie2 = (Zombie) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.ZOMBIE);
    		Zombie zombie3 = (Zombie) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.ZOMBIE);
    		Zombie zombie4 = (Zombie) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.ZOMBIE);
    		Zombie zombie5 = (Zombie) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.ZOMBIE);
    		Zombie zombie6 = (Zombie) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.ZOMBIE);
    		Zombie zombie7 = (Zombie) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.ZOMBIE);
    		Zombie zombie8 = (Zombie) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.ZOMBIE);
    		Zombie zombie9 = (Zombie) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.ZOMBIE);
    		Zombie zombie0 = (Zombie) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.ZOMBIE); 		
    	}
    	
    }
    
   
    
    public void SkeletalArmy() {
    	for (Player pl : Bukkit.getOnlinePlayers()) {
    	Location loc = pl.getLocation();
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time night");
    	Bukkit.broadcastMessage(ChatColor.BOLD+""+ChatColor.RED+"The Skeletal Army Event Has Begun!");
    	Skeleton skeleton = (Skeleton) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.SKELETON);
    	Skeleton skeleton1 = (Skeleton) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.SKELETON);
    	Skeleton skeleton2 = (Skeleton) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.SKELETON);
    	Skeleton skeleton3 = (Skeleton) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.SKELETON);
    	Skeleton skeleton4 = (Skeleton) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.SKELETON);
    	Skeleton skeleton5 = (Skeleton) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.SKELETON);
    	Skeleton skeleton6 = (Skeleton) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.SKELETON);
    	Skeleton skeleton7 = (Skeleton) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.SKELETON);
    	Skeleton skeleton8 = (Skeleton) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.SKELETON);
    	Skeleton skeleton9 = (Skeleton) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.SKELETON);
    	Skeleton skeleton0 = (Skeleton) pl.getWorld().spawnEntity((Location) Arrays.asList(circle(loc, 4, 1, true, true, 1)), EntityType.SKELETON);    	
    	}
    	
    }
    
    
    
    
    public void wwa() {
    	for (Player pl : Bukkit.getOnlinePlayers()) {
    		Bukkit.broadcastMessage(ChatColor.BOLD+""+ChatColor.RED+"A WWA Event Has Been Triggerd!");
    		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time night");
    		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather storm");
    		pl.setHealth(2);
    		pl.setSaturation(0);
    		pl.setFoodLevel(2);
    		if (pl.hasPermission("nshc.vip")) {
    			pl.setHealth(20);
    		}
    	}	
    }
    
    
    
    
    
    
    
    
    
    
    public static List < Location > circle(Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List < Location > circleblocks = new ArrayList < Location > ();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx + r; x++)
            for (int z = cz - r; z <= cz + r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
     
        return circleblocks;
    }
	}
