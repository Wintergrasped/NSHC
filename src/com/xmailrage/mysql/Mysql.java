package com.xmailrage.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

import com.xmailrage.net.MainClass;

public class Mysql {

	
	

	
	public Mysql(MainClass m, String Host, int porta, String databasea, String usera, String passa){
		main = m;
		host = Host;
		port = porta;
		database = databasea;
		user = usera;
		pass = passa;
		MySQL = new MySQLBase(main, host, String.valueOf(port), database, user, pass);
	}
	MainClass main = null;
	String host = "";
	String database = "";
	String user = "";
	String pass = "";
	int port = 0;
	MySQLBase MySQL = null;
	Connection c = null;
	public void openConnection(){
		c = MySQL.openConnection();
		
	}
	
	public void closeConnection(){
		MySQL.closeConnection();
	}
	
	public int getTokens(String Player){
		
		try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `NSHC_Points` WHERE  `name`='" + Player + "';");
		
		res.next();
		money = res.getInt("points");
		return money;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
		
	}
	
	public void setTokens(String Player, int a){
		
		try {
			Statement statement = c.createStatement();
		
		
	
		
			statement.executeUpdate("UPDATE `" + this.database + "`.`NSHC_Points` SET `points`=" + a + " WHERE  `name`='" + Player + "';");
			
		
		
		
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void addGameTotal(){
		try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getInt("TotalGames");
		money++;
		statement.executeUpdate("UPDATE `" + this.database + "`.`Game` SET `TotalGames`=" + money + " WHERE  `Game`='Breacher';");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getGameTag(){
		try {
			Statement statement = c.createStatement();
		
		String money = "";
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getString("GameTag");
		return money;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
	
	
   public void setupGameTable(String table){
		
		
		try {
			Statement statement = c.createStatement();
		
		
		
			
			statement.executeUpdate("CREATE TABLE " + table + " (`Game` TEXT,`GameTag` TEXT,`TotalGames` BIGINT,`MinPlayers` INT,`StartTimeSeconds` INT ,`GameTimeSeconds` INT, `SecureSeconds` INT)");

			statement.executeUpdate("INSERT INTO " + table + " (`Game`, `GameTag`, `TotalGames`, `MinPlayers`, `StartTimeSeconds`, `GameTimeSeconds`, `SecureSeconds`) VALUES ('Breacher', '&7[&6Breach&7]', '0', '5', '120', '600', '120');");
		
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
   public int getSecureSeconds(){
		try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getInt("SecureSeconds");
		return money;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
   }
   
   public int getStartSeconds(){
	   try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getInt("StartTimeSeconds");
		return money;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
   }
   
   public int getGameSeconds(){
	   try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getInt("GameTimeSeconds");
		return money;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
   }
   
   public int getMinPlayers(){
	   try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getInt("MinPlayers");
		return money;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
   }
   
   public int getTotalGames(){
		try {
			Statement statement = c.createStatement();
		
		int money = 0;
		ResultSet res;
		
			res = statement.executeQuery("SELECT * FROM `Game` WHERE  `Game`='Breacher';");
		
		res.next();
		money = res.getInt("TotalGames");
		return money;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
   }
	public boolean isSetup(String player){
	
			
			try {
				Statement statement = c.createStatement();
			
			
			ResultSet res;
		    	statement.executeUpdate("USE " + this.database); 
				res = statement.executeQuery("SELECT * FROM players  WHERE PlayerName='" + player + "';");
				
			
			
			
			return res.next();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		
		
	}
	
	public void setupPlayer(String player){
		try {
			Statement statement = c.createStatement();
		
		
		
			statement.executeUpdate("INSERT INTO Players (`PlayerName`, `Money`) VALUES ('" + player + "', '0');");
			
		
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setupTable(String table){
		
		
		try {
			Statement statement = c.createStatement();
		
		
		
			
			statement.executeUpdate("CREATE TABLE " + table + " (`PlayerName` TEXT,`Money` BIGINT )");
			
		
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean tableExist(String tablename){

		try {
			Statement statement = c.createStatement();
		
		
		 
		
			ResultSet res = statement.executeQuery("SELECT count(*) FROM information_schema.TABLES WHERE (TABLE_SCHEMA = '" + this.database + "') AND (TABLE_NAME = '" + tablename + "');");
		
			DatabaseMetaData dbm = c.getMetaData();
			ResultSet tables = dbm.getTables(null, null, tablename, null);
		
		
			if(tables.next()){
				return true;
			}else{
				return false;
			}
			
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
