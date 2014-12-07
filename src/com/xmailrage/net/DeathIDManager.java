package com.xmailrage.net;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DeathIDManager {

	
	private MainClass main;
	private YamlConfiguration config;
	public DeathIDManager(Player play, MainClass maina){
		main = maina;
		config = main.yaml;
	}
	
	public void reset(){
		config.set("Deaths", null);
		config.set("Items", null);
		this.saveConfig();
	}
	
	public void setPlayerDeaths(Player p, int a){
			config.set("Deaths." + p.getName(), a);
			config.set("Items." + p.getName(), null);
			saveConfig();
			List<String> sa = config.getStringList("Items." + p.getName());
			ItemStack i = new ItemStack(Material.RED_ROSE, 1);
			sa.add(this.getItemStack(p).getTypeId() + ":" + this.getItemStack(p).getAmount());
			sa.add(i.getTypeId() + ":" + i.getAmount());
			sa.add(i.getTypeId() + ":" + i.getAmount());
			saveConfig();
		
	}
	
	public int getPlayerDeaths(Player p){
		if(!config.contains("Deaths." + p.getName())){
			return 0;
		}else{
			return config.getInt("Deaths." + p.getName());
		}
	}
	
	public ItemStack getItemStack(Player p){
		ItemStack item = null;
		int x = 0;
		if(!config.contains("Deaths." + p.getName())){
			config.set("Deaths." + p.getName(), 0);
			saveConfig();
			x++;
			item = new ItemStack(Material.IRON_INGOT, 1);
		}else{
			x = config.getInt("Deaths." + p.getName());
			x++;
			if(x == 2){
				item = new ItemStack(Material.IRON_INGOT, 1);
			}
			if(x == 3){
				item = new ItemStack(Material.IRON_BLOCK, 1);
			}
			if(x == 4){
				item = new ItemStack(Material.GOLD_INGOT, 1);
			}
			if(x == 5){
				item = new ItemStack(Material.GOLD_BLOCK, 1);
			}
			if(x == 6){
				item = new ItemStack(Material.DIAMOND, 1);
			}
			if(x == 7){
				item = new ItemStack(Material.DIAMOND_BLOCK, 1);
			}
			if(x == 8){
				item = new ItemStack(Material.EMERALD, 1);
			}
			if(x == 9){
				item = new ItemStack(Material.EMERALD_BLOCK, 1);
			}
			if(x == 10){
				item = new ItemStack(Material.BEDROCK, 1);
			}
		}
		return item;
	}
	
	public ItemStack[] getItemsLefts(Player p){
		if(config.contains("Deaths." + p.getName())){
			List<String> items = config.getStringList("Items." + p.getName());
			int d = this.getPlayerDeaths(p);
			int total = 1;
			int itema = 0;
			
			for(String s: items){
				
				ItemStack i = new ItemStack(Material.valueOf(s.split(":")[0]));
				i.setAmount(Integer.valueOf(s.split(":")[1]));
				if(i.equals(getItemStack(p))){
					itema++;
				}
			}
			int x = total;
			x = x-itema;
		
			ItemStack[] g = new ItemStack[x];
		
			int xa = 0;
			if(itema != 1){
				g[xa] = getItemStack(p);
				xa++;
			}
			return g;
		}
		return null;
		
	}
	
	public void addItem(Player p, ItemStack s){
		if(config.contains("Items." + p.getName())){
			List<String> sa = config.getStringList("Items." + p.getName());
			sa.add(s.getType() + ":" + s.getAmount());
		config.set("Items." + p.getName(),sa);
		s.setAmount(0);
		
		saveConfig();
		}else{
			List<String> sa = new ArrayList();
			sa.add(s.getType() + ":" + s.getAmount());
		config.set("Items." + p.getName(), sa);
		saveConfig();
			
		}
	}
	
	public void saveConfig(){
		try {
			config.save(new File(main.getDataFolder(), "deaths.yml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean alreadycontains(ItemStack s, Player p){
		for(ItemStack so: this.getItemsLefts(p)){
			if(s.getType().equals(so.getType())){
				return false;
			}
		}
		return true;
	}
}
