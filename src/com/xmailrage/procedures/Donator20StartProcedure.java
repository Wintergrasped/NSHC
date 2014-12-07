/*  1:   */ package com.xmailrage.procedures;
/*  2:   */ 
/*  3:   */ import com.xmailrage.net.MainClass;
/*  4:   */ import me.confuser.barapi.BarAPI;
/*  5:   */ import org.bukkit.Bukkit;
/*  6:   */ import org.bukkit.ChatColor;
/*  7:   */ import org.bukkit.entity.Player;
/*  8:   */ import org.bukkit.scheduler.BukkitScheduler;
/*  9:   */ 
/* 10:   */ public class Donator20StartProcedure
/* 11:   */ {
/* 12:   */   private MainClass main;
/* 13:   */   
/* 14:   */   public Donator20StartProcedure(MainClass c)
/* 15:   */   {
/* 16:21 */     this.main = c;
/* 17:   */   }
/* 18:   */   
/* 19:23 */   int time = 20;
/* 20:24 */   int proc = 0;
/* 21:   */   
/* 22:   */   public void start()
/* 23:   */   {
/* 24:26 */     this.proc = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.main, new Runnable()
/* 25:   */     {
/* 26:27 */       Player[] pc = Bukkit.getOnlinePlayers();
/* 27:   */       
/* 28:   */       public void run()
/* 29:   */       {
/* 30:30 */         if (Donator20StartProcedure.this.time == 20) {
/* 31:31 */           for (Player s : this.pc)
/* 32:   */           {
/* 33:32 */             s.sendMessage(ChatColor.YELLOW + "Donator's have 20 seconds to select a team! /Team");
/* 34:33 */             MainClass.donatorteam = true;
/* 35:   */           }
/* 36:   */         }
/* 37:36 */         if (Donator20StartProcedure.this.time <= 0)
/* 38:   */         {
/* 39:37 */           MainClass.donatorteam = false;
/* 40:   */           
/* 41:   */ 
/* 42:40 */           Bukkit.broadcastMessage(ChatColor.AQUA + "The Donator period has expired!");
/* 43:41 */           for (Player op : Bukkit.getOnlinePlayers()) {
/* 44:42 */             BarAPI.removeBar(op);
/* 45:   */           }
/* 46:44 */           Bukkit.getScheduler().cancelTask(Donator20StartProcedure.this.proc);
/* 47:   */         }
/* 48:   */         else
/* 49:   */         {
/* 50:46 */           BarAPI.setMessage("" + ChatColor.DARK_AQUA + ChatColor.BOLD + "NSHC" + ChatColor.GRAY + " | " + ChatColor.AQUA + "Donators can change teams! /Team" + ChatColor.GREEN + " " + Donator20StartProcedure.this.ConvertInteger(Donator20StartProcedure.this.time));
/* 51:47 */           Donator20StartProcedure.this.time -= 1;
/* 52:   */         }
/* 53:   */       }
/* 54:53 */     }, 0L, 20L);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String ConvertInteger(int h)
/* 58:   */   {
/* 59:60 */     long s = h;
/* 60:   */     
/* 61:62 */     int rem = (int)(s % 3600L);
/* 62:63 */     int mn = rem / 60;
/* 63:64 */     int sec = rem % 60;
/* 64:65 */     String mnStr = (mn < 10 ? "0" : "") + mn;
/* 65:66 */     String secStr = (sec < 10 ? "0" : "") + sec;
/* 66:67 */     return mnStr + ":" + secStr + "s";
/* 67:   */   }
/* 68:   */ }


/* Location:           E:\_PluginWorkSpace\NSHC.jar
 * Qualified Name:     com.xmailrage.procedures.Donator20StartProcedure
 * JD-Core Version:    0.7.0.1
 */