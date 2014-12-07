/*  1:   */ package com.xmailrage.procedures;
/*  2:   */ 
/*  3:   */ import com.xmailrage.net.MainClass;
/*  4:   */ import com.xmailrage.net.TeamManager;
/*  5:   */ import me.confuser.barapi.BarAPI;
/*  6:   */ import org.bukkit.Bukkit;
/*  7:   */ import org.bukkit.ChatColor;
/*  8:   */ import org.bukkit.Material;
/*  9:   */ import org.bukkit.entity.Player;
/* 10:   */ import org.bukkit.inventory.ItemStack;
/* 11:   */ import org.bukkit.inventory.PlayerInventory;
/* 12:   */ import org.bukkit.scheduler.BukkitScheduler;
/* 13:   */ 
/* 14:   */ public class GameStartProcedure
/* 15:   */ {
/* 16:   */   private MainClass main;
/* 17:   */   
/* 18:   */   public GameStartProcedure(MainClass c)
/* 19:   */   {
/* 20:21 */     this.main = c;
/* 21:   */   }
/* 22:   */   
/* 23:23 */   int time = 30;
/* 24:24 */   int proc = 0;
/* 25:   */   
/* 26:   */   public void start()
/* 27:   */   {
/* 28:26 */     this.proc = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.main, new Runnable()
/* 29:   */     {
/* 30:27 */       Player[] pc = Bukkit.getOnlinePlayers();
/* 31:   */       
/* 32:   */       public void run()
/* 33:   */       {
/* 34:34 */         if (GameStartProcedure.this.time <= 0)
/* 35:   */         {
/* 36:35 */           MainClass.inprogress = true;
/* 37:36 */           for (Player pp : Bukkit.getOnlinePlayers())
/* 38:   */           {
/* 39:37 */             TeamManager ck = new TeamManager(GameStartProcedure.this.main);
/* 40:   */             
						 main.starts();
/* 41:39 */             pp.getInventory().clear();
/* 42:40 */             pp.getInventory().setBoots(new ItemStack(Material.AIR));
/* 43:41 */             pp.getInventory().setLeggings(new ItemStack(Material.AIR));
/* 44:42 */             pp.getInventory().setHelmet(new ItemStack(Material.AIR));
/* 45:43 */             pp.getInventory().setChestplate(new ItemStack(Material.AIR));
/* 46:44 */             pp.updateInventory();
/* 47:46 */             if (!ck.inTeam(pp)) {
/* 48:48 */               ck.addRandom(pp);
/* 49:   */             }
/* 50:50 */             GameStartProcedure.this.main.started = true;
/* 51:51 */             BarAPI.removeBar(pp);
/* 52:   */           }
/* 53:54 */           Bukkit.broadcastMessage(ChatColor.AQUA + "The game has began!");
/* 54:55 */           Donator20StartProcedure st = new Donator20StartProcedure(GameStartProcedure.this.main);
/* 55:56 */           st.start();
/* 56:57 */           Bukkit.getScheduler().cancelTask(GameStartProcedure.this.proc);
/* 57:   */         }
/* 58:   */         else
/* 59:   */         {
/* 60:59 */           BarAPI.setMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "NSHC" + ChatColor.GRAY + " | " + ChatColor.AQUA + "The game is starting shortly!" + ChatColor.GREEN + " " + GameStartProcedure.this.ConvertInteger(GameStartProcedure.this.time));
/* 61:60 */           GameStartProcedure.this.time -= 1;
/* 62:   */         }
/* 63:   */       }
/* 64:66 */     }, 0L, 20L);
/* 65:   */   }
/* 66:   */   
/* 67:   */   public String ConvertInteger(int h)
/* 68:   */   {
/* 69:73 */     long s = h;
/* 70:   */     
/* 71:75 */     int rem = (int)(s % 3600L);
/* 72:76 */     int mn = rem / 60;
/* 73:77 */     int sec = rem % 60;
/* 74:78 */     String mnStr = (mn < 10 ? "0" : "") + mn;
/* 75:79 */     String secStr = (sec < 10 ? "0" : "") + sec;
/* 76:80 */     return mnStr + ":" + secStr + "s";
/* 77:   */   }
/* 78:   */ }


/* Location:           E:\_PluginWorkSpace\NSHC.jar
 * Qualified Name:     com.xmailrage.procedures.GameStartProcedure
 * JD-Core Version:    0.7.0.1
 */