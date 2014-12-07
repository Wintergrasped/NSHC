/*  1:   */ package com.xmailrage.procedures;
/*  2:   */ 
/*  3:   */ import com.xmailrage.net.MainClass;
/*  4:   */ import lib.PatPeter.SQLibrary.MySQL;
/*  5:   */ import me.confuser.barapi.BarAPI;
/*  6:   */ import org.bukkit.Bukkit;
/*  7:   */ import org.bukkit.ChatColor;
/*  8:   */ import org.bukkit.Server;
/*  9:   */ import org.bukkit.configuration.file.FileConfiguration;
/* 10:   */ import org.bukkit.entity.Player;
/* 11:   */ import org.bukkit.scheduler.BukkitScheduler;
/* 12:   */ 
/* 13:   */ public class EndGameProcedure
/* 14:   */ {
/* 15:   */   public MySQL sql;
/* 16:   */   public static String ServerName;
/* 17:   */   public FileConfiguration config;
/* 18:   */   private MainClass main;
/* 19:   */   
/* 20:   */   public EndGameProcedure(MainClass c)
/* 21:   */   {
/* 22:29 */     this.main = c;
/* 23:   */   }
/* 24:   */   
/* 25:31 */   int time = 15;
/* 26:32 */   int proc = 0;
/* 27:   */   
/* 28:   */   public void start()
/* 29:   */   {
/* 30:36 */     this.config = this.main.getConfig();
/* 31:37 */     this.proc = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.main, new Runnable()
/* 32:   */     {
/* 33:   */       public void run()
/* 34:   */       {
/* 35:41 */         if (EndGameProcedure.this.time <= 0)
/* 36:   */         {
/* 37:43 */           EndGameProcedure.this.config = EndGameProcedure.this.main.getConfig();
/* 38:44 */           EndGameProcedure.ServerName = EndGameProcedure.this.config.getString("ServerInfo.Name");
/* 39:46 */           for (Player p : Bukkit.getOnlinePlayers()) {
/* 40:47 */             p.kickPlayer("" + ChatColor.AQUA + ChatColor.BOLD + "NSHC " + ChatColor.YELLOW + "The game is over!");
/* 41:   */           }
/* 42:50 */           Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "wr reset now");
/* 43:   */         }
/* 44:   */         else
/* 45:   */         {
/* 46:53 */           BarAPI.setMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "NSHC" + ChatColor.GRAY + " | " + ChatColor.AQUA + "Game will be ending in..." + ChatColor.GREEN + " " + EndGameProcedure.this.ConvertInteger(EndGameProcedure.this.time));
/* 47:54 */           EndGameProcedure.this.time -= 1;
/* 48:   */         }
/* 49:   */       }
/* 50:60 */     }, 0L, 20L);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String ConvertInteger(int h)
/* 54:   */   {
/* 55:67 */     long s = h;
/* 56:   */     
/* 57:69 */     int rem = (int)(s % 3600L);
/* 58:70 */     int mn = rem / 60;
/* 59:71 */     int sec = rem % 60;
/* 60:72 */     String mnStr = (mn < 10 ? "0" : "") + mn;
/* 61:73 */     String secStr = (sec < 10 ? "0" : "") + sec;
/* 62:74 */     return mnStr + ":" + secStr + "s";
/* 63:   */   }
/* 64:   */ }


/* Location:           E:\_PluginWorkSpace\NSHC.jar
 * Qualified Name:     com.xmailrage.procedures.EndGameProcedure
 * JD-Core Version:    0.7.0.1
 */