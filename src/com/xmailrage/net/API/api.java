package com.xmailrage.net.API;

import org.bukkit.entity.Player;

import com.xmailrage.net.MainClass;
import com.xmailrage.net.Team;
import com.xmailrage.net.TeamManager;

public class api {
	
	public TeamManager Team;
	
	public boolean isinprogress () {
		return MainClass.inprogress;
	}
	
	public String getTeam(Player p) {
		 String teamname = this.Team.getTeam(p).getTeamName();
		 return teamname;
	}

}
