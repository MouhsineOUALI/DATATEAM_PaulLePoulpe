package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dataParser.ExtractPlayerData;

public class ExtractFromDB {
	static Statement stmt = null;
	static String sql="";
	public static List<String> teamsAll= new ArrayList<String>();
	public static List<String> getTeamsAll() throws SQLException, Exception{
		List<String> teams = new ArrayList<String>();
		stmt=ExtractPlayerData.getConnection().createStatement();
		sql="Select FulName from equipe";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
	         String a  = rs.getString("FulName");
	         if(!a.equals("")){
	         teams.add(a);
	         teamsAll.add(a);
	         }
		}
		return teams;
	}
	public static int getIdTeamByName(String team) throws SQLException, Exception{
		int id = 0 ;
		sql="Select id from equipe where FulName = '"+team+"'";
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			  id = rs.getInt("id");
		}
		return id;
	}
	
	public static float moyenPlayersAge(String team) throws SQLException, Exception{
		float moyenAge = 0;
		int age=0;
		int idTeam = getIdTeamByName(team);
		String birthDayDate;
		int nbrePlayers =0;
		sql="Select DATEBIRTH from joueurs where TEAM_ID = '"+idTeam+"'";
		ResultSet rs = stmt.executeQuery(sql);
		if(!rs.isBeforeFirst()){
			age = 0;
			nbrePlayers=1;
		}
		while(rs.next()){
			birthDayDate = rs.getString("DATEBIRTH");
			String[] b = birthDayDate.split("-");
			int date= Integer.valueOf(b[2]);
			date =100-date+15;
			age= age +date;
			nbrePlayers++;
		}
		if(nbrePlayers != 0)
		moyenAge = age/nbrePlayers;
		
		return moyenAge;
	}
	
	public static String correp(String team){
		String tm="";
		if(team.equals("Manchester United")) {tm = "Man United";}
		   else if(team.equals("Manchester City")){tm ="Man City";}
		   else if(team.equals("Leicester City")){tm = "Leicester";}
		   else if(team.equals("West Bromwich Albion")){tm = "West Brom";}
		   else if(team.equals("Norwich City")){tm = "Norwich";}
		   else if(team.equals("Newcastle City")){tm = "Newcastle";}
		   else if(team.equals("Swansea City")){tm = "Swansea";}
		   else if(team.equals("Tottenham Hotspur")){tm = "Tottenham";}
		   else if(team.equals("West Ham United")){tm = "West Ham";}
		   else{
			   ;
		   }
		return tm;
	}
	
	public static int [] nbreWinHome(String team,String team2,boolean isHome,String annee) throws SQLException, Exception{
		int nbreW =0;
		int nbreL =0;
		int nbreE =0;
		int nbMatch = 0;
		int [] tab = new int[2] ;
		int sid1,sid2;
		if(team.equals("Manchester United")) {team = "Man United";}
		   else if(team.equals("Manchester City")){team ="Man City";}
		   else if(team.equals("Leicester City")){team = "Leicester";}
		   else if(team.equals("West Bromwich Albion")){team = "West Brom";}
		   else if(team.equals("Norwich City")){team = "Norwich";}
		   else if(team.equals("Newcastle City")){team = "Newcastle";}
		   else if(team.equals("Swansea City")){team = "Swansea";}
		   else if(team.equals("Stoke City")){team = "Stoke";}
		   else if(team.equals("Tottenham Hotspur")){team = "Tottenham";}
		   else if(team.equals("West Ham United")){team = "West Ham";}
		   else{
			   ;
		   }
		//stmt=ExtractPlayerData.getConnection().createStatement();
		if(!annee.equals("2015")){
		sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team+"' and result2015.A_TEAM = '"+team2+"' and result2015.DATE ='"+annee+"'";
		}
		else{
			sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team+"' and result2015.DATE ='"+annee+"'";
		}
		ResultSet rs = stmt.executeQuery(sql);
		int id = 0;
		while(rs.next()){
			id = rs.getInt("ID");
		}
		if(isHome){
		sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team+"' and result2015.DATE ='"+annee+"'"
				+ "and result2015.ID < '"+id+"'";
		rs = stmt.executeQuery(sql);
		if(!rs.isBeforeFirst()){
			nbreW = 0;
			nbMatch=1;
		}
		while(rs.next()){
			  String score = rs.getString("SCORE");
			  String [] result = score.split("-");
			  result[0] = result[0].replace(" ", "");
			  result[1] = result[1].replace(" ", "");
			  sid1 = Integer.valueOf(result[0]);
			  sid2 = Integer.valueOf(result[1]);
			  if(sid1>sid2){
				  nbreW++;
			  }else if(sid1 == sid2){
				  nbreE++;
			  }
			  else{
				  nbreL++;
			  }
			  nbMatch++;
		}
		}
		else{
			sql="Select * from result2015 WHERE result2015.A_TEAM = '"+team2+"' and result2015.DATE ='"+annee+"'"
					+ "and result2015.ID < '"+id+"'";
			rs = stmt.executeQuery(sql);
			
			if(!rs.isBeforeFirst()){
				nbreW = 0;
				nbMatch=1;
			}
			while(rs.next()){
				  String score = rs.getString("SCORE");
				  String [] result = score.split("-");
				  result[0] = result[0].replace(" ", "");
				  result[1] = result[1].replace(" ", "");
				  sid1 = Integer.valueOf(result[0]);
				  sid2 = Integer.valueOf(result[1]);
				  if(sid1>sid2){
					  nbreL++;
				  }else if(sid1 == sid2){
					  nbreE++;
				  }
				  else{
					  nbreW++;
				  }
				  nbMatch++;
			}
		}
		
		tab[0] = nbreW;
		tab[1] = nbMatch;
		return tab;
	} 
	
	public static boolean isLastMatchWin(String team) throws SQLException, Exception{
		boolean b=false;
		List<String> scores = new ArrayList<String>();
		if(team.equals("Manchester United")) {team = "Man United";}
		   else if(team.equals("Manchester City")){team ="Man City";}
		   else if(team.equals("Leicester City")){team = "Leicester";}
		   else if(team.equals("West Bromwich Albion")){team = "West Brom";}
		   else if(team.equals("Norwich City")){team = "Norwich";}
		   else if(team.equals("Newcastle City")){team = "Newcastle";}
		   else if(team.equals("Stoke City")){team = "Stoke";}
		   else if(team.equals("Swansea City")){team = "Swansea";}
		   else if(team.equals("Tottenham Hotspur")){team = "Tottenham";}
		   else if(team.equals("West Ham United")){team = "West Ham";}
		   else{
			   ;
		   }
			sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team+"' or result2015.A_TEAM = '"+team+"'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String score = rs.getString("SCORE");
				String home = rs.getString("H_TEAM");
				String away = rs.getString("A_TEAM");
				scores.add(home+"/"+away+"/"+score);
			}
			String result ="" ;
			if(!scores.isEmpty()){
				result = scores.get(scores.size()-1);
				String [] results = result.split("/");
				String [] result2 = results[2].split("-");
				
				result2[0] = result2[0].replace(" ", "");//home res
				int resH= Integer.valueOf(result2[0]);
				result2[1] = result2[1].replace(" ", "");//away res
				int resA=Integer.valueOf(result2[0]);
				if(results[0].equals(team)){
					
					if(resH >= resA){
						b = true;
					}
					else{
						b = false;
					}
				}
				else{
					if(resH <= resA){
						b = true;
					}
					else{
						b = false;
					}
				}
			}
			
		return b;
	} 
	
	public static int moyenClassement(String team,String saison) throws SQLException, Exception{
		int rank=0;
		sql="Select * from classement WHERE classement.TEAM = '"+team+"' and classement.ANNEE ='"+saison+"'";
		ResultSet rs = stmt.executeQuery(sql);
		if (!rs.isBeforeFirst()) {
			rank =0;
		}
		while(rs.next()){
			rank =rs.getInt("RANK");
		}
		return rank;
	}
	
	public static float  [] forcAttaqueHome_homeTeam(String team,String team2,String annee) throws SQLException, Exception{
		int nbBut = 0;
		int sid1 = 0,sid2 = 0;
		int nbMatch = 0;
		float res [] = new float[3] ;
		if(team.equals("Manchester United")) {team = "Man United";}
		   else if(team.equals("Manchester City")){team ="Man City";}
		   else if(team.equals("Leicester City")){team = "Leicester";}
		   else if(team.equals("West Bromwich Albion")){team = "West Brom";}
		   else if(team.equals("Norwich City")){team = "Norwich";}
		   else if(team.equals("Newcastle City")){team = "Newcastle";}
		   else if(team.equals("Swansea City")){team = "Swansea";}
		   else if(team.equals("Stoke City")){team = "Stoke";}
		   else if(team.equals("Tottenham Hotspur")){team = "Tottenham";}
		   else if(team.equals("West Ham United")){team = "West Ham";}
		   else{
			   ;
		   }
		//stmt=ExtractPlayerData.getConnection().createStatement();
		if(!annee.equals("2015")){
		sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team+"' and result2015.A_TEAM = '"+team2+"' and result2015.DATE ='"+annee+"'";
		}
		else{
			sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team+"' and result2015.DATE ='"+annee+"'";
		}
		ResultSet rs = stmt.executeQuery(sql);
		int id = 0;
		while(rs.next()){
			id = rs.getInt("ID");
			String score = rs.getString("SCORE");
			String [] result = score.split("-");
			result[0] = result[0].replace(" ", "");
			result[1] = result[1].replace(" ", "");
			sid1 = Integer.valueOf(result[0]);
			sid2 = Integer.valueOf(result[1]);
			if (sid1>sid2) {
				res[2] = 1;
			}else if(sid1<sid2) {
				res[2] = -1;
			}
			else {
				res[2] = 0;
			}
		}
		sid1=0;
		sid2=0;
		sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team+"' and result2015.DATE ='"+annee+"'"
				+ "and result2015.ID < '"+id+"'";
		rs = stmt.executeQuery(sql);
		
		int nbWinTeam = 0;
		while(rs.next()){
			String score = rs.getString("SCORE");
			String [] result = score.split("-");
			result[0] = result[0].replace(" ", "");
			result[1] = result[1].replace(" ", "");
			sid1 = Integer.valueOf(result[0]);
			sid2 = Integer.valueOf(result[1]);
			nbBut = sid1+sid2;
			nbMatch++;
			nbWinTeam = nbWinTeam+sid1;
		}
		if(nbMatch != 0){
			res[0]= (float) nbWinTeam/nbMatch;
			}
		sql="Select * from result2015 WHERE result2015.A_TEAM = '"+team2+"' and result2015.DATE ='"+annee+"'"
				+ "and result2015.ID < '"+id+"'";
		rs = stmt.executeQuery(sql);
		nbMatch =0;
		nbBut = 0;
		int nbWinTeam2 = 0;
		while(rs.next()){
			String score = rs.getString("SCORE");
			String [] result = score.split("-");
			result[0] = result[0].replace(" ", "");
			result[1] = result[1].replace(" ", "");
			sid1 = Integer.valueOf(result[0]);
			sid2 = Integer.valueOf(result[1]);
			nbBut = sid1+sid2;
			nbMatch++;
			nbWinTeam2 = nbWinTeam2+sid2;
		}
		if(nbMatch != 0){
			res[1]= (float) nbWinTeam2/nbMatch;
			}
		return res;
	} 
	public static int lastFiveMatchBut(String team) throws Exception {
		int nbBut = 0;
		List<Integer> butsAll = new ArrayList<Integer>();
		if(team.equals("Manchester United")) {team = "Man United";}
		   else if(team.equals("Manchester City")){team ="Man City";}
		   else if(team.equals("Leicester City")){team = "Leicester";}
		   else if(team.equals("West Bromwich Albion")){team = "West Brom";}
		   else if(team.equals("Norwich City")){team = "Norwich";}
		   else if(team.equals("Newcastle City")){team = "Newcastle";}
		   else if(team.equals("Swansea City")){team = "Swansea";}
		   else if(team.equals("Stoke City")){team = "Stoke";}
		   else if(team.equals("Tottenham Hotspur")){team = "Tottenham";}
		   else if(team.equals("West Ham United")){team = "West Ham";}
		   else{
			   ;
		   }
		sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team+"'";
		ResultSet rs = stmt.executeQuery(sql);
		int i = 0;
		if(!rs.isBeforeFirst()){
			nbBut=0;
		}
		while(rs.next()){
			String score = rs.getString("SCORE");
			String hTeam = rs.getString("H_TEAM");
			String res[] = score.split("-");
			res[0] = res[0].replace(" ", "");//home res
			int resH= Integer.valueOf(res[0]);
			res[1] = res[1].replace(" ", "");//away res
			int resA=Integer.valueOf(res[1]);
			if(hTeam.equals(team)){
				butsAll.add(resH);
			}
			i++;
		}
		
		for (int j = 0; j < 5; j++) {
			nbBut = nbBut + butsAll.get(butsAll.size()-1-j);
			
		}
		return nbBut;
	}
	
	public static int lastFiveMatchButAway(String team1,String team , String annee) throws Exception {
		int nbBut = 0;
		List<Integer> butsAll = new ArrayList<Integer>();
		if(team.equals("Manchester United")) {team = "Man United";}
		else if(team.equals("Manchester City")){team ="Man City";}
		else if(team.equals("Leicester City")){team = "Leicester";}
		else if(team.equals("West Bromwich Albion")){team = "West Brom";}
		else if(team.equals("Norwich City")){team = "Norwich";}
		else if(team.equals("Newcastle City")){team = "Newcastle";}
		 else if(team.equals("Stoke City")){team = "Stoke";}
		else if(team.equals("Swansea City")){team = "Swansea";}
		else if(team.equals("Tottenham Hotspur")){team = "Tottenham";}
		else if(team.equals("West Ham United")){team = "West Ham";}
		else{
			;
		}
		if(!annee.equals("2015")){
		sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team1+"' and result2015.A_TEAM = '"+team+"' and result2015.DATE ='"+annee+"'";
		}
		else{
			sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team+"' and result2015.DATE ='"+annee+"'";
		}
		ResultSet rs = stmt.executeQuery(sql);
		int id = 0;
		while(rs.next()){
			id = rs.getInt("ID");
		}
		if(!rs.isBeforeFirst()){
			nbBut=0;
		}
		
		sql="Select * from result2015 WHERE result2015.ID <'"+id+"' and result2015.DATE ='"+annee+"'";
		rs = stmt.executeQuery(sql);
		
		while(rs.next()){
			String score = rs.getString("SCORE");
			String hTeam = rs.getString("H_TEAM");
			String res[] = score.split("-");
			res[0] = res[0].replace(" ", "");//away res
			int resh=Integer.valueOf(res[0]);
			if(hTeam.equals(team1)){
				butsAll.add(resh);
			}
		}
		if(!butsAll.isEmpty() && butsAll.size() >4){
		for (int j = 0; j < 5; j++) {
			nbBut = nbBut + butsAll.get(butsAll.size()-1-j);
		}
		}
		return nbBut;
	}
	public static float  [] forcDeffHome_homeTeam(String team,String team2,String annee) throws SQLException, Exception{
		int nbBut = 0;
		int nbMatch = 0;
		int sid1 = 0,sid2 = 0;
		float res [] = new float[2];
		if(team.equals("Manchester United")) {team = "Man United";}
		   else if(team.equals("Manchester City")){team ="Man City";}
		   else if(team.equals("Leicester City")){team = "Leicester";}
		   else if(team.equals("West Bromwich Albion")){team = "West Brom";}
		   else if(team.equals("Norwich City")){team = "Norwich";}
		   else if(team.equals("Newcastle City")){team = "Newcastle";}
		   else if(team.equals("Swansea City")){team = "Swansea";}
		   else if(team.equals("Stoke City")){team = "Stoke";}
		   else if(team.equals("Tottenham Hotspur")){team = "Tottenham";}
		   else if(team.equals("West Ham United")){team = "West Ham";}
		   else{
			   ;
		   }
		//stmt=ExtractPlayerData.getConnection().createStatement();
		if(!annee.equals("2015")){
		sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team+"' and result2015.A_TEAM = '"+team2+"' and result2015.DATE ='"+annee+"'";
		}
		else{
			sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team+"' and result2015.DATE ='"+annee+"'";
		}
		ResultSet rs = stmt.executeQuery(sql);
		int id = 0;
		
		while(rs.next()){
			id = rs.getInt("ID");
		}
		sql="Select * from result2015 WHERE result2015.H_TEAM = '"+team+"' and result2015.DATE ='"+annee+"'"
				+ "and result2015.ID < '"+id+"'";
		rs = stmt.executeQuery(sql);
		int nbWinTeam = 0;
		while(rs.next()){
			String score = rs.getString("SCORE");
			String [] result = score.split("-");
			result[0] = result[0].replace(" ", "");
			result[1] = result[1].replace(" ", "");
			sid1 = Integer.valueOf(result[0]);
			sid2 = Integer.valueOf(result[1]);
			nbBut = sid1+sid2;
			nbMatch++;
			nbWinTeam = nbWinTeam+sid2;
		}
		if(nbMatch != 0){
			res[0]= (float) nbWinTeam/nbMatch;
			}
		sql="Select * from result2015 WHERE result2015.A_TEAM = '"+team2+"' and result2015.DATE ='"+annee+"'"
				+ "and result2015.ID < '"+id+"'";
		rs = stmt.executeQuery(sql);
		nbBut = 0;
		nbMatch=0;
		int nbWinTeam2 = 0;
		while(rs.next()){
			String score = rs.getString("SCORE");
			String [] result = score.split("-");
			result[0] = result[0].replace(" ", "");
			result[1] = result[1].replace(" ", "");
			sid1 = Integer.valueOf(result[0]);
			sid2 = Integer.valueOf(result[1]);
			nbBut = sid1+sid2;
			nbMatch++;
			nbWinTeam2 = nbWinTeam2+sid1;
		}
		if(nbBut != 0){
			res[1]= (float) nbWinTeam2/nbMatch;
			}
		return res;
	} 
	
}
