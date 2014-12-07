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

public class Death2Manager {

	
	private Player p;
	private MainClass main;
	private FileConfiguration config;
	public static List<String> dead = new ArrayList();
	
	public Death2Manager(Player player, MainClass m){
		p = player;
		main = m;
		config = main.getConfig();
	}
	
	public void logoutdeath (Player p){
		dead.add(p.getName());
		this.checkLastPlayers();
	}
	
	public void setDead(Boolean a){
		if(a == true){
			//Invis
			for(Player pp: Bukkit.getOnlinePlayers()){
				if(!pp.equals(p)){
					pp.hidePlayer(p);
				
					config.set("IsDead2." + p.getName(), a);
					dead.add(pp.getName());
				}
			}
			main.getConfig().set("DeathP2." + p.getName(), true);
			main.saveConfig();
		}else if(a == false){
			//UnInvis
			for(Player pp: Bukkit.getOnlinePlayers()){
				if(!pp.equals(p)){
					pp.showPlayer(p);
					config.set("IsDead2." + p.getName(), a);
			
				}
			}
			main.getConfig().set("DeathP2." + p.getName(), false);
			main.saveConfig();
		}
		
		DeathManager m = new DeathManager(p, main);
		m.setDead(true);
	 checkLastPlayers();
	}
	
	public void checkLastPlayers(){
		main.logger.info("CheckLast");

		TeamManager m = new TeamManager(main);

		if(m.teamsLeftInt() == 1){
			Team mem = m.getTeamLeft();
			Bukkit.broadcastMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "NSHC" + ChatColor.GRAY + "| " + ChatColor.YELLOW + mem.getTeamName() + " has won the game and has been awarded a token!");
			  for (Player tps : mem.getPlayers()) {
		        	
              	
              	int x = main.MySQL.getTokens(tps.getName());
              	x++;
              	main.MySQL.setTokens(tps.getName(), x);
				tps.sendMessage(ChatColor.AQUA + "You have been awarded a token!");
          
	        }
			EndGameProcedure n = new EndGameProcedure(main);
			n.start();
		
		}
		
	}
	public boolean isDead(){
		if(main.getConfig().contains("DeathP2." + p.getName())){
			boolean a = main.getConfig().getBoolean("DeathP2." + p.getName());
		
			return a;
		}else{
			return false;
		}
	}
}
