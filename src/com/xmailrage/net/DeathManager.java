package com.xmailrage.net;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;



import com.xmailrage.procedures.EndGameProcedure;

public class DeathManager {

	
	private Player p;
	private MainClass main;
	private FileConfiguration config;
	public static List<String> dead = new ArrayList();
	
	public DeathManager(Player player, MainClass m){
		p = player;
		main = m;
		config = main.getConfig();
	}
	
	public void setDead(Boolean a){
		if(a == true){
			//Invis
			for(Player pp: Bukkit.getOnlinePlayers()){
				if(!pp.equals(p)){
					pp.hidePlayer(p);
				
					config.set("IsDead." + p.getName(), a);
					dead.add(pp.getName());
				}
			}
			main.getConfig().set("DeathP." + p.getName(), true);
			main.saveConfig();
		}else if(a == false){
			//UnInvis
			for(Player pp: Bukkit.getOnlinePlayers()){
				if(!pp.equals(p)){
					pp.showPlayer(p);
					config.set("IsDead." + p.getName(), a);
			
				}
			}
			main.getConfig().set("DeathP." + p.getName(), false);
			main.saveConfig();
		}
		
		
	}
	
	public boolean isDead(){
		if(main.getConfig().contains("DeathP." + p.getName())){
			boolean a = main.getConfig().getBoolean("DeathP." + p.getName());
		
			return a;
		}else{
			return false;
		}
	}
}
