package com.xmailrage.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.MySQL;

































import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.xmailrage.mysql.Mysql;
import com.xmailrage.procedures.EndGameProcedure;
import com.xmailrage.procedures.PreGameStart;
import com.xmailrage.procedures.RandomEvents;


public class MainClass extends JavaPlugin implements Listener{
	
	 public Mysql MySQL = null;
	 
	 
	
	public static boolean inprogress = false;
	
	public static List<String> dead = DeathManager.dead;
	public static String winner = "";
	
	List<String> EventTirggerUserd;
	
	public final static Logger logger = Logger.getLogger("Minecraft");
	private FileConfiguration config = null;
	String Tag = ChatColor.GRAY + "[" + ChatColor.GOLD + "NSHC" + ChatColor.GRAY + "]";
	public static boolean donatorteam = false;
	public boolean started = true;
	public TeamManager Teams = new TeamManager(this);
	YamlConfiguration yaml = new YamlConfiguration();
	  PreGameStart s;
		Team Alpha = new Team(TeamTypes.Alpha, this);
		Team Bravo = new Team(TeamTypes.Bravo, this);
		Team Titan = new Team(TeamTypes.Titan, this);
		Team Trionic = new Team(TeamTypes.Trionic, this);
		Team Wolf = new Team(TeamTypes.Wolf, this);

		
		public static String SQLDB;
		public static String SQLIP;
		public static String SQLPORT;
		public static String SQLTABLE;
		public static String SQLUSER;
		public static String SQLPASS;
		

		private List<Player> TP;
		
		
		public static Timer gametime;
		
		public int EventID;
	    
	@SuppressWarnings("deprecation")
	public void onEnable() {
		
		
		String SQLDB;
		String SQLIP;
		String SQLPORT;
		String SQLTABLE;
		String SQLUSER;
		String SQLPASS;
		
		inprogress = false;
		
		if (config == null) {
			config = this.getConfig();
			config.addDefault("ServerInfo.Name", "Server1");
			config.addDefault("sql.IP", "212.83.161.167");
			config.addDefault("sql.Port", "3306");
			config.addDefault("sql.DBName", "winterec_gmod");
			config.addDefault("sql.Table", "NSHC_Points");
			config.addDefault("sql.User", "winterec_admin");
			config.addDefault("sql.Pass", "wowhead1");
			this.getConfig().options().copyDefaults(true);
			this.saveConfig();
		} else {
			config = this.getConfig();
		}
		
		SQLDB = config.getString("sql.DBName");
		SQLIP = config.getString("sql.IP");
		SQLPORT = config.getString("sql.Port");
		SQLTABLE = config.getString("sql.Table");
		SQLUSER = config.getString("sql.User");
		SQLPASS = config.getString("sql.Pass");
		
		MySQL = new Mysql(this, SQLIP, Integer.valueOf(SQLPORT), SQLDB, SQLUSER, SQLPASS);
	    MySQL.openConnection();
		
		if(new File(getDataFolder(), "deaths.yml").exists()){
			try {
				yaml.load(new File(getDataFolder(), "deaths.yml"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				yaml.save(new File(getDataFolder(), "deaths.yml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	

		if(!config.contains("WorldName")){
			config.set("WorldName", "world");
			saveConfig();
			World w = Bukkit.getWorld(config.getString("WorldName"));
			w.setAutoSave(false);
		}
		config.set("DeathP", null);
		config.set("DeathS", null);
		config.set("DeathL", null);
		
		config.set("DeathP2", null);
		config.set("DeathS2", null);
		config.set("DeathL2", null);
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has been enabled!");
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(this.team, this);
		getServer().getPluginManager().registerEvents(this.revive, this);
 
		DeathIDManager m = new DeathIDManager(null, this);
		m.reset();
		saveConfig();
      
       s = new PreGameStart(this);
        s.start();
           
        for(Player p: Bukkit.getOnlinePlayers()){
        	for(Player pp: Bukkit.getOnlinePlayers()){
        	p.showPlayer(pp);
        	}
        }
	}

	public void onQuit(PlayerQuitEvent e) {
		Death2Manager nm = new Death2Manager(e.getPlayer(), this);
		nm.setDead(true);
		Player damagedp = e.getPlayer();
        damagedp.setHealth(20);
        damagedp.setFoodLevel(20);
        ironmancheck();
       
        damagedp.getInventory().clear();
        damagedp.getInventory().setBoots(new ItemStack(Material.AIR));
        damagedp.getInventory().setLeggings(new ItemStack(Material.AIR));
        damagedp.getInventory().setHelmet(new ItemStack(Material.AIR));
        damagedp.getInventory().setChestplate(new ItemStack(Material.AIR));
        damagedp.updateInventory();
        Player p = damagedp;
        p.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).setType(Material.GRAVEL);
        p.getLocation().getBlock().getRelative(BlockFace.DOWN).setType(Material.GRAVEL);
        p.getLocation().getBlock().getRelative(BlockFace.NORTH).setType(Material.SIGN_POST);
       
       
        Sign s = (Sign)         p.getLocation().getBlock().getRelative(BlockFace.NORTH).getState();
        org.bukkit.material.Sign matSign =  new org.bukkit.material.Sign(Material.SIGN_POST);
        matSign.setFacingDirection(BlockFace.SOUTH); // TODO edit to what you want
        s.setData(matSign);
       


        //TODO:remove location
        s.setLine(0, ChatColor.DARK_RED + "R.I.P");
        s.setLine(1, ChatColor.AQUA + p.getName());
        s.setLine(2, ChatColor.DARK_RED + "Death #:");
        DeathIDManager m = new DeathIDManager(p, this);
        int x = m.getPlayerDeaths(p);
       
        x++;
        s.setLine(3, ChatColor.AQUA + "" + x);
        m.setPlayerDeaths(p, x);
        s.update();
       
        DeathManager d = new DeathManager(p, this);
        SignManager sd = new SignManager(p, s.getLocation(), this);
        sd.addSign();
        d.setDead(true);
        damagedp.teleport(s.getLocation());
        Bukkit.broadcastMessage(Tag + ChatColor.AQUA + damagedp.getName() + ChatColor.DARK_PURPLE + " Loged out D: ");
	int	pc = Bukkit.getOnlinePlayers().length;
		if (pc <= 1) {
			EndGameProcedure EG = new EndGameProcedure(main);
			EG.start();
		}
		
	}
	
	@EventHandler
    public void onLogin(PlayerLoginEvent e) {
	if (inprogress == true) {
       e.getPlayer().kickPlayer("DO NOT ENTER! Game In Progress");
      String ServerName = config.getString("ServerInfo.Name");
	}
    }
	
	@EventHandler
	public void onleave(PlayerQuitEvent e){
		e.setQuitMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "-" + ChatColor.GRAY + "]" + ChatColor.AQUA + " " + e.getPlayer().getName() + " has left the server!");
	}
	@EventHandler
    public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "+" + ChatColor.GRAY + "]" + ChatColor.AQUA + " " + e.getPlayer().getName() + " has joined the server!");
	if (inprogress == true) { 
       e.getPlayer().kickPlayer("DO NOT ENTER! Game In Progress");
      
	}
    }
	
	@EventHandler
	public void death(PlayerDeathEvent e){

		
 Player damagedp = e.getEntity();
 Death2Manager nm = new Death2Manager(damagedp, this);
 if(nm.isDead() == false){
                 damagedp.setHealth(20);
         damagedp.setFoodLevel(20);
        
         nm.setDead(true);
         ironmancheck();
        
         damagedp.getInventory().clear();
         damagedp.getInventory().setBoots(new ItemStack(Material.AIR));
         damagedp.getInventory().setLeggings(new ItemStack(Material.AIR));
         damagedp.getInventory().setHelmet(new ItemStack(Material.AIR));
         damagedp.getInventory().setChestplate(new ItemStack(Material.AIR));
         damagedp.updateInventory();
         Player p = damagedp;
         p.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).setType(Material.GRAVEL);
         p.getLocation().getBlock().getRelative(BlockFace.DOWN).setType(Material.GRAVEL);
         p.getLocation().getBlock().getRelative(BlockFace.NORTH).setType(Material.SIGN_POST);
        
        
         Sign s = (Sign)         p.getLocation().getBlock().getRelative(BlockFace.NORTH).getState();
         org.bukkit.material.Sign matSign =  new org.bukkit.material.Sign(Material.SIGN_POST);
         matSign.setFacingDirection(BlockFace.SOUTH); // TODO edit to what you want
         s.setData(matSign);
        


         //TODO:remove location
         s.setLine(0, ChatColor.DARK_RED + "R.I.P");
         s.setLine(1, ChatColor.AQUA + p.getName());
         s.setLine(2, ChatColor.DARK_RED + "Death #:");
         DeathIDManager m = new DeathIDManager(p, this);
         int x = m.getPlayerDeaths(p);
        
         x++;
         s.setLine(3, ChatColor.AQUA + "" + x);
         m.setPlayerDeaths(p, x);
         s.update();
        
         DeathManager d = new DeathManager(p, this);
         SignManager sd = new SignManager(p, s.getLocation(), this);
         sd.addSign();
         d.setDead(true);
         damagedp.teleport(s.getLocation());
         Bukkit.broadcastMessage(Tag + ChatColor.AQUA + damagedp.getName() + ChatColor.DARK_PURPLE + " was killed!");
 }
         
	}
	
	@EventHandler
    public void onDeath(EntityDeathEvent e) {
		if (inprogress == true) {
		      String ServerName = config.getString("ServerInfo.Name");
			}
		ironmancheck();
		int pointst = 0;
		
		this.logger.info("On Death Event Triggerd!" + e.getEntityType().toString());
		if(e.getEntityType() == EntityType.PLAYER){
			this.logger.info("On Death Event Triggerpd!");
	    Death2Manager nm = new Death2Manager((Player) e.getEntity(), this);
        if(nm.isDead() == false){
        	nm.setDead(true);
        	Player damagedp = (Player) e.getEntity();
            damagedp.setHealth(20);
            damagedp.setFoodLevel(20);
            ironmancheck();
           
            damagedp.getInventory().clear();
            damagedp.getInventory().setBoots(new ItemStack(Material.AIR));
            damagedp.getInventory().setLeggings(new ItemStack(Material.AIR));
            damagedp.getInventory().setHelmet(new ItemStack(Material.AIR));
            damagedp.getInventory().setChestplate(new ItemStack(Material.AIR));
            damagedp.updateInventory();
            Player p = damagedp;
            p.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).setType(Material.GRAVEL);
            p.getLocation().getBlock().getRelative(BlockFace.DOWN).setType(Material.GRAVEL);
            p.getLocation().getBlock().getRelative(BlockFace.NORTH).setType(Material.SIGN_POST);
           
           
            Sign s = (Sign)         p.getLocation().getBlock().getRelative(BlockFace.NORTH).getState();
            org.bukkit.material.Sign matSign =  new org.bukkit.material.Sign(Material.SIGN_POST);
            matSign.setFacingDirection(BlockFace.SOUTH); // TODO edit to what you want
            s.setData(matSign);
           
   
   
            //TODO:remove location
            s.setLine(0, ChatColor.DARK_RED + "R.I.P");
            s.setLine(1, ChatColor.AQUA + p.getName());
            s.setLine(2, ChatColor.DARK_RED + "Death #:");
            DeathIDManager m = new DeathIDManager(p, this);
            int x = m.getPlayerDeaths(p);
           
            x++;
            s.setLine(3, ChatColor.AQUA + "" + x);
            m.setPlayerDeaths(p, x);
            s.update();
           
            DeathManager d = new DeathManager(p, this);
            SignManager sd = new SignManager(p, s.getLocation(), this);
            sd.addSign();
            d.setDead(true);
            damagedp.teleport(s.getLocation());
            Bukkit.broadcastMessage(Tag + ChatColor.AQUA + damagedp.getName() + ChatColor.DARK_PURPLE + " was killed by " + damagedp.getKiller().getName());
        }
		}
		if(e.getEntityType() == EntityType.WITHER  || e.getEntityType() == EntityType.ENDER_DRAGON){
			this.logger.info("A Boss Has Been Killed!");
			if(e.getEntity().getKiller() instanceof Player){
				this.logger.info("Game Has Ended");
				Player p = e.getEntity().getKiller();
				winner = p.getName() + " [" + this.Teams.getTeam(p).getTeamName() + "] has won the game!";
				Bukkit.broadcastMessage(ChatColor.DARK_RED + "!!Notice!!" + ChatColor.AQUA + " " + p.getName() + " [" + this.Teams.getTeam(p).getTeamName() + "] has won the game!" + ChatColor.DARK_RED + "!!Notice!!");
				EndGameProcedure m = new EndGameProcedure(this);
				
		        for (Player tps : this.Teams.getTeam(p).getPlayers()) {
		        	
	                	
	                	int x = MySQL.getTokens(tps.getName());
	                	x++;
	                	MySQL.setTokens(tps.getName(), x);
					
	                	tps.sendMessage(ChatColor.AQUA + "You have been awarded a token!");
		        }
		     
			    m.start();
			}
		}
	}
	
	public void onDisable() {
		//IGNORE BELOW
		//IF NOT EXISTS (SELECT points FROM NSHC_Points WHERE name='Wintergrasped') INSERT INTO NSHC_Points VALUES 'Wintergrasped',0;
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has been disabled!");		
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
             //   p.kickPlayer("Game Over! "+ p.getName() + " [" + this.Teams.getTeam(p).getTeamName() + "] has won the game!");
            }
    	Bukkit.getServer().unloadWorld("world", true);
        File world_files = new File("world/");
        world_files.delete();
        ironmancheck();
	}
	
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent e){
		if (inprogress == true) {
		      String ServerName = config.getString("ServerInfo.Name");
			}
		Player p = e.getPlayer();
		if(Teams.inTeam(p) == true){
			e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + Teams.getTeam(p).getTeamName() + ChatColor.DARK_GRAY + "] "+ e.getFormat());
		}else{
			e.setFormat(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + "" + ChatColor.BOLD + "Guest" + ChatColor.DARK_GRAY + "] "+ e.getFormat());
		}
		ironmancheck();
	}
	//Damage
	
		@EventHandler
		public void onEntityDamage(EntityDamageEvent e){
			if (inprogress == true) {
			      String ServerName = config.getString("ServerInfo.Name");
				}
			ironmancheck();
            if(e instanceof EntityDamageByEntityEvent){
                EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
             
                        Entity damaged = event.getEntity();
                        Entity damager = event.getDamager();
                   
                    
                        ironmancheck();
                            if(damager instanceof Player){
                                    Player shooter = (Player) damager;
                                   
                                    ironmancheck(); 
                                   
                                    //shooter.sendMessage(".");
                                    if(damaged instanceof Player){
                                    	ironmancheck();
                                            Player damagedp = (Player) damaged;
                                      
                                            double damagedpHealth = damagedp.getHealth();
                                            DeathIDManager mk = new DeathIDManager(damagedp, this);
                                            ironmancheck();
                                            if(mk.getPlayerDeaths(damagedp) <= 6){
                                            	TeamManager mn = new TeamManager(this);
                                            	if(!mn.getTeam(damagedp).getTeamType().equals(mn.getTeam(shooter).getTeamType())){
                                   
                                    if(damagedpHealth <= event.getDamage()){
                                        Death2Manager nm = new Death2Manager((Player) damaged, this);
                                        if(nm.isDead() == false){
                                        	nm.setDead(true);
                                            DeathManager da = new DeathManager(shooter, this);
                                                    if(da.isDead() == false){
                                                            damagedp.setHealth(20);
                                                    damagedp.setFoodLevel(20);
                                                    ironmancheck();
                                                   
                                                    damagedp.getInventory().clear();
                                                    damagedp.getInventory().setBoots(new ItemStack(Material.AIR));
                                                    damagedp.getInventory().setLeggings(new ItemStack(Material.AIR));
                                                    damagedp.getInventory().setHelmet(new ItemStack(Material.AIR));
                                                    damagedp.getInventory().setChestplate(new ItemStack(Material.AIR));
                                                    damagedp.updateInventory();
                                                    Player p = damagedp;
                                                    p.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).setType(Material.GRAVEL);
                                                    p.getLocation().getBlock().getRelative(BlockFace.DOWN).setType(Material.GRAVEL);
                                                    p.getLocation().getBlock().getRelative(BlockFace.NORTH).setType(Material.SIGN_POST);
                                                   
                                                   
                                                    Sign s = (Sign)         p.getLocation().getBlock().getRelative(BlockFace.NORTH).getState();
                                                    org.bukkit.material.Sign matSign =  new org.bukkit.material.Sign(Material.SIGN_POST);
                                                    matSign.setFacingDirection(BlockFace.SOUTH); // TODO edit to what you want
                                                    s.setData(matSign);
                                                   
                                           
                                           
                                                    //TODO:remove location
                                                    s.setLine(0, ChatColor.DARK_RED + "R.I.P");
                                                    s.setLine(1, ChatColor.AQUA + p.getName());
                                                    s.setLine(2, ChatColor.DARK_RED + "Death #:");
                                                    DeathIDManager m = new DeathIDManager(p, this);
                                                    int x = m.getPlayerDeaths(p);
                                                   
                                                    x++;
                                                    s.setLine(3, ChatColor.AQUA + "" + x);
                                                    m.setPlayerDeaths(p, x);
                                                    s.update();
                                                   
                                                    DeathManager d = new DeathManager(p, this);
                                                    SignManager sd = new SignManager(p, s.getLocation(), this);
                                                    sd.addSign();
                                                    d.setDead(true);
                                                    damagedp.teleport(s.getLocation());
                                                    Bukkit.broadcastMessage(Tag + ChatColor.AQUA + damagedp.getName() + ChatColor.DARK_PURPLE + " was killed by " + shooter.getName());
                                                    }else{
                                                            e.setCancelled(true);
                                                    }
                                    }
                                           
                                   
                                    }
                                            	}
                                   
                                            }
                                    }
                            }else{
                                    if(damaged instanceof Player){
                                    	ironmancheck();
                                            Player damagedp = (Player) damaged;
                                           
                                            DeathIDManager mk = new DeathIDManager(damagedp, this);
                                            if(mk.getPlayerDeaths(damagedp) <= 6){
                                   
                                    if(damagedp.getHealth() <= event.getDamage()){
                                    	 Death2Manager nm = new Death2Manager((Player) damaged, this);
                                         if(nm.isDead() == false){
                                         	nm.setDead(true);
                                    	ironmancheck();
                                           
                                                            damagedp.setHealth(20);
                                                    damagedp.setFoodLevel(20);
                                                   
                                                   
                                                    damagedp.getInventory().clear();
                                                    damagedp.getInventory().setBoots(new ItemStack(Material.AIR));
                                                    damagedp.getInventory().setLeggings(new ItemStack(Material.AIR));
                                                    damagedp.getInventory().setHelmet(new ItemStack(Material.AIR));
                                                    damagedp.getInventory().setChestplate(new ItemStack(Material.AIR));
                                                    damagedp.updateInventory();
                                                    Player p = damagedp;
                                                    p.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH).setType(Material.GRAVEL);
                                                    p.getLocation().getBlock().getRelative(BlockFace.DOWN).setType(Material.GRAVEL);
                                                    p.getLocation().getBlock().getRelative(BlockFace.NORTH).setType(Material.SIGN_POST);
                                                   
                                                   
                                                    Sign s = (Sign)         p.getLocation().getBlock().getRelative(BlockFace.NORTH).getState();
                                                    org.bukkit.material.Sign matSign =  new org.bukkit.material.Sign(Material.SIGN_POST);
                                                    matSign.setFacingDirection(BlockFace.SOUTH); // TODO edit to what you want
                                                    s.setData(matSign);
                                                   
                                           
                                           
                                                    //TODO:remove location
                                                    s.setLine(0, ChatColor.DARK_RED + "R.I.P");
                                                    s.setLine(1, ChatColor.AQUA + p.getName());
                                                    s.setLine(2, ChatColor.DARK_RED + "Death #:");
                                                    DeathIDManager m = new DeathIDManager(p, this);
                                                    int x = m.getPlayerDeaths(p);
                                                   
                                                    x++;
                                                    s.setLine(3, ChatColor.AQUA + "" + x);
                                                    m.setPlayerDeaths(p, x);
                                                    s.update();
                                                   
                                                    DeathManager d = new DeathManager(p, this);
                                                    SignManager sd = new SignManager(p, s.getLocation(), this);
                                                    sd.addSign();
                                                    try {
                                                    d.setDead(true);
                                                    }catch(NullPointerException e1){
                                                    }
                                                    damagedp.teleport(s.getLocation());
                                                    Bukkit.broadcastMessage(Tag + ChatColor.AQUA + damagedp.getName() + ChatColor.DARK_PURPLE + " was killed!");
                                                   
                                    }
                                   
                                    }
                                            }
                                    }
                                    }
                            
                       
        }
            }
		
		@EventHandler
		public void inte(PlayerInteractEvent e){
			if (inprogress == true) {
			      String ServerName = config.getString("ServerInfo.Name");
				}
			ironmancheck();
			Player p = e.getPlayer();
			boolean cont = false;
		
		
			DeathManager d = new DeathManager(p, this);
			if(d.isDead() == false){
				
			
			}
			if (d.isDead() == true){
			
				
				e.setCancelled(true);
			}
		}
		
		public MainClass main = this;
	    IconMenu revive = new IconMenu(ChatColor.AQUA + "Revive" + ChatColor.BOLD + "!", 9, new IconMenu.OptionClickEventHandler() {
	        public void onOptionClick(IconMenu.OptionClickEvent event) {
	        	Player p = event.getPlayer();
	           String kiled = event.getItem().getItemMeta().getLore().get(0);
	           Player killed = Bukkit.getPlayer(kiled);
	   		DeathIDManager m = new DeathIDManager(killed, main);
	            int amount = event.getItem().getAmount();
	            if(p.getInventory().contains(event.getItem().getType())){
	            	ItemStack one = event.getItem();
	            	one.setAmount(1);
	            	m.addItem(killed, one);
	            	boolean ad = false;
	            	for(ItemStack s : p.getInventory().getContents()){
	            		if(s.getType().equals(one.getType())){
	            			if(ad == false){
	            			if(s.getAmount() == 1){
	            				p.getInventory().remove(s);
	            			}else{
	            				int x = s.getAmount();
	            				x--;
	            				p.getInventory().remove(s);
	            			    s.setAmount(x);
	            			    p.getInventory().addItem(s);
	            			    ad=true;
	            			}
	            		}
	            		}
	            	}
	            	p.sendMessage(ChatColor.GOLD + "You have added 1 x " + one.getType().toString());
	            }else{
	            	p.sendMessage(ChatColor.GOLD + "You do not have any of this item!");
	            }
	            saveConfig();
	            event.setWillClose(true);
	        }
	    }, this);
	    
	    IconMenu team = new IconMenu(ChatColor.AQUA + "" + ChatColor.BOLD + "Pick your Team!", 9, new IconMenu.OptionClickEventHandler() {
	        public void onOptionClick(IconMenu.OptionClickEvent event) {
	        	Player p = event.getPlayer();
	            if(event.getPosition() == 1){
	              Teams.getAlpha().forceaddPlayer(p);
	            }else if(event.getPosition() == 2){
	            	 Teams.getBravo().forceaddPlayer(p);
	            }else if(event.getPosition() == 3){
	            	 Teams.getTitan().forceaddPlayer(p);
		        }else if(event.getPosition() == 4){
		        	 Teams.getTrionic().forceaddPlayer(p);
		        }else if(event.getPosition() == 5){
		        	 Teams.getWolf().forceaddPlayer(p);
		        }
	            
	            saveConfig();
	            event.setWillClose(true);
	        }
	    }, this)
	    .setOption(1, new ItemStack(Material.BEACON, 1), ChatColor.YELLOW + "Alpha", "Click to join Alpha!")
	    .setOption(2, new ItemStack(Material.GOLD_BLOCK, 1), ChatColor.YELLOW + "Bravo", "Click to join Bravo!")
	    .setOption(3, new ItemStack(Material.GOLD_NUGGET, 1), ChatColor.YELLOW + "Titan", "Click to join Titan!")
	    .setOption(4, new ItemStack(Material.BLAZE_POWDER, 1), ChatColor.YELLOW + "Trionic", "Click to join Trionic!")
	    .setOption(5, new ItemStack(Material.TNT, 1), ChatColor.YELLOW + "Wolf", "Click to join Wolf!");
		  


			@SuppressWarnings("deprecation")
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
			String named = sender.getName();
			@SuppressWarnings("deprecation")
			Player damagedp = Bukkit.getPlayerExact(named);
			
			
			if(cmd.getName().equalsIgnoreCase("Team")){
			if(this.donatorteam == true){
				team.open(damagedp);
			}else{
				damagedp.sendMessage(ChatColor.YELLOW + "The donator team period is not in progress! ");
			}
		
		}
			if(cmd.getName().equalsIgnoreCase("Revive")){
				DeathManager da = new DeathManager(damagedp, this);
				if(!da.isDead()){
				if(damagedp.getTargetBlock(null, 40).getType() != null){
				if(damagedp.getTargetBlock(null, 40).getType().equals(Material.SIGN_POST) || damagedp.getTargetBlock(null, 40).getType().equals(Material.WALL_SIGN)){
				for(String k: config.getStringList("DeathL")){
					World w = Bukkit.getWorld(config.getString("DeathS." + k + ".world"));
					Double xa = config.getDouble("DeathS." + k + ".x");
					Double ya = config.getDouble("DeathS." + k + ".y");
					Double za = config.getDouble("DeathS." + k + ".z");
					Location loc = new Location(w, xa, ya, za);
					if(damagedp.getTargetBlock(null, 50).getLocation().equals(loc)){
						Player killed = Bukkit.getPlayer(k);
						int x = 0;
						DeathManager d = new DeathManager(damagedp, this);
						DeathIDManager m = new DeathIDManager(killed, this);
						for(ItemStack s: m.getItemsLefts(killed)){
							
						revive.setOption(x, s, ChatColor.RED + "" + s.getType().toString(), killed.getName());
					  
						x++;
						}
						revive.open(damagedp);
					}
				
			}
				}
				}}else{
					damagedp.sendMessage(ChatColor.RED + "You are dead and can not revive people!");}
				}
			
			return false;
			}
			
		public void ironmancheck() {
}
	    Zombie zombie;
		public void starts () {
			start();
		}
		public void start () {
			
			
	        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.main, new Runnable() {
	            public void run() {
	            		int EventID = (int) (Math.random() * 100);
	            		main.logger.info("Event ID = "+EventID);
	            	
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
	        }, 1200L, 1200L);
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
	    	int adds = (int) (Math.random() * 10);
	    	int l = adds - 5;
	    	for (Player pl : Bukkit.getOnlinePlayers()) {
	    		Bukkit.broadcastMessage(ChatColor.BOLD+""+ChatColor.RED+"The Zombie Appocolypse Has Begun!");
	    		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time night");
	    		Location loc = pl.getLocation();
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);  
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);  
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);  
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);  
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);  
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE); 
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);  
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);  
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);  
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);  
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);  
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE); 
		    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.ZOMBIE);
	    	}
	    	
	    }
	    
	   
	    
	    public void SkeletalArmy() {
	    	int adds = (int) (Math.random() * 10);
	    	int l = adds - 5;
	    	for (Player pl : Bukkit.getOnlinePlayers()) {
	    	Location loc = pl.getLocation();
	    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time night");
	    	Bukkit.broadcastMessage(ChatColor.BOLD+""+ChatColor.RED+"The Skeletal Army Event Has Begun!");
	    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.SKELETON);
	    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.SKELETON);  
	    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.SKELETON);  
	    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.SKELETON);  
	    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.SKELETON);  
	    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.SKELETON);
	    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.SKELETON);  
	    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.SKELETON); 
	    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.SKELETON);
	    	pl.getWorld().spawnEntity(loc.add(ls(), 0, ls()), EntityType.SKELETON);
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
	    	}	
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    public int ls () {
	    	int adds = (int) (Math.random() * 10);
	    	int ls = adds - 5; 	
			return ls;
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