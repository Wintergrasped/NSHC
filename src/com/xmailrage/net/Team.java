package com.xmailrage.net;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Team {

	

	private TeamTypes team;
	private int Health = 100;
	private List<Player> players = new ArrayList();
	private MainClass main;
	public Team(TeamTypes s, MainClass m){
		team = s;
		main = m;
	}
	
	public TeamTypes getTeamType(){
		return team;
	}
	
	public String getTeamName(){
		return team.toString();
	}
	
	public int amountAlive(){
		int x = players.size();
		for(Player pp: Bukkit.getOnlinePlayers()){
			if(players.contains(pp)){
			Death2Manager d = new Death2Manager(pp, main);
			if(d.isDead()){
				x--;
			}
			}
		}
		if(players.size() == 0){
			return 0;
		}
		return x;
	}
	
	
	//Health [Scoring]
	public int getHealth(){
		return Health;
	}
	
	public void setHealth(int x){
		Health = x;
	}
	
	public void addHealthPoints(int x){
		Health = Health + x;
	}
	
	public void removeHealthPoints(int x){
		Health = Health - x;
	}
	//Players
	public void addPlayer(Player p){
		if(players.size() < 5){
			if(this.main.Teams.inTeam(p) == false){
		players.add(p);
		p.sendMessage(ChatColor.AQUA + "You have been added to the " + this.getTeamName()+ "!");
			}else{
			p.sendMessage(ChatColor.AQUA + "You are already in a team!");
			}
		}else{
			p.sendMessage(ChatColor.RED + "That team is full! Please pick another team!");
		}
	}
	public void forceaddPlayer(Player p){
		if(players.size() < 5){
			TeamManager m = new TeamManager(main);
			m.getTeam(p).removePlayer(p);
		players.add(p);
		p.sendMessage(ChatColor.AQUA + "You have been added to the " + this.getTeamName()+ "!");
		}else{
			p.sendMessage(ChatColor.RED + "That team is full! Please pick another team!");
		}
	}
	
	public boolean canAdd(){
		if(players.size() < 5){
			return true;
			}else{
				return false;
			}
		
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public void removePlayer(Player p){
		players.remove(p);
	}
	
	public boolean containsPlayer(Player p){
		if(players.contains(p)){
			return true;
		}else{
			return false;
		}
	}
}

