package com.xmailrage.procedures;

import java.util.Scanner;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.xmailrage.net.MainClass;

public class PreGameStart {

	private MainClass main;
	public PreGameStart(MainClass c){
		main = c;
	}
	int time = 120;
    int proc = 0;
    public int pc = 0;
	public void start(){
		proc = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable(){
			
			public void run(){
				pc = Bukkit.getOnlinePlayers().length;
			
				    if(time <= 0){
				  	if (pc <= 1) {
				    		Bukkit.getScheduler().cancelTask(proc);
							Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "NSHC" + ChatColor.GRAY + " | " + ChatColor.AQUA + "Not Enough Players Game Not Started");
							time = 300;
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload");
							new PreGameStart(main);
							
				    	}else{
				    	GameStartProcedure s = new GameStartProcedure(main);
				        s.start();
				        main.started = false;
				        Bukkit.getScheduler().cancelTask(proc);
				        for(Player p: Bukkit.getOnlinePlayers()){
				        	p.playSound(p.getLocation(), Sound.AMBIENCE_THUNDER, 100, 100);
				        }
				    	}
			}else{
				for (Player p : Bukkit.getOnlinePlayers()) {
				
				}
				BarAPI.setMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "NSHC" + ChatColor.GRAY + " | " + ChatColor.AQUA + "Waiting For Players" + ChatColor.GREEN + " " + ConvertInteger(time));
				time--;
				if (pc == 25) {
					time = 0;
					Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "NSHC " + ChatColor.RESET + ChatColor.AQUA + "Game Started Max Players Reached!");
				}
				
			    }
			}
		}
				, 0, 20);
	}
	
	public String ConvertInteger(int h){
		
		pc = Bukkit.getOnlinePlayers().length;
		int mn;
		int sec;
		long s = h;
		
		int rem = (int)(s%3600);
		mn = rem/60;
		sec = rem%60;
		String mnStr = (mn<10 ? "0" : "")+mn;
		String secStr = (sec<10 ? "0" : "")+sec; 
		return mnStr+ ":"+secStr + "s";
	}
}
