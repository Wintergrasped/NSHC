package com.xmailrage.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;

import com.xmailrage.net.MainClass;

public class TeamManager {

	private MainClass main;

	
	
	
	public TeamManager(MainClass c){
		main = c;
	}
	
	public Team getAlpha(){
		return main.Alpha;
	}
	
	public Team getBravo(){
		return main.Bravo;
	}
	
	public Team getTitan(){
		return main.Titan;
	}
	
	public Team getTrionic(){
		return main.Trionic;
	}
	
	public Team getWolf(){
		return main.Wolf;
	}
	
	public boolean otherTeamsAlive(Team h){
		int x = 0;
		for(Team m: this.getTeams()){
			if(!m.equals(h)){
				if(m.amountAlive() <= 0){
					x++;
				}
			}
		}
		if(x == 4){
			return false;
		}
		return true;
	}
	
	public int teamsLeftInt(){
		int x = 0;
		
		for(Team m: this.getTeams()){
			
				if(m.amountAlive() <= 0){
					
				}else{
					x++;
				}
			
		}
		return x;
	}
	
	public Team getTeamLeft(){
	Team t = null;
		
		for(Team m: this.getTeams()){
			
				if(m.amountAlive() <= 0){
					
				}else{
					t = m;
				}
			
		}
		return t;
	}
	
	public boolean inTeam(Player p){
		for(Team ts: getTeams()){
			if(ts.getPlayers().contains(p)){
				return true;
			}
		}
		return false;
	}
	public Team getTeam(Player p){
		for(Team ts: getTeams()){
			if(ts.getPlayers().contains(p)){
				return ts;
			}
		}
		return null;
	}
	public void addRandom(Player p){
		Random ran = new Random();
		Team ts = getTeams().get(ran.nextInt(getTeams().size()));
			boolean x = ts.canAdd();
			if(x == true){
				ts.addPlayer(p);
			}else{
				addRandom(p);
			}
		
	}
	public List<Team> getTeams(){
		List<Team> m = new ArrayList();
		m.add(main.Alpha);
		m.add(main.Bravo);
		m.add(main.Titan);
		m.add(main.Trionic);
		m.add(main.Wolf);
		return m;
	}
	
	
	

	
	
}
