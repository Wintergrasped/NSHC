package com.xmailrage.net;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SignManager {

	
	private Player p;
	private Location loc;
	private MainClass main;
	public SignManager(Player pp, Location l, MainClass m){
		loc = l;
		p = pp;
		main = m;
	}
	
	public void addSign(){
		main.getConfig().set("DeathS." + p.getName() + ".world",loc.getWorld().getName());
		main.getConfig().set("DeathS." + p.getName() + ".x", loc.getX());
		main.getConfig().set("DeathS." + p.getName() + ".y", loc.getY());
		main.getConfig().set("DeathS." + p.getName() + ".z", loc.getZ());
		main.saveConfig();
		if(main.getConfig().contains("DeathL")){
			List<String> mk = main.getConfig().getStringList("DeathL");
			mk.remove(p.getName());
			mk.add(p.getName());
			main.getConfig().set("DeathL", mk);
			main.saveConfig();
		}else{
			List<String> mk = new ArrayList();
			mk.add(p.getName());
			main.getConfig().set("DeathL", mk);
			main.saveConfig();
		}
	}
	
	public void remove(){
		main.getConfig().set("DeathS." + p.getName(), null);
		main.saveConfig();
		if(main.getConfig().contains("DeathL")){
			List<String> mk = main.getConfig().getStringList("DeathL");
			mk.remove(p.getName());
		
			main.getConfig().set("DeathL", mk);
			main.saveConfig();
		
	}
	}
}
