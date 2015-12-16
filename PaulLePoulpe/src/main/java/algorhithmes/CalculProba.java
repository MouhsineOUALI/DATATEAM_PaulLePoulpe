package algorhithmes;

import java.sql.SQLException;

import dao.ExtractFromDB;

public class CalculProba {
	
	static ExtractFromDB exDB = new ExtractFromDB();
	public static float probOfWinHome(String team,String team2,String annee) throws SQLException, Exception{
		float proba = 0 ;
		int [] tabRes = new int[2];
		tabRes = exDB.nbreWinHome(team,team2,true,annee);
		proba = (float) tabRes[0]/tabRes[1];
		return proba;
	}
	public static float probOfWinAway(String team,String team2,String annee) throws SQLException, Exception{
		float proba = 0 ;
		int [] tabRes = new int[2];
		tabRes = exDB.nbreWinHome(team,team2,false,annee);
		proba = (float) tabRes[0]/tabRes[1];
		return proba;
	}
	public static float [] forceAttaqueHome_Home(String team,String team2,String annee) throws SQLException, Exception{
		float  proba [] = new float[3];
		proba = exDB.forcAttaqueHome_homeTeam(team, team2, annee);
		return proba;
	}
	public static float [] forceDeffHome_Home(String team,String team2,String annee) throws SQLException, Exception{
		float  proba [] = new float[2];
		proba = exDB.forcDeffHome_homeTeam(team, team2, annee);
		return proba;
	}
	
	public static int moyenClass(String team,String saison) throws SQLException, Exception{
		int rank = 0 ;
		rank = exDB.moyenClassement(team,saison);
		return rank;
	}
	public static float ageMoyen(String team) throws SQLException, Exception{
		float proba = 0 ;
		proba = exDB.moyenPlayersAge(team);
		return proba;
	}
	public static boolean isLastMatchWin(String team) throws SQLException, Exception{
		boolean b = false ;
		b = exDB.isLastMatchWin(team);
		return b;
	}
	public int lastFiveMatch(String team) throws Exception {
		int nbMatch = 0;
		nbMatch = exDB.lastFiveMatchBut(team);
		return nbMatch;
	}
	public int lastFiveMatchAway(String team1,String team,String annee) throws Exception {
		int nbMatch = 0;
		nbMatch = exDB.lastFiveMatchButAway(team1,team,annee);
		return nbMatch;
	}
	
}
